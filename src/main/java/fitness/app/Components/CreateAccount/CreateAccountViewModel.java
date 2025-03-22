package fitness.app.Components.CreateAccount;

import fitness.app.Components.Account;
import fitness.app.Components.Login.LoginView;

import javax.swing.*;

public class CreateAccountViewModel {
    private static CreateAccountView createAccountView;

    public static JPanel getCreateAccountView() {
        createAccountView = new CreateAccountView();
        return createAccountView.getCreateAccountView();
    }

}
