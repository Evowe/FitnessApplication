package fitness.app.Settings;

import fitness.app.Main;
import fitness.app.Objects.Account;
import java.sql.SQLException;
import fitness.app.Objects.DatabaseManager;

public class SettingsModel {

    public static String validatePassword(String password) {
        Account currentUser = Main.getCurrentUser();

        if (currentUser.getPassword().equals(password)) {
            return null;
        } else {
            return "The password you entered is incorrect";
        }
    }

    public static boolean changePassword(String oldPassword, String newPassword) {
        try {

            Account currentUser = Main.getCurrentUser();


            if (validatePassword(oldPassword) == null) {
                currentUser.setPassword(newPassword);
                currentUser.updateAccount();
                return true;
            }

            return false; // Old password didn't match

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void saveSettings(int themeIndex, boolean notificationsEnabled) {
        Account currentUser = Main.getCurrentUser();
        if (currentUser == null) {
            System.out.println("Cannot save settings: No user logged in");
            return;
        }

        String theme = (themeIndex == 0) ? "dark" : "light";
        System.out.println("Saving settings for user " + currentUser.getUsername() +
                ": Theme=" + theme + ", Notifications=" + notificationsEnabled);

        // TODO: Implement actual saving logic here
        // You might want to add theme and notification preferences to your Account class
        // or have a separate UserPreferences table in your database
    }
}