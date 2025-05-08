package BackEndTesting.WidgetTesting;

import Application.Utility.Widgets.Calendar.CalendarModel;
import org.junit.jupiter.api.Test;

import java.time.YearMonth;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CalendarModelTest {

    @Test
    public void testCalendarInitializationForKnownMonth() {
        YearMonth yearMonth = YearMonth.of(2025, 2);
        CalendarModel calendarModel = new CalendarModel(yearMonth);
        ArrayList<String[]> calendar = calendarModel.getCalendar();

        assertEquals(7, calendar.size());

        for (int i = 0; i < calendar.size(); i++) {
            assertEquals(7, calendar.get(i).length, "Row " + i + " should have 7 days");
        }

        String[] expectedLabels = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        String[] actualLabels = calendar.get(0);

        for (int i = 0; i < 7; i++) {
            assertEquals(expectedLabels[i], actualLabels[i], "Label mismatch at index " + i);
        }

        assertEquals("26", calendar.get(1)[0]); // Sunday
    }
}
