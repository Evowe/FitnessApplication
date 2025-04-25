package fitness.app.BadProjectStructureSection.Databases;

import fitness.app.BadProjectStructureSection.Objects.CreditCard;

import java.sql.*;

public class CreditCardDB extends DBTemplate {

    public CreditCardDB() {
        super("creditCards");
    }

    @Override
    public void createTables() throws SQLException {
        // Create Credit Card table
        String[] columns = {
                "cardNumber TEXT NOT NULL",
                "expiryDate TEXT NOT NULL",
                "cvv TEXT NOT NULL",
                "cardHolder TEXT NOT NULL",
                "zipCode TEXT NOT NULL"
        };
        createTable("creditCards", columns);
    }

    public void addCreditCard(CreditCard card) throws SQLException {
        String sql = "INSERT INTO creditCards (cardNumber, expiryDate, cvv, cardHolder, zipCode) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, card.getCardNumber());
            pstmt.setString(2, card.getExpiryDate());
            pstmt.setString(3, card.getCvv());
            pstmt.setString(4, card.getCardHolder());
            pstmt.setString(5, card.getZipCode());

            pstmt.executeUpdate();
            System.out.println("Credit card added: " + card.getCardNumber());

        } catch (SQLException e) {
            System.out.println("Error adding credit card: " + e.getMessage());
            throw e;
        }
    }

    protected CreditCard getCreditCard(String cardNumber) throws SQLException {
        String sql = "SELECT * FROM creditCards WHERE cardNumber = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cardNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new CreditCard(
                        rs.getString("cardNumber"),
                        rs.getString("expiryDate"),
                        rs.getString("cvv"),
                        rs.getString("cardHolder"),
                        rs.getString("zipCode")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error getting credit card: " + e.getMessage());
            throw e;
        }
        return null;
    }

    public boolean updateCreditCard(CreditCard card) throws SQLException {
        String sql = "UPDATE creditCards SET expiryDate = ?, cvv = ?, cardHolder = ?, zipCode = ? WHERE cardNumber = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, card.getExpiryDate());
            pstmt.setString(2, card.getCvv());
            pstmt.setString(3, card.getCardHolder());
            pstmt.setString(4, card.getZipCode());
            pstmt.setString(5, card.getCardNumber());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error updating credit card: " + e.getMessage());
            throw e;
        }
    }

    public boolean cardExists(String cardNumber) throws SQLException {
        String sql = "SELECT COUNT(*) FROM creditCards WHERE cardNumber = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cardNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error checking card existence: " + e.getMessage());
            throw e;
        }
        return false;
    }

}