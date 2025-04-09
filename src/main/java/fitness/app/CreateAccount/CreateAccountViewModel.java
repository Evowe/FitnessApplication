package fitness.app.CreateAccount;

import fitness.app.Objects.Account;
import fitness.app.Main;

import javax.swing.*;

public class CreateAccountViewModel {
    private static CreateAccountModel createAccountModel;

    public CreateAccountViewModel() {
        createAccountModel = new CreateAccountModel();
    }

    public String validateUsername(Account account) {
        return createAccountModel.validateUsername(account.getUsername());
    }

    public String validatePassword(Account account) {
        return createAccountModel.validatePassword(account.getPassword());
    }
}
