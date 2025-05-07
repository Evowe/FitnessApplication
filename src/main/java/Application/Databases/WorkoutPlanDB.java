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
                "Author TEXT NOT NULL"
        };

        createTable("WorkoutPlan", columns);

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

    public String getAuthor(WorkoutPlan workoutPlan) throws SQLException {
        String sql = "SELECT Author FROM WorkoutPlan WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, workoutPlan.getAuthor());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("Author");
            }

        }
        return "Error No Author";
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

}
