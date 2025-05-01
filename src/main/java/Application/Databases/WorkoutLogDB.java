package Application.Databases;

import Application.Utility.Objects.Workout;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class WorkoutLogDB extends DBTemplate {
    private static final String WORKOUTS_TABLE = "WorkoutLog";

    public WorkoutLogDB() {
        super(WORKOUTS_TABLE);
    }

    @Override
    protected void createTables() throws SQLException {
        // Create Workouts table
        String[] workoutColumns = {
                "username STRING NOT NULL",
                "Name TEXT NOT NULL",
                "Description TEXT",
                "Duration INTEGER",
                "CaloriesBurned INTEGER DEFAULT 0",
                "Date TEXT DEFAULT CURRENT_TIMESTAMP",
                "Exercises TEXT"
        };
        createTable(WORKOUTS_TABLE, workoutColumns);

        //TODO IM NOT SURE IF THIS IS NEEDED
        // Create Exercises table with Description field

    }
    //TODO FIX
    public int addWorkout(Workout workout,String username) throws SQLException {
        String sql = "INSERT INTO " + WORKOUTS_TABLE + " (Username, Name, Description, Duration,CaloriesBurned,Date,Exercises) VALUES (?,?, ?, ?,?,?,?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Use workout name or a default if not available
            pstmt.setString(1, username);
            pstmt.setString(2, workout.getName());
            pstmt.setString(3, workout.getDescription());
            pstmt.setInt(4, workout.getDuration());
            pstmt.setInt(5, workout.getCaloriesBurned());
            pstmt.setString(6, workout.getDate());
            pstmt.setString(7, workout.getExercises());
            pstmt.executeUpdate();

            // Get the generated workout ID
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1; // Failed to save
    }

    public Workout getWorkout(String username,String name) throws SQLException {
        String sql = "SELECT * FROM " + WORKOUTS_TABLE + " WHERE username = ? AND name = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1,username);
            pstmt.setString(2,name);
            //pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Get workout details
                    String namee = rs.getString("Name");
                    String description = rs.getString("Description");
                    int duration = rs.getInt("Duration");
                    int CaloriesBurned = rs.getInt("CaloriesBurned");
                    String date = rs.getString("Date");
                    String exerciseId = rs.getString("Exercises");

                    return new Workout(namee,description,duration,CaloriesBurned,date,exerciseId);
                }
            }
        }

        return null; // Not found
    }

    public List<Workout> getAllWorkouts(String username) throws SQLException {
        List<Workout> Workouts = new ArrayList<>();
        String sql = "SELECT * FROM " + WORKOUTS_TABLE + " WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, username);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    String name = rs.getString("Name");
                    String description = rs.getString("Description");
                    int duration = rs.getInt("Duration");
                    int caloriesBurned = rs.getInt("CaloriesBurned");
                    String date = rs.getString("Date");
                    String exercises = rs.getString("Exercises");

                    Workout workout = new Workout(name, description, duration, caloriesBurned, date,exercises);
                    Workouts.add(workout);
                }
            }
        }

        return Workouts;
    }

    public boolean deleteWorkout(String username, String name) throws SQLException {
        String workoutSql = "DELETE FROM " + WORKOUTS_TABLE + " WHERE name = ? AND username = ?";
        try (Connection conn = getConnection()) {

            // Then delete the workout
            try (PreparedStatement pstmt = conn.prepareStatement(workoutSql)) {
                pstmt.setString(1,name);
                pstmt.setString(2, username);
                int affectedRows = pstmt.executeUpdate();
                return affectedRows > 0;
            }
        }
    }
    public boolean updateWorkout(String username, Workout workout, String originalName) throws SQLException {
        String sql = "UPDATE " + WORKOUTS_TABLE + " SET Name = ?, Description = ?, Duration = ?, CaloriesBurned = ?, Date = ?, Exercises = ? " +
                "WHERE username = ? AND Name = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, workout.getName());
            pstmt.setString(2, workout.getDescription());
            pstmt.setInt(3, workout.getDuration());
            pstmt.setInt(4, workout.getCaloriesBurned());
            pstmt.setString(5, workout.getDate());
            pstmt.setString(6, workout.getExercises());
            pstmt.setString(7, username);
            pstmt.setString(8, originalName);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected == 1;

        } catch (SQLException e) {
            System.out.println("Error updating workout: " + e.getMessage());
            throw e;
        }
    }

}