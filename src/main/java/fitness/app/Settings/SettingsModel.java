package fitness.app.Settings;

import fitness.app.Main;
import fitness.app.Objects.Account;
import java.sql.SQLException;

public class SettingsModel {

    public static String validateOldPassword(String password) {
        Account currentUser = Main.getCurrentUser();

        if (currentUser.getPassword().equals(password)) {
            return null;
        } else {
            return "The password you entered is incorrect";
        }
    }

    public static String validateNewPassword(String password) {
        if (password == null || password.isEmpty()) {
            return "Password cannot be empty";
        }
        if (password.length() < 8) {
            return "Password must be at least 8 characters long";
        }
        return null;
    }

    public static boolean changePassword(String oldPassword, String newPassword) {
        try {
            Account currentUser = Main.getCurrentUser();

            boolean success = Account.changePassword(currentUser.getUsername(), newPassword);

            if (success) {
                currentUser.setPassword(newPassword);
                return true;
            }

            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int getThemeIndex() {
        Account currentUser = Main.getCurrentUser();
        if (currentUser == null) return 0;

        return currentUser.getTheme().equals("dark") ? 0 : 1;
    }

    // Get notifications enabled
    public static boolean isNotificationsEnabled() {
        Account currentUser = Main.getCurrentUser();
        if (currentUser == null) return true;

        return currentUser.isNotificationsEnabled();
    }

    // Get weight unit display string for dropdown
    public static String getWeightUnitDisplayString() {
        Account currentUser = Main.getCurrentUser();
        if (currentUser == null) return "Kilograms (kg)"; // Default

        return currentUser.getWeightUnit().equals("kg") ? "Kilograms (kg)" : "Pounds (lbs)";
    }


    public static boolean saveSettings(int themeIndex, boolean notificationsEnabled, String weightUnitDisplay) {
        try {
            Account currentUser = Main.getCurrentUser();

            String theme = (themeIndex == 0) ? "dark" : "light";

            // Convert weight unit display string to stored value
            String weightUnit = weightUnitDisplay.contains("kg") ? "kg" : "lbs";

            // Update account object
            currentUser.setTheme(theme);
            currentUser.setNotifications(notificationsEnabled);
            currentUser.setWeightUnit(weightUnit);

            // Save to database
            boolean success = currentUser.savePreferences();
            if (success) {
                System.out.println("Successfully saved user preferences");
            } else {
                System.out.println("Failed to save user preferences");
            }
            return success;

        } catch (SQLException e) {
            System.out.println("Error saving preferences: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}