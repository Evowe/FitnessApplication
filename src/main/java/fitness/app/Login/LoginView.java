package fitness.app.Login;

import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;
import com.formdev.flatlaf.extras.components.FlatPasswordField;
import com.formdev.flatlaf.extras.components.FlatTextField;
import fitness.app.Objects.Account;
import fitness.app.Main;
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

public class LoginView extends JPanel {
    private static JPanel mainPanel;

    public LoginView() {
        //Organizes the window into 2 halves
        mainPanel = new JPanel(new GridLayout(1,2));

        //Left half
        JPanel logoPanel = new JPanel(new MigLayout("fill,insets 20", "center", "center"));
        logoPanel.putClientProperty(FlatClientProperties.STYLE, "[dark]background:darken(@background,5%)");

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/main/resources/Images/RocketHealthLogo.PNG"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image scaledImg = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaledImg);
        JLabel logo = new JLabel(icon);

        logoPanel.add(logo);

        mainPanel.add(logoPanel);


        //Right half
        JPanel loginPanel = new JPanel(new MigLayout("fill,insets 20", "center", "center"));

        JPanel loginMenu = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,275"));
        loginMenu.putClientProperty(FlatClientProperties.STYLE, "arc:20;" + "background:lighten(@background,5%)");

        FlatLabel title = new FlatLabel();
        title.setText("Welcome to Rocket Health");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +6");

        FlatLabel description = new FlatLabel();
        description.setText("Please log in to continue");
        description.putClientProperty(FlatClientProperties.STYLE, "foreground:darken(@foreground,33%)");

        FlatTextField usernameField = new FlatTextField();
        usernameField.setPlaceholderText("Enter username");

        FlatLabel result = new FlatLabel();

        FlatPasswordField passwordField = new FlatPasswordField();
        passwordField.setPlaceholderText("Enter password");
        passwordField.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true");

        FlatButton logInButton = new FlatButton();
        logInButton.setText("Log in");
        logInButton.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%);");
        logInButton.addActionListener(new ActionListener() {
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
                    // Check role and redirect accordingly
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
        createAccountButton.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%);");
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

        mainPanel.add(loginPanel);
    }

    public static JPanel get() {
        return mainPanel;
    }
}
