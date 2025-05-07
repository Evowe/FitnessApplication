package BackEndTesting;

import Application.AccountManagement.CreateAccount.CreateAccountModel;
import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreateAccountModelTest {

    @Test
    public void testUsernameTooShort() {
        String result = CreateAccountModel.validateUsername("abc");
        assertEquals("Username must be between 4 and 20 characters", result);
    }

    @Test
    public void testUsernameTooLong() {
        String result = CreateAccountModel.validateUsername("a".repeat(21));
        assertEquals("Username must be between 4 and 20 characters", result);
    }

    @Test
    public void testUsernameInvalidCharacters() {
        String result = CreateAccountModel.validateUsername("user@123");
        assertEquals("Username may only contain letters and numbers", result);
    }

    @Test
    public void testPasswordTooShort() {
        String result = CreateAccountModel.validatePassword("short");
        assertEquals("Password must be between 8 and 20 characters", result);
    }

    @Test
    public void testPasswordTooLong() {
        String result = CreateAccountModel.validatePassword("a".repeat(21));
        assertEquals("Password must be between 8 and 20 characters", result);
    }

    @Test
    public void testPasswordValid() {
        String result = CreateAccountModel.validatePassword("GoodPass123");
        assertNull(result);
    }
}
