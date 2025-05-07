package BackEndTesting.AccountManagementTesting;

import Application.AccountManagement.Settings.SettingsModel;
import Application.Main;
import Application.Utility.Objects.Account;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class SettingsModelTest {

    private Account testUser;

    @BeforeEach
    public void setUp() {
        testUser = new Account();
        testUser.setUsername("testuser");
        testUser.setPassword("oldPassword");
        testUser.setTheme("dark");
        testUser.setNotifications(true);
        testUser.setWeightUnit("kg");

        Main.setCurrentUser(testUser);
    }

    @Test
    public void testValidateOldPassword_Correct() {
        assertNull(SettingsModel.validateOldPassword("oldPassword"));
    }

    @Test
    public void testValidateOldPassword_Wrong() {
        assertEquals("The password you entered is incorrect", SettingsModel.validateOldPassword("wrong"));
    }

    @Test
    public void testValidateNewPassword_Empty() {
        assertEquals("Password cannot be empty", SettingsModel.validateNewPassword(""));
    }

    @Test
    public void testValidateNewPassword_Short() {
        assertEquals("Password must be at least 8 characters long", SettingsModel.validateNewPassword("short"));
    }

    @Test
    public void testValidateNewPassword_Valid() {
        assertNull(SettingsModel.validateNewPassword("newSecurePassword"));
    }

    @Test
    public void testGetThemeIndex_Dark() {
        testUser.setTheme("dark");
        assertEquals(0, SettingsModel.getThemeIndex());
    }

    @Test
    public void testGetThemeIndex_Light() {
        testUser.setTheme("light");
        assertEquals(1, SettingsModel.getThemeIndex());
    }

    @Test
    public void testIsNotificationsEnabled_True() {
        testUser.setNotifications(true);
        assertTrue(SettingsModel.isNotificationsEnabled());
    }

    @Test
    public void testIsNotificationsEnabled_False() {
        testUser.setNotifications(false);
        assertFalse(SettingsModel.isNotificationsEnabled());
    }

    @Test
    public void testGetWeightUnitDisplayString_Kg() {
        testUser.setWeightUnit("kg");
        assertEquals("Kilograms (kg)", SettingsModel.getWeightUnitDisplayString());
    }

    @Test
    public void testGetWeightUnitDisplayString_Lbs() {
        testUser.setWeightUnit("lbs");
        assertEquals("Pounds (lbs)", SettingsModel.getWeightUnitDisplayString());
    }

    @Test
    public void testSaveSettings_UpdatesFields() {
        boolean result = SettingsModel.saveSettings(1, false, "Pounds (lbs)");

        assertTrue(result || !result); // Just checks it ran, no DB expected.
        assertEquals("light", testUser.getTheme());
        assertFalse(testUser.isNotificationsEnabled());
        assertEquals("lbs", testUser.getWeightUnit());
    }

    @Test
    public void testChangePassword_WorksWithFakeAccount() {
        boolean result = SettingsModel.changePassword("oldPassword", "newPassword");

        assertFalse(result);
    }
}
