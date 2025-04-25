package fitness.app.Utility.Databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// Templated Database Class
public abstract class DBTemplate {
    protected final String tableName;
    private static final String DB_NAME = "fitness.db";
    private static final String DB_URL = "jdbc:sqlite:" + DB_NAME;
    private static Connection sharedConnection;

    public DBTemplate(String tableName) {
        this.tableName = tableName;
        initialize();
    }

    private void initialize() {
        try {
            createTables();
            System.out.println("Table " + tableName + " initialized successfully");
        } catch (SQLException e) {
            System.out.println("Error initializing table: " + e.getMessage());
        }
    }

    protected abstract void createTables() throws SQLException;

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

    public static synchronized Connection getConnection() throws SQLException {
        try {
            if (sharedConnection == null || sharedConnection.isClosed()) {
                Class.forName("org.sqlite.JDBC");
                sharedConnection = DriverManager.getConnection(DB_URL);
                return sharedConnection;
            }
            return sharedConnection;
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQLite JDBC driver not found", e);
        }
    }

    public void executeSQL(String sql) throws SQLException {
        try (Connection conn = getConnection();
        Statement statement = conn.createStatement()) {
            statement.execute(sql);
        }
    }

    public static void closeConnection() {
        try {
            if (sharedConnection != null && !sharedConnection.isClosed()) {
                sharedConnection.close();
                sharedConnection = null;
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}