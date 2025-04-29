package Application.AccountManagement.CreateAccount;

import com.formdev.flatlaf.extras.components.FlatPasswordField;
import com.formdev.flatlaf.extras.components.FlatTextField;
import com.formdev.flatlaf.extras.components.FlatComboBox;
import Application.Main;
import Application.Utility.Objects.Account;
import Application.Databases.AccountsDB;
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
    private final AccountsDB accountsDB;

    public CreateAccountView() {
        createAccountViewModel = new CreateAccountViewModel();
        accountsDB = new AccountsDB();

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

        // Use a JScrollPane to ensure all content is accessible
        JPanel createAccountMenu = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,275"));
        createAccountMenu.putClientProperty(FlatClientProperties.STYLE, "arc:20;");

        JScrollPane scrollPane = new JScrollPane(createAccountMenu);
        scrollPane.setBorder(null);
        scrollPane.setViewportBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

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

        // Security Questions Section
        JLabel securityQuestionsTitle = new JLabel("Security Questions");
        securityQuestionsTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold;");

        JLabel securityQuestionsDescription = new JLabel("Set up security questions to recover your account");
        securityQuestionsDescription.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground;");

        // Get all security questions
        String[] questions = AccountsDB.SECURITY_QUESTIONS;

        // Question 1
        JLabel question1Label = new JLabel("Question 1:");
        FlatComboBox<String> question1ComboBox = new FlatComboBox<>();
        question1ComboBox.setModel(new DefaultComboBoxModel<>(questions));

        JLabel answer1Label = new JLabel("Answer 1:");
        FlatTextField answer1Field = new FlatTextField();
        answer1Field.putClientProperty(FlatClientProperties.STYLE, "arc:10;");
        answer1Field.setPlaceholderText("Your answer");
        JLabel answer1Error = new JLabel("");

        // Question 2
        JLabel question2Label = new JLabel("Question 2:");
        FlatComboBox<String> question2ComboBox = new FlatComboBox<>();
        question2ComboBox.setModel(new DefaultComboBoxModel<>(questions));
        question2ComboBox.setSelectedIndex(1); // Default to different question

        JLabel answer2Label = new JLabel("Answer 2:");
        FlatTextField answer2Field = new FlatTextField();
        answer2Field.putClientProperty(FlatClientProperties.STYLE, "arc:10;");
        answer2Field.setPlaceholderText("Your answer");
        JLabel answer2Error = new JLabel("");

        // Question 3
        JLabel question3Label = new JLabel("Question 3:");
        FlatComboBox<String> question3ComboBox = new FlatComboBox<>();
        question3ComboBox.setModel(new DefaultComboBoxModel<>(questions));
        question3ComboBox.setSelectedIndex(2); // Default to different question

        JLabel answer3Label = new JLabel("Answer 3:");
        FlatTextField answer3Field = new FlatTextField();
        answer3Field.putClientProperty(FlatClientProperties.STYLE, "arc:10;");
        answer3Field.setPlaceholderText("Your answer");
        JLabel answer3Error = new JLabel("");

        JLabel securityQuestionsError = new JLabel("");

        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.setBorderPainted(false);
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                boolean valid = true;
                Account account = new Account(usernameField.getText(), passwordField.getText());

                // Clear all error messages
                usernameError.setText("");
                passwordError.setText("");
                confirmPasswordError.setText("");
                answer1Error.setText("");
                answer2Error.setText("");
                answer3Error.setText("");
                securityQuestionsError.setText("");

                // Validate username and password
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

                // Validate security questions
                int q1Index = question1ComboBox.getSelectedIndex();
                int q2Index = question2ComboBox.getSelectedIndex();
                int q3Index = question3ComboBox.getSelectedIndex();

                // Validate questions are different
                if (q1Index == q2Index || q1Index == q3Index || q2Index == q3Index) {
                    valid = false;
                    securityQuestionsError.setText("Please select different questions for each slot");
                    securityQuestionsError.setForeground(Color.RED);
                    securityQuestionsError.putClientProperty(FlatClientProperties.STYLE, "font:-4;");
                }

                // Validate answers are provided
                String answer1 = answer1Field.getText().trim();
                String answer2 = answer2Field.getText().trim();
                String answer3 = answer3Field.getText().trim();

                if (answer1.isEmpty()) {
                    valid = false;
                    answer1Error.setText("Please provide an answer");
                    answer1Error.setForeground(Color.RED);
                    answer1Error.putClientProperty(FlatClientProperties.STYLE, "font:-4;");
                }

                if (answer2.isEmpty()) {
                    valid = false;
                    answer2Error.setText("Please provide an answer");
                    answer2Error.setForeground(Color.RED);
                    answer2Error.putClientProperty(FlatClientProperties.STYLE, "font:-4;");
                }

                if (answer3.isEmpty()) {
                    valid = false;
                    answer3Error.setText("Please provide an answer");
                    answer3Error.setForeground(Color.RED);
                    answer3Error.putClientProperty(FlatClientProperties.STYLE, "font:-4;");
                }

                if (valid) {
                    try {
                        // Create the account first
                        account.addAccount();

                        // Then set up security questions
                        boolean securityQuestionsAdded = accountsDB.setupSecurityQuestions(
                                account.getUsername(),
                                q1Index, answer1,
                                q2Index, answer2,
                                q3Index, answer3
                        );

                        if (!securityQuestionsAdded) {
                            // This shouldn't happen, but just in case
                            JOptionPane.showMessageDialog(
                                    CreateAccountView.this,
                                    "Account created but failed to set up security questions.",
                                    "Warning",
                                    JOptionPane.WARNING_MESSAGE
                            );
                        }

                        Main.setCurrentUser(account);
                        Main.setWindow("HomePage");
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(
                                CreateAccountView.this,
                                "Error creating account: " + e.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                        System.out.println(e.getMessage());
                    } catch (IllegalArgumentException e) {
                        securityQuestionsError.setText(e.getMessage());
                        securityQuestionsError.setForeground(Color.RED);
                        securityQuestionsError.putClientProperty(FlatClientProperties.STYLE, "font:-4;");
                    }
                }
            }
        });

        // Add account creation components
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

        // Add security questions section
        createAccountMenu.add(securityQuestionsTitle, "gapy 16");
        createAccountMenu.add(securityQuestionsDescription);

        createAccountMenu.add(question1Label, "gapy 8");
        createAccountMenu.add(question1ComboBox);
        createAccountMenu.add(answer1Label);
        createAccountMenu.add(answer1Field);
        createAccountMenu.add(answer1Error, "gapy 0");

        createAccountMenu.add(question2Label, "gapy 8");
        createAccountMenu.add(question2ComboBox);
        createAccountMenu.add(answer2Label);
        createAccountMenu.add(answer2Field);
        createAccountMenu.add(answer2Error, "gapy 0");

        createAccountMenu.add(question3Label, "gapy 8");
        createAccountMenu.add(question3ComboBox);
        createAccountMenu.add(answer3Label);
        createAccountMenu.add(answer3Field);
        createAccountMenu.add(answer3Error, "gapy 0");

        createAccountMenu.add(securityQuestionsError, "gapy 4");

        // Add create account button
        createAccountMenu.add(createAccountButton, "gapy 16");

        createAccountPanel.add(scrollPane, "grow");
        add(createAccountPanel);
    }
}