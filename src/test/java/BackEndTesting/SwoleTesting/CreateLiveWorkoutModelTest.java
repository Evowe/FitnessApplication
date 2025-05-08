package BackEndTesting.SwoleTesting;

import Application.TheSwoleSection.CreateLiveWorkout.NewLiveWorkoutModel;
import Application.Utility.Objects.LiveWorkout;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class CreateLiveWorkoutModelTest {

    private NewLiveWorkoutModel model;

    @BeforeEach
    public void setUp() {
        model = new NewLiveWorkoutModel();
    }

    @Test
    public void testAddWorkout() {
        LiveWorkout workout = new LiveWorkout(
                "Test Live Workout",1,
                false,
                "This is a test workout",
                "2025-05-08",
                60,5
        );

        assertDoesNotThrow(() -> model.addWorkout(workout, 1));
    }
}
