package fitness.app.BadProjectStructureSection.Databases;

import fitness.app.BadProjectStructureSection.Objects.Account;
import fitness.app.BadProjectStructureSection.Objects.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

public class AccountsDB extends DBTemplate {

    public AccountsDB() {
        super("accounts");
    }


    @Override
    protected void createTables() throws SQLException {
        // Create Account table'

        String[] columns = {
                "username TEXT NOT NULL",
                "password TEXT NOT NULL",
                "status TEXT DEFAULT 'active'",
                "role TEXT DEFAULT 'user'",
                "wallet INTEGER DEFAULT 0",
                "theme TEXT DEFAULT 'dark'",
                "notifications INTEGER DEFAULT 1",
                "weight_unit TEXT DEFAULT 'kg'"
        };


        createTable("accounts", columns);

    }

    public void addAccount(Account account) throws SQLException {
        String sql = "INSERT INTO accounts (username, password, status, role, wallet) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, account.getUsername());
            pstmt.setString(2, hashPassword(account.getPassword()));
            pstmt.setString(3, account.getStatus());
            pstmt.setString(4, account.getRole());
            pstmt.setInt(5, account.getWallet());

            pstmt.executeUpdate();

            // Create ItemsDB instance
            ItemsDB itemsDB = DatabaseManager.getItemsDB();

            try {
                // Ensure all rocket skins exist
                itemsDB.createDefaultRocketItems();

                // Get the ID of the default rocket
                int defaultRocketId = itemsDB.getItemIdByName("Default Rocket");

                if (defaultRocketId != -1) {
                    // Use account.getUsername() instead of username
                    itemsDB.giveItemToUser(account.getUsername(), defaultRocketId);
                    itemsDB.equipItem(account.getUsername(), defaultRocketId);
                    System.out.println("Default rocket given to: " + account.getUsername());
                } else {
                    System.err.println("Default rocket not found in database");
                }
            } catch (SQLException e) {
                System.err.println("Error giving default rocket to new user: " + e.getMessage());
                // Don't re-throw this exception as we want account creation to succeed
                // even if giving the rocket fails
            }

            System.out.println("Account added: " + account.getUsername());

        } catch (SQLException e) {
            System.out.println("Error adding account: " + e.getMessage());
            throw e; // Throw e again for caller to handle
        }
    }

    public Account getAccount(String username) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Account account = new Account(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("status"),
                        rs.getString("role")
                );

                account.setWallet(rs.getInt("wallet"));
                account.setId(rs.getInt("id"));

                // Set preferences if columns exist
                try {
                    account.setTheme(rs.getString("theme"));
                    account.setNotifications(rs.getInt("notifications") == 1);
                    account.setWeightUnit(rs.getString("weight_unit"));
                } catch (SQLException e) {
                    // Columns might not exist yet in older database versions
                    System.out.println("Note: Preference columns may not exist yet: " + e.getMessage());
                }

                return account;
            }
        } catch (SQLException e) {
            System.out.println("Error getting account: " + e.getMessage());
            throw e;
        }
        
        return null;
    }


    public Account getAccountNoClose(String username) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE username = ?";

        try {
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Account account = new Account(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("status"),
                        rs.getString("role")
                );

                account.setWallet(rs.getInt("wallet"));
                account.setId(rs.getInt("id"));

                // Set preferences if columns exist
                try {
                    account.setTheme(rs.getString("theme"));
                    account.setNotifications(rs.getInt("notifications") == 1);
                    account.setWeightUnit(rs.getString("weight_unit"));
                } catch (SQLException e) {
                    // Columns might not exist yet in older database versions
                    System.out.println("Note: Preference columns may not exist yet: " + e.getMessage());
                }

                return account;
            }
        } catch (SQLException e) {
            System.out.println("Error getting account: " + e.getMessage());
            throw e;
        }

        return null;
    }

    public boolean validLogin(String username, String password) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE username = ? AND password = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, hashPassword(password));
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

    public boolean changePassword(String username, String newPassword) throws SQLException {
        String sql = "UPDATE accounts SET password = ? WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, hashPassword(newPassword));
            pstmt.setString(2, username);

            int rowsAffected = pstmt.executeUpdate();

            // Return true if the update was successful (affected exactly one row)
            return rowsAffected == 1;

        } catch (SQLException e) {
            System.out.println("Error changing password: " + e.getMessage());
            throw e; // Throw exception for caller to handle
        }
    }

    public boolean updateUserPreferences(String username, String theme, boolean notifications, String weightUnit) throws SQLException {
        String sql = "UPDATE accounts SET theme = ?, notifications = ?, weight_unit = ? WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, theme);
            pstmt.setInt(2, notifications ? 1 : 0);
            pstmt.setString(3, weightUnit);
            pstmt.setString(4, username);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected == 1;

        } catch (SQLException e) {
            System.out.println("Error updating user preferences: " + e.getMessage());
            throw e;
        }
    }
    
    //Still necessary? Don't see implementation anywhere. Was it replaced by impl. in main?
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
    
    public List<Account> getAllUsers() throws SQLException {
    	//db query to select all users
		String query = "SELECT * FROM accounts WHERE role != 'admin'";
		List<Account> allUsers = new ArrayList<>();
    	
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			
			ResultSet rs = pstmt.executeQuery();
			
			allUsers = new ArrayList<>();
			
			//Processes all query results and creates and account object for each row
			while (rs.next()) {
				Account account = new Account(
						rs.getString("username"),
						rs.getString("password"),
						rs.getString("status"),
						rs.getString("role")
				);
				
				account.setId(rs.getInt("id"));
				account.setWallet(rs.getInt("wallet"));
				
				allUsers.add(account);
			}
			
		} catch (SQLException e) {
			System.out.println("Error getting users: " + e.getMessage());
		}

    	return allUsers;
    }
    
    public void promoteUser(Integer id) {
    	String sql = "UPDATE accounts SET role = ? WHERE id = ?";
    	
    	try (Connection conn = getConnection();
    		PreparedStatement pstmt = conn.prepareStatement(sql)) {
    		
    		pstmt.setString(1, "trainer");
    		pstmt.setInt(2, id);
    		pstmt.executeUpdate();
    		
    	} catch (SQLException e) {
			System.out.println("Error: Could not promote user through admin action");
		}
    }
    
    public void depromoteUser(Integer id) {
    	String sql = "UPDATE accounts SET role = ? WHERE id = ?";
    	
    	try (Connection conn = getConnection();
    		PreparedStatement pstmt = conn.prepareStatement(sql)) {
    		
    		pstmt.setString(1, "user");
    		pstmt.setInt(2, id);
    		pstmt.executeUpdate();
    		
    	} catch (SQLException e) {
    		System.out.println("Error: Could not depromote user through admin action");
    	}
    }

    public void updateWallet(String username, Integer wallet) throws SQLException {
        String sql = "UPDATE accounts SET wallet = ? WHERE username = ?";
        try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, wallet);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
        }
    }

    public int getWallet(String username) throws SQLException {
        String sql = "SELECT wallet FROM accounts WHERE username = ?";
        try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("wallet");
            }
        }
        return -1000000;
    }

    public int getCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM accounts";
        try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }

    private String hashPassword(String plainPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(
                    plainPassword.getBytes(StandardCharsets.UTF_8));


            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("SHA-256 algorithm not available: " + e.getMessage());
            // Fall back to plaintext in the worst case
            return plainPassword;
        }
    }

    // This function is temporary to migrate all plaintext passwords to hash
    // Only needs to run once. Uncomment and add accountsDB.migratePasswordsToHash();
    // in the db manager after the account db is initialized.
    // Ask me for more guidance -Ethan

    /*
    public void migratePasswordsToHash(){
        String selectSql = "SELECT username, password FROM accounts";
        String updateSql = "UPDATE accounts SET password = ? WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {

            ResultSet rs = selectStmt.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                String plainPassword = rs.getString("password");
                String hashedPassword = hashPassword(plainPassword);

                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.setString(1, hashedPassword);
                    updateStmt.setString(2, username);
                    updateStmt.executeUpdate();
                    System.out.println("Migrated password for user: " + username);
                }
            }

            System.out.println("All passwords have been migrated to SHA-256 hash");
        } catch (SQLException e) {
            System.out.println("Error migrating passwords: " + e.getMessage());
        }
    }
     */
    
}