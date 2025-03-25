package fitness.app.Components.CreateAccount;

import fitness.app.Components.Account;
import fitness.app.Components.Login.LoginView;
import fitness.app.Components.Main;

import javax.swing.*;

public class CreateAccountViewModel {
    private static CreateAccountView createAccountView;

    public static JPanel getCreateAccountView() {
        createAccountView = new CreateAccountView();
        return createAccountView.getCreateAccountView();
    }

    public static String validateUsername(Account account) {
        return CreateAccountModel.validateUsername(account.getUsername());
    }

    public static String validatePassword(Account account) {
        return CreateAccountModel.validatePassword(account.getPassword());
    }

    public static void setWindow() {
        Main.setWindow("LoginPage");
    }
}
