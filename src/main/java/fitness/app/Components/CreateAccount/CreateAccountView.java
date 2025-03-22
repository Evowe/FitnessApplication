package fitness.app.Components.CreateAccount;

import fitness.app.Components.Account;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto_mono.FlatRobotoMonoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import fitness.app.Components.Main;
import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.desktop.ScreenSleepEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CreateAccountView {
    private static JFrame window;
    private static JTextField usernameField;
    private static JPasswordField passwordField;
    private static JPasswordField confirmPasswordField;
    private static JButton createAccountButton;
    private static JPanel mainPanel;
    private static JPanel logoPanel;
    private static JPanel createAccountPanel;
    
    public CreateAccountView() {
        //Organizes the window into 2 halves
        mainPanel = new JPanel(new GridLayout(1,2));

        //Left half
        logoPanel = new JPanel(new MigLayout("fill,insets 20", "center", "center"));
        logoPanel.putClientProperty(FlatClientProperties.STYLE, ""+ "[dark]background:darken(@background,5%)");

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/main/resources/Images/RocketHealthLogo.PNG"));
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        Image scaledImg = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaledImg);
        JLabel logo = new JLabel(icon);

        logoPanel.add(logo);

        mainPanel.add(logoPanel);


        //Right half
        createAccountPanel = new JPanel(new MigLayout("fill,insets 20", "center", "center"));

        JPanel createAccountMenu = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,275"));
        createAccountMenu.putClientProperty(FlatClientProperties.STYLE, "" + "arc:20;" + "background:lighten(@background,5%)");

        JLabel title = new JLabel("Welcome to Rocket Health");
        title.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +6");

        JLabel description = new JLabel("Create a new account to continue");
        description.putClientProperty(FlatClientProperties.STYLE, "" + "foreground:darken(@foreground,33%)");

        usernameField = new JTextField();
        usernameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter username");

        passwordField = new JPasswordField();
        passwordField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter password");
        passwordField.putClientProperty(FlatClientProperties.STYLE, "" + "showRevealButton:true");

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Confirm password");

        createAccountButton = new JButton("Create Account");
        createAccountButton.putClientProperty(FlatClientProperties.STYLE, "" + "background:lighten(@background,10%);");
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Account account = new Account(usernameField.getText(), passwordField.getText());
                account.addAccount();
                description.setText("Successfully created account");
                Main.setWindow("LoginPage");
            }
        });

        createAccountMenu.add(title);
        createAccountMenu.add(description);
        createAccountMenu.add(new JLabel("Username"), "gapy 8");
        createAccountMenu.add(usernameField);
        createAccountMenu.add(new JLabel("Password"), "gapy 8");
        createAccountMenu.add(passwordField);
        createAccountMenu.add(new JLabel("Confirm Password"), "gapy 8");
        createAccountMenu.add(confirmPasswordField);
        createAccountMenu.add(createAccountButton, "gapy 10");
        createAccountPanel.add(createAccountMenu);

        mainPanel.add(createAccountPanel);
    }

    public static JPanel getCreateAccountView() {
        return mainPanel;
    }
}
