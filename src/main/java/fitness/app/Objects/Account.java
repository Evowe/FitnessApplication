package fitness.app.Objects;

import java.util.HashMap;

public class Account {
    private static HashMap<String, Account> accounts = new HashMap<>();
    private String username;
    private String password;
    private String status;
    private String role;
    private int wallet;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Account(String username, String password, String status, String role) {
        this.username = username;
        this.password = password;
        this.status = status;
        this.role = role;
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

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public int getWallet() {return wallet;}
    public void setWallet(int wallet) {this.wallet = wallet;}

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
