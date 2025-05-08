package BackEndTesting.MetricTesting;

import Application.Metrics.Report.ReportModel;
import Application.Utility.Objects.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class ReportModelTest {

    private Account testAccount;
    private ReportModel reportModel;

    @BeforeEach
    public void setUp() throws Exception {
        testAccount = new Account("test_user", "password123");


        reportModel = new ReportModel(testAccount);

        Workout workout = new Workout();
        workout.setName("Test Workout");
        workout.setDescription("Test Description");
        workout.setDuration(30);
        workout.setCaloriesBurned(250);
        workout.setDate("2025-05-07");
        workout.setExercises("1");

        reportModel.addWorkout(workout, testAccount.getUsername(),50);
    }

    @Test
    public void testGetWorkoutData() {
        Object[][] data = reportModel.getWorkoutData();

        assertNotNull(data, "Workout data should not be null.");
        assertTrue(data.length > 0, "There should be at least one workout returned.");

        Object[] row = data[0];
        assertEquals("Workout", row[0]);
        assertEquals("Test Workout", row[1]);
        assertEquals("Test Description", row[2]);
        assertEquals(30, row[3]);
        assertEquals(250, row[4]);
        assertEquals("2025-05-07", row[5]);
        assertTrue(row[6].toString().contains("[]") || row[6].toString().isEmpty(), "Exercise list should be empty.");
    }
}
