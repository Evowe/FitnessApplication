package BackEndTesting.SwoleTesting;

import Application.TheSwoleSection.ExerciseLibrary.ExerciseLibraryModel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExerciseLibraryModelTest {

    @Test
    public void testGetExerciseData_NoExceptionThrown() {
        final Object[][][] data = {null};
        assertDoesNotThrow(() -> {
            data[0] = ExerciseLibraryModel.getExerciseData();
        });
        assertNotNull(data[0], "Returned data should not be null");
        if (data[0].length > 0) {
            assertEquals(5, data[0][0].length, "Each exercise row should have 5 columns");
        }
    }

    @Test
    public void testGetExerciseColumns_CorrectHeaders() {
        Object[] columns = ExerciseLibraryModel.getExerciseColums();
        assertNotNull(columns);
        assertEquals(5, columns.length);
        assertEquals("Name", columns[0]);
        assertEquals("Description", columns[1]);
        assertEquals("Sets", columns[2]);
        assertEquals("Reps", columns[3]);
        assertEquals("Weight", columns[4]);
    }
}
