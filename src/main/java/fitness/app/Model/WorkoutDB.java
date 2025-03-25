package fitness.app.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class WorkoutDB extends DBTemplate {

	public WorkoutDB() {
		super("workouts");
	}

	@Override
	protected void createDatabase() throws SQLException {
		// Create Workouts table
		String[] workoutColumns = {
				"Name TEXT NOT NULL",
				"Description TEXT",
				"Duration INTEGER",
				"CaloriesBurned INTEGER DEFAULT 0",
				"Date TEXT DEFAULT CURRENT_TIMESTAMP"
		};
		createTable("Workouts", workoutColumns);

		// Create Exercises table with Description field
		String[] exerciseColumns = {
				"WorkoutID INTEGER",
				"Name TEXT NOT NULL",
				"Description TEXT",
				"Sets INTEGER DEFAULT 0",
				"Reps INTEGER DEFAULT 0",
				"Weight REAL DEFAULT 0.0",
				"FOREIGN KEY (WorkoutID) REFERENCES Workouts(ID)"
		};
		createTable("Exercises", exerciseColumns);
	}

	public int saveWorkout(Workout workout) throws SQLException {
		String sql = "INSERT INTO Workouts (Name, Description, Duration) VALUES (?, ?, ?)";

		try (Connection conn = getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			// Use workout name or a default if not available
			String workoutName = workout.getTitle() != null ? workout.getTitle() : "Workout";
			pstmt.setString(1, workoutName);
			pstmt.setString(2, workout.getDescription());
			pstmt.setInt(3, workout.getDuration());

			pstmt.executeUpdate();

			// Get the generated workout ID
			try (ResultSet rs = pstmt.getGeneratedKeys()) {
				if (rs.next()) {
					int workoutId = rs.getInt(1);

					// Save associated exercises
					saveExercises(workoutId, workout.getExercises(), conn);

					return workoutId;
				}
			}
		}

		return -1; // Failed to save
	}

	private void saveExercises(int workoutId, List<Exercise> exercises, Connection conn) throws SQLException {
		if (exercises == null || exercises.isEmpty()) {
			return;
		}

		String sql = "INSERT INTO Exercises (WorkoutID, Name, Description, Sets, Reps, Weight) VALUES (?, ?, ?, ?, ?, ?)";

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
		String sql = "SELECT * FROM Workouts WHERE ID = ?";

		try (Connection conn = getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, id);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					// Get workout details
					String name = rs.getString("Name");
					String description = rs.getString("Description");
					int duration = rs.getInt("Duration");

					// Get exercises for this workout
					List<Exercise> exercises = getExercisesForWorkout(id, conn);

					// Create and return the workout
					Workout workout = new Workout(duration, description, exercises);
					return workout;
				}
			}
		}

		return null; // Not found
	}

	private List<Exercise> getExercisesForWorkout(int workoutId, Connection conn) throws SQLException {
		List<Exercise> exercises = new ArrayList<>();
		String sql = "SELECT * FROM Exercises WHERE WorkoutID = ?";

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
		String sql = "SELECT ID FROM Workouts";

		try (Connection conn = getConnection();
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				int id = rs.getInt("ID");
				Workout workout = getWorkout(id);
				if (workout != null) {
					workouts.add(workout);
				}
			}
		}

		return workouts;
	}

	public boolean deleteWorkout(int workoutId) throws SQLException {
		try (Connection conn = getConnection()) {
			// First delete associated exercises
			String exercisesSql = "DELETE FROM Exercises WHERE WorkoutID = ?";
			try (PreparedStatement pstmt = conn.prepareStatement(exercisesSql)) {
				pstmt.setInt(1, workoutId);
				pstmt.executeUpdate();
			}

			// Then delete the workout
			String workoutSql = "DELETE FROM Workouts WHERE ID = ?";
			try (PreparedStatement pstmt = conn.prepareStatement(workoutSql)) {
				pstmt.setInt(1, workoutId);
				int affectedRows = pstmt.executeUpdate();
				return affectedRows > 0;
			}
		}
	}

	public boolean updateWorkout(int workoutId, Workout workout) throws SQLException {
		String sql = "UPDATE Workouts SET Description = ?, Duration = ? WHERE ID = ?";

		try (Connection conn = getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, workout.getDescription());
			pstmt.setInt(2, workout.getDuration());
			pstmt.setInt(3, workoutId);

			int affectedRows = pstmt.executeUpdate();

			if (affectedRows > 0) {
				// Delete existing exercises
				String deleteSql = "DELETE FROM Exercises WHERE WorkoutID = ?";
				try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
					deleteStmt.setInt(1, workoutId);
					deleteStmt.executeUpdate();
				}

				// Save new exercises
				saveExercises(workoutId, workout.getExercises(), conn);
				return true;
			}
		}

		return false;
	}
}