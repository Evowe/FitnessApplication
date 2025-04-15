package fitness.app.Objects.Databases;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class StatsDB extends DBTemplate {
    private static final String TABLE_NAME = "stats";

    public StatsDB() {
        super(TABLE_NAME);
    }

    @Override
    protected void createTables() throws SQLException {
        String[] columns = {
                "username STRING NOT NULL",
                "date TEXT NOT NULL", // Store as YYYY-MM-DD
                "calories INTEGER DEFAULT 0",
                "sleep DOUBLE DEFAULT 0.0",
                "weight DOUBLE DEFAULT 0.0",
                "UNIQUE(username, date)" // Make sure only one entry per day for graphing
        };

        createTable(TABLE_NAME, columns);
    }

    public boolean addOrUpdateMetric(String username, LocalDate date, Integer calories, Double sleep, Double weight) throws SQLException {
        // Validate parameters
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }

        // Check if an entry already exists for this user and date
        if (metricExists(username, date)) {
            return updateMetric(username, date, calories, sleep, weight);
        } else {
            return addMetric(username, date, calories, sleep, weight);
        }
    }

    private boolean addMetric(String username, LocalDate date, Integer calories, Double sleep, Double weight) throws SQLException{
        String sql = "INSERT INTO " + TABLE_NAME + " (username, date, calories, sleep, weight) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, username);
            ps.setString(2, date.toString());

            if (calories != null){
                ps.setInt(3, calories);
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            if (sleep != null){
                ps.setDouble(4, sleep);
            } else {
                ps.setNull(4, Types.DOUBLE);
            }

            if (weight != null){
                ps.setDouble(5, weight);
            } else {
                ps.setNull(5, Types.DOUBLE);
            }

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error adding metrics: " + e.getMessage());
            throw e;
        }
    }

    private boolean updateMetric(String username, LocalDate date, Integer calories, Double sleep, Double weight) throws SQLException {
        // If all values are null, nothing to update
        if (calories == null && sleep == null && weight == null) {
            return false;
        }

        StringBuilder sqlBuilder = new StringBuilder("UPDATE " + TABLE_NAME + " SET ");
        List<Object> params = new ArrayList<>();

        boolean needComma = false;

        // Only update fields that are not null
        if (calories != null) {
            sqlBuilder.append("calories = ?");
            params.add(calories);
            needComma = true;
        }

        if (sleep != null) {
            if (needComma) sqlBuilder.append(", ");
            sqlBuilder.append("sleep = ?");
            params.add(sleep);
            needComma = true;
        }

        if (weight != null) {
            if (needComma) sqlBuilder.append(", ");
            sqlBuilder.append("weight = ?");
            params.add(weight);
        }

        sqlBuilder.append(" WHERE username = ? AND date = ?");

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlBuilder.toString())) {

            // Set parameters
            for (int i = 0; i < params.size(); i++) {
                if (params.get(i) instanceof Integer) {
                    ps.setInt(i + 1, (Integer) params.get(i));
                } else if (params.get(i) instanceof Double) {
                    ps.setDouble(i + 1, (Double) params.get(i));
                }
            }

            // Set the WHERE clause parameters
            ps.setString(params.size() + 1, username);
            ps.setString(params.size() + 2, date.toString());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    private boolean metricExists(String username, LocalDate date) throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE username = ? AND date = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, date.toString());

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    public Map<String, Object> getDailyMetrics(String username, LocalDate date) throws SQLException {
        if (username == null || username.trim().isEmpty() || date == null) {
            throw new IllegalArgumentException("Invalid username or date");
        }

        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ? AND date = ?";
        Map<String, Object> metrics = new HashMap<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, date.toString());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    metrics.put("username", rs.getString("username"));
                    metrics.put("date", rs.getString("date"));
                    metrics.put("calories", rs.getInt("calories"));
                    metrics.put("sleep", rs.getDouble("sleep"));
                    metrics.put("weight", rs.getDouble("weight"));
                }
            }

            return metrics;
        }
    }

    public List<Map<String, Object>> getMetricsRange(String username, LocalDate startDate, LocalDate endDate) throws SQLException {
        if (username == null || username.trim().isEmpty() || startDate == null || endDate == null) {
            throw new IllegalArgumentException("Invalid parameters");
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }

        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ? AND date BETWEEN ? AND ? ORDER BY date";
        List<Map<String, Object>> metricsList = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, startDate.toString());
            ps.setString(3, endDate.toString());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> metrics = new HashMap<>();
                    metrics.put("username", rs.getString("username"));
                    metrics.put("date", rs.getString("date"));
                    metrics.put("calories", rs.getInt("calories"));
                    metrics.put("sleep", rs.getDouble("sleep"));
                    metrics.put("weight", rs.getDouble("weight"));

                    metricsList.add(metrics);
                }
            }

            return metricsList;
        }
    }

    public boolean deleteMetrics(String username, LocalDate date) throws SQLException {
        if (username == null || username.trim().isEmpty() || date == null) {
            throw new IllegalArgumentException("Invalid username or date");
        }

        String sql = "DELETE FROM " + TABLE_NAME + " WHERE username = ? AND date = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, date.toString());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }
}