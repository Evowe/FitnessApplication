package fitness.app.Components.Login;

import fitness.app.Components.Account;

import javax.swing.*;

public class LoginViewModel extends JFrame {
    private static LoginView loginView;

    public static String logInRequest(Account account) {
        return LoginModel.validateAccount(account);
    }

    public static JPanel getLoginView() {
        loginView = new LoginView();
        return loginView.get();
    }
}
