package BackEndTesting.SwoleTesting;

import Application.Databases.*;
import Application.Utility.Objects.*;
import Application.TheSwoleSection.WorkoutSchedule.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WorkoutScheduleModelTest {

    private WorkoutScheduleModel workoutScheduleModel;
    private WorkoutPlanDB mockWorkoutPlanDB;
    private WorkoutPlanLogDB mockWorkoutPlanLogDB;
    private WorkoutDB mockWorkoutDB;
    private ExerciseDB mockExerciseDB;

    @BeforeEach
    void setUp() {
        // Create mock instances of the database classes
        mockWorkoutPlanDB = new WorkoutPlanDB() {
            @Override
            public Map<WorkoutPlan, String> getWorkoutPlan(String name) {
                Map<WorkoutPlan, String> map = new HashMap<>();
                WorkoutPlan workoutPlan = new WorkoutPlan();
                workoutPlan.setName("Test Plan");
                map.put(workoutPlan, "[Workout1, Workout2]");
                return map;
            }
        };

        mockWorkoutPlanLogDB = new WorkoutPlanLogDB() {
            @Override
            public String getCurrentPlan(String username) {
                return "Test Plan";
            }

            @Override
            public String getEquipDate(String workoutPlanName) {
                return "2025-05-01";
            }

            @Override
            public void equipWorkoutPlan(String workoutPlan, String dateEquipped, String username) {
                // Simulate setting a workout plan
            }

            @Override
            public void updateCurrentPlan(String username, String workoutPlan, String dateEquipped) {
                // Simulate updating the workout plan
            }

            @Override
            public void deleteCurrentPlan(String username) {
                // Simulate deleting a workout plan
            }
        };

        mockWorkoutDB = new WorkoutDB() {
            @Override
            public Workout getWorkout(String workoutId) {
                return new Workout();
            }
        };

        mockExerciseDB = new ExerciseDB() {
            @Override
            public Exercise getExercise(int exerciseId) {
                return new Exercise("Test Exercise", "Test Description", 3, 10, 50);
            }
        };

        workoutScheduleModel = new WorkoutScheduleModel();
        workoutScheduleModel.workoutPlanDB = mockWorkoutPlanDB;
        workoutScheduleModel.workoutPlanLogDB = mockWorkoutPlanLogDB;
        workoutScheduleModel.workoutDB = mockWorkoutDB;
        workoutScheduleModel.exerciseDB = mockExerciseDB;
    }

    @Test
    void testGetCurrentWorkoutPlan() {
        WorkoutPlan result = workoutScheduleModel.getCurrentWorkoutPlan("testUser");

        assertNotNull(result);
        assertEquals("Test Plan", result.getName());
    }

    @Test
    void testSetCurrentWorkoutPlan() {
        workoutScheduleModel.setCurrentWorkoutPlan("Test Plan", "2025-05-01", "testUser");

        // Since the equipWorkoutPlan method has no output, just ensure it doesn't throw an exception.
        assertDoesNotThrow(() -> workoutScheduleModel.setCurrentWorkoutPlan("Test Plan", "2025-05-01", "testUser"));
    }

    @Test
    void testUpdateCurrentWorkoutPlan() {
        workoutScheduleModel.updateCurrentWorkoutPlan("Test Plan", "2025-05-02", "testUser");

        // Ensure updateCurrentPlan does not throw an exception
        assertDoesNotThrow(() -> workoutScheduleModel.updateCurrentWorkoutPlan("Test Plan", "2025-05-02", "testUser"));
    }

    @Test
    void testDeleteCurrentWorkoutPlan() {
        workoutScheduleModel.deleteCurrentWorkoutPlan("testUser");

        // Ensure deleteCurrentPlan does not throw an exception
        assertDoesNotThrow(() -> workoutScheduleModel.deleteCurrentWorkoutPlan("testUser"));
    }

    @Test
    void testIsDateValid() {
        WorkoutPlan workoutPlan = new WorkoutPlan();
        workoutPlan.setName("Test Plan");
        workoutPlan.setDurationInWeeks(4);

        Boolean result = workoutScheduleModel.isDateValid(workoutPlan, "05/10/2025");

        assertTrue(result);
    }

    @Test
    void testFindNextMonday() throws ParseException {
        String nextMonday = workoutScheduleModel.findNextMonday("05/07/2025");
        assertEquals("05/12/2025", nextMonday);
    }

    @Test
    void testGetDayOfWeek() {
        int day = workoutScheduleModel.getDayOfWeek("05/07/2025");
        assertEquals(4, day);
    }

    @Test
    void testGetExerciseData() {
        Workout workout = new Workout(
                "Test Workout",
                "Test Description",
                45,
                300,
                "2025-05-08"
        );        workout.setExercises("1,2");

        Object[][] data = workoutScheduleModel.getExerciseData(workout);
        assertNotNull(data);
        assertEquals(2, data.length);
    }

    @Test
    void testGetExerciseColumns() {
        Object[] columns = workoutScheduleModel.getExerciseColumns();
        assertEquals(5, columns.length);
        assertEquals("Name", columns[0]);
    }
}
