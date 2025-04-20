package fitness.app.Objects.Databases;

import java.sql.SQLException;

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
}
