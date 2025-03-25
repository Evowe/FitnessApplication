package fitness.app.Objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// Templated Database Class
public abstract class DBTemplate {
    protected final String dbName;
    protected final String url;

    public DBTemplate(String dbName) {
        this.dbName = dbName;
        this.url = "jdbc:sqlite:" + dbName + ".db";
        initialize();
    }

    private void initialize() {
        try {
            createDatabase();
            System.out.println("Database " + dbName + " initialized successfully");
        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }

    protected abstract void createDatabase() throws SQLException;

    protected void createTable(String tableName, String[] columns) throws SQLException {
        try (Connection conn = getConnection();
             Statement statement = conn.createStatement()) {

            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS ")
                    .append(tableName)
                    .append(" (");

            // Add ID as primary key by default
            sqlBuilder.append("ID INTEGER PRIMARY KEY AUTOINCREMENT");

            // Add other columns
            for (String column : columns) {
                sqlBuilder.append(", ").append(column);
            }

            sqlBuilder.append(");");

            // Execute the SQL statement
            statement.execute(sqlBuilder.toString());
            System.out.println("Table " + tableName + " created successfully");
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public void executeSQL(String sql) throws SQLException {
        try (Connection conn = getConnection();
             Statement statement = conn.createStatement()) {
            statement.execute(sql);
        }
    }
}