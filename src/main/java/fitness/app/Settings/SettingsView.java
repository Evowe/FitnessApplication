package fitness.app.Settings;

import com.formdev.flatlaf.FlatClientProperties;
import fitness.app.Home.HomeView;
import fitness.app.Main;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsView {
    private static JPanel mainPanel;
    private static JPanel settingsPanel;
    private static JPasswordField oldPassField;
    private static JPasswordField newPassField;
    private static JPasswordField newPassConfField;
    private static JLabel passwordError;
    private static JLabel confirmPasswordError;
    private static JComboBox<String> themeSelector;
    private static JCheckBox enableNotifications;
    private static JComboBox<String> weightUnitSelector;

    public SettingsView() {
        // Set the main panel with black background
        mainPanel = new JPanel(new MigLayout("fill, insets 20", "[grow]", "[grow]"));
        mainPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background;");

        // Create the settings panel with WHITE background and rounded corners
        settingsPanel = new JPanel(new MigLayout("wrap, insets 30", "[400:400:600]", "[]"));
        settingsPanel.putClientProperty(FlatClientProperties.STYLE, "arc:20; background:@secondaryBackground");

        // Style the title with red text
        JLabel title = new JLabel("Settings");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +10; foreground:@foreground;");

        // Style section labels with red text
        JLabel accountSectionLabel = new JLabel("Account");
        accountSectionLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +4; foreground:@foreground");

        oldPassField = new JPasswordField();
        oldPassField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Old Password");
        oldPassField.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true; background:@secondaryBackground; foreground:@foreground;");

        newPassField = new JPasswordField();
        newPassField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "New Password");
        newPassField.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true; background:@secondaryBackground; foreground:@foreground;");
        passwordError = new JLabel("");

        newPassConfField = new JPasswordField();
        newPassConfField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Confirm New Password");
        newPassConfField.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true; background:@secondaryBackground; foreground:@foreground;");
        confirmPasswordError = new JLabel("");

        // Style button to RED with white text
        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.putClientProperty(FlatClientProperties.STYLE, "background:@accent; foreground:@foreground;");
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean valid = true;

                passwordError.setText("");
                confirmPasswordError.setText("");

                String oldPassword = new String(oldPassField.getPassword());
                String newPassword = new String(newPassField.getPassword());
                String confirmPassword = new String(newPassConfField.getPassword());

                String oldPasswordValidation = SettingsViewModel.validateOldPassword(oldPassword);
                if (oldPasswordValidation != null) {
                    valid = false;
                    JOptionPane.showMessageDialog(null, oldPasswordValidation, "Error", JOptionPane.ERROR_MESSAGE);
                }

                String validation = SettingsViewModel.validateNewPassword(newPassword);
                if (validation != null) {
                    valid = false;
                    passwordError.setText(validation);
                    passwordError.setForeground(Color.RED);
                    passwordError.putClientProperty(FlatClientProperties.STYLE, "font:-4");
                }

                if (!newPassword.equals(confirmPassword)) {
                    valid = false;
                    confirmPasswordError.setText("Passwords do not match");
                    confirmPasswordError.setForeground(Color.RED);
                    confirmPasswordError.putClientProperty(FlatClientProperties.STYLE, "font:-4");
                }

                if (valid) {
                    boolean success = SettingsViewModel.changePassword(oldPassword, newPassword);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Password changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        oldPassField.setText("");
                        newPassField.setText("");
                        newPassConfField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to change password. Database error occurred.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Style appearance section with red text
        JLabel appearanceLabel = new JLabel("Appearance");
        appearanceLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +4; foreground:@foreground;");

        JLabel themeLabel = new JLabel("Theme");
        themeLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:@foreground");

        String[] themes = {"Dark Mode", "Light Mode"};
        themeSelector = new JComboBox<>(themes);
        themeSelector.setSelectedIndex(SettingsViewModel.getThemeIndex());
        // Make dropdown more readable with dark background
        themeSelector.putClientProperty(FlatClientProperties.STYLE, "background:@secondaryBackground; foreground:@foreground");

        // Style notifications section with red text
        JLabel notificationsLabel = new JLabel("Notifications");
        notificationsLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +4; foreground:@foreground;");

        enableNotifications = new JCheckBox("Enable Notifications");
        enableNotifications.setSelected(SettingsViewModel.isNotificationsEnabled());
        enableNotifications.setForeground(Color.WHITE);
        enableNotifications.putClientProperty(FlatClientProperties.STYLE, "");

        // Style unit preferences section with red text
        JLabel unitPreferencesLabel = new JLabel("Unit Preferences");
        unitPreferencesLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +4; foreground:@foreground;");

        JLabel weightUnitLabel = new JLabel("Weight Unit");
        weightUnitLabel.setForeground(Color.WHITE);

        String[] weightUnits = {"Kilograms (kg)", "Pounds (lbs)"};
        weightUnitSelector = new JComboBox<>(weightUnits);
        weightUnitSelector.setSelectedItem(SettingsViewModel.getWeightUnitDisplayString());
        // Make dropdown more readable with dark background
        weightUnitSelector.setBackground(new Color(30, 30, 30));
        weightUnitSelector.setForeground(Color.WHITE);
        weightUnitSelector.putClientProperty(FlatClientProperties.STYLE, "background:@secondaryBackground; foreground:@foreground;");

        // Style save button to RED with white text
        JButton saveButton = new JButton("Save Changes");
        saveButton.putClientProperty(FlatClientProperties.STYLE, "background:@accent; foreground:@foreground;");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean success = SettingsViewModel.saveSettings(
                        themeSelector.getSelectedIndex(),
                        enableNotifications.isSelected(),
                        weightUnitSelector.getSelectedItem().toString()
                );

                if (success) {
                    JOptionPane.showMessageDialog(null, "Settings saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    Main.setWindow("HomePage");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to save settings.", "Error", JOptionPane.ERROR_MESSAGE);
                    Main.setWindow("HomePage");
                }
            }
        });

        // Add components with proper sizing
        settingsPanel.add(title, "align center, growx");
        settingsPanel.add(accountSectionLabel, "gapy 20, growx");

        JLabel changePasswordLabel = new JLabel("Change Password");
        changePasswordLabel.setForeground(Color.WHITE);
        settingsPanel.add(changePasswordLabel, "growx");

        settingsPanel.add(oldPassField, "growx");
        settingsPanel.add(newPassField, "growx");
        settingsPanel.add(passwordError, "gapy 0");
        settingsPanel.add(newPassConfField, "growx");
        settingsPanel.add(confirmPasswordError, "gapy 0");
        settingsPanel.add(changePasswordButton, "growx");

        settingsPanel.add(appearanceLabel, "gapy 20, growx");
        settingsPanel.add(themeLabel, "growx");
        settingsPanel.add(themeSelector, "growx");

        settingsPanel.add(notificationsLabel, "gapy 20, growx");
        settingsPanel.add(enableNotifications, "growx");

        settingsPanel.add(unitPreferencesLabel, "gapy 20, growx");
        settingsPanel.add(weightUnitLabel, "growx");
        settingsPanel.add(weightUnitSelector, "growx");

        settingsPanel.add(saveButton, "width 200, align center, gapy 20");

        mainPanel.add(settingsPanel, "align center");
    }

    public static JPanel getSettingsView() {
        return mainPanel;
    }
}