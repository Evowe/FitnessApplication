package fitness.app.Settings;

import fitness.app.Objects.Account;

import javax.swing.*;

public class SettingsViewModel {
    private static SettingsView settingsView;

    public static JPanel getSettingsView() {
        settingsView = new SettingsView();
        return settingsView.getSettingsView();
    }

    public static String validateNewPassword(String password) {
        return SettingsModel.validatePassword(password);
    }

    public static boolean changePassword(String oldPassword, String newPassword) {
        return SettingsModel.changePassword(oldPassword, newPassword);
    }

    public static void saveSettings(int themeIndex, boolean notificationsEnabled) {
        SettingsModel.saveSettings(themeIndex, notificationsEnabled);
    }
}
