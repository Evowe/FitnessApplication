package fitness.app.Components.CreateAccount;

import fitness.app.Components.Account;

import java.util.regex.Pattern;

public class CreateAccountModel {
    private static Pattern usernamePattern = Pattern.compile("^[A-Za-z0-9]+$");

    public static String validateUsername(String username) {
        if (username.length() < 4 || username.length() > 20) {
            return "Username must be between 4 and 20 characters";
        }
        else if (!usernamePattern.matcher(username).matches()){
            return "Username may only contain letters and numbers";
        }
        else if (Account.usernameExists(username)) {
            return "This username is already taken";
        }
        return null;
    }

    public static String validatePassword(String password) {
        if (password.length() < 8 || password.length() > 20) {
            return "Password must be between 8 and 20 characters";
        }
        return null;
    }

}
