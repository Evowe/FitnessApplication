package Application.Databases;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemsDB extends DBTemplate {

    public ItemsDB() {
        super("items");
    }

    @Override
    protected void createTables() throws SQLException {
        String[] itemsColumns = {
                "name TEXT NOT NULL",
                "description TEXT",
                "type TEXT NOT NULL",
                "price INTEGER DEFAULT 0",
                "icon TEXT"
        };
        createTable("items", itemsColumns);

        String[] userItemsColumns = {
                "username TEXT NOT NULL",
                "item_id INTEGER NOT NULL",
                "equipped INTEGER DEFAULT 0",
                "acquisition_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP",
                "FOREIGN KEY (item_id) REFERENCES items(id)",
                "UNIQUE (username, item_id)"
        };
        createTable("user_items", userItemsColumns);
    }

    // Methods for the items table
    public void addItem(String name, String description, String type, int price, String icon) throws SQLException {
        String sql = "INSERT INTO items (name, description, type, price, icon) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, description);
            ps.setString(3, type);
            ps.setInt(4, price);
            ps.setString(5, icon);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding item: " + e.getMessage());
            throw e;
        }
    }

    public ResultSet getAllItems() throws SQLException {
        String sql = "SELECT * FROM items";

        try {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error getting items: " + e.getMessage());
            throw e;
        }
    }

    public void createDefaultRocketItems() {
        // Define all rocket skins
        String[][] rocketSkins = {
                {"Default Rocket", "The standard rocket everyone starts with", "rocket", "0", "src/main/resources/Images/RocketHealthLogo.PNG"},
                {"Blue Rocket", "A cool blue rocket skin", "rocket", "100", "src/main/resources/Images/BlueRocket.png"},
                {"Green Rocket", "An eco-friendly green rocket", "rocket", "100", "src/main/resources/Images/GreenRocket.png"},
                {"Orange Rocket", "A vibrant orange rocket", "rocket", "100", "src/main/resources/Images/OrangeRocket.png"},
                {"Purple Rocket", "A royal purple rocket", "rocket", "100", "src/main/resources/Images/PurpleRocket.png"},
                {"Yellow Rocket", "A bright yellow rocket", "rocket", "100", "src/main/resources/Images/YellowRocket.png"},
                {"Cow Rocket", "MOOOOOOO", "rocket", "1000", "src/main/resources/Images/mooket.PNG"},
                {"BU Rocket", "Sic 'em Bears", "rocket", "500", "src/main/resources/Images/bucket.PNG"},
                {"Disco Rocket", "DANCE TIME", "rocket", "500", "src/main/resources/Images/disket.PNG"},
                {"Kirket", "Prof. Kirk Rocket", "rocket", "10000", "src/main/resources/Images/kirket.PNG"},
                {"Party Rocket", "PARTY TIME", "rocket", "750", "src/main/resources/Images/parket.PNG"},
                {"Shrek Rocket", "WHAT ARE YOU DOING IN MY SWAMP", "rocket", "1500", "src/main/resources/Images/shreket.PNG"},
                {"Sunset Rocket", "How peaceful", "rocket", "500", "src/main/resources/Images/sunket.PNG"},
                {"SWOLEKET", "THE BIGGEST ROCKET OF THEM ALL", "rocket", "10000", "src/main/resources/Images/swoleket.PNG"},
        };

        String checkSql = "SELECT name FROM items WHERE name = ?";
        String insertSql = "INSERT INTO items (name, description, type, price, icon) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection()) {
            PreparedStatement checkPs = conn.prepareStatement(checkSql);
            PreparedStatement insertPs = conn.prepareStatement(insertSql);

            for (String[] skin : rocketSkins) {
                // Check if skin exists
                checkPs.setString(1, skin[0]);
                ResultSet rs = checkPs.executeQuery();

                if (!rs.next()) {
                    // Skin doesn't exist, add it
                    insertPs.setString(1, skin[0]);      // name
                    insertPs.setString(2, skin[1]);      // description
                    insertPs.setString(3, skin[2]);      // type
                    insertPs.setInt(4, Integer.parseInt(skin[3])); // price
                    insertPs.setString(5, skin[4]);      // icon path

                    insertPs.executeUpdate();
                    System.out.println("Added rocket skin: " + skin[0]);
                }
            }
        } catch (SQLException e){
            System.err.println("Error inserting/checking rocket skins: " + e.getMessage());
        }
    }

    public void createDefaultTitles() {
        String[][] titles = {
                {"Pleb", "Your muscles are small. Ew.", "title", "0", ""},
                {"Wimp", "Try going to the gym for once", "title", "0", ""},
                {"Weakling", "I guess thats progress...", "title", "0", ""},
                {"Fledgling", "You might actually make it", "title", "0", ""},
                {"Chubby", "There is SOME mass I guess", "title", "0", ""},
                {"Soggy Noodle", "Protein? ever heard of it?", "title", "0", ""},
                {"Cheat Day Pro", "go to the gym.", "title", "0", ""},
                {"Error 404", "Abs not found", "title", "0", ""},
                {"Skips Leg day", "and arms. And everything else...", "title", "0", ""},
                {"Outrageously Mediocre", "No comment", "title", "0", ""},
                {"Not Embarrassing", "Hey, you are not the weakest now", "title", "0", ""},
                {"Trying your best", "Effort is effort", "title", "0", ""},
                {"Looks Maxxer", "Keep mewing bud", "title", "0", ""},
                {"Protein Addict", "just one more scoop", "title", "0", ""},
                {"SpeedRunner", "You are the PR", "title", "0", ""},
                {"Big (In a good way)", "Gravity complains when you arive", "title", "0", ""},
                {"Maximus", "Your Gluteous is...", "title", "0", ""},
                {"Ascended Lifter", "Beyond gym. Beyond mortal.", "title", "0", ""},
                {"SWOLE", "You have acheived peak mass, physics weeps", "title", "0", ""}
        };

        String checkSql = "SELECT name FROM items WHERE name = ?";
        String insertSql = "INSERT INTO items (name, description, type, price, icon) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection()) {
            PreparedStatement checkPs = conn.prepareStatement(checkSql);
            PreparedStatement insertPs = conn.prepareStatement(insertSql);

            for (String[] title : titles) {
                // Check if title exists
                checkPs.setString(1, title[0]);
                ResultSet rs = checkPs.executeQuery();

                if (!rs.next()) {
                    insertPs.setString(1, title[0]);
                    insertPs.setString(2, title[1]);
                    insertPs.setString(3, title[2]);
                    insertPs.setInt(4, Integer.parseInt(title[3]));
                    insertPs.setString(5, title[4]);

                    insertPs.executeUpdate();
                }
            }
        } catch (SQLException e){
            System.err.println("Error inserting/checking titles: " + e.getMessage());
        }
    }

    public int getItemIdByName(String itemName){
        String sql = "SELECT id FROM items WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, itemName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            } else {
                return -1; // Item not found
            }
        } catch (SQLException e) {
            System.err.println("Error getting item id: " + e.getMessage());
            return -1;
        }
    }

    // Methods for the user_items table
    public void giveItemToUser(String username, int itemId) throws SQLException {
        String sql = "INSERT INTO user_items (username, item_id) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setInt(2, itemId);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error giving item to user: " + e.getMessage());
            throw e;
        }
    }

    public void equipItem(String username, int itemId) throws SQLException {
        String sql = "UPDATE user_items SET equipped = 1 WHERE username = ? AND item_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setInt(2, itemId);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error equipping item: " + e.getMessage());
            throw e;
        }
    }

    public void unequipItem(String username, int itemId) throws SQLException {
        String sql = "UPDATE user_items SET equipped = 0 WHERE username = ? AND item_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setInt(2, itemId);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error unequipping item: " + e.getMessage());
            throw e;
        }
    }

    public List<Integer> getUserItems(String username) throws SQLException {
        String sql = "SELECT item_id FROM user_items WHERE username = ?";
        List<Integer> userItems = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                userItems.add(rs.getInt("item_id"));
            }
        } catch (SQLException e) {
            System.out.println("Error getting user items: " + e.getMessage());
            throw e;
        }

        return userItems;
    }

    public List<Integer> getEquippedItems(String username) throws SQLException {
        String sql = "SELECT item_id FROM user_items WHERE username = ? AND equipped = 1";
        List<Integer> equippedItems = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                equippedItems.add(rs.getInt("item_id"));
            }
        } catch (SQLException e) {
            System.out.println("Error getting equipped items: " + e.getMessage());
            throw e;
        }

        return equippedItems;
    }

    public boolean userHasItem(String username, int itemId) throws SQLException {
        String sql = "SELECT * FROM user_items WHERE username = ? AND item_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setInt(2, itemId);

            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error checking if user has item: " + e.getMessage());
            throw e;
        }
    }

    public void giveDefaultRocketToAllUsers() {
        // First make sure all skins exist
        createDefaultRocketItems();

        int defaultRocketId = getItemIdByName("Default Rocket");

        if (defaultRocketId == -1) {
            System.out.println("Error: Default rocket not found in the database");
            return;
        }

        // Get all non-admin users
        List<String> usernames = new ArrayList<>();

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT username FROM accounts WHERE role != 'admin'")) {

            // Collect all usernames first
            while (rs.next()) {
                usernames.add(rs.getString("username"));
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving users: " + e.getMessage());
            return;
        }

        // Now process each username with separate connections
        for (String username : usernames) {
            try {
                // Check if user already has the item
                boolean hasItem = false;
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement("SELECT * FROM user_items WHERE username = ? AND item_id = ?")) {

                    ps.setString(1, username);
                    ps.setInt(2, defaultRocketId);

                    try (ResultSet rs = ps.executeQuery()) {
                        hasItem = rs.next();
                    }
                }

                if (!hasItem) {
                    // Give item to user
                    try (Connection conn = getConnection();
                         PreparedStatement ps = conn.prepareStatement("INSERT INTO user_items (username, item_id) VALUES (?, ?)")) {

                        ps.setString(1, username);
                        ps.setInt(2, defaultRocketId);
                        ps.executeUpdate();
                    }

                    // Equip item
                    try (Connection conn = getConnection();
                         PreparedStatement ps = conn.prepareStatement("UPDATE user_items SET equipped = 1 WHERE username = ? AND item_id = ?")) {

                        ps.setString(1, username);
                        ps.setInt(2, defaultRocketId);
                        ps.executeUpdate();
                    }

                    System.out.println("Gave and equipped default rocket to: " + username);
                }
            } catch (SQLException e) {
                System.err.println("Error processing user " + username + ": " + e.getMessage());
            }
        }
    }

    public String getEquippedRocketIconPath(String username) {
        try {
            List<Integer> equippedItems = getEquippedItems(username);

            if (equippedItems.isEmpty()) {
                return null;
            }

            String sql = "SELECT icon FROM items WHERE id = ? AND type = 'rocket'";

            try (Connection conn = getConnection()) {
                PreparedStatement ps = conn.prepareStatement(sql);

                for (int itemId : equippedItems) {
                    ps.setInt(1, itemId);
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        return rs.getString("icon");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting equipped rocket icon: " + e.getMessage());
        }

        return null;
    }

    public String getEquippedTitleName(String username) {
        try {
            List<Integer> equippedItems = getEquippedItems(username);

            if (equippedItems.isEmpty()) {
                return null;
            }

            String sql = "SELECT name FROM items WHERE id = ? AND type = 'title'";

            try (Connection conn = getConnection()) {
                PreparedStatement ps = conn.prepareStatement(sql);

                for (int itemId : equippedItems) {
                    ps.setInt(1, itemId);
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        return rs.getString("name");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting equipped title name: " + e.getMessage());
        }

        return null;
    }

    public void giveAllItemsToUser(String username) {
        String selectItemsSql = "SELECT id FROM items";
        String checkItemSql = "SELECT * FROM user_items WHERE username = ? AND item_id = ?";
        String insertItemSql = "INSERT INTO user_items (username, item_id) VALUES (?, ?)";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet itemsRs = stmt.executeQuery(selectItemsSql);
             PreparedStatement checkPs = conn.prepareStatement(checkItemSql);
             PreparedStatement insertPs = conn.prepareStatement(insertItemSql)) {

            List<Integer> itemsToAdd = new ArrayList<>();

            // First collect all item IDs
            while (itemsRs.next()) {
                itemsToAdd.add(itemsRs.getInt("id"));
            }

            // For each item, check if user already has it
            for (int itemId : itemsToAdd) {
                checkPs.setString(1, username);
                checkPs.setInt(2, itemId);
                ResultSet rs = checkPs.executeQuery();

                // If user doesn't have this item, add it
                if (!rs.next()) {
                    insertPs.setString(1, username);
                    insertPs.setInt(2, itemId);
                    insertPs.executeUpdate();
                    System.out.println("Gave item ID " + itemId + " to user: " + username);
                }
            }

            System.out.println("Successfully processed all items for user: " + username);
        } catch (SQLException e) {
            System.err.println("Error giving all items to user " + username + ": " + e.getMessage());
        }
    }


}