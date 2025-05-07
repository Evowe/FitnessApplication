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
    public void testCreateExercise_Type1_Reps() {
        createdExercise = createExercise.CreateExerciseCall("dob", "Upper body exercise", 1, 20, 0);
        assertNotNull(createdExercise, "Exercise should be created");
        assertEquals("dob", createdExercise.getName());
    }

    @Test
    public void testCreateDuplicateExerciseReturnsNull() {
        Exercise first = createExercise.CreateExerciseCall("dob", "First insert", 1, 15, 0);
        assertNotNull(first, "First insert should succeed");

        Exercise second = createExercise.CreateExerciseCall("DuplicateTest", "Second insert", 1, 10, 0);
        assertNull(second, "Duplicate insert should return null");
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
