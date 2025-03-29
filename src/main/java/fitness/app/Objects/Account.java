package fitness.app.Objects;

import java.sql.SQLException;

public class Account {
    private String username;
    private String password;
    private String status;
    private String role;
    private int wallet;
    private AccountsDB accountsDB;
    private int Calories;
    private Double Weight;
    private Double Sleep;

    public int getCalories() {
        return Calories;
    }
    public void setCalories(int calories) {
        Calories = calories;
    }
    public Double getWeight() {
        return Weight;
    }
    public void setWeight(Double weight) {
        Weight = weight;
    }
    public Double getSleep() {
        return Sleep;
    }
    public void setSleep(Double sleep) {
        Sleep = sleep;
    }


    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.accountsDB = (AccountsDB) DatabaseManager.getDatabase("accounts");
    }

    public Account(String username, String password, String status, String role) {
        this.username = username;
        this.password = password;
        this.status = status;
        this.role = role;
        this.accountsDB = (AccountsDB) DatabaseManager.getDatabase("accounts");
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

    public void addAccount() throws SQLException {
        accountsDB.addAccount(this);
    }

    public static Account getAccount(String username) throws SQLException {
        AccountsDB accountsDB = (AccountsDB) DatabaseManager.getDatabase("accounts");
        return accountsDB.getAccount(username);
    }

    public static boolean validateLogin(String username, String password) throws SQLException {
        AccountsDB accountsDB = (AccountsDB) DatabaseManager.getDatabase("accounts");
        return accountsDB.validLogin(username, password);  // Validate login credentials
    }

    public static boolean usernameExists(String username) throws SQLException {
        AccountsDB accountsDB = (AccountsDB) DatabaseManager.getDatabase("accounts");
        return accountsDB.usernameExists(username);  // Check if username exists in the database
    }
}
