package fitness.app.Locker;

import fitness.app.Objects.Databases.ItemsDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LockerModel {
    private final ItemsDB itemsDB;
    private final String username;
    private List<Item> catalogItems;
    private List<Integer> ownedItemIds;
    private List<Integer> equippedItemIds;
    private Map<Integer, Item> itemsCache;

    public LockerModel(String username) {
        this.username = username;
        this.itemsDB = new ItemsDB();
        this.catalogItems = new ArrayList<>();
        this.ownedItemIds = new ArrayList<>();
        this.equippedItemIds = new ArrayList<>();
        this.itemsCache = new HashMap<>();

        loadData();
    }

    private void loadData() {
        try {
            // Load all catalog items
            loadCatalogItems();

            // Load user's owned items
            ownedItemIds = itemsDB.getUserItems(username);

            // Load user's equipped items
            equippedItemIds = itemsDB.getEquippedItems(username);
        } catch (SQLException e) {
            System.out.println("Error loading locker data: " + e.getMessage());
        }
    }

    private void loadCatalogItems() throws SQLException {
        catalogItems.clear();
        itemsCache.clear();

        ResultSet rs = itemsDB.getAllItems();
        while (rs.next()) {
            Item item = new Item(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("type"),
                    rs.getInt("price"),
                    rs.getString("icon")
            );

            catalogItems.add(item);
            itemsCache.put(item.getId(), item);
        }
        rs.close();
    }

    // Get all available items from the catalog
    public List<Item> getCatalogItems() {
        return new ArrayList<>(catalogItems);
    }

    // Get items the user owns
    public List<Item> getOwnedItems() {
        List<Item> ownedItems = new ArrayList<>();

        for (Integer itemId : ownedItemIds) {
            Item item = itemsCache.get(itemId);
            if (item != null) {
                ownedItems.add(item);
            }
        }

        return ownedItems;
    }

    // Get items the user has equipped
    public List<Item> getEquippedItems() {
        List<Item> equipped = new ArrayList<>();

        for (Integer itemId : equippedItemIds) {
            Item item = itemsCache.get(itemId);
            if (item != null) {
                equipped.add(item);
            }
        }

        return equipped;
    }

    // Check if a user has a specific item
    public boolean hasItem(int itemId) {
        return ownedItemIds.contains(itemId);
    }

    // Check if a user has equipped a specific item
    public boolean hasEquipped(int itemId) {
        return equippedItemIds.contains(itemId);
    }

    // Purchase an item for the user
    public boolean purchaseItem(int itemId) {
        try {
            if (hasItem(itemId)) {
                return false; // Already owns this item
            }

            itemsDB.giveItemToUser(username, itemId);
            ownedItemIds.add(itemId);
            return true;
        } catch (SQLException e) {
            System.out.println("Error purchasing item: " + e.getMessage());
            return false;
        }
    }

    // Equip an item
    public boolean equipItem(int itemId) {
        try {
            if (!hasItem(itemId)) {
                return false; // Doesn't own this item
            }

            if (hasEquipped(itemId)) {
                return true; // Already equipped
            }

            itemsDB.equipItem(username, itemId);
            equippedItemIds.add(itemId);
            return true;
        } catch (SQLException e) {
            System.out.println("Error equipping item: " + e.getMessage());
            return false;
        }
    }

    // Unequip an item
    public boolean unequipItem(int itemId) {
        try {
            if (!hasEquipped(itemId)) {
                return false; // Not equipped
            }

            itemsDB.unequipItem(username, itemId);
            equippedItemIds.remove(Integer.valueOf(itemId));
            return true;
        } catch (SQLException e) {
            System.out.println("Error unequipping item: " + e.getMessage());
            return false;
        }
    }

    // Refresh data from the database
    public void refresh() {
        loadData();
    }

    // Inner class to represent an Item
    public static class Item {
        private final int id;
        private final String name;
        private final String description;
        private final String type;
        private final int price;
        private final String icon;

        public Item(int id, String name, String description, String type, int price, String icon) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.type = type;
            this.price = price;
            this.icon = icon;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getType() {
            return type;
        }

        public int getPrice() {
            return price;
        }

        public String getIcon() {
            return icon;
        }

        //For debug
        @Override
        public String toString() {
            return name + " (" + type + ")";
        }
    }
}
