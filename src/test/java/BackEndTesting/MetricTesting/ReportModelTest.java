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

}
