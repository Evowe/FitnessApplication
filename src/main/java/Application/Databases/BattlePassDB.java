package Application.Databases;

import java.sql.*;

public class BattlePassDB extends DBTemplate {

    public BattlePassDB() {
        super("BattlePass");
    }

    @Override
    protected void createTables() throws SQLException {
        String[] columns = new String[] {
                "TierNumber INTEGER NOT NULL UNIQUE",
                "Title TEXT NOT NULL",
                "Description TEXT",
                "ImagePath TEXT",
                "IsUnlocked INTEGER DEFAULT 0"
        };

        createTable(tableName, columns);
    }

    // Optional helper to reset data
    public void resetBattlePass() throws SQLException {
        executeSQL("DELETE FROM " + tableName);
    }

    // Optional helper to populate placeholder tiers
    public void insertMockData() throws SQLException {
        for (int i = 1; i <= 50; i++) {
            String sql = String.format(
                    "INSERT INTO %s (TierNumber, Title, Description, ImagePath, IsUnlocked) VALUES (%d, 'Tier %d', 'Coming Soon...', '', 0);",
                    tableName, i, i
            );
            executeSQL(sql);
        }
    }

    public static void fillBP() {
        try (Connection conn = BattlePassDB.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("DELETE FROM BattlePass");

            // Insert skin tiers with image paths
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (1, 'Blue Rocket', 'A cool blue rocket skin', 'src/main/resources/Images/BlueRocket.png', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (2, 'Pleb', 'A title to show your progress', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (10, 'Cow Rocket', 'MOOOOOOO', 'src/main/resources/Images/mooket.PNG', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (20, 'Disco Rocket', 'DANCE TIME', 'src/main/resources/Images/disket.PNG', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (30, 'Kirket', 'Prof. Kirk Rocket', 'src/main/resources/Images/kirket.PNG', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (40, 'Shrek Rocket', 'WHAT ARE YOU DOING IN MY SWAMP', 'src/main/resources/Images/shreket.PNG', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (50, 'SWOLEKET', 'THE BIGGEST ROCKET OF THEM ALL', 'src/main/resources/Images/swoleket.PNG', 0)");

            // Insert default filler tiers
            for (int i = 2; i <= 49; i++) {
                if (i % 10 == 0) continue; // Skip custom skin tiers
                if ( i == 2 ) continue;
                String title = "Tier " + i;
                String description = "Coming Soon...";

                stmt.executeUpdate(String.format(
                        "INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                                "VALUES (%d, '%s', '%s', '', 0)",
                        i, title.replace("'", "''"), description.replace("'", "''")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

/*
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (2, 'Pleb', 'A title to show your progress', '', 0)");

 */

