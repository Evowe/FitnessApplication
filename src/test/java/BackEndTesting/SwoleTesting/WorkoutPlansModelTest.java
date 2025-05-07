package BackEndTesting.SwoleTesting;

import Application.Databases.WorkoutPlanDB;
import Application.Databases.WorkoutPlanLogDB;
import Application.TheSwoleSection.WorkoutPlans.WorkoutPlansModel;
import Application.Utility.Objects.Workout;
import Application.Utility.Objects.WorkoutPlan;
import Application.Main;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class WorkoutPlansModelTest {

    static final String TEST_PLAN_NAME = "Test Plan";
    static final String TEST_USERNAME = "test_user";

    @BeforeAll
    public static void setupUser() {
        Application.Utility.Objects.Account testUser =
                new Application.Utility.Objects.Account(TEST_USERNAME,"password");
        Main.setCurrentUser(testUser);
    }

    @Test
    public void testEquipWorkoutPlan() {
        WorkoutPlanDB workoutPlanDB = new WorkoutPlanDB();
        WorkoutPlanLogDB logDB = new WorkoutPlanLogDB();
        WorkoutPlansModel model = new WorkoutPlansModel();

        try {
            ArrayList<Workout> workouts = new ArrayList<>();
            WorkoutPlan plan = new WorkoutPlan(TEST_PLAN_NAME, "String", 2, workouts,1,"BENBILLYBOBJOE");
            plan.setWorkoutSchedule(Collections.singletonList(new Workout()));

            workoutPlanDB.addWorkoutPlan(plan);

            JTable table = new JTable(new DefaultTableModel(
                    new Object[][] {{ TEST_PLAN_NAME, "Strength", "4", "2", "[Fake]" }},
                    model.getWorkoutPlanColumns()
            ));
            table.setRowSelectionInterval(0, 0);

            assertDoesNotThrow(() -> model.equipWorkoutPlan(table));

            String currentPlan = logDB.getCurrentPlan(TEST_USERNAME);
            assertEquals(TEST_PLAN_NAME, currentPlan);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Test threw an unexpected exception: " + e.getMessage());
        } finally {
            try {
                workoutPlanDB.deleteWorkoutPlan(TEST_PLAN_NAME);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
