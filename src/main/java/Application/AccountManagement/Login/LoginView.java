package Application.AccountManagement.Login;

import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;
import com.formdev.flatlaf.extras.components.FlatPasswordField;
import com.formdev.flatlaf.extras.components.FlatTextField;
import Application.Utility.Objects.Account;
import Application.Main;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//import static fitness.app.Main.updateTheme;

public class LoginView extends JPanel {
    private final LoginViewModel loginViewModel;

    public LoginView() {
        loginViewModel = new LoginViewModel();

        //Organizes the window into 2 halves
        setLayout(new GridLayout(1,2));

        //Left half
        JPanel logoPanel = new JPanel(new MigLayout("fill,insets 20", "center", "center"));
        logoPanel.putClientProperty(FlatClientProperties.STYLE, "background:darken(@background,3%)");

        BufferedImage img;
        try {
            img = ImageIO.read(new File("src/main/resources/Images/RocketHealthLogo.PNG"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image scaledImg = img.getScaledInstance(450, 450, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaledImg);
        JLabel logo = new JLabel(icon);

        logoPanel.add(logo, "pos (45% - pref/2) (50% - pref/2)");

        add(logoPanel);


        //Right half
        JPanel loginPanel = new JPanel(new MigLayout("fill,insets 20", "center", "center"));
        loginPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");

        JPanel loginMenu = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,275"));
        loginMenu.putClientProperty(FlatClientProperties.STYLE, "arc:20;");

        FlatLabel title = new FlatLabel();
        title.setText("Welcome to Rocket Health");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +6");

        FlatLabel description = new FlatLabel();
        description.setText("Please log in to continue");
        description.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground");

        FlatTextField usernameField = new FlatTextField();
        usernameField.putClientProperty(FlatClientProperties.STYLE, "arc:10;");
        usernameField.setPlaceholderText("Enter username");

        FlatLabel result = new FlatLabel();

        FlatPasswordField passwordField = new FlatPasswordField();
        passwordField.putClientProperty(FlatClientProperties.STYLE, "arc:10; showRevealButton:true");
        passwordField.setPlaceholderText("Enter password");

        FlatButton logInButton = new FlatButton();
        logInButton.setBorderPainted(false);
        logInButton.setText("Log in");
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Account account = new Account(usernameField.getText(), passwordField.getText());
                Object[] loginResult = loginViewModel.logInRequest(account);
                String errorMessage = (String)loginResult[0];
                Account validatedAccount = (Account)loginResult[1];

                if (errorMessage != null) {
                    passwordField.setText("");
                    result.setText(errorMessage);
                    result.setForeground(Color.RED);
                    result.putClientProperty(FlatClientProperties.STYLE, "font:-4");
                }
                else {
                    Main.setCurrentUser(validatedAccount);
                    // Check role and redirect accordingly
                    if ("admin".equals(validatedAccount.getRole())) {
                        Main.setWindow("AdminPage");
                    } else {
                        Main.setWindow("HomePage");
                    }
                }
            }
        });


        usernameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                passwordField.requestFocus();
            }
        });

        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Account account = new Account(usernameField.getText(), passwordField.getText());
                Object[] loginResult = LoginViewModel.logInRequest(account);
                String errorMessage = (String)loginResult[0];
                Account validatedAccount = (Account)loginResult[1];

                if (errorMessage != null) {
                    passwordField.setText("");
                    result.setText(errorMessage);
                    result.setForeground(Color.RED);
                    result.putClientProperty(FlatClientProperties.STYLE, "font:-4");
                }
                else {
                    Main.setCurrentUser(validatedAccount);

                    if ("admin".equals(validatedAccount.getRole())) {
                        Main.setWindow("AdminPage");
                    } else {
                        Main.setWindow("HomePage");
                    }
                }
            }
        });

        FlatButton createAccountButton = new FlatButton();
        createAccountButton.setText("Create Account");
        createAccountButton.setBorderPainted(false);
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Main.setWindow("CreateAccountPage");
            }
        });

        loginMenu.add(title);
        loginMenu.add(description);
        loginMenu.add(new JLabel("Username"), "gapy 8");
        loginMenu.add(usernameField);
        loginMenu.add(new JLabel("Password"), "gapy 8");
        loginMenu.add(passwordField);
        loginMenu.add(result, "gapy 0");
        loginMenu.add(logInButton, "gapy 10");
        loginMenu.add(createAccountButton, "gapy 10");
        loginPanel.add(loginMenu);

        add(loginPanel);
    }
}
