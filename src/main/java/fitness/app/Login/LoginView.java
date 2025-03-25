package fitness.app.Login;

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
    private static JFrame window;
    private static JTextField usernameField;
    private static JPasswordField passwordField;
    private static JButton logInButton;
    private static JButton createAccountButton;
    private static JPanel mainPanel;
    private static JPanel logoPanel;
    private static JPanel loginPanel;

    public LoginView() {
        //Organizes the window into 2 halves
        mainPanel = new JPanel(new GridLayout(1,2));

        //Left half
        logoPanel = new JPanel(new MigLayout("fill,insets 20", "center", "center"));
        logoPanel.putClientProperty(FlatClientProperties.STYLE, ""+ "[dark]background:darken(@background,5%)");

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
        loginPanel = new JPanel(new MigLayout("fill,insets 20", "center", "center"));

        JPanel loginMenu = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,275"));
        loginMenu.putClientProperty(FlatClientProperties.STYLE, "" + "arc:20;" + "background:lighten(@background,5%)");

        JLabel title = new JLabel("Welcome to Rocket Health");
        title.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +6");

        JLabel description = new JLabel("Please log in to continue");
        description.putClientProperty(FlatClientProperties.STYLE, "" + "foreground:darken(@foreground,33%)");

        usernameField = new JTextField();
        usernameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter username");

        passwordField = new JPasswordField();
        passwordField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter password");
        passwordField.putClientProperty(FlatClientProperties.STYLE, "" + "showRevealButton:true");

        JLabel result = new JLabel("");

        logInButton = new JButton("Log In");
        logInButton.putClientProperty(FlatClientProperties.STYLE, "" + "background:lighten(@background,10%);");
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Account account = new Account(usernameField.getText(), passwordField.getText());
                String errorMessage = LoginViewModel.logInRequest(account);
                if (errorMessage != null) {
                    passwordField.setText("");
                    result.setText(errorMessage);
                    result.setForeground(Color.RED);
                    result.putClientProperty(FlatClientProperties.STYLE, "" + "font:-4");
                }
                else {
                    result.setText("Successfully logged in");
                    result.setForeground(Color.GREEN);
                    result.putClientProperty(FlatClientProperties.STYLE, "" + "font:-4");
                }
            }
        });

        createAccountButton = new JButton("Create Account");
        createAccountButton.putClientProperty(FlatClientProperties.STYLE, "" + "background:lighten(@background,10%);");
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
