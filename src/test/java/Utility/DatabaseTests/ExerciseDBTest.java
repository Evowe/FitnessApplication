package Utility.DatabaseTests;

import Application.Databases.DatabaseManager;
import Application.Databases.ExerciseDB;
import Application.Utility.Objects.Exercise;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExerciseDBTest {

    private ExerciseDB exerciseDB;
    private final String TEST_EXERCISE_PREFIX = "test_exercise_" + UUID.randomUUID().toString().substring(0, 8) + "_";

    @BeforeEach
    public void setUp() {
        exerciseDB = DatabaseManager.getExerciseDB();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        Connection conn = null;
        try {
            conn = exerciseDB.getConnection();

            try (PreparedStatement pstmt = conn.prepareStatement(
                    "DELETE FROM Exercises WHERE Name LIKE ?")) {
                pstmt.setString(1, TEST_EXERCISE_PREFIX + "%");
                pstmt.executeUpdate();
            }
        } finally {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
    }

    private String getTestExerciseName(String baseName) {
        return TEST_EXERCISE_PREFIX + baseName;
    }

    @Test
    public void testSaveAndGetExercise() throws SQLException {
        String name = getTestExerciseName("pushup");
        Exercise testExercise = new Exercise(name, "Push exercise", 3, 10, 0);

        int id = exerciseDB.saveExercise(testExercise);
        assertTrue(id > 0, "Exercise should be saved with valid ID");

        Exercise retrievedExercise = exerciseDB.getExercise(id);

        assertNotNull(retrievedExercise);
        assertEquals(name, retrievedExercise.getName());
        assertEquals("Push exercise", retrievedExercise.getDescription());
        assertEquals(3, retrievedExercise.getSets());
        assertEquals(10, retrievedExercise.getReps());
        assertEquals(0, retrievedExercise.getWeight());
    }

    @Test
    public void testExerciseExists() throws SQLException {
        String name = getTestExerciseName("squat");
        Exercise testExercise = new Exercise(name, "Leg exercise", 4, 8, 50);

        exerciseDB.saveExercise(testExercise);

        assertTrue(exerciseDB.exerciseExists(name), "Exercise should exist after saving");
        assertFalse(exerciseDB.exerciseExists(getTestExerciseName("nonexistent")), "Non-existent exercise should return false");
    }

    @Test
    public void testSaveDuplicateExercise() throws SQLException {
        String name = getTestExerciseName("duplicate");
        Exercise testExercise1 = new Exercise(name, "First exercise", 2, 15, 0);
        Exercise testExercise2 = new Exercise(name, "Second exercise", 3, 12, 0);

        int id1 = exerciseDB.saveExercise(testExercise1);
        assertTrue(id1 > 0, "First exercise should be saved");

        int id2 = exerciseDB.saveExercise(testExercise2);
        assertEquals(-1, id2, "Duplicate exercise should not be saved");
    }

    @Test
    public void testGetAllExercises() throws SQLException {
        String name1 = getTestExerciseName("exercise1");
        String name2 = getTestExerciseName("exercise2");

        Exercise exercise1 = new Exercise(name1, "Test exercise 1", 3, 10, 0);
        Exercise exercise2 = new Exercise(name2, "Test exercise 2", 4, 8, 20);

        exerciseDB.saveExercise(exercise1);
        exerciseDB.saveExercise(exercise2);

        List<Exercise> exercises = exerciseDB.getAllExercises();

        assertNotNull(exercises);
        assertTrue(exercises.size() >= 2, "Should retrieve at least the two test exercises");

        boolean foundExercise1 = false;
        boolean foundExercise2 = false;

        for (Exercise exercise : exercises) {
            if (exercise.getName().equals(name1)) {
                foundExercise1 = true;
            }
            if (exercise.getName().equals(name2)) {
                foundExercise2 = true;
            }
        }

        assertTrue(foundExercise1, "Should find first test exercise");
        assertTrue(foundExercise2, "Should find second test exercise");
    }

    @Test
    public void testDeleteExercise() throws SQLException {
        String name = getTestExerciseName("toDelete");
        Exercise testExercise = new Exercise(name, "Exercise to delete", 2, 15, 0);

        int id = exerciseDB.saveExercise(testExercise);
        assertTrue(id > 0);

        boolean deleted = exerciseDB.deleteExercise(id);
        assertTrue(deleted, "Exercise should be deleted successfully");

        Exercise retrievedExercise = exerciseDB.getExercise(id);
        assertNull(retrievedExercise, "Exercise should not exist after deletion");
    }
}