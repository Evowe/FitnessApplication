package fitness.app.Objects.Databases;

import fitness.app.Objects.Exercise;
import fitness.app.Objects.Workout;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class WorkoutDB extends DBTemplate {
	private static final String WORKOUTS_TABLE = "Workouts";
	private static final String EXERCISES_TABLE = "WorkoutExercises";  // Renamed to avoid conflict with main Exercises table

	public WorkoutDB() {
		super(WORKOUTS_TABLE);
	}

	@Override
	protected void createTables() throws SQLException {
		// Create Workouts table
		String[] workoutColumns = {
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
//		String[] exerciseColumns = {
//				"WorkoutID INTEGER",
//				"Name TEXT NOT NULL",
//				"Description TEXT",
//				"Sets INTEGER DEFAULT 0",
//				"Reps INTEGER DEFAULT 0",
//				"Weight REAL DEFAULT 0.0",
//				"FOREIGN KEY (WorkoutID) REFERENCES " + WORKOUTS_TABLE + "(ID)"
//		};
//		createTable(EXERCISES_TABLE, exerciseColumns);
	}
	//TODO FIX
	public int saveWorkout(Workout workout) throws SQLException {
		String sql = "INSERT INTO " + WORKOUTS_TABLE + " (Name, Description, Duration,CaloriesBurned,Date,Exercises) VALUES (?, ?, ?,?,?,?)";

		try (Connection conn = getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			// Use workout name or a default if not available
			String workoutName = workout.getName() != null ? workout.getName() : "Workout";
			pstmt.setString(1, workoutName);
			pstmt.setString(2, workout.getDescription());
			pstmt.setInt(3, workout.getDuration());
			pstmt.setInt(4, workout.getCaloriesBurned());
			pstmt.setString(5, workout.getDate());
			pstmt.setString(6, workout.getExercises());

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

	private void saveExercises(int workoutId, List<Exercise> exercises, Connection conn) throws SQLException {
		if (exercises == null || exercises.isEmpty()) {
			return;
		}

		String sql = "INSERT INTO " + EXERCISES_TABLE + " (WorkoutID, Name, Description, Sets, Reps, Weight) VALUES (?, ?, ?, ?, ?, ?)";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			for (Exercise exercise : exercises) {
				pstmt.setInt(1, workoutId);
				pstmt.setString(2, exercise.getName());
				pstmt.setString(3, exercise.getDescription());
				pstmt.setInt(4, exercise.getSets());
				pstmt.setInt(5, exercise.getReps());
				pstmt.setDouble(6, exercise.getWeight());

				pstmt.executeUpdate();
			}
		}
	}

	public Workout getWorkout(int id) throws SQLException {
		String sql = "SELECT * FROM " + WORKOUTS_TABLE + " WHERE ID = ?";

		try (Connection conn = getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, id);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					// Get workout details
					String name = rs.getString("Name");
					String description = rs.getString("Description");
					int duration = rs.getInt("Duration");
					int CaloriesBurned = rs.getInt("CaloriesBurned");
					String date = rs.getString("Date");
					String exerciseId = rs.getString("Exercises");

					// Get exercises for this workout
					//List<Exercise> exercises = getExercisesForWorkout(id, conn);

					// Create and return the workout
                    return new Workout(name,description,duration,CaloriesBurned,date,exerciseId);
				}
			}
		}

		return null; // Not found
	}

	private List<Exercise> getExercisesForWorkout(int workoutId, Connection conn) throws SQLException {
		List<Exercise> exercises = new ArrayList<>();
		String sql = "SELECT * FROM " + EXERCISES_TABLE + " WHERE WorkoutID = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, workoutId);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					String name = rs.getString("Name");
					String description = rs.getString("Description");
					int sets = rs.getInt("Sets");
					int reps = rs.getInt("Reps");
					double weight = rs.getDouble("Weight");

					Exercise exercise = new Exercise(name, description, sets, reps, weight);
					exercises.add(exercise);
				}
			}
		}

		return exercises;
	}

	public List<Workout> getAllWorkouts() throws SQLException {
		List<Workout> workouts = new ArrayList<>();
		List<Integer> ids = new ArrayList<>();
		String sql = "SELECT ID FROM " + WORKOUTS_TABLE;

		try (Connection conn = getConnection();
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(sql)) {

			//getWorkout(id) is breakign rs
			//This gets rs purpose before breaking it
			//I did not fix the problem but I did get around it
			while (rs.next()) {
				ids.add(rs.getInt("ID"));
			}
		}
		for (int id : ids) {
			Workout workout = getWorkout(id);
			if (workout != null) {
				workouts.add(workout);
			}
		}

		return workouts;
	}


	public boolean deleteWorkout(int workoutId) throws SQLException {
		try (Connection conn = getConnection()) {

			// Then delete the workout
			String workoutSql = "DELETE FROM " + WORKOUTS_TABLE + " WHERE ID = ?";
			try (PreparedStatement pstmt = conn.prepareStatement(workoutSql)) {
				pstmt.setInt(1, workoutId);
				int affectedRows = pstmt.executeUpdate();
				return affectedRows >= 0;
			}
		}
	}
	//TODO FIX
	public boolean updateWorkout(int workoutId, Workout workout) throws SQLException {
		String sql = "UPDATE " + WORKOUTS_TABLE + " SET Description = ?, Duration = ? WHERE ID = ?";

		try (Connection conn = getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, workout.getDescription());
			pstmt.setInt(2, workout.getDuration());
			pstmt.setInt(3, workoutId);

			int affectedRows = pstmt.executeUpdate();

			if (affectedRows > 0) {
				// Delete existing exercises
				String deleteSql = "DELETE FROM " + EXERCISES_TABLE + " WHERE WorkoutID = ?";
				try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
					deleteStmt.setInt(1, workoutId);
					deleteStmt.executeUpdate();
				}

				// Save new exercises
				//saveExercises(workoutId, workout.getExercises(), conn);
				return true;
			}
		}

		return false;
	}
}