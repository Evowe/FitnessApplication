package Utility.DatabaseTests;

import Application.Databases.DatabaseManager;
import Application.Databases.ItemsDB;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ItemsDBTest {

    private ItemsDB itemsDB;
    private final String TEST_PREFIX = "test_" + UUID.randomUUID().toString().substring(0, 8) + "_";

    @BeforeEach
    public void setUp() {
        itemsDB = DatabaseManager.getItemsDB();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        Connection conn = null;
        try {
            conn = itemsDB.getConnection();

            try (PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT id FROM items WHERE name LIKE ?")) {
                pstmt.setString(1, TEST_PREFIX + "%");
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    int itemId = rs.getInt("id");

                    try (PreparedStatement deleteUserItems = conn.prepareStatement(
                            "DELETE FROM user_items WHERE item_id = ?")) {
                        deleteUserItems.setInt(1, itemId);
                        deleteUserItems.executeUpdate();
                    }
                }
            }

            try (PreparedStatement pstmt = conn.prepareStatement(
                    "DELETE FROM items WHERE name LIKE ?")) {
                pstmt.setString(1, TEST_PREFIX + "%");
                pstmt.executeUpdate();
            }

            try (PreparedStatement pstmt = conn.prepareStatement(
                    "DELETE FROM user_items WHERE username LIKE ?")) {
                pstmt.setString(1, TEST_PREFIX + "%");
                pstmt.executeUpdate();
            }
        } finally {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
    }

    private String getTestItemName(String baseName) {
        return TEST_PREFIX + baseName;
    }

    private String getTestUsername(String baseName) {
        return TEST_PREFIX + baseName;
    }

    @Test
    public void testAddAndRetrieveItem() throws SQLException {
        String itemName = getTestItemName("rocket");
        String description = "Test rocket item";
        String type = "rocket";
        int price = 100;
        String icon = "test/path/to/icon.png";

        itemsDB.addItem(itemName, description, type, price, icon);

        int itemId = itemsDB.getItemIdByName(itemName);
        assertTrue(itemId > 0, "Item should be added with valid ID");

        ResultSet allItems = itemsDB.getAllItems();
        boolean found = false;

        while (allItems.next()) {
            if (allItems.getString("name").equals(itemName)) {
                found = true;
                assertEquals(description, allItems.getString("description"));
                assertEquals(type, allItems.getString("type"));
                assertEquals(price, allItems.getInt("price"));
                assertEquals(icon, allItems.getString("icon"));
                break;
            }
        }

        assertTrue(found, "Added item should be found in all items");
        allItems.close();
    }

    @Test
    public void testGiveItemToUser() throws SQLException {
        String itemName = getTestItemName("userItem");
        String username = getTestUsername("user");
        itemsDB.addItem(itemName, "Test user item", "rocket", 200, "test/path.png");

        int itemId = itemsDB.getItemIdByName(itemName);
        assertTrue(itemId > 0);

        itemsDB.giveItemToUser(username, itemId);

        assertTrue(itemsDB.userHasItem(username, itemId), "User should have the item after it's given");

        List<Integer> userItems = itemsDB.getUserItems(username);
        assertTrue(userItems.contains(itemId), "User items should contain the given item ID");
    }

    @Test
    public void testEquipAndUnequipItem() throws SQLException {
        String itemName = getTestItemName("equippable");
        String username = getTestUsername("equip_user");
        itemsDB.addItem(itemName, "Test equippable item", "rocket", 300, "test/equip.png");

        int itemId = itemsDB.getItemIdByName(itemName);

        itemsDB.giveItemToUser(username, itemId);

        List<Integer> initialEquipped = itemsDB.getEquippedItems(username);
        assertFalse(initialEquipped.contains(itemId), "Item should not be equipped initially");

        itemsDB.equipItem(username, itemId);

        List<Integer> equippedItems = itemsDB.getEquippedItems(username);
        assertTrue(equippedItems.contains(itemId), "Item should be in equipped items after equipping");

        itemsDB.unequipItem(username, itemId);

        List<Integer> afterUnequip = itemsDB.getEquippedItems(username);
        assertFalse(afterUnequip.contains(itemId), "Item should not be in equipped items after unequipping");
    }

    @Test
    public void testCreateDefaultRocketItems() throws SQLException {
        itemsDB.createDefaultRocketItems();

        int defaultRocketId = itemsDB.getItemIdByName("Default Rocket");
        assertTrue(defaultRocketId > 0, "Default rocket should exist");

        String[] rocketNames = {
                "Blue Rocket", "Green Rocket", "Orange Rocket",
                "Purple Rocket", "Yellow Rocket", "Cow Rocket"
        };

        for (String rocketName : rocketNames) {
            int rocketId = itemsDB.getItemIdByName(rocketName);
            assertTrue(rocketId > 0, rocketName + " should exist");
        }
    }

    @Test
    public void testGetEquippedRocketIconPath() throws SQLException {
        String itemName = getTestItemName("icon_rocket");
        String username = getTestUsername("icon_user");
        String iconPath = "test/icon/path.png";

        itemsDB.addItem(itemName, "Test rocket with icon", "rocket", 400, iconPath);

        int itemId = itemsDB.getItemIdByName(itemName);

        itemsDB.giveItemToUser(username, itemId);
        itemsDB.equipItem(username, itemId);

        String retrievedPath = itemsDB.getEquippedRocketIconPath(username);

        assertEquals(iconPath, retrievedPath, "Should retrieve the correct icon path");
    }

    @Test
    public void testUserHasItem() throws SQLException {
        String itemName = getTestItemName("has_item");
        String username = getTestUsername("has_user");

        itemsDB.addItem(itemName, "Test has item", "rocket", 500, "test/has.png");

        int itemId = itemsDB.getItemIdByName(itemName);

        assertFalse(itemsDB.userHasItem(username, itemId), "User should not have item initially");

        itemsDB.giveItemToUser(username, itemId);

        assertTrue(itemsDB.userHasItem(username, itemId), "User should have item after it's given");
    }
}