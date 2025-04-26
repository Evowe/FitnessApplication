package Application.Utility.Objects;

import Application.Utility.Databases.AccountsDB;
import Application.Utility.Databases.DatabaseManager;

import java.sql.SQLException;

public class Account {
    private Integer id;
    private String username;
    private String password;
    private String status;
    private String role;
    private int wallet;
    private int Calories;
    private Double Weight;
    private Double Sleep;
    private String theme = "dark";
    private boolean notifications = true;
    private String weightUnit = "kg";
    private CreditCard card;


    private static AccountsDB getAccountsDB() {
        return DatabaseManager.getAccountsDB();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
        this.status = "active";
        this.role = "user";
        setCalories(0);
        setWeight(0.0);
        setSleep(0.0);
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

    public String getTheme() {
        return theme;
    }

    /*
    public Boolean getMode(){
        if(theme.equalsIgnoreCase("light")){
            return false;
        }
        return true;
    }


     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    public boolean isNotificationsEnabled() {
        return notifications;
    }

    public void setNotifications(boolean notifications) {
        this.notifications = notifications;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    public void setCard(CreditCard card) {this.card = card;}

    public Boolean hasCard() {return card != null;}

    public void addAccount() throws SQLException {
        getAccountsDB().addAccount(this);
    }

    public static Account getAccount(String username) throws SQLException {
        return getAccountsDB().getAccount(username);
    }

    public static Account getAccountNoClose(String username) throws SQLException {
        return getAccountsDB().getAccountNoClose(username);
    }

    public static Account updateWallet(String username, int val) throws SQLException {
        AccountsDB accountsDB = getAccountsDB();
        int wallet = accountsDB.getWallet(username);
        val += wallet;
        accountsDB.updateWallet(username, val);
        return accountsDB.getAccount(username);
    }

    public static boolean validateLogin(String username, String password) throws SQLException {
        return getAccountsDB().validLogin(username, password);  // Validate login credentials
    }

    public static boolean usernameExists(String username) throws SQLException {
        return getAccountsDB().usernameExists(username);  // Check if username exists in the database
    }

    public static boolean changePassword(String username, String newPassword) throws SQLException {
        return getAccountsDB().changePassword(username, newPassword);
    }

    public boolean savePreferences() throws SQLException {
        return getAccountsDB().updateUserPreferences(username, theme, notifications, weightUnit);
    }



    public Object [] getString(){
        Object [] str = new Object [2];
        str[0] = this.username;
        str[1] = this.role;

        return str;
    }


}