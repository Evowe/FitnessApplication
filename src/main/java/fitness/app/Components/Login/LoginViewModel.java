package fitness.app.Components.Login;

import fitness.app.Components.Account;

import javax.swing.*;

public class LoginViewModel extends JFrame {
    public static String logInRequest(Account account) {
        return LoginModel.validateAccount(account);
    }
}
