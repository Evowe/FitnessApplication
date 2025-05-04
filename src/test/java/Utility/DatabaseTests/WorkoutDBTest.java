package Utility.DatabaseTests;

import Application.Databases.DatabaseManager;
import Application.Databases.WorkoutDB;
import Application.Utility.Objects.Account;
import Application.Utility.Objects.Workout;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WorkoutDBTest {

    private WorkoutDB workoutDB;
    private final String TEST_WORKOUT_PREFIX = "test_workout_" + UUID.randomUUID().toString().substring(0, 8) + "_";
    private final String TEST_USERNAME = "test_user";

    @BeforeEach
    public void setUp() {
        workoutDB = DatabaseManager.getWorkoutDB();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        Connection conn = null;
        try {
            conn = workoutDB.getConnection();

            try (PreparedStatement pstmt = conn.prepareStatement(
                    "DELETE FROM Workouts WHERE Name LIKE ? AND username = ?")) {
                pstmt.setString(1, TEST_WORKOUT_PREFIX + "%");
                pstmt.setString(2, TEST_USERNAME);
                pstmt.executeUpdate();
            }
        } finally {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
    }

    private String getTestWorkoutName(String baseName) {
        return TEST_WORKOUT_PREFIX + baseName;
    }

    @Test
    public void addWorkoutAndGetWorkout() throws SQLException {
        // Arrange
        String name = getTestWorkoutName("pushups");
        Workout testWorkout = new Workout(name, "doing stuff", 5, 5, "2025-05-04", "1,2,3,4");

        // Act
        int id = workoutDB.addWorkout(testWorkout, TEST_USERNAME);

        // Assert
        assertTrue(id > 0, "Workout should be saved with valid ID");

        Workout retrievedWorkout = workoutDB.getWorkout(TEST_USERNAME, name);

        // Assert
        assertNotNull(retrievedWorkout, "Retrieved workout should not be null");
        assertEquals(name, retrievedWorkout.getName(), "Workout names should match");
        assertEquals("doing stuff", retrievedWorkout.getDescription(), "Descriptions should match");
        assertEquals(5, retrievedWorkout.getDuration(), "Duration should match");
        assertEquals(5, retrievedWorkout.getCaloriesBurned(), "Calories burned should match");
        assertEquals("1,2,3,4", retrievedWorkout.getExercises(), "Exercises should match");
    }

    @Test
    public void testGetAllWorkouts() throws SQLException {
        // Arrange
        String name1 = getTestWorkoutName("Workout1");
        String name2 = getTestWorkoutName("Workout2");

        Workout testWorkout1 = new Workout(name1, "doing stuff", 5, 5, "2025-05-04", "1,2,3,4");
        Workout testWorkout2 = new Workout(name2, "more stuff", 10, 10, "2025-05-04", "5,6,7,8");

        // Act
        workoutDB.addWorkout(testWorkout1, TEST_USERNAME);
        workoutDB.addWorkout(testWorkout2, TEST_USERNAME);

        List<Workout> workouts = workoutDB.getAllWorkouts(TEST_USERNAME);

        // Assert
        assertNotNull(workouts, "Workouts list should not be null");
        assertTrue(workouts.size() >= 2, "Should retrieve at least the two test workouts");

        boolean foundWorkout1 = false;
        boolean foundWorkout2 = false;

        for (Workout workout : workouts) {
            if (workout.getName().equals(name1)) {
                foundWorkout1 = true;
                assertEquals("doing stuff", workout.getDescription(), "Description should match for workout 1");
            }
            if (workout.getName().equals(name2)) {
                foundWorkout2 = true;
                assertEquals("more stuff", workout.getDescription(), "Description should match for workout 2");
            }
        }

        assertTrue(foundWorkout1, "Should find first test workout");
        assertTrue(foundWorkout2, "Should find second test workout");
    }

    @Test
    public void testDeleteWorkout() throws SQLException {
        // Arrange
        String name = getTestWorkoutName("toDelete");
        Workout testWorkout = new Workout(name, "doing stuff", 5, 5, "2025-05-04", "1,2,3,4");

        // Act
        int id = workoutDB.addWorkout(testWorkout, TEST_USERNAME);

        // Assert
        assertTrue(id > 0, "Workout should be saved with valid ID");

        // Act
        boolean deleted = workoutDB.deleteWorkout(TEST_USERNAME, name);

        // Assert
        assertTrue(deleted, "Workout should be deleted successfully");

        Workout retrievedWorkout = workoutDB.getWorkout(TEST_USERNAME, name);
        assertNull(retrievedWorkout, "Workout should not exist after deletion");
    }

    @Test
    public void testUpdateWorkout() throws SQLException {
        // Arrange
        String originalName = getTestWorkoutName("originalWorkout");
        String updatedName = getTestWorkoutName("updatedWorkout");

        Workout originalWorkout = new Workout(originalName, "original description", 5, 5, "2025-05-04", "1,2,3,4");
        Workout updatedWorkout = new Workout(updatedName, "updated description", 10, 10, "2025-05-04", "5,6,7,8");

        // Act
        int id = workoutDB.addWorkout(originalWorkout, TEST_USERNAME);

        // Assert
        assertTrue(id > 0, "Workout should be saved with valid ID");

        // Act
        boolean updated = workoutDB.updateWorkout(TEST_USERNAME, updatedWorkout, originalName);

        // Assert
        assertTrue(updated, "Workout should be updated successfully");

        // Verify the workout was updated
        Workout retrievedWorkout = workoutDB.getWorkout(TEST_USERNAME, updatedName);
        assertNotNull(retrievedWorkout, "Updated workout should exist");
        assertEquals(updatedName, retrievedWorkout.getName(), "Name should be updated");
        assertEquals("updated description", retrievedWorkout.getDescription(), "Description should be updated");
        assertEquals(10, retrievedWorkout.getDuration(), "Duration should be updated");
        assertEquals(10, retrievedWorkout.getCaloriesBurned(), "Calories burned should be updated");
        assertEquals("5,6,7,8", retrievedWorkout.getExercises(), "Exercises should be updated");

        // Verify the original workout name no longer exists
        Workout originalRetrievedWorkout = workoutDB.getWorkout(TEST_USERNAME, originalName);
        assertNull(originalRetrievedWorkout, "Original workout should no longer exist");
    }
}