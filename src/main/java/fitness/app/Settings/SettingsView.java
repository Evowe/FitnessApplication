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

    public SettingsView() {
        mainPanel = new JPanel(new MigLayout("fill,insets 20", "[grow]", "center"));

        settingsPanel = new JPanel(new MigLayout("wrap,fillx,insets 30", "center"));
        settingsPanel.putClientProperty(FlatClientProperties.STYLE, "arc:20;" + "background:lighten(@background,5%)");

        JLabel title = new JLabel("Settings");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");

        // Account settings section
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

                // Validate password
                String validation = SettingsViewModel.validateNewPassword(newPassField.getText());
                if (validation != null) {
                    valid = false;
                    passwordError.setText(validation);
                    passwordError.setForeground(Color.RED);
                    passwordError.putClientProperty(FlatClientProperties.STYLE, "font:-4");
                }

                // Validate password confirmation
                if (!newPassField.getText().equals(newPassConfField.getText())) {
                    valid = false;
                    confirmPasswordError.setText("Passwords do not match");
                    confirmPasswordError.setForeground(Color.RED);
                    confirmPasswordError.putClientProperty(FlatClientProperties.STYLE, "font:-4");
                }

                if (valid) {
                    boolean success = SettingsViewModel.changePassword(oldPassField.getText(), newPassField.getText());
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Password changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        oldPassField.setText("");
                        newPassField.setText("");
                        newPassConfField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to change password. Please check your old password.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Appearance section
        JLabel appearanceLabel = new JLabel("Appearance");
        appearanceLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +4");

        String[] themes = {"Dark Mode", "Light Mode"};
        themeSelector = new JComboBox<>(themes);

        // Notifications section
        JLabel notificationsLabel = new JLabel("Notifications");
        notificationsLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +4");

        enableNotifications = new JCheckBox("Enable Notifications");
        enableNotifications.setSelected(true);

        // Save button
        JButton saveButton = new JButton("Save Changes");
        saveButton.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%);");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SettingsViewModel.saveSettings(themeSelector.getSelectedIndex(), enableNotifications.isSelected());
                JOptionPane.showMessageDialog(null, "Settings saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Add all components to the settings panel
        settingsPanel.add(title, "width 100%");

        settingsPanel.add(accountSectionLabel, "gapy 20, width 100%");
        settingsPanel.add(new JLabel("Change Password"), "width 100%");
        settingsPanel.add(oldPassField, "width 100%");
        settingsPanel.add(newPassField, "width 100%");
        settingsPanel.add(passwordError, "gapy 0");
        settingsPanel.add(newPassConfField, "width 100%");
        settingsPanel.add(confirmPasswordError, "gapy 0");
        settingsPanel.add(changePasswordButton, "width 100%");

        settingsPanel.add(appearanceLabel, "gapy 20, width 100%");
        settingsPanel.add(new JLabel("Theme"), "width 100%");
        settingsPanel.add(themeSelector, "width 100%");

        settingsPanel.add(notificationsLabel, "gapy 20, width 100%");
        settingsPanel.add(enableNotifications, "width 100%");

        settingsPanel.add(saveButton, "gapy 20, width 50%, align center");

        mainPanel.add(settingsPanel, "grow");
    }

    public static JPanel getSettingsView() {
        return mainPanel;
    }
}