package Application.AccountManagement.CreateAccount;

import Application.Utility.Objects.Account;

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
