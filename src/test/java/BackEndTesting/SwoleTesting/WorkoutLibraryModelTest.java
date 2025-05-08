package BackEndTesting.SwoleTesting;

import Application.Databases.WorkoutDB;
import Application.TheSwoleSection.WorkoutLibrary.WorkoutLibraryModel;
import Application.Utility.Objects.Workout;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class WorkoutLibraryModelTest {

    @Test
    public void testGetWorkoutColumnsReturnsExpectedLength() {
        WorkoutLibraryModel model = new WorkoutLibraryModel();
        Object[] columns = model.getWorkoutColumns();

        assertNotNull(columns, "Workout columns should not be null");
        assertEquals(4, columns.length, "There should be 4 columns: Name, Description, Duration, Exercises");
    }

    @Test
    public void testRecordWorkoutDoesNotThrow() {
        WorkoutLibraryModel model = new WorkoutLibraryModel();
        WorkoutDB workoutDB = new WorkoutDB();

        String testWorkoutName = "Test Workout";
        String testUsername = "test_user";

        // Create a simple workout object
        Workout workout = new Workout(testWorkoutName,"a",2,3,"test","1 2 3");

        try {
            workoutDB.addWorkout(workout, testUsername);
            int caloriesBurned = Integer.valueOf(120);
            model.recordWorkout(testWorkoutName, testUsername, caloriesBurned);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown during test: " + e.getMessage());
        } finally {
            try {
                Workout insertedWorkout = workoutDB.getWorkout(testWorkoutName);
                if (insertedWorkout != null) {
                    workoutDB.deleteWorkout(testUsername, testWorkoutName);
                }
            } catch (SQLException cleanupEx) {
                cleanupEx.printStackTrace();
            }
        }
    }
}
