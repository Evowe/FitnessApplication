package fitness.app.Components;

import java.util.HashMap;

public class Account {
    private static HashMap<String, Account> accounts = new HashMap<>();
    private String username;
    private String password;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void addAccount() {
        accounts.put(this.username, this);
    }

    public Account getAccount() {
        return accounts.get(username);
    }

    public static boolean validLogin(Account account) {
        String username = account.getUsername();
        String password = account.getPassword();
        if (accounts.containsKey(username)) {
            if (accounts.get(username).getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public static boolean usernameExists(String username) {
        return accounts.containsKey(username);
    }
}
