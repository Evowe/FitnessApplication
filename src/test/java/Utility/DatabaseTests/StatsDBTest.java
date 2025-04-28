package Utility.DatabaseTests;

import Application.Utility.Databases.StatsDB;
import Application.Utility.Databases.DatabaseManager;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StatsDBTest {

    private StatsDB statsDB;
    private final String TEST_USERNAME_PREFIX = "test_stats_" + UUID.randomUUID().toString().substring(0, 8) + "_";

    @BeforeEach
    public void setUp() {
        statsDB = DatabaseManager.getStatsDB();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        Connection conn = null;
        try {
            conn = statsDB.getConnection();

            try (PreparedStatement pstmt = conn.prepareStatement(
                    "DELETE FROM stats WHERE username LIKE ?")) {
                pstmt.setString(1, TEST_USERNAME_PREFIX + "%");
                pstmt.executeUpdate();
            }
        } finally {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
    }

    private String getTestUsername(String baseName) {
        return TEST_USERNAME_PREFIX + baseName;
    }

    @Test
    public void testAddMetric() throws SQLException {
        String username = getTestUsername("add");
        LocalDate today = LocalDate.now();

        boolean result = statsDB.addOrUpdateMetric(username, today, 2000, 8.0, 70.5);

        assertTrue(result, "Adding new metric should return true");

        Map<String, Object> metrics = statsDB.getDailyMetrics(username, today);

        assertNotNull(metrics, "Retrieved metrics should not be null");
        assertFalse(metrics.isEmpty(), "Retrieved metrics should not be empty");
        assertEquals(username, metrics.get("username"));
        assertEquals(today.toString(), metrics.get("date"));
        assertEquals(2000, metrics.get("calories"));
        assertEquals(8.0, metrics.get("sleep"));
        assertEquals(70.5, metrics.get("weight"));
    }

    @Test
    public void testUpdateMetric() throws SQLException {
        String username = getTestUsername("update");
        LocalDate today = LocalDate.now();

        statsDB.addOrUpdateMetric(username, today, 2000, 8.0, 70.5);

        boolean result = statsDB.addOrUpdateMetric(username, today, 2200, 7.5, 70.0);

        assertTrue(result, "Updating existing metric should return true");

        Map<String, Object> metrics = statsDB.getDailyMetrics(username, today);

        assertEquals(2200, metrics.get("calories"));
        assertEquals(7.5, metrics.get("sleep"));
        assertEquals(70.0, metrics.get("weight"));
    }

    @Test
    public void testPartialUpdate() throws SQLException {
        String username = getTestUsername("partial");
        LocalDate today = LocalDate.now();

        statsDB.addOrUpdateMetric(username, today, 2000, 8.0, 70.5);

        boolean result = statsDB.addOrUpdateMetric(username, today, 2200, null, null);

        assertTrue(result, "Partial update should return true");

        Map<String, Object> metrics = statsDB.getDailyMetrics(username, today);

        assertEquals(2200, metrics.get("calories"));
        assertEquals(8.0, metrics.get("sleep"), "Sleep should remain unchanged");
        assertEquals(70.5, metrics.get("weight"), "Weight should remain unchanged");
    }

    @Test
    public void testGetMetricsRange() throws SQLException {
        String username = getTestUsername("range");
        LocalDate date1 = LocalDate.now().minusDays(2);
        LocalDate date2 = LocalDate.now().minusDays(1);
        LocalDate date3 = LocalDate.now();

        statsDB.addOrUpdateMetric(username, date1, 1800, 7.0, 71.0);
        statsDB.addOrUpdateMetric(username, date2, 2000, 8.0, 70.5);
        statsDB.addOrUpdateMetric(username, date3, 2200, 7.5, 70.0);

        List<Map<String, Object>> metricsList = statsDB.getMetricsRange(username, date1, date3);

        assertEquals(3, metricsList.size(), "Should return metrics for all three days");

        assertEquals(date1.toString(), metricsList.get(0).get("date"));
        assertEquals(1800, metricsList.get(0).get("calories"));

        assertEquals(date2.toString(), metricsList.get(1).get("date"));
        assertEquals(2000, metricsList.get(1).get("calories"));

        assertEquals(date3.toString(), metricsList.get(2).get("date"));
        assertEquals(2200, metricsList.get(2).get("calories"));
    }

    @Test
    public void testGetMetricsRangePartial() throws SQLException {
        String username = getTestUsername("range_partial");
        LocalDate date1 = LocalDate.now().minusDays(2);
        LocalDate date2 = LocalDate.now().minusDays(1);
        LocalDate date3 = LocalDate.now();

        statsDB.addOrUpdateMetric(username, date1, 1800, 7.0, 71.0);
        statsDB.addOrUpdateMetric(username, date3, 2200, 7.5, 70.0);

        List<Map<String, Object>> metricsList = statsDB.getMetricsRange(username, date1, date3);

        assertEquals(2, metricsList.size(), "Should return metrics for two days");
        assertEquals(date1.toString(), metricsList.get(0).get("date"));
        assertEquals(date3.toString(), metricsList.get(1).get("date"));
    }

    @Test
    public void testDeleteMetrics() throws SQLException {
        String username = getTestUsername("delete");
        LocalDate today = LocalDate.now();

        statsDB.addOrUpdateMetric(username, today, 2000, 8.0, 70.5);

        Map<String, Object> metrics = statsDB.getDailyMetrics(username, today);
        assertFalse(metrics.isEmpty(), "Metrics should exist before deletion");

        boolean result = statsDB.deleteMetrics(username, today);

        assertTrue(result, "Deletion should return true");

        Map<String, Object> metricsAfterDelete = statsDB.getDailyMetrics(username, today);
        assertTrue(metricsAfterDelete.isEmpty(), "Metrics should be empty after deletion");
    }

    @Test
    public void testDailyMetricsNonExistent() throws SQLException {
        String username = getTestUsername("nonexistent");
        LocalDate today = LocalDate.now();

        Map<String, Object> metrics = statsDB.getDailyMetrics(username, today);

        assertTrue(metrics.isEmpty(), "Metrics for non-existent entry should be empty");
    }

    @Test
    public void testAddNullValues() throws SQLException {
        String username = getTestUsername("null_values");
        LocalDate today = LocalDate.now();

        boolean result = statsDB.addOrUpdateMetric(username, today, null, null, null);

        assertTrue(result, "Adding with null values should succeed");

        Map<String, Object> metrics = statsDB.getDailyMetrics(username, today);
        assertFalse(metrics.isEmpty(), "Entry should still be created");

        assertEquals(0, metrics.get("calories"), "Default value for calories should be 0");
        assertEquals(0.0, metrics.get("sleep"), "Default value for sleep should be 0.0");
        assertEquals(0.0, metrics.get("weight"), "Default value for weight should be 0.0");
    }

    @Test
    public void testInvalidParameters() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            statsDB.addOrUpdateMetric(null, LocalDate.now(), 2000, 8.0, 70.5);
        });
        assertTrue(exception.getMessage().contains("Username cannot be null or empty"));

        exception = assertThrows(IllegalArgumentException.class, () -> {
            statsDB.addOrUpdateMetric("", LocalDate.now(), 2000, 8.0, 70.5);
        });
        assertTrue(exception.getMessage().contains("Username cannot be null or empty"));

        exception = assertThrows(IllegalArgumentException.class, () -> {
            statsDB.addOrUpdateMetric(getTestUsername("invalid"), null, 2000, 8.0, 70.5);
        });
        assertTrue(exception.getMessage().contains("Date cannot be null"));
    }

    @Test
    public void testMetricsRangeInvalidDates() {
        String username = getTestUsername("invalid_range");
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().minusDays(1); // End date before start date

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            statsDB.getMetricsRange(username, startDate, endDate);
        });
        assertTrue(exception.getMessage().contains("Start date must be before or equal to end date"));
    }
}