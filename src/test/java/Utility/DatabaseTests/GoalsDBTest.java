package Utility.DatabaseTests;

import Application.Databases.DatabaseManager;
import Application.Databases.GoalsDB;
import Application.Utility.Objects.Goal;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GoalsDBTest {

    private GoalsDB goalsDB;
    private final String TEST_USER_PREFIX = "test_user_" + UUID.randomUUID().toString().substring(0, 8) + "_";
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    @BeforeEach
    public void setUp() {
        goalsDB = DatabaseManager.getGoalsDB();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        Connection conn = null;
        try {
            conn = goalsDB.getConnection();

            try (PreparedStatement pstmt = conn.prepareStatement(
                    "DELETE FROM Goals WHERE username LIKE ?")) {
                pstmt.setString(1, TEST_USER_PREFIX + "%");
                pstmt.executeUpdate();
            }
        } finally {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
    }

    private String getTestUsername(String baseName) {
        return TEST_USER_PREFIX + baseName;
    }

    @Test
    public void testAddAndGetGoal() throws SQLException, ParseException {
        String username = getTestUsername("addGet");
        Goal testGoal = new Goal(username, "Weight", 150, "05/15/2025", false);

        goalsDB.addGoal(testGoal);

        Goal retrievedGoal = goalsDB.getGoalByTypeAndUsername(username, "Weight");

        assertNotNull(retrievedGoal);
        assertEquals(username, retrievedGoal.getAssociatedUsername());
        assertEquals("Weight", retrievedGoal.getType());
        assertEquals(150, retrievedGoal.getValue());
        assertEquals(dateFormat.parse("05/15/2025"), retrievedGoal.getDate());
        assertFalse(retrievedGoal.getCompleted());
    }

    @Test
    public void testEnsureDefaultGoals() throws SQLException, ParseException {
        String username = getTestUsername("defaults");

        assertNull(goalsDB.getGoalByTypeAndUsername(username, "Weight"));
        assertNull(goalsDB.getGoalByTypeAndUsername(username, "Distance"));

        goalsDB.ensureDefaultGoals(username);

        Goal weightGoal = goalsDB.getGoalByTypeAndUsername(username, "Weight");
        Goal distanceGoal = goalsDB.getGoalByTypeAndUsername(username, "Distance");

        assertNotNull(weightGoal);
        assertNotNull(distanceGoal);

        assertEquals(0, weightGoal.getValue());
        assertEquals(0, distanceGoal.getValue());
        assertEquals(dateFormat.parse("01/01/2099"), weightGoal.getDate());
        assertEquals(dateFormat.parse("01/01/2099"), distanceGoal.getDate());
    }

    @Test
    public void testGetGoalsByUsername() throws SQLException, ParseException {
        String username = getTestUsername("multiple");

        Goal weightGoal = new Goal(username, "Weight", 160, "06/15/2025", false);
        Goal distanceGoal = new Goal(username, "Distance", 500, "06/30/2025", true);

        goalsDB.addGoal(weightGoal);
        goalsDB.addGoal(distanceGoal);

        List<Goal> goals = goalsDB.getGoalsByUsername(username);

        assertEquals(2, goals.size());

        boolean foundWeight = false;
        boolean foundDistance = false;

        for (Goal goal : goals) {
            if (goal.getType().equals("Weight")) {
                foundWeight = true;
                assertEquals(160, goal.getValue());
                assertEquals(dateFormat.parse("06/15/2025"), goal.getDate());
                assertFalse(goal.getCompleted());
            } else if (goal.getType().equals("Distance")) {
                foundDistance = true;
                assertEquals(500, goal.getValue());
                assertEquals(dateFormat.parse("06/30/2025"), goal.getDate());
                assertTrue(goal.getCompleted());
            }
        }

        assertTrue(foundWeight, "Should find Weight goal");
        assertTrue(foundDistance, "Should find Distance goal");
    }

    @Test
    public void testUpdateGoal() throws SQLException, ParseException {
        String username = getTestUsername("update");
        Goal initialGoal = new Goal(username, "Weight", 170, "07/15/2025", false);

        goalsDB.addGoal(initialGoal);

        Goal updatedGoal = new Goal(username, "Weight", 165, "08/15/2025", true);
        goalsDB.updateGoal(updatedGoal);

        Goal retrievedGoal = goalsDB.getGoalByTypeAndUsername(username, "Weight");

        assertNotNull(retrievedGoal);
        assertEquals(165, retrievedGoal.getValue());
        assertEquals(dateFormat.parse("08/15/2025"), retrievedGoal.getDate());
        assertTrue(retrievedGoal.getCompleted());
    }

    @Test
    public void testUpdateGoalCompletion() throws SQLException, ParseException {
        String username = getTestUsername("completion");
        Goal initialGoal = new Goal(username, "Distance", 1000, "09/15/2025", false);

        goalsDB.addGoal(initialGoal);

        Goal beforeUpdate = goalsDB.getGoalByTypeAndUsername(username, "Distance");
        assertFalse(beforeUpdate.getCompleted());

        initialGoal.setCompleted(true);
        goalsDB.updateGoalCompletion(initialGoal);

        Goal afterUpdate = goalsDB.getGoalByTypeAndUsername(username, "Distance");
        assertTrue(afterUpdate.getCompleted());

        assertEquals(1000, afterUpdate.getValue());
        assertEquals(dateFormat.parse("09/15/2025"), afterUpdate.getDate());
    }

    @Test
    public void testDeleteGoal() throws SQLException, ParseException {
        String username = getTestUsername("delete");
        Goal goal = new Goal(username, "Weight", 180, "10/15/2025", false);

        goalsDB.addGoal(goal);

        assertNotNull(goalsDB.getGoalByTypeAndUsername(username, "Weight"));

        goalsDB.deleteGoal(username, "Weight");

        assertNull(goalsDB.getGoalByTypeAndUsername(username, "Weight"));
    }
}