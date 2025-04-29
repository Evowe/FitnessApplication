package Application.AccountManagement.ResetPassword;

import Application.Main;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.*;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ResetPasswordView extends JPanel {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);
    private final ResetPasswordViewModel viewModel;

    // Reset password panels
    private JPanel usernamePanel;
    private JPanel questionsPanel;
    private JPanel newPasswordPanel;
    private JPanel resetSuccessPanel;

    // Setup security questions panel
    private JPanel setupQuestionsPanel;
    private JPanel setupSuccessPanel;

    // Constructor for password reset flow
    public ResetPasswordView() {
        this(null, null, false);
    }

    // Constructor for security questions setup
    public ResetPasswordView(String username, String returnPage, boolean setupMode) {
        viewModel = new ResetPasswordViewModel();

        if (username != null) {
            viewModel.setCurrentUsername(username);
            viewModel.setPreviousPage(returnPage != null ? returnPage : "HomePage");
            viewModel.setSetupMode(setupMode);
        }

        setLayout(new MigLayout("fill,insets 20", "center", "center"));

        // Create all panels
        createUsernamePanel();
        createQuestionsPanel();
        createNewPasswordPanel();
        createResetSuccessPanel();
        createSetupQuestionsPanel();
        createSetupSuccessPanel();

        // Add panels to card layout
        cardPanel.add(usernamePanel, "username");
        cardPanel.add(questionsPanel, "questions");
        cardPanel.add(newPasswordPanel, "newPassword");
        cardPanel.add(resetSuccessPanel, "resetSuccess");
        cardPanel.add(setupQuestionsPanel, "setupQuestions");
        cardPanel.add(setupSuccessPanel, "setupSuccess");

        // Start with the appropriate panel
        if (viewModel.isSetupMode() && viewModel.getCurrentUsername() != null) {
            cardLayout.show(cardPanel, "setupQuestions");
        } else {
            cardLayout.show(cardPanel, "username");
        }

        // Add card panel to main panel
        add(cardPanel, "grow");
    }

    // Reset Password Flow Panels
    private void createUsernamePanel() {
        usernamePanel = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,400"));
        usernamePanel.putClientProperty(FlatClientProperties.STYLE, "arc:20;");

        FlatLabel title = new FlatLabel();
        title.setText("Reset Password");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +6");

        FlatLabel description = new FlatLabel();
        description.setText("Enter your username to begin resetting your password");
        description.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground");

        FlatTextField usernameField = new FlatTextField();
        usernameField.putClientProperty(FlatClientProperties.STYLE, "arc:10;");
        usernameField.setPlaceholderText("Enter username");

        FlatLabel errorLabel = new FlatLabel();
        errorLabel.putClientProperty(FlatClientProperties.STYLE, "font:-4");
        errorLabel.setForeground(Color.RED);

        FlatButton nextButton = new FlatButton();
        nextButton.setBorderPainted(false);
        nextButton.setText("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();

                if (!viewModel.validateUsername(username)) {
                    errorLabel.setText("Username not found or invalid");
                    return;
                }

                try {
                    viewModel.setCurrentUsername(username);
                    int[] questionIds = viewModel.getSecurityQuestionIds();

                    if (questionIds == null) {
                        errorLabel.setText("No security questions set up for this user");
                        return;
                    }

                    setupQuestionsPanel(questionIds);
                    cardLayout.show(cardPanel, "questions");
                } catch (SQLException ex) {
                    errorLabel.setText("Error: " + ex.getMessage());
                }
            }
        });

        FlatButton backButton = new FlatButton();
        backButton.setBorderPainted(false);
        backButton.setText("Back to Login");
        backButton.addActionListener(e -> Main.setWindow("LoginPage"));

        // Add components to panel
        usernamePanel.add(title);
        usernamePanel.add(description);
        usernamePanel.add(new JLabel("Username"), "gapy 8");
        usernamePanel.add(usernameField);
        usernamePanel.add(errorLabel, "gapy 4");
        usernamePanel.add(nextButton, "gapy 10");
        usernamePanel.add(backButton, "gapy 5");
    }

    private void createQuestionsPanel() {
        questionsPanel = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,400"));
        questionsPanel.putClientProperty(FlatClientProperties.STYLE, "arc:20;");
        // This will be filled in dynamically when we know the questions
    }

    private void setupQuestionsPanel(int[] questionIds) {
        // UI setup code remains largely the same, but logic moves to ViewModel
        questionsPanel.removeAll();

        FlatLabel title = new FlatLabel();
        title.setText("Security Questions");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +6");

        FlatLabel description = new FlatLabel();
        description.setText("Please answer your security questions");
        description.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground");

        FlatLabel errorLabel = new FlatLabel();
        errorLabel.putClientProperty(FlatClientProperties.STYLE, "font:-4");
        errorLabel.setForeground(Color.RED);

        // Create answer fields for each question
        JLabel question1Label = new JLabel(viewModel.getQuestionText(questionIds[0]));
        FlatTextField answer1Field = new FlatTextField();
        answer1Field.putClientProperty(FlatClientProperties.STYLE, "arc:10;");
        answer1Field.setPlaceholderText("Your answer");

        JLabel question2Label = new JLabel(viewModel.getQuestionText(questionIds[1]));
        FlatTextField answer2Field = new FlatTextField();
        answer2Field.putClientProperty(FlatClientProperties.STYLE, "arc:10;");
        answer2Field.setPlaceholderText("Your answer");

        JLabel question3Label = new JLabel(viewModel.getQuestionText(questionIds[2]));
        FlatTextField answer3Field = new FlatTextField();
        answer3Field.putClientProperty(FlatClientProperties.STYLE, "arc:10;");
        answer3Field.setPlaceholderText("Your answer");

        FlatButton nextButton = new FlatButton();
        nextButton.setBorderPainted(false);
        nextButton.setText("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String answer1 = answer1Field.getText().trim();
                String answer2 = answer2Field.getText().trim();
                String answer3 = answer3Field.getText().trim();

                if (answer1.isEmpty() || answer2.isEmpty() || answer3.isEmpty()) {
                    errorLabel.setText("All questions must be answered");
                    return;
                }

                try {
                    boolean answersCorrect = viewModel.verifyAnswers(answer1, answer2, answer3);

                    if (!answersCorrect) {
                        errorLabel.setText("One or more answers are incorrect");
                        return;
                    }

                    // Move to new password panel
                    cardLayout.show(cardPanel, "newPassword");
                } catch (SQLException ex) {
                    errorLabel.setText("Error: " + ex.getMessage());
                }
            }
        });

        FlatButton backButton = new FlatButton();
        backButton.setBorderPainted(false);
        backButton.setText("Back");
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "username"));

        // Add components to panel
        questionsPanel.add(title);
        questionsPanel.add(description);
        questionsPanel.add(question1Label, "gapy 8");
        questionsPanel.add(answer1Field);
        questionsPanel.add(question2Label, "gapy 8");
        questionsPanel.add(answer2Field);
        questionsPanel.add(question3Label, "gapy 8");
        questionsPanel.add(answer3Field);
        questionsPanel.add(errorLabel, "gapy 4");
        questionsPanel.add(nextButton, "gapy 10");
        questionsPanel.add(backButton, "gapy 5");

        questionsPanel.revalidate();
        questionsPanel.repaint();
    }

    private void createNewPasswordPanel() {
        newPasswordPanel = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,400"));
        newPasswordPanel.putClientProperty(FlatClientProperties.STYLE, "arc:20;");

        FlatLabel title = new FlatLabel();
        title.setText("Create New Password");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +6");

        FlatLabel description = new FlatLabel();
        description.setText("Please enter your new password");
        description.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground");

        FlatPasswordField newPasswordField = new FlatPasswordField();
        newPasswordField.putClientProperty(FlatClientProperties.STYLE, "arc:10; showRevealButton:true");
        newPasswordField.setPlaceholderText("New password");

        FlatPasswordField confirmPasswordField = new FlatPasswordField();
        confirmPasswordField.putClientProperty(FlatClientProperties.STYLE, "arc:10; showRevealButton:true");
        confirmPasswordField.setPlaceholderText("Confirm new password");

        FlatLabel errorLabel = new FlatLabel();
        errorLabel.putClientProperty(FlatClientProperties.STYLE, "font:-4");
        errorLabel.setForeground(Color.RED);

        FlatButton resetButton = new FlatButton();
        resetButton.setBorderPainted(false);
        resetButton.setText("Reset Password");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newPassword = new String(newPasswordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (!viewModel.validatePasswords(newPassword, confirmPassword)) {
                    errorLabel.setText("Passwords do not match or are empty");
                    return;
                }

                try {
                    boolean success = viewModel.changePassword(newPassword);
                    if (success) {
                        cardLayout.show(cardPanel, "resetSuccess");
                    } else {
                        errorLabel.setText("Failed to reset password");
                    }
                } catch (SQLException ex) {
                    errorLabel.setText("Error: " + ex.getMessage());
                }
            }
        });

        FlatButton backButton = new FlatButton();
        backButton.setBorderPainted(false);
        backButton.setText("Back");
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "questions"));

        // Add components to panel
        newPasswordPanel.add(title);
        newPasswordPanel.add(description);
        newPasswordPanel.add(new JLabel("New Password"), "gapy 8");
        newPasswordPanel.add(newPasswordField);
        newPasswordPanel.add(new JLabel("Confirm Password"), "gapy 8");
        newPasswordPanel.add(confirmPasswordField);
        newPasswordPanel.add(errorLabel, "gapy 4");
        newPasswordPanel.add(resetButton, "gapy 10");
        newPasswordPanel.add(backButton, "gapy 5");
    }

    private void createResetSuccessPanel() {
        resetSuccessPanel = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,400"));
        resetSuccessPanel.putClientProperty(FlatClientProperties.STYLE, "arc:20;");

        FlatLabel title = new FlatLabel();
        title.setText("Password Reset Successful");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +6");

        FlatLabel description = new FlatLabel();
        description.setText("Your password has been successfully reset");
        description.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground");

        FlatButton loginButton = new FlatButton();
        loginButton.setBorderPainted(false);
        loginButton.setText("Return to Login");
        loginButton.addActionListener(e -> Main.setWindow("LoginPage"));

        resetSuccessPanel.add(title);
        resetSuccessPanel.add(description);
        resetSuccessPanel.add(loginButton, "gapy 20");
    }

    // Security Questions Setup Panels
    private void createSetupQuestionsPanel() {
        setupQuestionsPanel = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,450"));
        setupQuestionsPanel.putClientProperty(FlatClientProperties.STYLE, "arc:20;");

        FlatLabel title = new FlatLabel();
        title.setText("Security Questions Setup");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +6");

        FlatLabel description = new FlatLabel();
        description.setText("Set up security questions to recover your account");
        description.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground");

        // Get all security questions
        String[] questions = viewModel.getSecurityQuestions();

        // Question 1
        JLabel question1Label = new JLabel("Question 1:");
        FlatComboBox<String> question1ComboBox = new FlatComboBox<>();
        question1ComboBox.setModel(new DefaultComboBoxModel<>(questions));

        JLabel answer1Label = new JLabel("Answer 1:");
        FlatTextField answer1Field = new FlatTextField();
        answer1Field.putClientProperty(FlatClientProperties.STYLE, "arc:10;");
        answer1Field.setPlaceholderText("Your answer");

        // Question 2
        JLabel question2Label = new JLabel("Question 2:");
        FlatComboBox<String> question2ComboBox = new FlatComboBox<>();
        question2ComboBox.setModel(new DefaultComboBoxModel<>(questions));
        question2ComboBox.setSelectedIndex(1); // Default to different question

        JLabel answer2Label = new JLabel("Answer 2:");
        FlatTextField answer2Field = new FlatTextField();
        answer2Field.putClientProperty(FlatClientProperties.STYLE, "arc:10;");
        answer2Field.setPlaceholderText("Your answer");

        // Question 3
        JLabel question3Label = new JLabel("Question 3:");
        FlatComboBox<String> question3ComboBox = new FlatComboBox<>();
        question3ComboBox.setModel(new DefaultComboBoxModel<>(questions));
        question3ComboBox.setSelectedIndex(2); // Default to different question

        JLabel answer3Label = new JLabel("Answer 3:");
        FlatTextField answer3Field = new FlatTextField();
        answer3Field.putClientProperty(FlatClientProperties.STYLE, "arc:10;");
        answer3Field.setPlaceholderText("Your answer");

        FlatLabel errorLabel = new FlatLabel();
        errorLabel.putClientProperty(FlatClientProperties.STYLE, "font:-4");
        errorLabel.setForeground(Color.RED);

        FlatButton saveButton = new FlatButton();
        saveButton.setBorderPainted(false);
        saveButton.setText("Save Security Questions");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validate selections are different
                int q1Index = question1ComboBox.getSelectedIndex();
                int q2Index = question2ComboBox.getSelectedIndex();
                int q3Index = question3ComboBox.getSelectedIndex();

                // Validate answers are provided
                String answer1 = answer1Field.getText().trim();
                String answer2 = answer2Field.getText().trim();
                String answer3 = answer3Field.getText().trim();

                try {
                    String validationError = viewModel.validateSecurityQuestions(
                            q1Index, answer1,
                            q2Index, answer2,
                            q3Index, answer3
                    );

                    if (validationError != null) {
                        errorLabel.setText(validationError);
                        return;
                    }

                    boolean success = viewModel.setupSecurityQuestions(
                            q1Index, answer1,
                            q2Index, answer2,
                            q3Index, answer3
                    );

                    if (success) {
                        cardLayout.show(cardPanel, "setupSuccess");
                    } else {
                        errorLabel.setText("Failed to save security questions");
                    }
                } catch (IllegalArgumentException ex) {
                    errorLabel.setText(ex.getMessage());
                } catch (SQLException ex) {
                    errorLabel.setText("Database error: " + ex.getMessage());
                }
            }
        });

        FlatButton skipButton = new FlatButton();
        skipButton.setBorderPainted(false);
        skipButton.setText("Skip for Now");
        skipButton.addActionListener(e -> Main.setWindow(viewModel.getPreviousPage()));

        setupQuestionsPanel.add(title);
        setupQuestionsPanel.add(description, "gapy 4");

        setupQuestionsPanel.add(question1Label, "gapy 10");
        setupQuestionsPanel.add(question1ComboBox);
        setupQuestionsPanel.add(answer1Label);
        setupQuestionsPanel.add(answer1Field);

        setupQuestionsPanel.add(question2Label, "gapy 10");
        setupQuestionsPanel.add(question2ComboBox);
        setupQuestionsPanel.add(answer2Label);
        setupQuestionsPanel.add(answer2Field);

        setupQuestionsPanel.add(question3Label, "gapy 10");
        setupQuestionsPanel.add(question3ComboBox);
        setupQuestionsPanel.add(answer3Label);
        setupQuestionsPanel.add(answer3Field);

        setupQuestionsPanel.add(errorLabel, "gapy 10");
        setupQuestionsPanel.add(saveButton, "gapy 10");
        setupQuestionsPanel.add(skipButton, "gapy 5");
    }

    private void createSetupSuccessPanel() {
        setupSuccessPanel = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,400"));
        setupSuccessPanel.putClientProperty(FlatClientProperties.STYLE, "arc:20;");

        FlatLabel title = new FlatLabel();
        title.setText("Security Questions Saved");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +6");

        FlatLabel description = new FlatLabel();
        description.setText("Your security questions have been successfully saved");
        description.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground");

        FlatButton continueButton = new FlatButton();
        continueButton.setBorderPainted(false);
        continueButton.setText("Continue");
        continueButton.addActionListener(e -> Main.setWindow(viewModel.getPreviousPage()));

        setupSuccessPanel.add(title);
        setupSuccessPanel.add(description);
        setupSuccessPanel.add(continueButton, "gapy 20");
    }
}