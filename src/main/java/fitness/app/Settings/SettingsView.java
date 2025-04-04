package fitness.app.Settings;

import com.formdev.flatlaf.FlatClientProperties;
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
        mainPanel = new JPanel(new MigLayout("fill, insets 20", "[grow]", "[grow]"));

        // Use a fixed width panel with some flexibility
        settingsPanel = new JPanel(new MigLayout("wrap, insets 30", "[400:400:600]", "[]"));
        settingsPanel.putClientProperty(FlatClientProperties.STYLE, "arc:20;" + "background:lighten(@background,5%)");

        JLabel title = new JLabel("Settings");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");

        JLabel accountSectionLabel = new JLabel("Account");
        accountSectionLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +4");

        oldPassField = new JPasswordField();
        oldPassField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Old Password");
        oldPassField.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true");

        newPassField = new JPasswordField();
        newPassField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "New Password");
        newPassField.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true");
        passwordError = new JLabel("");

        newPassConfField = new JPasswordField();
        newPassConfField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Confirm New Password");
        newPassConfField.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true");
        confirmPasswordError = new JLabel("");

        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%);");
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

        JLabel appearanceLabel = new JLabel("Appearance");
        appearanceLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +4");

        String[] themes = {"Dark Mode", "Light Mode"};
        themeSelector = new JComboBox<>(themes);
        themeSelector.setSelectedIndex(SettingsViewModel.getThemeIndex());

        JLabel notificationsLabel = new JLabel("Notifications");
        notificationsLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +4");

        enableNotifications = new JCheckBox("Enable Notifications");
        enableNotifications.setSelected(SettingsViewModel.isNotificationsEnabled());

        JLabel unitPreferencesLabel = new JLabel("Unit Preferences");
        unitPreferencesLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +4");

        JLabel weightUnitLabel = new JLabel("Weight Unit");
        String[] weightUnits = {"Kilograms (kg)", "Pounds (lbs)"};
        weightUnitSelector = new JComboBox<>(weightUnits);
        weightUnitSelector.setSelectedItem(SettingsViewModel.getWeightUnitDisplayString());

        JButton saveButton = new JButton("Save Changes");
        saveButton.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%);");
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
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to save settings.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add components with proper sizing
        settingsPanel.add(title, "align center, growx");
        settingsPanel.add(accountSectionLabel, "gapy 20, growx");
        settingsPanel.add(new JLabel("Change Password"), "growx");
        settingsPanel.add(oldPassField, "growx");
        settingsPanel.add(newPassField, "growx");
        settingsPanel.add(passwordError, "gapy 0");
        settingsPanel.add(newPassConfField, "growx");
        settingsPanel.add(confirmPasswordError, "gapy 0");
        settingsPanel.add(changePasswordButton, "growx");

        settingsPanel.add(appearanceLabel, "gapy 20, growx");
        settingsPanel.add(new JLabel("Theme"), "growx");
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