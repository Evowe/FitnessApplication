package Application.Databases;

import Application.Utility.Objects.WorkoutPlan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class WorkoutPlanDB extends DBTemplate {

    public WorkoutPlanDB() {
        super("WorkoutPlan");
    }

    @Override
    protected void createTables() throws SQLException {
        String[] columns = {
                "name TEXT NOT NULL",
                "goal TEXT NOT NULL",
                "duration INTEGER NOT NULL",
                "intensity INTEGER NOT NULL",
                "workoutSchedule TEXT NOT NULL",
                "Author TEXT NOT NULL",
                "users INTEGER DEFAULT 0"
        };
        createTable("WorkoutPlan", columns);
    }

    public static void incrementUsersbyName(String name) throws SQLException {
        String sql = "UPDATE WorkoutPlan SET users = users + 1 WHERE name = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        }
    }

    public static int getTotalUserCountByAuthor(String Author) throws SQLException {
        String sql = "SELECT COALESCE(SUM(users), 0) AS total_users FROM WorkoutPlan WHERE Author = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, Author);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total_users");
                }
            }
        }
        return 0; // If no workouts or an error occurs
    }

    public void addWorkoutPlan(WorkoutPlan workoutPlan) throws SQLException {
        String sql = "INSERT INTO WorkoutPlan (name, goal, duration, intensity, workoutSchedule, Author) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, workoutPlan.getName());
            ps.setString(2, workoutPlan.getGoal());
            ps.setInt(3, workoutPlan.getDurationInWeeks());
            ps.setInt(4, workoutPlan.getIntensity());
            ps.setString(5, workoutPlan.getWorkoutSchedule().toString());
            ps.setString(6, workoutPlan.getAuthor());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding workout plan to DB: " + e.getMessage());
            throw e;
        }
    }

    public Map<WorkoutPlan, String> getAllWorkoutPlans() throws SQLException {
        String sql = "SELECT * FROM WorkoutPlan";

        Map<WorkoutPlan, String> workoutPlans = new HashMap<>();
        try(Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                WorkoutPlan workoutPlan = new WorkoutPlan();
                workoutPlan.setName(rs.getString("name"));
                workoutPlan.setGoal(rs.getString("goal"));
                workoutPlan.setDurationInWeeks(rs.getInt("duration"));
                workoutPlan.setIntensity(rs.getInt("intensity"));
                String workouts = rs.getString("workoutSchedule");
                workoutPlan.setAuthor(rs.getString("Author"));
                workoutPlans.put(workoutPlan, workouts);
            }
        }

        return workoutPlans;
    }

    public Map<WorkoutPlan, String> getWorkoutPlan(String nameOfPlan) throws SQLException {
        String sql = "SELECT * FROM WorkoutPlan WHERE name = ?";


        Map<WorkoutPlan, String> workoutPlans = new HashMap<>();
        try(Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nameOfPlan);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                WorkoutPlan workoutPlan = new WorkoutPlan();
                workoutPlan.setName(rs.getString("name"));
                workoutPlan.setGoal(rs.getString("goal"));
                workoutPlan.setDurationInWeeks(rs.getInt("duration"));
                workoutPlan.setIntensity(rs.getInt("intensity"));
                String workouts = rs.getString("workoutSchedule");
                workoutPlan.setAuthor(rs.getString("Author"));
                workoutPlans.put(workoutPlan, workouts);
            }
        }

        return workoutPlans;
    }


    public void updateWorkoutPlan(WorkoutPlan workoutPlan) throws SQLException {
        String sql = "UPDATE WorkoutPlan SET goal=?, duration = ?, intensity=?, workoutSchedule = ? WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, workoutPlan.getGoal());
            pstmt.setInt(2, workoutPlan.getDurationInWeeks());
            pstmt.setInt(3, workoutPlan.getIntensity());
            pstmt.setString(4, workoutPlan.getWorkoutSchedule().toString());
            pstmt.setString(5, workoutPlan.getName());
            //pstmt.setString(6, workoutPlan.getAuthor());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating goal: " + e.getMessage());
            throw e;
        }
    }
    public void deleteWorkoutPlan(String planName) throws SQLException {
        String sql = "DELETE FROM WorkoutPlan WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, planName);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting workout plan from DB: " + e.getMessage());
            throw e;
        }
    }

}
