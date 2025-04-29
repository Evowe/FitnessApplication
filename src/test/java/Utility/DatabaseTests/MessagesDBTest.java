package Utility.DatabaseTests;

import Application.Databases.MessagesDB;
import Application.Databases.DatabaseManager;
import Application.Utility.Objects.Message;
import Application.Utility.Objects.Account;

import static Application.Databases.DBTemplate.getConnection;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MessagesDBTest {

    private MessagesDB messagesDB;
    private Account testSender;
    private Account testReceiver;
    private final String TEST_USERNAME_PREFIX = "test_msg_" + UUID.randomUUID().toString().substring(0, 8) + "_";

    @BeforeEach
    public void setUp() throws SQLException {
        messagesDB = DatabaseManager.getMessagesDB();

        testSender = new Account(getTestUsername("sender"), "password123", "active", "user");
        testReceiver = new Account(getTestUsername("receiver"), "password123", "active", "user");

        DatabaseManager.getAccountsDB().addAccount(testSender);
        DatabaseManager.getAccountsDB().addAccount(testReceiver);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        Connection conn = null;
        try {
            conn = getConnection();

            try (PreparedStatement pstmt = conn.prepareStatement(
                    "DELETE FROM messages WHERE sender LIKE ? OR receiver LIKE ?")) {
                pstmt.setString(1, TEST_USERNAME_PREFIX + "%");
                pstmt.setString(2, TEST_USERNAME_PREFIX + "%");
                pstmt.executeUpdate();
            }

            try (PreparedStatement pstmt = conn.prepareStatement(
                    "DELETE FROM accounts WHERE username LIKE ?")) {
                pstmt.setString(1, TEST_USERNAME_PREFIX + "%");
                pstmt.executeUpdate();
            }
        } finally {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
    }

    private String getTestUsername(String baseName) {
        return TEST_USERNAME_PREFIX + baseName;
    }

    @Test
    public void testAddMessage() throws SQLException {
        Message testMessage = new Message(
                "Hello, this is a test message",
                testSender,
                testReceiver,
                Message.getType("FRIEND_REQUEST")
        );

        messagesDB.addMessage(testMessage);

        List<Object[]> basicIncoming = messagesDB.getAllIncoming(testReceiver.getUsername());
        assertFalse(basicIncoming.isEmpty(), "Basic message list should not be empty");

        List<Message> incomingMessages = messagesDB.getAllIncomingMessages(testReceiver.getUsername());

        if (incomingMessages.isEmpty()) {
            System.out.println("Debug: incomingMessages list is empty!");
            System.out.println("Receiver username: " + testReceiver.getUsername());

            Connection conn = messagesDB.getConnection();
            try {
                PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM messages WHERE receiver = ?");
                pstmt.setString(1, testReceiver.getUsername());
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    System.out.println("Direct DB query found " + rs.getInt(1) + " messages");
                }
                rs.close();
                pstmt.close();
            } finally {
                conn.close();
            }
        }

        assertFalse(incomingMessages.isEmpty(), "Message list should not be empty");
        assertEquals(1, incomingMessages.size(), "Should have exactly one message");
        assertEquals("Hello, this is a test message", incomingMessages.get(0).getMessage());
        assertEquals(testSender.getUsername(), incomingMessages.get(0).getSenderUsername());
        assertEquals(testReceiver.getUsername(), incomingMessages.get(0).getReceiverUsername());
        assertEquals("FRIEND_REQUEST", incomingMessages.get(0).getTypeString());
    }

    @Test
    public void testRespondToMessage() throws SQLException {
        Message testMessage = new Message(
                "Friend request message",
                testSender,
                testReceiver,
                Message.getType("FRIEND_REQUEST")
        );
        messagesDB.addMessage(testMessage);

        testMessage.setResponse("I accept your friend request");
        testMessage.setResponseType(Message.getType("ACCEPT_FRIEND"));
        messagesDB.respond(testMessage);

        List<Object[]> responses = messagesDB.getAllResponses(testSender.getUsername());

        assertFalse(responses.isEmpty(), "Response list should not be empty");
        assertEquals("I accept your friend request", responses.get(0)[0], "Response text should match");
        assertEquals(testReceiver.getUsername(), responses.get(0)[1], "Responder username should match");
        assertEquals("ACCEPT_FRIEND", responses.get(0)[2], "Response type should match");
    }

    @Test
    public void testGetAllIncoming() throws SQLException {
        Message message1 = new Message(
                "Test message 1",
                testSender,
                testReceiver,
                Message.getType("FRIEND_REQUEST")
        );
        Message message2 = new Message(
                "Test message 2",
                testSender,
                testReceiver,
                Message.getType("FRIEND_REQUEST")
        );

        messagesDB.addMessage(message1);
        messagesDB.addMessage(message2);

        List<Object[]> incomingMessages = messagesDB.getAllIncoming(testReceiver.getUsername());

        assertEquals(2, incomingMessages.size(), "Should have two incoming messages");
    }

    @Test
    public void testAcceptFriendRequest() throws SQLException {
        Message friendRequest = new Message(
                "I would like to be your friend",
                testSender,
                testReceiver,
                Message.getType("FRIEND_REQUEST")
        );
        messagesDB.addMessage(friendRequest);

        messagesDB.acceptFriendRequestMessages(testReceiver.getUsername(), testSender.getUsername());

        List<Object[]> responses = messagesDB.getAllResponses(testSender.getUsername());

        assertFalse(responses.isEmpty(), "Response list should not be empty");
        assertEquals(testSender.getUsername() + " accepted your friend request", responses.get(0)[0]);
        assertEquals("ACCEPT_FRIEND", responses.get(0)[2]);
    }

    @Test
    public void testDeclineFriendRequest() throws SQLException {
        Message friendRequest = new Message(
                "I would like to be your friend",
                testSender,
                testReceiver,
                Message.getType("FRIEND_REQUEST")
        );
        messagesDB.addMessage(friendRequest);

        messagesDB.deleteFriendRequestMessages(testReceiver.getUsername(), testSender.getUsername());

        List<Object[]> responses = messagesDB.getAllResponses(testSender.getUsername());

        assertFalse(responses.isEmpty(), "Response list should not be empty");
        assertEquals(testSender.getUsername() + " declined your friend request", responses.get(0)[0]);
        assertEquals("REJECT_FRIEND", responses.get(0)[2]);
    }

}