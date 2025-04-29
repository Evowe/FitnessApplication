package Utility.DatabaseTests;

import Application.Databases.DatabaseManager;
import Application.Databases.FriendsDB;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FriendsDBTest {

    private FriendsDB friendsDB;
    private final String TEST_USER_PREFIX = "test_user_" + UUID.randomUUID().toString().substring(0, 8) + "_";

    @BeforeEach
    public void setUp() {
        friendsDB = DatabaseManager.getFriendsDB();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        Connection conn = null;
        try {
            conn = friendsDB.getConnection();

            try (PreparedStatement pstmt = conn.prepareStatement(
                    "DELETE FROM friends WHERE user_username LIKE ? OR friend_username LIKE ?")) {
                pstmt.setString(1, TEST_USER_PREFIX + "%");
                pstmt.setString(2, TEST_USER_PREFIX + "%");
                pstmt.executeUpdate();
            }
        } finally {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
    }

    private String getTestUsername(String baseName) {
        return TEST_USER_PREFIX + baseName;
    }

    @Test
    public void testSendFriendRequest() throws SQLException {
        String user1 = getTestUsername("sender");
        String user2 = getTestUsername("receiver");

        friendsDB.sendFriendRequest(user1, user2);

        List<String> pendingRequests = friendsDB.getPendingRequests(user2);
        assertTrue(pendingRequests.contains(user1), "Friend request should be in pending list");
    }

    @Test
    public void testAcceptFriendRequest() throws SQLException {
        String user1 = getTestUsername("requester");
        String user2 = getTestUsername("acceptor");

        friendsDB.sendFriendRequest(user1, user2);

        friendsDB.acceptFriendRequest(user2, user1);

        List<Object[]> user1Friends = friendsDB.getFriends(user1);
        boolean foundUser2 = false;
        for (Object[] friend : user1Friends) {
            if (friend[0].equals(user2)) {
                foundUser2 = true;
                break;
            }
        }
        assertTrue(foundUser2, "User2 should be in User1's friends list");

        List<Object[]> user2Friends = friendsDB.getFriends(user2);
        boolean foundUser1 = false;
        for (Object[] friend : user2Friends) {
            if (friend[0].equals(user1)) {
                foundUser1 = true;
                break;
            }
        }
        assertTrue(foundUser1, "User1 should be in User2's friends list");

        List<String> pendingRequests = friendsDB.getPendingRequests(user2);
        assertFalse(pendingRequests.contains(user1), "Request should no longer be pending after acceptance");
    }

    @Test
    public void testDeclineFriendRequest() throws SQLException {
        String user1 = getTestUsername("requester");
        String user2 = getTestUsername("decliner");

        friendsDB.sendFriendRequest(user1, user2);

        friendsDB.declineFriendRequest(user2, user1);

        List<String> pendingRequests = friendsDB.getPendingRequests(user2);
        assertFalse(pendingRequests.contains(user1), "Request should no longer be pending after decline");

        List<Object[]> user1Friends = friendsDB.getFriends(user1);
        boolean foundUser2 = false;
        for (Object[] friend : user1Friends) {
            if (friend[0].equals(user2)) {
                foundUser2 = true;
                break;
            }
        }
        assertFalse(foundUser2, "User2 should not be in User1's friends list after decline");
    }

    @Test
    public void testGetPendingRequests() throws SQLException {
        String user1 = getTestUsername("receiver");
        String sender1 = getTestUsername("sender1");
        String sender2 = getTestUsername("sender2");

        friendsDB.sendFriendRequest(sender1, user1);
        friendsDB.sendFriendRequest(sender2, user1);

        List<String> pendingRequests = friendsDB.getPendingRequests(user1);

        assertEquals(2, pendingRequests.size(), "Should have 2 pending requests");
        assertTrue(pendingRequests.contains(sender1), "Should have request from sender1");
        assertTrue(pendingRequests.contains(sender2), "Should have request from sender2");
    }

    @Test
    public void testGetFriends() throws SQLException {
        String user1 = getTestUsername("user");
        String friend1 = getTestUsername("friend1");
        String friend2 = getTestUsername("friend2");

        friendsDB.sendFriendRequest(friend1, user1);
        friendsDB.acceptFriendRequest(user1, friend1);

        friendsDB.sendFriendRequest(friend2, user1);
        friendsDB.acceptFriendRequest(user1, friend2);

        List<Object[]> friends = friendsDB.getFriends(user1);

        assertEquals(2, friends.size(), "Should have 2 friends");

        boolean foundFriend1 = false;
        boolean foundFriend2 = false;
        for (Object[] friend : friends) {
            if (friend[0].equals(friend1)) {
                foundFriend1 = true;
            }
            if (friend[0].equals(friend2)) {
                foundFriend2 = true;
            }
        }

        assertTrue(foundFriend1, "Should have friend1 in friends list");
        assertTrue(foundFriend2, "Should have friend2 in friends list");
    }

    @Test
    public void testRemoveFriend() throws SQLException {
        String user1 = getTestUsername("user");
        String friend1 = getTestUsername("friend");

        friendsDB.sendFriendRequest(friend1, user1);
        friendsDB.acceptFriendRequest(user1, friend1);

        List<Object[]> user1Friends = friendsDB.getFriends(user1);
        boolean initiallyFriends = false;
        for (Object[] friend : user1Friends) {
            if (friend[0].equals(friend1)) {
                initiallyFriends = true;
                break;
            }
        }
        assertTrue(initiallyFriends, "Users should initially be friends");

        friendsDB.removeFriend(user1, friend1);

        List<Object[]> updatedUser1Friends = friendsDB.getFriends(user1);
        boolean stillFriends1 = false;
        for (Object[] friend : updatedUser1Friends) {
            if (friend[0].equals(friend1)) {
                stillFriends1 = true;
                break;
            }
        }
        assertFalse(stillFriends1, "User1 should no longer have friend1 in friends list");

        List<Object[]> updatedFriend1Friends = friendsDB.getFriends(friend1);
        boolean stillFriends2 = false;
        for (Object[] friend : updatedFriend1Friends) {
            if (friend[0].equals(user1)) {
                stillFriends2 = true;
                break;
            }
        }
        assertFalse(stillFriends2, "Friend1 should no longer have user1 in friends list");
    }
}