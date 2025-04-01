package fitness.app.Login;

import fitness.app.Objects.Account;

import javax.swing.*;
import java.sql.SQLException;

public class LoginViewModel extends JFrame {
    private static LoginView loginView;

    public static Object[] logInRequest(Account account) {
        String errorMessage = LoginModel.validateAccount(account);

        // If there's no error, get the full account details
        if (errorMessage == null) {
            try {
                Account validatedAccount = Account.getAccount(account.getUsername());
                return new Object[]{null, validatedAccount}; // No error, return account
            } catch (SQLException e) {
                e.printStackTrace();
                return new Object[]{"Error retrieving account details", null};
            }
        }

        return new Object[]{errorMessage, null}; // Error found, no account
    }

    public static JPanel getLoginView() {
        loginView = new LoginView();
        return loginView.get();
    }
}
