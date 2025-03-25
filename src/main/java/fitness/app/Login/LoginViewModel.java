package fitness.app.Login;

import fitness.app.Objects.Account;

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
