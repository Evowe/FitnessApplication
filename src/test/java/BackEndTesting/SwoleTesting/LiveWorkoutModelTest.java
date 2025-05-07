package BackEndTesting.SwoleTesting;

import Application.TheSwoleSection.LiveWorkout.LiveWorkoutModel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LiveWorkoutModelTest {

    @Test
    public void testGetWorkoutData() {
        LiveWorkoutModel model = new LiveWorkoutModel();
        final Object[][][] data = {null};

        assertDoesNotThrow(() -> {
            data[0] = model.getWorkoutData();
        });

        assertNotNull(data[0], "Workout data should not be null");

        for (int i = 0; i < data[0].length; i++) {
            assertEquals(5, data[0][i].length, "Each row should contain exactly 5 columns");
            assertEquals("Join", data[0][i][4], "Last column should contain 'Join'");
        }
    }

    @Test
    public void testGetWorkoutColumns() {
        LiveWorkoutModel model = new LiveWorkoutModel();
        Object[] columns = model.getWorkoutColumns();

        assertNotNull(columns, "Column headers should not be null");
        assertEquals(6, columns.length, "There should be 6 column headers");

        assertEquals("Name", columns[0]);
        assertEquals("Description", columns[1]);
        assertEquals("Duration", columns[2]);
        assertEquals("Start Time", columns[3]);
        assertEquals("Join", columns[4]);
    }
}
