package fitness.app.Utility.Objects;

public class Admin extends Account{
    public Admin(String username, String password, String status) {
        super(username, password, status, "Admin");
    }
    public void resetPassword(Account account, String newPassword) {
        account.setPassword(newPassword);
    }

    public void suspendAccount(Account account) {
        account.setStatus("Suspended");
    }
}
