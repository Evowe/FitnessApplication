package fitness.app.Objects.Databases;

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
                "id INTEGER PRIMARY KEY AUTOINCREMENT",
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
}