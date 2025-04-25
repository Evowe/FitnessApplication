package fitness.app.AccountManagement.Login;

import fitness.app.BadProjectStructureSection.Objects.Account;

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
}
