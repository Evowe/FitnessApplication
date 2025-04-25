package fitness.app.AccountManagement.Login;

import fitness.app.Utility.Objects.Account;

public class LoginModel {
    public static String validateAccount(Account account) {
        // Validate login by passing username and password as arguments
        try {
            if (Account.validateLogin(account.getUsername(), account.getPassword())) {
                return null;  // Valid login
            } else {
                return "Invalid username or password";  // Invalid login
            }
        } catch (Exception e) {
            // If there's an error (e.g., database connection issue), log the exception
            e.printStackTrace();
            return "Error validating login";
        }
    }

}
