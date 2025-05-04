package Application.BonusFeatures.CurrencyShop;

import Application.Databases.ItemsDB;
import Application.Databases.DatabaseManager;
import Application.Utility.Objects.ShoppingCart;
import Application.Utility.Objects.CartItem;
import Application.Utility.Objects.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class currencyShopModel {
    private Account currentUser;
    private final ShoppingCart cart;
    private List<Item> items;

    public currencyShopModel() {
        this.cart = new ShoppingCart();
        this.items = new ArrayList<>();

        try {
            refreshItems();
        } catch (SQLException e) {
            System.err.println("Error loading shop items: " + e.getMessage());
        }
    }

    public void setCurrentUser(Account user) {
        this.currentUser = user;
    }

    public Account getCurrentUser() {
        return currentUser;
    }

    public void refreshItems() throws SQLException {
        ItemsDB itemsDB = DatabaseManager.getItemsDB();
        List<Item> dbItems = new ArrayList<>();

        // The ItemsDB returns a ResultSet, not a List, so we need to process it differently
        ResultSet rs = itemsDB.getAllItems();

        while (rs.next()) {
            Item item = new Item(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("price"),
                    rs.getString("description"),
                    rs.getString("icon")
            );
            dbItems.add(item);
        }

        // Close the ResultSet to avoid memory leaks
        rs.close();

        this.items = dbItems;
    }

    public List<Item> getAllItems() {
        return new ArrayList<>(items);
    }

    public void addToCart(int rocketBucks, double price) {
        cart.addItem(new CartItem(rocketBucks, price));
    }

    public void removeFromCart(int index) {
        cart.removeItem(index);
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public boolean isCartEmpty() {
        return cart.getItems().isEmpty();
    }

    public int getTotalRocketBucks() {
        return cart.getTotalRocketBucks();
    }

    public double getTotalPrice() {
        // Add 5% credit card fee
        return cart.getTotalPrice() * 1.05;
    }

    public void clearCart() {
        cart.clearCart();
    }

    // Inner class for shop items
    public static class Item {
        private final int id;
        private final String name;
        private final int price;
        private final String description;
        private final String icon;

        public Item(int id, String name, int price, String description, String icon) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.description = description;
            this.icon = icon;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }

        public String getDescription() {
            return description;
        }

        public String getIcon() {
            return icon;
        }
    }
}