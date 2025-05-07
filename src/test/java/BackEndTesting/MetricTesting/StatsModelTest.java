package BackEndTesting.MetricTesting;

import Application.Metrics.Statistics.StatsModel;
import Application.Utility.Objects.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class StatsModelTest {

    private StatsModel model;
    private Account testAcc;

    @BeforeEach
    public void setUp() {
        testAcc = new Account("testuser","testPass");
        model = new StatsModel(testAcc);
    }

    @Test
    public void testVerifyCalories_Valid() {
        String result = model.verifyCalories(500);
        assertNull(result);  // null means success
    }

    @Test
    public void testVerifyCalories_InvalidNegative() {
        String result = model.verifyCalories(-10);
        assertEquals("Bad Input", result);
    }

    @Test
    public void testVerifyCalories_InvalidTooHigh() {
        String result = model.verifyCalories(20000);
        assertEquals("Bad Input", result);
    }

    @Test
    public void testVerifySleep_Valid() {
        String result = model.verifySleep(7.5);
        assertNull(result);
    }

    @Test
    public void testVerifySleep_InvalidNegative() {
        String result = model.verifySleep(-1.0);
        assertEquals("Bad Input", result);
    }

    @Test
    public void testVerifySleep_TooHigh() {
        String result = model.verifySleep(16.0);
        assertEquals("Bad Input", result);
    }

    @Test
    public void testVerifyWeight_Valid() {
        String result = model.verifyWeight(180.0);
        assertNull(result);
    }

    @Test
    public void testVerifyWeight_InvalidNegative() {
        String result = model.verifyWeight(-5.0);
        assertEquals("Bad Input", result);
    }

    @Test
    public void testVerifyWeight_TooHigh() {
        String result = model.verifyWeight(900.0);
        assertEquals("Bad Input", result);
    }

    @Test
    public void testGetXData_HasTenDays() {
        ArrayList<Integer> xData = model.getXData();
        assertEquals(10, xData.size());
    }

    @Test
    public void testGetYData_Calories() {
        ArrayList<Integer> yData = model.getYData("calories");
        assertEquals(10, yData.size());
    }

    @Test
    public void testGetYData_Sleep() {
        ArrayList<Integer> yData = model.getYData("sleep");
        assertEquals(10, yData.size());
    }

    @Test
    public void testGetYData_Weight() {
        ArrayList<Integer> yData = model.getYData("weight");
        assertEquals(10, yData.size());
    }

    @Test
    public void testGetDailyMetric_Calories() {
        String result = model.getDailyMetric("calories");
        assertNotNull(result);
    }

    @Test
    public void testGetDailyMetric_Sleep() {
        String result = model.getDailyMetric("sleep");
        assertNotNull(result);
    }

    @Test
    public void testGetDailyMetric_Weight() {
        String result = model.getDailyMetric("weight");
        assertNotNull(result);
    }

    @Test
    public void testGetDailyMetric_InvalidCategory() {
        String result = model.getDailyMetric("nonsense");
        assertEquals("0", result);
    }
}
