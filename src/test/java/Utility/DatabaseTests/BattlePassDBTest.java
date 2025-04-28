package Utility.DatabaseTests;

import Application.Utility.Databases.BattlePassDB;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BattlePassDBTest {

    private BattlePassDB battlePassDB;
    private boolean dataExistedBefore = false;

    @Before
    public void setUp() throws SQLException {
        // Create a new instance of BattlePassDB
        battlePassDB = new BattlePassDB();

        // Check if table has data
        Connection conn = battlePassDB.getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM BattlePass");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                dataExistedBefore = true;
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            // Table might not exist yet - this is fine
            dataExistedBefore = false;
        } finally {
            conn.close();
        }
    }

    @After
    public void tearDown() throws SQLException {
        // If there was no data before, we can clear the table
        // If there was data, we should leave it as is
        if (!dataExistedBefore) {
            battlePassDB.resetBattlePass();
        } else {
            // If there was data before, we just leave it as is
            // The original data may have been modified, but that's ok for the specific tests
        }
    }

    @Test
    public void testCreateTables() throws SQLException {
        // Just verify the table exists
        Connection conn = battlePassDB.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(
                "SELECT name FROM sqlite_master WHERE type='table' AND name='BattlePass'"
        );
        ResultSet rs = pstmt.executeQuery();
        assertTrue("BattlePass table should exist", rs.next());

        rs.close();
        pstmt.close();
        conn.close();
    }

    @Test
    public void testResetBattlePass() throws SQLException {
        // Only run this test if there was no data before
        if (dataExistedBefore) {
            // Skip this test to avoid deleting existing data
            System.out.println("Skipping testResetBattlePass to preserve existing data");
            return;
        }

        // Insert mock data first
        battlePassDB.insertMockData();

        // Reset the battle pass
        battlePassDB.resetBattlePass();

        // Verify the table is empty
        Connection conn = battlePassDB.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM BattlePass");
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        assertEquals("Table should be empty after reset", 0, count);

        rs.close();
        pstmt.close();
        conn.close();
    }

    @Test
    public void testInsertMockData() throws SQLException {
        // Only run this test if there was no data before
        if (dataExistedBefore) {
            // Skip this test to avoid overwriting existing data
            System.out.println("Skipping testInsertMockData to preserve existing data");
            return;
        }

        // Reset before inserting
        battlePassDB.resetBattlePass();

        // Insert mock data
        battlePassDB.insertMockData();

        // Verify 50 tiers were inserted
        Connection conn = battlePassDB.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM BattlePass");
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        assertEquals("50 tiers should be inserted", 50, count);

        // Test a specific tier
        pstmt = conn.prepareStatement("SELECT * FROM BattlePass WHERE TierNumber = 1");
        rs = pstmt.executeQuery();
        assertTrue("Tier 1 should exist", rs.next());
        assertEquals("Tier 1", rs.getString("Title"));
        assertEquals("Coming Soon...", rs.getString("Description"));
        assertEquals("", rs.getString("ImagePath"));
        assertEquals(0, rs.getInt("IsUnlocked"));

        rs.close();
        pstmt.close();
        conn.close();
    }
}