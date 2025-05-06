package Application.Databases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Application.Utility.Objects.LiveWorkout;

public class LiveWorkoutDB extends DBTemplate {
	
	public LiveWorkoutDB() {
		super("liveWorkouts");
	}
	
	@Override
	protected void createTables() throws SQLException {
		String[] cols = {"name TEXT NOT NULL",
						 "trainer_id INTEGER NOT NULL",
						 "live INTEGER NOT NULL DEFAULT 0",	//0: Not live, 1: Live
						 "description TEXT NOT NULL",
						 "start_time TEXT",
						 "duration TEXT NOT NULL"
						 };
		
		createTable("liveWorkouts", cols);
	}
		
	public int addWorkout(LiveWorkout liveWorkout, Integer trainerId) throws SQLException {
		String sql = "INSERT INTO liveWorkouts" + "(name, trainer_id, description, start_time, duration) VALUES (?,?,?,?,?)";

		try (Connection conn = getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// Use workout name or a default if not available
			pstmt.setString(1, liveWorkout.getName());
			pstmt.setInt(2, trainerId);
			pstmt.setString(3, liveWorkout.getDescription());
			pstmt.setString(4, liveWorkout.getStartTime());
			pstmt.setInt(5, liveWorkout.getDuration());
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
	
	public List<LiveWorkout> getAllLiveWorkouts() throws SQLException {
		List<LiveWorkout> workouts = new ArrayList<>();
		String query = "SELECT * FROM liveWorkouts";
		
		try (Connection conn = getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query)) {
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				String name = rs.getString("name");
				Integer trainerId = rs.getInt("trainer_id");
				int liveInt = rs.getInt("live");
				String description = rs.getString("description");
				String startTime = rs.getString("start_time");
				int duration = rs.getInt("duration");
				
				Boolean liveBool = false;
				
				if (liveInt == 1) {
					liveBool = true;
				}
				
				LiveWorkout liveWorkout = new LiveWorkout(name, trainerId, liveBool, description, startTime, duration);
				workouts.add(liveWorkout);
			}
		}
		
		return workouts;
	}
	
	public List<LiveWorkout> getTrainerWorkouts(Integer trainerId) throws SQLException {
		List<LiveWorkout> workouts = new ArrayList<>();
		String query = "SELECT * FROM liveWorkouts WHERE trainer_id = ?";
		
		try (Connection conn = getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, trainerId);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				String name = rs.getString("name");
				int liveInt = rs.getInt("live");
				String description = rs.getString("description");
				String startTime = rs.getString("start_time");
				int duration = rs.getInt("duration");
				
				Boolean liveBool = false;
				
				if (liveInt == 1) {
					liveBool = true;
				}
				
				LiveWorkout liveWorkout = new LiveWorkout(name, trainerId, liveBool, description, startTime, duration);
				workouts.add(liveWorkout);
			}
		}
		
		return workouts;
	}
	
}
