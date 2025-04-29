package Application.Databases;

import Application.Utility.Objects.Goal;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class GoalsDB extends DBTemplate {
    private static final String TABLE_NAME = "Goals";

    public GoalsDB() {
        super(TABLE_NAME);
    }

    @Override
    protected void createTables() throws SQLException {
        String[] goalColumns = {
                "username TEXT NOT NULL",
                "Type TEXT",
                "Value INTEGER DEFAULT 0",
                "Date TEXT NOT NULL",
                "Completed BOOL DEFAULT FALSE"
        };
        createTable(TABLE_NAME, goalColumns);
    }

    public void ensureDefaultGoals(String username) throws SQLException, ParseException {
        // Insert default Distance goal if not present
        if (getGoalByTypeAndUsername(username, "Distance") == null) {
            addGoal(new Goal(username, "Distance", 0, "01/01/2099", false));
        }

        // Insert default Weight goal if not present
        if (getGoalByTypeAndUsername(username, "Weight") == null) {
            addGoal(new Goal(username, "Weight", 0, "01/01/2099", false));
        }
    }

    public void addGoal(Goal goal) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + " (username, Type, Value, Date, Completed) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, goal.getAssociatedUsername());
            pstmt.setString(2, goal.getType());
            pstmt.setInt(3, goal.getValue());

            // Format Date object to string in MM/dd/yyyy format
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            String dateString = dateFormat.format(goal.getDate());
            pstmt.setString(4, dateString);

            pstmt.setBoolean(5, goal.getCompleted());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding goal: " + e.getMessage());
            throw e;
        }
    }

    public List<Goal> getGoalsByUsername(String username) throws SQLException, ParseException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ?";
        List<Goal> goals = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String type = rs.getString("Type");
                int value = rs.getInt("Value");
                String dateStr = rs.getString("Date");
                boolean completed = rs.getBoolean("Completed");

                Goal goal = new Goal(username, type, value, dateStr, completed);
                goals.add(goal);
            }

            return goals;
        } catch (SQLException | ParseException e) {
            System.out.println("Error retrieving goals: " + e.getMessage());
            throw e;
        }
    }

    public Goal getGoalByTypeAndUsername(String username, String type) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ? AND Type = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, type);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int value = rs.getInt("Value");
                String dateStr = rs.getString("Date");
                boolean completed = rs.getBoolean("Completed");

                return new Goal(username, type, value, dateStr, completed);
            }

            return null; // No goal found
        } catch (SQLException e) {
            System.out.println("Error retrieving goal: " + e.getMessage());
            throw e;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateGoal(Goal goal) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " SET Value = ?, Date = ?, Completed = ? WHERE username = ? AND Type = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, goal.getValue());

            // Format Date object to string
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            String dateString = dateFormat.format(goal.getDate());
            pstmt.setString(2, dateString);

            pstmt.setBoolean(3, goal.getCompleted());
            pstmt.setString(4, goal.getAssociatedUsername());
            pstmt.setString(5, goal.getType());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating goal: " + e.getMessage());
            throw e;
        }
    }

    public void updateGoalCompletion(Goal goal) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " SET Completed = ? WHERE username = ? AND Type = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, goal.getCompleted());

            pstmt.setString(2, goal.getAssociatedUsername());
            pstmt.setString(3, goal.getType());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating goal: " + e.getMessage());
            throw e;
        }
    }

    public void deleteGoal(String username, String type) throws SQLException {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE username = ? AND Type = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, type);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting goal: " + e.getMessage());
            throw e;
        }
    }
}