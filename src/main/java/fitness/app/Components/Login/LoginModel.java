package fitness.app.Components.Login;

import fitness.app.Components.Account;

public class LoginModel {
    public static String validateAccount(Account account) {
        if (Account.validLogin(account)) {
            return null;
        }
        else {
            return "Invalid username or password";
        }
    }

}
