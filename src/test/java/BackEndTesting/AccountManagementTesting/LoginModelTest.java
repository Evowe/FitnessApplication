package BackEndTesting.AccountManagementTesting;

import Application.AccountManagement.Login.LoginModel;
import Application.Utility.Objects.Account;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginModelTest {

    public static class TestAccount extends Account {
        private final String username;
        private final String password;

        public TestAccount(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public String getPassword() {
            return password;
        }
    }

    @Test
    public void testInvalidLogin() {
        Account acc = new TestAccount("username", "passwodfghjrd");
        String result = LoginModel.validateAccount(acc);
        assertEquals("Invalid username or password", result);
    }

    @Test
    public void testValidLogin() {
        Account acc = new TestAccount("username", "password");
        String result = LoginModel.validateAccount(acc);
        assertNull(result);
    }
}
