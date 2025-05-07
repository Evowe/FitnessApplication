package BackEndTesting;

import Application.AccountManagement.ResetPassword.ResetPasswordModel;
import Application.Databases.AccountsDB;
import Application.Utility.Objects.Account;
import org.junit.jupiter.api.*;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class ResetPasswordModelTest {

    private static AccountsDB accountsDB;
    private static Account testAccount;

    @BeforeAll
    public static void setupBeforeAll() throws SQLException {
        accountsDB = new AccountsDB();

        testAccount = new Account();
        testAccount.setUsername("testUser");
        testAccount.setPassword("testPassword123");
        accountsDB.addAccount(testAccount);
        accountsDB.setupSecurityQuestions("testUser",0,"a",1,"b",2,"c");
    }

    @AfterAll
    public static void tearDownAfterAll() throws SQLException {
        accountsDB.deleteUser(testAccount.getUsername());
    }

    @Test
    public void testUsernameExistsFalse() {
        ResetPasswordModel model = new ResetPasswordModel();
        boolean exists = model.usernameExists("nonexistent_user_12345");
        assertFalse(exists);
    }

    @Test
    public void testUsernameExistsTrue() {
        ResetPasswordModel model = new ResetPasswordModel();
        boolean exists = model.usernameExists("testUser");
        assertTrue(exists);
    }

    @Test
    public void testGetSecurityQuestions() {
        ResetPasswordModel model = new ResetPasswordModel();
        String[] questions = model.getSecurityQuestions();
        assertNotNull(questions);
        assertTrue(questions.length > 0);
    }

    @Test
    public void testGetQuestionText() {
        ResetPasswordModel model = new ResetPasswordModel();
        String question = model.getQuestionText(0);
        assertNotNull(question);
        assertFalse(question.isEmpty());
    }

    @Test
    public void testGetSecurityQuestionIds() throws SQLException {
        ResetPasswordModel model = new ResetPasswordModel();
        int[] ids = model.getSecurityQuestionIds("testUser");
        assertNotNull(ids);
        assertEquals(3, ids.length);
    }

    @Test
    public void testVerifySecurityAnswerCorrect() throws SQLException {
        ResetPasswordModel model = new ResetPasswordModel();
        boolean correct = model.verifySecurityAnswer("testUser", 1, "a");
        assertTrue(correct);
    }

    @Test
    public void testVerifySecurityAnswerIncorrect() throws SQLException {
        ResetPasswordModel model = new ResetPasswordModel();
        boolean correct = model.verifySecurityAnswer("testUser", 1, "123");
        assertFalse(correct);
    }

    @Test
    public void testChangePassword() throws SQLException {
        ResetPasswordModel model = new ResetPasswordModel();
        boolean success = model.changePassword("testUser", "newPassword1234");
        assertTrue(success);
    }

    @Test
    public void testSetupSecurityQuestions() throws SQLException {
        ResetPasswordModel model = new ResetPasswordModel();
        boolean setup = model.setupSecurityQuestions("testUser", 0, "a", 1, "b", 2, "c");
        assertTrue(setup);
    }
}

