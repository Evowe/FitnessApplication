package Utility.DatabaseTests;

import Application.Databases.CreditCardDB;
import Application.Databases.DatabaseManager;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Application.Utility.Objects.CreditCard;

public class CreditCardDBTest {

    private CreditCardDB creditCardDB;
    private final String TEST_CARD_PREFIX = "4111111111" + UUID.randomUUID().toString().substring(0, 6).replaceAll("-", "");

    @BeforeEach
    public void setUp() {
        creditCardDB = DatabaseManager.getCreditCardDB();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        Connection conn = null;
        try {
            conn = creditCardDB.getConnection();

            try (PreparedStatement pstmt = conn.prepareStatement(
                    "DELETE FROM creditCards WHERE cardNumber LIKE ?")) {
                pstmt.setString(1, TEST_CARD_PREFIX + "%");
                pstmt.executeUpdate();
            }
        } finally {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
    }

    private String getTestCardNumber(String suffix) {
        return TEST_CARD_PREFIX + suffix;
    }

    @Test
    public void testAddAndGetCreditCard() throws SQLException {
        String cardNumber = getTestCardNumber("1234");
        CreditCard testCard = new CreditCard(cardNumber, "12/25", "123", "Test User", "12345");
        creditCardDB.addCreditCard(testCard);

        CreditCard retrievedCard = creditCardDB.getCreditCard(cardNumber);

        assertNotNull(retrievedCard);
        assertEquals(cardNumber, retrievedCard.getCardNumber());
        assertEquals("12/25", retrievedCard.getExpiryDate());
        assertEquals("123", retrievedCard.getCvv());
        assertEquals("Test User", retrievedCard.getCardHolder());
        assertEquals("12345", retrievedCard.getZipCode());
    }

    @Test
    public void testCardExists() throws SQLException {
        String cardNumber = getTestCardNumber("5678");
        CreditCard testCard = new CreditCard(cardNumber, "12/25", "123", "Test User", "12345");
        creditCardDB.addCreditCard(testCard);

        assertTrue(creditCardDB.cardExists(cardNumber), "Existing card should return true");
        assertFalse(creditCardDB.cardExists(getTestCardNumber("9999")), "Non-existent card should return false");
    }

    @Test
    public void testUpdateCreditCard() throws SQLException {
        String cardNumber = getTestCardNumber("4321");
        CreditCard testCard = new CreditCard(cardNumber, "12/25", "123", "Test User", "12345");
        creditCardDB.addCreditCard(testCard);

        assertTrue(creditCardDB.cardExists(cardNumber), "Card should exist after adding");

        CreditCard updatedCard = new CreditCard(cardNumber, "01/26", "456", "Updated User", "54321");
        boolean result = creditCardDB.updateCreditCard(updatedCard);

        assertTrue(result, "Update should return true for successful update");

        CreditCard retrievedCard = creditCardDB.getCreditCard(cardNumber);
        assertNotNull(retrievedCard);
        assertEquals("01/26", retrievedCard.getExpiryDate());
        assertEquals("456", retrievedCard.getCvv());
        assertEquals("Updated User", retrievedCard.getCardHolder());
        assertEquals("54321", retrievedCard.getZipCode());
    }

    @Test
    public void testUpdateNonExistentCard() throws SQLException {
        String nonExistentCardNumber = getTestCardNumber("9876");
        CreditCard nonExistentCard = new CreditCard(nonExistentCardNumber, "12/25", "123", "Test User", "12345");

        boolean result = creditCardDB.updateCreditCard(nonExistentCard);

        assertFalse(result, "Update should return false for non-existent card");
    }

    @Test
    public void testGetNonExistentCard() throws SQLException {
        String nonExistentCardNumber = getTestCardNumber("5432");

        CreditCard retrievedCard = creditCardDB.getCreditCard(nonExistentCardNumber);

        assertNull(retrievedCard, "Getting non-existent card should return null");
    }
}