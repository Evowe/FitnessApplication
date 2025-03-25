package fitness.app.Login;

import fitness.app.Objects.Account;

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
