package Application.Utility.Databases;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FriendsDB extends DBTemplate{

    public FriendsDB() {
        super("friends");
    }

    @Override
    protected void createTables() throws SQLException {
        String[] columns = {
                "user_username TEXT NOT NULL",
                "friend_username TEXT NOT NULL",
                "status TEXT DEFAULT 'waiting'",
                "UNIQUE(user_username, friend_username)"
        };

        createTable("friends", columns);
    }

    public void sendFriendRequest(String username, String friend) throws SQLException {
        String sql = "INSERT INTO friends (user_username, friend_username) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, friend);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error sending friend request: " + e.getMessage());
            throw e;
        }
    }

    public void acceptFriendRequest(String username, String friendRequester) throws SQLException {
        // First check if the request exists
        String checkSql = "SELECT * FROM friends WHERE user_username = ? AND friend_username = ? AND status = 'waiting'";

        try (Connection conn = getConnection();
             PreparedStatement checkPs = conn.prepareStatement(checkSql)) {

            checkPs.setString(1, friendRequester);
            checkPs.setString(2, username);

            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                // Update the existing request to 'accepted'
                String updateSql = "UPDATE friends SET status = 'accepted' WHERE user_username = ? AND friend_username = ?";

                try (PreparedStatement updatePs = conn.prepareStatement(updateSql)) {
                    updatePs.setString(1, friendRequester);
                    updatePs.setString(2, username);
                    updatePs.executeUpdate();
                }

                // Create the reciprocal relationship
                String insertSql = "INSERT INTO friends (user_username, friend_username, status) VALUES (?, ?, 'accepted')";

                try (PreparedStatement insertPs = conn.prepareStatement(insertSql)) {
                    insertPs.setString(1, username);
                    insertPs.setString(2, friendRequester);
                    insertPs.executeUpdate();
                }

            } else {
                System.out.println("No pending friend request found from " + friendRequester + " to " + username);
            }
        } catch (SQLException e) {
            System.out.println("Error accepting friend request: " + e.getMessage());
            throw e;
        }
    }

    public void declineFriendRequest(String username, String friendRequester) throws SQLException {
        // First check if the request exists
        String checkSql = "SELECT * FROM friends WHERE user_username = ? AND friend_username = ? AND status = 'waiting'";

        try (Connection conn = getConnection();
             PreparedStatement checkPs = conn.prepareStatement(checkSql)) {

            checkPs.setString(1, friendRequester);
            checkPs.setString(2, username);

            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                // Update the existing request to 'declines'
                String updateSql = "UPDATE friends SET status = 'declined' WHERE user_username = ? AND friend_username = ?";

                try (PreparedStatement updatePs = conn.prepareStatement(updateSql)) {
                    updatePs.setString(1, friendRequester);
                    updatePs.setString(2, username);
                    updatePs.executeUpdate();
                }

                // Create the reciprocal relationship
                String insertSql = "INSERT INTO friends (user_username, friend_username, status) VALUES (?, ?, 'declined')";

                try (PreparedStatement insertPs = conn.prepareStatement(insertSql)) {
                    insertPs.setString(1, username);
                    insertPs.setString(2, friendRequester);
                    insertPs.executeUpdate();
                }

            } else {
                System.out.println("No pending friend request found from " + friendRequester + " to " + username);
            }
        } catch (SQLException e) {
            System.out.println("Error declining friend request: " + e.getMessage());
            throw e;
        }
    }

    public List<String> getPendingRequests(String username) throws SQLException {
        String sql = "SELECT user_username FROM friends WHERE friend_username = ? AND status = 'waiting'";
        List<String> pendingRequests = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                pendingRequests.add(rs.getString("user_username"));
            }
        } catch (SQLException e) {
            System.out.println("Error getting pending requests: " + e.getMessage());
            throw e;
        }

        return pendingRequests;
    }

    public List<Object []> getFriends(String username) throws SQLException {
        String sql = "SELECT friend_username FROM friends WHERE user_username=? AND status = 'accepted'";
        List<Object []> friends = new ArrayList<>();

        try (Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] friend = new Object[1];
                friend[0] = rs.getString("friend_username");
                friends.add(friend);
            }
        } catch (SQLException e) {
            System.out.println("Error getting friends: " + e.getMessage());
            throw e;
        }

        return friends;
    }

    public void removeFriend(String username, String friend) throws SQLException {
        String sql = "DELETE FROM friends WHERE (user_username = ? AND friend_username = ?) OR (user_username = ? AND friend_username = ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, friend);
            ps.setString(3, friend);
            ps.setString(4, username);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error removing friend: " + e.getMessage());
            throw e;
        }
    }
}
