package Application.Databases;

import Application.Utility.Objects.WorkoutPlan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WorkoutPlanDB extends DBTemplate {

    public WorkoutPlanDB() {super("WorkoutPlan");}

    @Override
    protected void createTables() throws SQLException {
        String [] columns = {
                "name TEXT NOT NULL",
                "goal TEXT NOT NULL",
                "duration INTEGER NOT NULL",
                "workoutSchedule TEXT NOT NULL"
        };

        createTable("WorkoutPlan", columns);

    }


    public void addWorkoutPlan(WorkoutPlan workoutPlan) throws SQLException {
        String sql = "INSERT INTO WorkoutPlan (name, goal, duration, workoutSchedule) VALUES (?, ?, ?, ?)";

        try(Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, workoutPlan.getName());
            ps.setString(2, workoutPlan.getGoal());
            ps.setInt(3, workoutPlan.getDurationInWeeks());
            ps.setString(4, workoutPlan.getWorkoutSchedule().toString());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding workout plan to DB: " + e.getMessage());
            throw e;
        }
    }




}
