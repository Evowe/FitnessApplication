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
                    "VALUES (2, 'Pleb', 'Your muscles are small. Ew.', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (3, 'Rocket Bucks', '11', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (4, 'Wimp', 'Try going to the gym for once', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (5, 'Nothing', '', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (6, 'Rocket Bucks', '28', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (7, 'Weakling', 'I guess thats progress...', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (8, 'Rocket Bucks', '-4', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (9, 'Fledgling', 'You might actually make it', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (10, 'Cow Rocket', 'MOOOOOOO', 'src/main/resources/Images/mooket.PNG', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (11, 'Nothing', 'You just got a skin, be happy', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (12, 'Chubby', 'There is SOME mass I guess', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (13, 'Rocket Bucks', '-7', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (14, 'Soggy Noodle', 'Protein? ever heard of it?', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (15, 'Rocket Bucks', '17', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (16, 'Cheat Day Pro', 'go to the gym.', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (17, 'Rocket Bucks', '5', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (18, 'Error 404', 'Abs not found', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (19, 'Nothing', 'A rocket is next, keep working', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (20, 'Disco Rocket', 'DANCE TIME', 'src/main/resources/Images/disket.PNG', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (21, 'Skips Leg day', 'and arms. And everything else...', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (22, 'Rocket Bucks', '15', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (23, 'Nothing', 'stop complaining', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (24, 'Outrageously Mediocre', 'No comment', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (25, 'Rocket Bucks', '-32', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (26, 'Not Embarrassing', 'Hey, you are not the weakest now', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (27, 'Rocket Bucks', '31', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (28, 'Trying your best', 'Effort is effort', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (29, 'Rocket Bucks', '18', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (40, 'Kirket', 'Prof. Kirk Rocket', 'src/main/resources/Images/kirket.PNG', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (31, 'Rocket Bucks', '9', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (32, 'Looks Maxxer', 'Keep mewing bud', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (33, 'Nothing', 'try Buying your rocket bucks', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (34, 'Rocket Bucks', '1', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (35, 'Protein Addict', 'just one more scoop', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (36, 'Rocket Bucks', '28', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (37, 'SpeedRunner', 'You are the PR', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (38, 'Rocket Bucks', '14', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (39, 'Nothing', 'So close', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (30, 'Shrek Rocket', 'WHAT ARE YOU DOING IN MY SWAMP', 'src/main/resources/Images/shreket.PNG', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (41, 'Nothing', 'You really want more?', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (42, 'Rocket Bucks', '12', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (43, 'Big (In a good way)', 'Gravity complains when you arive', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (44, 'Rocket Bucks', '100', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (45, 'Maximus', 'Your Gluteous is...', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (46, 'Nothing', 'Last stretch', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (47, 'Ascended Lifter', 'Beyond gym. Beyond mortal.', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (48, 'Rocket Bucks', '104', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (49, 'SWOLE', 'You have acheived peak mass, physics weeps', '', 0)");
            stmt.executeUpdate("INSERT INTO BattlePass (TierNumber, Title, Description, ImagePath, IsUnlocked) " +
                    "VALUES (50, 'SWOLEKET', 'THE BIGGEST ROCKET OF THEM ALL', 'src/main/resources/Images/swoleket.PNG', 0)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
