package BackEndTesting.MetricTesting;

import Application.Metrics.Goals.GoalsModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class GoalsModelTest {

    private GoalsModel model;
    private Date futureDate;

    @BeforeEach
    public void setUp() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 5);
        futureDate = cal.getTime();

        model = new GoalsModel("Run 5K", futureDate);
    }

    @Test
    public void testGetAndSetGoal() {
        model.setGoal("Swim 1K");
        assertEquals("Swim 1K", model.getGoal());
    }

    @Test
    public void testGetAndSetDeadline() {
        Calendar newDate = Calendar.getInstance();
        newDate.add(Calendar.MONTH, 1);
        Date updated = newDate.getTime();

        model.setDeadline(updated);
        assertEquals(updated, model.getDeadline());
    }

    @Test
    public void testVerifyDistance_Valid() {
        assertNull(GoalsModel.verifyDistance(0.0));
        assertNull(GoalsModel.verifyDistance(10000.0));
        assertNull(GoalsModel.verifyDistance(500.0));
    }

    @Test
    public void testVerifyDistance_Invalid() {
        assertEquals("Bad Input", GoalsModel.verifyDistance(-1.0));
        assertEquals("Bad Input", GoalsModel.verifyDistance(10001.0));
    }

    @Test
    public void testVerifyDate_NullOrEmpty() {
        assertEquals("Please enter a date.", GoalsModel.verifyDate(null));
        assertEquals("Please enter a date.", GoalsModel.verifyDate("   "));
    }

    @Test
    public void testVerifyDate_InvalidFormat() {
        assertEquals("Invalid date format. Please use MM/dd/yyyy.", GoalsModel.verifyDate("2025-12-01"));
        assertEquals("Invalid date format. Please use MM/dd/yyyy.", GoalsModel.verifyDate("12/1/2025"));
    }

    @Test
    public void testVerifyDate_InvalidMonth() {
        assertEquals("Invalid month.", GoalsModel.verifyDate("13/01/2025"));
    }

    @Test
    public void testVerifyDate_InvalidDay() {
        assertEquals("Invalid day.", GoalsModel.verifyDate("12/00/2025"));
    }

    @Test
    public void testVerifyDate_InvalidRealDate() {
        assertEquals("Invalid date. Please check day and month values.", GoalsModel.verifyDate("02/30/2025")); // Feb 30 does not exist
    }

    @Test
    public void testVerifyDate_PastDate() {
        assertEquals("Please enter a future date.", GoalsModel.verifyDate("01/01/2000"));
    }

    @Test
    public void testVerifyDate_ValidFuture() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 2);
        Date future = cal.getTime();

        int mm = cal.get(Calendar.MONTH) + 1;
        int dd = cal.get(Calendar.DAY_OF_MONTH);
        int yyyy = cal.get(Calendar.YEAR);

        String dateString = String.format("%02d/%02d/%04d", mm, dd, yyyy);
        assertNull(GoalsModel.verifyDate(dateString));
    }
}
