package fitness.app.Objects;

import java.sql.*;

public class AccountsDB extends DBTemplate {

    public AccountsDB(String dbName) {
        super(dbName);
    }


    @Override
    protected void createDatabase() throws SQLException {
        // Create Account table
        String[] columns = {
                "username TEXT NOT NULL",
                "password TEXT NOT NULL",
                "status TEXT DEFAULT 'active'",
                "role TEXT DEFAULT 'user'",
                "wallet INTEGER DEFAULT 0"
        };
        createTable("accounts", columns);
        //insertBaseUser();
    }



    public void addAccount(Account account) throws SQLException {
        String sql = "INSERT INTO accounts (username, password, status, role, wallet) VALUES (?, ?, ?, ?, ?)";


        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, account.getUsername());
            pstmt.setString(2, account.getPassword());
            pstmt.setString(3, account.getStatus());
            pstmt.setString(4, account.getRole());
            pstmt.setInt(5, account.getWallet());

            pstmt.executeUpdate();
            System.out.println("Account added: " + account.getUsername());

        } catch (SQLException e) {
            System.out.println("Error adding account: " + e.getMessage());
            throw e; //Throw e again for caller to handle
        }
    }

    public Connection getConnection() throws SQLException {
        //System.out.println("Trying to connect");
        try {
            // Load SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");

            // Create a connection to the SQLite database
            String dbURL = "jdbc:sqlite:accounts.db";
            return DriverManager.getConnection(dbURL);
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQLite JDBC driver not found", e);
        }
    }

    public Account getAccount(String username) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            //If it finds it will make an account object
            if (rs.next()) {
                return new Account(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("status"),
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error getting account: " + e.getMessage());
            throw e; //Throw e again for caller to handle
        }
        return null;
    }

    public boolean validLogin(String username, String password) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE username = ? AND password = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            /*
             This line will check if an object like that exists in the DB,
             if it does it will return 1 aka true and the login should
             succeed. -Ethan
             */
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error validating login: " + e.getMessage());
        }
        return false;
    }

    public boolean usernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM accounts WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.out.println("Error checking username: " + e.getMessage());
        }
        return false;
    }

    private void insertBaseUser() throws SQLException {
        String insertSQL = "INSERT INTO accounts (username, password, status, role, wallet) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            // Set the values for the base user
            pstmt.setString(1, "username");
            pstmt.setString(2, "password");
            pstmt.setString(3, "active");
            pstmt.setString(4, "user");
            pstmt.setInt(5, 1000);
            // Execute the insert
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting base user: " + e.getMessage());
            throw e;
        }
    }
}