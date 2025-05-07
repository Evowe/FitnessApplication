package BackEndTesting.SwoleTesting;

import Application.TheSwoleSection.TrainerCreatedWorkoutPlan.CreateWorkoutPlanModel;
import Application.Utility.Objects.Workout;
import Application.Utility.Objects.WorkoutPlan;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CreateWorkoutPlanModelTest {

    @Test
    public void testGetAllWorkoutsReturnsNonNullList() {
        CreateWorkoutPlanModel model = new CreateWorkoutPlanModel();
        final List<Workout>[] workouts = new List[]{null};

        assertDoesNotThrow(() -> {
            workouts[0] = model.getAllWorkouts();
        });

        assertNotNull(workouts[0], "Returned workout list should not be null");
    }

    @Test
    public void testAddWorkoutPlanDoesNotThrow() {
        CreateWorkoutPlanModel model = new CreateWorkoutPlanModel();
        WorkoutPlan plan = new WorkoutPlan();

        ArrayList<Workout> list = new ArrayList<>();
        plan.setName("Test Plan");
        plan.setGoal("Test Plan Goal");
        plan.setAuthor("Test Plan Author");
        plan.setIntensity(2);
        plan.setDurationInWeeks(2);
        plan.setWorkoutSchedule(list);


        assertDoesNotThrow(() -> {
            model.addWorkoutPlan(plan);
        });
    }
}
