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
        return SettingsModel.validateNewPassword(password);
    }

    public static boolean changePassword(String oldPassword, String newPassword) {
        return SettingsModel.changePassword(oldPassword, newPassword);
    }

    public static boolean saveSettings(int themeIndex, boolean notificationsEnabled, String weightUnit) {
        return SettingsModel.saveSettings(themeIndex, notificationsEnabled, weightUnit);
    }

    public static String validateOldPassword(String password) {
        return SettingsModel.validateOldPassword(password);
    }

    public static int getThemeIndex() {
        return SettingsModel.getThemeIndex();
    }

    public static boolean isNotificationsEnabled() {
        return SettingsModel.isNotificationsEnabled();
    }

    public static String getWeightUnitDisplayString() {
        return SettingsModel.getWeightUnitDisplayString();
    }
}
