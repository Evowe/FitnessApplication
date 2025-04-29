package Application.AccountManagement.ResetPassword;

import java.sql.SQLException;

public class ResetPasswordViewModel {
    private final ResetPasswordModel model;
    private String currentUsername;
    private int[] questionIds;
    private String previousPage = "HomePage"; // Default return page
    private boolean isSetupMode = false;

    public ResetPasswordViewModel() {
        model = new ResetPasswordModel();
    }

    public void setPreviousPage(String previousPage) {
        this.previousPage = previousPage;
    }

    public String getPreviousPage() {
        return previousPage;
    }

    public void setSetupMode(boolean setupMode) {
        this.isSetupMode = setupMode;
    }

    public boolean isSetupMode() {
        return isSetupMode;
    }

    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public boolean validateUsername(String username) {
        if (username.isEmpty()) {
            return false;
        }
        return model.usernameExists(username);
    }

    public int[] getSecurityQuestionIds() throws SQLException {
        if (questionIds == null) {
            questionIds = model.getSecurityQuestionIds(currentUsername);
        }
        return questionIds;
    }

    public String getQuestionText(int questionId) {
        return model.getQuestionText(questionId);
    }

    public String[] getSecurityQuestions() {
        return model.getSecurityQuestions();
    }

    public boolean verifyAnswers(String answer1, String answer2, String answer3) throws SQLException {
        boolean answer1Correct = model.verifySecurityAnswer(currentUsername, 1, answer1);
        boolean answer2Correct = model.verifySecurityAnswer(currentUsername, 2, answer2);
        boolean answer3Correct = model.verifySecurityAnswer(currentUsername, 3, answer3);

        return answer1Correct && answer2Correct && answer3Correct;
    }

    public boolean validatePasswords(String newPassword, String confirmPassword) {
        return !newPassword.isEmpty() && newPassword.equals(confirmPassword);
    }

    public String validateSecurityQuestions(int q1Index, String a1, int q2Index, String a2, int q3Index, String a3) {
        // Check if questions are different
        if (q1Index == q2Index || q1Index == q3Index || q2Index == q3Index) {
            return "Please select different questions for each slot";
        }

        // Check if all answers are provided
        if (a1.isEmpty() || a2.isEmpty() || a3.isEmpty()) {
            return "Please provide an answer for each question";
        }

        return null; // No errors
    }

    public boolean changePassword(String newPassword) throws SQLException {
        return model.changePassword(currentUsername, newPassword);
    }

    public boolean setupSecurityQuestions(int q1Index, String a1, int q2Index, String a2, int q3Index, String a3)
            throws SQLException, IllegalArgumentException {
        return model.setupSecurityQuestions(currentUsername, q1Index, a1, q2Index, a2, q3Index, a3);
    }
}