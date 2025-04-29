package Utility.DatabaseTests;

import Application.Databases.AccountsDB;
import Application.Databases.DatabaseManager;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Application.Utility.Objects.Account;

public class AccountsDBTest {

    private AccountsDB accountsDB;
    private final String TEST_USERNAME_PREFIX = "test_user_" + UUID.randomUUID().toString().substring(0, 8) + "_";

    @BeforeEach
    public void setUp() {
        accountsDB = DatabaseManager.getAccountsDB();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        // Clean up only the test users created
        Connection conn = null;
        try {
            conn = accountsDB.getConnection();

            // Delete test accounts
            try (PreparedStatement pstmt = conn.prepareStatement(
                    "DELETE FROM accounts WHERE username LIKE ?")) {
                pstmt.setString(1, TEST_USERNAME_PREFIX + "%");
                int deleted = pstmt.executeUpdate();
            }

            // Delete test user items
            try (PreparedStatement pstmt = conn.prepareStatement(
                    "DELETE FROM user_items WHERE username LIKE ?")) {
                pstmt.setString(1, TEST_USERNAME_PREFIX + "%");
                int deleted = pstmt.executeUpdate();
            }
        } finally {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
    }

    private String getTestUsername(String baseName) {
        return TEST_USERNAME_PREFIX + baseName;
    }

    @Test
    public void testAddAndGetAccount() throws SQLException {
        String username = getTestUsername("addGet");
        Account testAccount = new Account(username, "password123", "active", "user");
        testAccount.setWallet(100);
        accountsDB.addAccount(testAccount);

        Account retrievedAccount = accountsDB.getAccount(username);

        assertNotNull(retrievedAccount);
        assertEquals(username, retrievedAccount.getUsername());
        assertEquals("user", retrievedAccount.getRole());
        assertEquals(100, retrievedAccount.getWallet());
    }

    @Test
    public void testGetAccountNoClose() throws SQLException {
        String username = getTestUsername("noClose");
        Account testAccount = new Account(username, "password123", "active", "user");
        accountsDB.addAccount(testAccount);

        Account retrievedAccount = accountsDB.getAccountNoClose(username);

        assertNotNull(retrievedAccount);
        assertEquals(username, retrievedAccount.getUsername());

        Connection conn = accountsDB.getConnection();
        assertTrue(!conn.isClosed(), "Should be able to get a new connection");
        conn.close();
    }

    // Test validLogin method
    @Test
    public void testValidLogin() throws SQLException {
        String username = getTestUsername("login");
        Account testAccount = new Account(username, "password123", "active", "user");
        accountsDB.addAccount(testAccount);

        assertTrue(accountsDB.validLogin(username, "password123"), "Valid login should return true");
        assertFalse(accountsDB.validLogin(username, "wrongPassword"), "Invalid login should return false");
        assertFalse(accountsDB.validLogin(getTestUsername("nonexistent"), "password123"), "Non-existent user should return false");
    }

    @Test
    public void testUsernameExists() throws SQLException {
        String username = getTestUsername("exists");
        Account testAccount = new Account(username, "password123", "active", "user");
        accountsDB.addAccount(testAccount);

        assertTrue(accountsDB.usernameExists(username), "Existing username should return true");
        assertFalse(accountsDB.usernameExists(getTestUsername("nonexistent")), "Non-existent username should return false");
    }

    // Test changePassword method
    @Test
    public void testChangePassword() throws SQLException {
        String username = getTestUsername("changePwd");
        Account testAccount = new Account(username, "password", "active", "user");
        accountsDB.addAccount(testAccount);

        assertTrue(accountsDB.usernameExists(username), "User should exist");
        assertTrue(accountsDB.validLogin(username, "password"), "Should login with original password");

        boolean result = accountsDB.changePassword(username, "newPassword");

        assertTrue(result, "Change password should return true for successful update");

        assertTrue(accountsDB.validLogin(username, "newPassword"), "Login should work with new password");
        assertFalse(accountsDB.validLogin(username, "password"), "Login should fail with old password");
    }

    @Test
    public void testUpdateUserPreferences() throws SQLException {
        String username = getTestUsername("prefs");
        Account testAccount = new Account(username, "password123", "active", "user");
        accountsDB.addAccount(testAccount);

        assertTrue(accountsDB.usernameExists(username), "User should exist before updating preferences");

        boolean result = accountsDB.updateUserPreferences(username, "light", false, "lb");

        assertTrue(result, "Update preferences should return true for successful update");

        Account updatedAccount = accountsDB.getAccount(username);

        assertEquals("light", updatedAccount.getTheme());
        assertEquals("lb", updatedAccount.getWeightUnit());
        assertFalse(updatedAccount.isNotificationsEnabled());
    }

    // Test getAllUsers method - modified to avoid affecting all users
    @Test
    public void testGetAllUsers() throws SQLException {
        String regularUser = getTestUsername("regular");
        String adminUser = getTestUsername("admin");

        Account userAccount = new Account(regularUser, "password", "active", "user");
        Account adminAccount = new Account(adminUser, "password", "active", "admin");

        accountsDB.addAccount(userAccount);
        accountsDB.addAccount(adminAccount);

        var users = accountsDB.getAllUsers();

        boolean foundTestUser = false;
        for (Account user : users) {
            if (user.getUsername().equals(regularUser)) {
                foundTestUser = true;
                break;
            }
        }

        assertTrue(foundTestUser, "Regular test user should be in the results");

        boolean foundAdminUser = false;
        for (Account user : users) {
            if (user.getUsername().equals(adminUser)) {
                foundAdminUser = true;
                break;
            }
        }

        assertFalse(foundAdminUser, "Admin test user should not be in the results");
    }

    @Test
    public void testPromoteAndDepromoteUser() throws SQLException {
        String username = getTestUsername("promote");
        Account testAccount = new Account(username, "password123", "active", "user");
        accountsDB.addAccount(testAccount);

        int userId = accountsDB.getAccount(username).getId();

        accountsDB.promoteUser(userId);

        Account promotedAccount = accountsDB.getAccount(username);
        assertEquals("trainer", promotedAccount.getRole());

        accountsDB.depromoteUser(userId);

        Account demotedAccount = accountsDB.getAccount(username);
        assertEquals("user", demotedAccount.getRole());
    }

    // Test updateWallet and getWallet methods
    @Test
    public void testWalletOperations() throws SQLException {
        String username = getTestUsername("wallet");
        Account testAccount = new Account(username, "password123", "active", "user");
        testAccount.setWallet(100);
        accountsDB.addAccount(testAccount);

        Account createdAccount = accountsDB.getAccount(username);
        assertEquals(100, createdAccount.getWallet(), "Wallet should be set to 100");

        int initialWallet = accountsDB.getWallet(username);
        assertEquals(100, initialWallet, "Initial wallet should be 100");

        accountsDB.updateWallet(username, 500);

        int updatedWallet = accountsDB.getWallet(username);
        assertEquals(500, updatedWallet, "Updated wallet should be 500");

        int nonExistentWallet = accountsDB.getWallet(getTestUsername("nonexistent"));
        assertEquals(-1000000, nonExistentWallet, "Non-existent user wallet should return error value");
    }

    // Test getCount method
    @Test
    public void testGetCount() throws SQLException {
        int initialCount = accountsDB.getCount();

        String user1 = getTestUsername("count1");
        String user2 = getTestUsername("count2");

        Account account1 = new Account(user1, "password", "active", "user");
        Account account2 = new Account(user2, "password", "active", "user");

        accountsDB.addAccount(account1);
        accountsDB.addAccount(account2);

        int updatedCount = accountsDB.getCount();

        assertEquals(initialCount + 2, updatedCount, "Count should increase by 2 after adding 2 users");
    }
}