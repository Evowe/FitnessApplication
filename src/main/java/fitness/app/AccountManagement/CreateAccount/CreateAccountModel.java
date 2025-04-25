package fitness.app.AccountManagement.CreateAccount;

import fitness.app.BadProjectStructureSection.Objects.Account;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class CreateAccountModel {
    private static Pattern usernamePattern = Pattern.compile("^[A-Za-z0-9]+$");

    public static String validateUsername(String username) {
        // Check for username length constraints
        if (username.length() < 4 || username.length() > 20) {
            return "Username must be between 4 and 20 characters";
        }
        // Check if username matches the required pattern (letters and numbers only)
        else if (!usernamePattern.matcher(username).matches()) {
            return "Username may only contain letters and numbers";
        }

        // Check if username already exists in the database
        try {
            if (Account.usernameExists(username)) {
                return "This username is already taken";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error checking username availability";
        }

        // If no validation errors, return null
        return null;
    }

    public static String validatePassword(String password) {
        if (password.length() < 8 || password.length() > 20) {
            return "Password must be between 8 and 20 characters";
        }
        return null;
    }

}
