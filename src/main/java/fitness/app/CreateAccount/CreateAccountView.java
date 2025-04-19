package fitness.app.CreateAccount;

import com.formdev.flatlaf.extras.components.FlatPasswordField;
import com.formdev.flatlaf.extras.components.FlatTextField;
import fitness.app.Main;
import fitness.app.Objects.Account;
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
import java.sql.SQLException;

public class CreateAccountView extends JPanel {
    private final CreateAccountViewModel createAccountViewModel;

    public CreateAccountView() {
        createAccountViewModel = new CreateAccountViewModel();

        //Organizes the window into 2 halves
        setLayout(new GridLayout(1,2));

        //Left half
        JPanel logoPanel = new JPanel(new MigLayout("fill,insets 20", "center", "center"));
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
        JPanel createAccountPanel = new JPanel(new MigLayout("fill,insets 20", "center", "center"));
        createAccountPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background;");

        JPanel createAccountMenu = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,275"));
        createAccountMenu.putClientProperty(FlatClientProperties.STYLE, "arc:20;");

        JButton backButton = new JButton("<");
        backButton.setMaximumSize(new Dimension(30,30));
        backButton.setBorderPainted(false);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.setWindow("LoginPage");
            }
        });

        JLabel title = new JLabel("Welcome to Rocket Health");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +6;");

        JLabel description = new JLabel("Create a new account to continue");
        description.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground;");

        FlatTextField usernameField = new FlatTextField();
        usernameField.putClientProperty(FlatClientProperties.STYLE, "arc:10;");
        usernameField.setPlaceholderText("Enter username");
        JLabel usernameError = new JLabel("");

        FlatPasswordField passwordField = new FlatPasswordField();
        passwordField.setPlaceholderText("Enter password");
        passwordField.putClientProperty(FlatClientProperties.STYLE, "arc:10; showRevealButton:true;");
        JLabel passwordError = new JLabel("");

        FlatPasswordField confirmPasswordField = new FlatPasswordField();
        confirmPasswordField.putClientProperty(FlatClientProperties.STYLE, "arc:10;");
        confirmPasswordField.setPlaceholderText("Confirm password");
        JLabel confirmPasswordError = new JLabel("");

        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.setBorderPainted(false);
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                boolean valid = true;
                Account account = new Account(usernameField.getText(), passwordField.getText());

                if (createAccountViewModel.validateUsername(account) != null) {
                    valid = false;
                    usernameError.setText(createAccountViewModel.validateUsername(account));
                    usernameError.setForeground(Color.RED);
                    usernameError.putClientProperty(FlatClientProperties.STYLE, "font:-4;");
                }
                else {
                    usernameError.setText("");
                }

                if (createAccountViewModel.validatePassword(account) != null) {
                    valid = false;
                    passwordError.setText(createAccountViewModel.validatePassword(account));
                    passwordError.setForeground(Color.RED);
                    passwordError.putClientProperty(FlatClientProperties.STYLE, "font:-4;");

                }
                else {
                    passwordError.setText("");
                }

                if (!passwordField.getText().equals(confirmPasswordField.getText())) {
                    valid = false;
                    confirmPasswordError.setText("Passwords do not match");
                    confirmPasswordError.setForeground(Color.RED);
                    confirmPasswordError.putClientProperty(FlatClientProperties.STYLE, "font:-4;");
                }
                else {
                    confirmPasswordError.setText("");
                }


                if (valid) {
                    try {
                        account.addAccount();
                    } catch (SQLException e){
                        System.out.println(e.getMessage());
                    }

                    Main.setCurrentUser(account);
                    Main.setWindow("HomePage");
                }
            }
        });

        createAccountMenu.add(backButton, "gapy 2");
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
        createAccountMenu.add(createAccountButton, "gapy 10");
        createAccountPanel.add(createAccountMenu);

        add(createAccountPanel);
    }
}
