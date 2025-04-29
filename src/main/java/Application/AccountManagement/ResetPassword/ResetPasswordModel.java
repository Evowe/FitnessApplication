package Application.AccountManagement.ResetPassword;

import Application.Utility.Databases.AccountsDB;
import java.sql.SQLException;

public class ResetPasswordModel {
    private final AccountsDB accountsDB;

    public ResetPasswordModel() {
        accountsDB = new AccountsDB();
    }

    public boolean usernameExists(String username) {
        return accountsDB.usernameExists(username);
    }

    public int[] getSecurityQuestionIds(String username) throws SQLException {
        return accountsDB.getSecurityQuestionIds(username);
    }

    public String getQuestionText(int questionId) {
        return accountsDB.getQuestionText(questionId);
    }

    public String[] getSecurityQuestions() {
        return AccountsDB.SECURITY_QUESTIONS;
    }

    public boolean verifySecurityAnswer(String username, int questionNum, String answer) throws SQLException {
        return accountsDB.verifySecurityAnswer(username, questionNum, answer);
    }

    public boolean changePassword(String username, String newPassword) throws SQLException {
        return accountsDB.changePassword(username, newPassword);
    }

    public boolean setupSecurityQuestions(String username, int q1Index, String a1, int q2Index, String a2, int q3Index, String a3)
            throws SQLException, IllegalArgumentException {
        return accountsDB.setupSecurityQuestions(username, q1Index, a1, q2Index, a2, q3Index, a3);
    }
}