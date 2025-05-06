package Application.Databases;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkoutPlanLogDB extends DBTemplate{

    public WorkoutPlanLogDB() {
        super("WorkoutPlanLog");
    }

    @Override
    protected void createTables() throws SQLException {
        String [] columns={
                "planName TEXT NOT NULL",
                "dateEquipped TEXT NOT NULL",
                "username TEXT NOT NULL"
        };

        createTable("WorkoutPlanLog", columns);
    }

    public void equipWorkoutPlan(String planName, String dateEquipped, String username){
        String sql = "INSERT INTO WorkoutPlanLog (planName, dateEquipped, username) VALUES (?, ?, ?)";

        try(Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, planName);
            ps.setString(2, dateEquipped);
            ps.setString(3, username);

            ps.executeUpdate();

            System.out.println("Workout Plan Equiped: " + planName);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }


    public String getCurrentPlan(String username){
        String sql = "SELECT * FROM WorkoutPlanLog WHERE username = ?";

        try(Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            String planName = null;
            if(rs.next()){
                planName = rs.getString("planName");
            }

            return planName;

        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void updateCurrentPlan(String username, String planName, String dateEquipped){
        String sql = "UPDATE WorkoutPlanLog SET planName = ?, dateEquipped = ? WHERE username = ?";

        try(Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, planName);
            ps.setString(2, dateEquipped);
            ps.setString(3, username);

            ps.executeUpdate();
            System.out.println("New Workout Plan Equiped: " + planName);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteCurrentPlan(String username){
        String sql = "DELETE FROM WorkoutPlanLog WHERE username = ?";

        try(Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, username);
            ps.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String getEquipDate(String workoutPlanName){
        String sql = "SELECT * FROM WorkoutPlanLog WHERE planName = ?";

        try(Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, workoutPlanName);
            ResultSet rs = ps.executeQuery();
            String equipDate = null;
            if(rs.next()){
                equipDate = rs.getString("dateEquipped");
            }

            return equipDate;

        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

}
