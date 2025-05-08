package BackEndTesting.SwoleTesting;

import Application.TheSwoleSection.CreateExercise.CreateExercise;
import Application.Utility.Objects.Exercise;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class CreateExerciseModelTest {

    private CreateExercise createExercise;
    private Exercise createdExercise;

    @BeforeEach
    public void setUp() {
        createExercise = new CreateExercise();
    }


    @Test
    public void testInvalidTypeReturnsNull() {
        Exercise invalid = createExercise.CreateExerciseCall("Invalid", "Wrong type", 999, 5, 0);
        assertNull(invalid, "Invalid type should return null");
    }

    @AfterEach
    public void tearDown() {
        if (createdExercise != null) {
            // Try to delete by name if ID isn't available
            Exercise[] all = createExercise.getAllExercises();
            for (Exercise e : all) {
                if (e.getName().equals(createdExercise.getName())) {
                    boolean deleted = createExercise.deleteExercise(e.getId());
                    assertTrue(deleted, "Exercise should be deleted after test");
                }
            }
        }
    }
}
