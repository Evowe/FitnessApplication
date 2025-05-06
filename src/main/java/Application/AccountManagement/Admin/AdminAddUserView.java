package Application.AccountManagement.Admin;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;

import Application.Main;
import Application.AccountManagement.CreateAccount.CreateAccountViewModel;
import Application.Utility.Objects.Account;
import net.miginfocom.swing.MigLayout;

public class AdminAddUserView extends JPanel {
    private final CreateAccountViewModel createAccountViewModel;

    public AdminAddUserView() {
        createAccountViewModel = new CreateAccountViewModel();
        createView();
    }

    public void createView() {
        JTextField usernameField;
        JPasswordField passwordField;
        JPasswordField confirmPasswordField;
        JButton createAccountButton;
        JPanel logoPanel;
        JPanel createAccountPanel;

        //Organizes the window into 2 halves
        setLayout(new GridLayout(1,2));

        //Left half
        logoPanel = new JPanel(new MigLayout("fill,insets 20", "center", "center"));
        logoPanel.putClientProperty(FlatClientProperties.STYLE, "background:darken(@background,3%)");

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/main/resources/Images/RocketHealthLogo.PNG"));
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        Image scaledImg = img.getScaledInstance(450, 450, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaledImg);
        JLabel logo = new JLabel(icon);

        logoPanel.add(logo, "pos (45% - pref/2) (50% - pref/2)");

        add(logoPanel);

        //Right half
        createAccountPanel = new JPanel(new MigLayout("fill,insets 20", "center", "center"));
        createAccountPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background;");

        JPanel createAccountMenu = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,275"));
        createAccountMenu.putClientProperty(FlatClientProperties.STYLE, "arc:20;");

        JLabel title = new JLabel("Add a New User");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +6;");

        JLabel description = new JLabel("Create a new account");
        description.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground;");

        usernameField = new JTextField();
        usernameField.putClientProperty(FlatClientProperties.STYLE, "arc:10;");
        usernameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter username");
        JLabel usernameError = new JLabel("");

        passwordField = new JPasswordField();
        passwordField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter password");
        passwordField.putClientProperty(FlatClientProperties.STYLE, "arc:10; showRevealButton:true;");
        JLabel passwordError = new JLabel("");

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Confirm password");
        confirmPasswordField.putClientProperty(FlatClientProperties.STYLE, "arc:10;");
        JLabel confirmPasswordError = new JLabel("");

        //Dropdown for role selection
        String[] roles = {"Admin", "Trainer", "User"};
        JComboBox<String> roleSelect = new JComboBox(roles);
        roleSelect.putClientProperty(FlatClientProperties.STYLE, "arc:10;");

        //Create account button
        createAccountButton = new JButton("Create Account");
        createAccountButton.setBorderPainted(false);
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                boolean valid = true;
                Account account = new Account(usernameField.getText(), passwordField.getText(), "active", ((String) roleSelect.getSelectedItem()).toLowerCase());

                // Clear error messages
                usernameError.setText("");
                passwordError.setText("");
                confirmPasswordError.setText("");

                if (createAccountViewModel.validateUsername(account) != null) {
                    valid = false;
                    usernameError.setText(createAccountViewModel.validateUsername(account));
                    usernameError.setForeground(Color.RED);
                    usernameError.putClientProperty(FlatClientProperties.STYLE, "font:-4;");
                }

                if (createAccountViewModel.validatePassword(account) != null) {
                    valid = false;
                    passwordError.setText(createAccountViewModel.validatePassword(account));
                    passwordError.setForeground(Color.RED);
                    passwordError.putClientProperty(FlatClientProperties.STYLE, "font:-4;");
                }

                if (!passwordField.getText().equals(confirmPasswordField.getText())) {
                    valid = false;
                    confirmPasswordError.setText("Passwords do not match");
                    confirmPasswordError.setForeground(Color.RED);
                    confirmPasswordError.putClientProperty(FlatClientProperties.STYLE, "font:-4;");
                }

                if (valid) {
                    try {
                        account.addAccount();
                    } catch (SQLException e){
                        System.out.println(e.getMessage());
                    }

                    Main.setWindow("AdminUsers");
                }
            }
        });

        createAccountMenu.add(title);
        createAccountMenu.add(description);
        createAccountMenu.add(new JLabel("Username"), "gapy 8");
        createAccountMenu.add(usernameField);
        createAccountMenu.add(usernameError, "gapy 0");
        createAccountMenu.add(new JLabel("Password"), "gapy 8");
        createAccountMenu.add(passwordField);
        createAccountMenu.add(passwordError, "gapy 0");
        createAccountMenu.add(new JLabel("Confirm Password"), "gapy 8");
        createAccountMenu.add(confirmPasswordField);
        createAccountMenu.add(confirmPasswordError, "gapy 0");
        createAccountMenu.add(new JLabel("Role"), "gapy 8");
        createAccountMenu.add(roleSelect);
        createAccountMenu.add(createAccountButton, "gapy 16");
        createAccountPanel.add(createAccountMenu, "grow");

        add(createAccountPanel);
    }
}