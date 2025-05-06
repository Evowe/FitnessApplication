package Application.Databases;

import Application.Utility.Objects.Account;
import Application.Utility.Objects.CreditCard;

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

    //Constant for security questions
    public static final String[] SECURITY_QUESTIONS = {
            "What was the name of your first pet?",
            "In what city were you born?",
            "What was the make of your first car?",
            "What elementary school did you attend?",
            "What is your mother's maiden name?",
            "What was the name of your childhood best friend?",
            "What street did you grow up on?",
            "What was your favorite subject in high school?",
            "What is the name of the hospital you were born in?",
            "What was the first concert you attended?"
    };


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
                "weight_unit TEXT DEFAULT 'kg'",
                "XP INTEGER DEFAULT 0",
        };

        String[] securityColumns = {
                "username TEXT ",
                "question1_id INTEGER NOT NULL",
                "answer1 TEXT NOT NULL",
                "question2_id INTEGER NOT NULL",
                "answer2 TEXT NOT NULL",
                "question3_id INTEGER NOT NULL",
                "answer3 TEXT NOT NULL",
                "FOREIGN KEY (username) REFERENCES accounts(username) ON DELETE CASCADE"
        };

        createTable("accounts", columns);
        createTable("security_questions", securityColumns);

        updateAccountsTableForCreditCards();
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

    // This is extremely insecure, simply there for our project's scope.
    public void insertBaseAccounts() {
        try {
            if (!Account.usernameExists("username")) {
                Account userAccount = new Account("username", "password", "active", "user");
                userAccount.addAccount();
            }

            if (!Account.usernameExists("admin")) {
                Account adminAccount = new Account("admin", "admin123", "active", "admin");
                adminAccount.addAccount();
            }
        } catch (SQLException e) {
            System.out.println("Error inserting accounts: " + e.getMessage());
            e.printStackTrace();
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

    public static int getXP(String username) throws SQLException {
        String sql = "SELECT XP FROM accounts WHERE username = ?";
        try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("xp");
            }
        }
        return 0;
    }

    public static void addxp(String username, int xp) throws SQLException {
        String sql = "UPDATE accounts SET XP = XP + ? WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, xp);           // Set XP increment first
            pstmt.setString(2, username);  // Set username second
            pstmt.executeUpdate();
        }
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

    public boolean setupSecurityQuestions(String username,
                                          int question1Id, String answer1,
                                          int question2Id, String answer2,
                                          int question3Id, String answer3) throws SQLException {
        // Validate question IDs
        if (question1Id < 0 || question1Id >= SECURITY_QUESTIONS.length ||
                question2Id < 0 || question2Id >= SECURITY_QUESTIONS.length ||
                question3Id < 0 || question3Id >= SECURITY_QUESTIONS.length) {
            throw new IllegalArgumentException("Invalid question ID");
        }

        // Validate answers aren't empty
        if (answer1 == null || answer1.trim().isEmpty() ||
                answer2 == null || answer2.trim().isEmpty() ||
                answer3 == null || answer3.trim().isEmpty()) {
            throw new IllegalArgumentException("Security answers cannot be empty");
        }

        // Make sure username exists
        if (!usernameExists(username)) {
            return false;
        }

        // Check that all questions are different
        if (question1Id == question2Id || question1Id == question3Id || question2Id == question3Id) {
            throw new IllegalArgumentException("All security questions must be different");
        }

        String sql = "INSERT OR REPLACE INTO security_questions (username, question1_id, answer1, " +
                "question2_id, answer2, question3_id, answer3) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setInt(2, question1Id);
            pstmt.setString(3, hashPassword(answer1.toLowerCase().trim())); // Using existing hash method
            pstmt.setInt(4, question2Id);
            pstmt.setString(5, hashPassword(answer2.toLowerCase().trim()));
            pstmt.setInt(6, question3Id);
            pstmt.setString(7, hashPassword(answer3.toLowerCase().trim()));

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public int[] getSecurityQuestionIds(String username) throws SQLException {

        String sql = "SELECT question1_id, question2_id, question3_id FROM security_questions WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int[] questionIds = new int[3];
                    questionIds[0] = rs.getInt("question1_id");
                    questionIds[1] = rs.getInt("question2_id");
                    questionIds[2] = rs.getInt("question3_id");
                    return questionIds;
                }
            }
        }

        return null;
    }

    public boolean verifySecurityAnswer(String username, int questionNumber, String answer) throws SQLException {
        if (questionNumber < 1 || questionNumber > 3) {
            throw new IllegalArgumentException("Question number must be 1, 2, or 3");
        }

        String answerColumn = "answer" + questionNumber;
        String sql = "SELECT " + answerColumn + " FROM security_questions WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String storedAnswer = rs.getString(answerColumn);
                    String hashedInput = hashPassword(answer.toLowerCase().trim());
                    return storedAnswer.equals(hashedInput);
                }
            }
        }

        return false;
    }

    public boolean resetPasswordWithSecurityAnswers(String username,
                                                    String answer1,
                                                    String answer2,
                                                    String answer3,
                                                    String newPassword) throws SQLException {
        if (!verifySecurityAnswer(username, 1, answer1) ||
                !verifySecurityAnswer(username, 2, answer2) ||
                !verifySecurityAnswer(username, 3, answer3)) {
            return false;
        }
        return changePassword(username, newPassword);
    }

    public String getQuestionText(int questionId) {
        if (questionId >= 0 && questionId < SECURITY_QUESTIONS.length) {
            return SECURITY_QUESTIONS[questionId];
        }
        return null;
    }

    public boolean addCreditCardToAccount(String username, CreditCard card) throws SQLException {
        String sql = "UPDATE accounts SET cardNumber = ?, expiryDate = ?, cvv = ?, cardHolder = ?, zipCode = ? WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, card.getCardNumber());
            pstmt.setString(2, card.getExpiryDate());
            pstmt.setString(3, card.getCvv());
            pstmt.setString(4, card.getCardHolder());
            pstmt.setString(5, card.getZipCode());
            pstmt.setString(6, username);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public CreditCard getAccountCreditCard(String username) throws SQLException {
        String sql = "SELECT cardNumber, expiryDate, cvv, cardHolder, zipCode FROM accounts WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() && rs.getString("cardNumber") != null) {
                return new CreditCard(
                        rs.getString("cardNumber"),
                        rs.getString("expiryDate"),
                        rs.getString("cvv"),
                        rs.getString("cardHolder"),
                        rs.getString("zipCode")
                );
            }
        }
        return null;
    }


    public boolean removeCreditCardFromAccount(String username) throws SQLException {
        String sql = "UPDATE accounts SET cardNumber = NULL, expiryDate = NULL, cvv = NULL, cardHolder = NULL, zipCode = NULL WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public void updateAccountsTableForCreditCards() throws SQLException {
        String[] columns = {
                "cardNumber TEXT",
                "expiryDate TEXT",
                "cvv TEXT",
                "cardHolder TEXT",
                "zipCode TEXT"
        };

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            for (String column : columns) {
                String columnName = column.split(" ")[0];
                try {
                    ResultSet rs = stmt.executeQuery("SELECT " + columnName + " FROM accounts LIMIT 1");
                    rs.close();
                } catch (SQLException e) {
                    stmt.executeUpdate("ALTER TABLE accounts ADD COLUMN " + column);
                }
            }
        }
    }

    public boolean linkCreditCardToAccount(String username, String cardNumber) throws SQLException {
        String sql = "UPDATE accounts SET linkedCardNumber = ? WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cardNumber);
            pstmt.setString(2, username);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }


}