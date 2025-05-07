package BackEndTesting.SwoleTesting;

import Application.TheSwoleSection.CreateWorkout.NewWorkoutModel;
import Application.Utility.Objects.Workout;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class WorkoutModelTest {

    private NewWorkoutModel model;

    @BeforeEach
    public void setUp() {
        model = new NewWorkoutModel();
    }

    @Test
    public void testAddWorkoutToDatabase() {
        Workout workout = new Workout(
                "Test Workout",
                "Test Description",
                45,
                300,
                "2025-05-08"
        );

        assertDoesNotThrow(() -> model.addWorkoutToDatabase(workout, "testuser"));
    }
}
