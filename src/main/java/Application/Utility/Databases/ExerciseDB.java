package Application.Utility.Databases;

import Application.Utility.Objects.Exercise;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExerciseDB extends DBTemplate {
    private static final String TABLE_NAME = "Exercises";

    // Default path for sample exercises
    private static final String DEFAULT_SAMPLE_PATH =
            Paths.get(System.getProperty("user.dir"), "src", "main",
                    "resources", "sample_exercises.csv").toString();

    public ExerciseDB() {
        super(TABLE_NAME);
        loadSampleExercisesIfEmpty();
    }

    @Override
    protected void createTables() throws SQLException {
        String[] exerciseColumns = {
                "Name TEXT NOT NULL UNIQUE",
                "Description TEXT",
                "Type INTEGER DEFAULT 0", // 1 = reps, 2 = sets w/ weight, 3 = sets w/o weight
                "Sets INTEGER DEFAULT 0",
                "Reps INTEGER DEFAULT 0",
                "Weight REAL DEFAULT 0.0"
        };
        createTable(TABLE_NAME, exerciseColumns);
    }

    /**
     * Checks if an exercise with the given name already exists
     */
    public boolean exerciseExists(String name) throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE Name = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    public int saveExercise(Exercise exercise) throws SQLException {
        // Check if exercise already exists
        if (exerciseExists(exercise.getName())) {
            System.out.println("Exercise '" + exercise.getName() + "' already exists");
            return -1; // Exercise already exists
        }

        String sql = "INSERT INTO " + TABLE_NAME + " (Name, Description, Type, Sets, Reps, Weight) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            int type = 0;
            if (exercise.getSets() == 0 && exercise.getReps() > 0) {
                type = 1; // Reps only
            } else if (exercise.getSets() > 0 && exercise.getWeight() > 0) {
                type = 2; // Sets with weight
            } else if (exercise.getSets() > 0 && exercise.getWeight() == 0) {
                type = 3; // Sets without weight
            }

            ps.setString(1, exercise.getName());
            ps.setString(2, exercise.getDescription());
            ps.setInt(3, type);
            ps.setInt(4, exercise.getSets());
            ps.setInt(5, exercise.getReps());
            ps.setDouble(6, exercise.getWeight());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    public int loadExercisesFromCsv(String csvFilePath) {
        int successCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            // Skip header line
            String header = reader.readLine();

            if (header == null) {
                System.out.println("CSV file is empty: " + csvFilePath);
                return 0;
            }

            String line;
            while ((line = reader.readLine()) != null) {
                // Split the CSV line
                String[] parts = line.split(",");

                if (parts.length < 6) {
                    System.out.println("Invalid CSV line format: " + line);
                    continue;
                }

                try {
                    String name = parts[0].trim();
                    String description = parts[1].trim();
                    int type = Integer.parseInt(parts[2].trim());
                    int sets = Integer.parseInt(parts[3].trim());
                    int reps = Integer.parseInt(parts[4].trim());
                    double weight = Double.parseDouble(parts[5].trim());
                    Exercise exercise = null;

                    switch (type) {
                        case 1: // Reps only
                            exercise = new Exercise(name, description, 0, reps, 0);
                            break;
                        case 2: // Sets with weight
                            exercise = new Exercise(name, description, sets, 0, weight);
                            break;
                        case 3: // Sets without weight
                            exercise = new Exercise(name, description, sets, reps, 0);
                            break;
                        default:
                            System.out.println("Invalid exercise type: " + type);
                            continue;
                    }

                    int id = saveExercise(exercise);
                    if (id > 0) {
                        successCount++;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing number in line: " + line);
                } catch (SQLException e) {
                    System.out.println("Database error: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }

        return successCount;
    }

    public void loadSampleExercisesIfEmpty() {
        try {
            // Only load samples if database is empty
            if (getAllExercises().isEmpty()) {
                File sampleFile = new File(DEFAULT_SAMPLE_PATH);

                if (!sampleFile.exists()) {
                    System.out.println("Sample exercises file not found at: " + DEFAULT_SAMPLE_PATH);
                    System.out.println("Please ensure the file exists in the resources directory.");
                    return;
                }

                // Load the samples
                int count = loadExercisesFromCsv(DEFAULT_SAMPLE_PATH);
                System.out.println("Loaded " + count + " sample exercises");
            }
        } catch (SQLException e) {
            System.out.println("Error checking database: " + e.getMessage());
        }
    }

    public Exercise getExercise(int id) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ID = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("Name");
                    String description = rs.getString("Description");
                    int sets = rs.getInt("Sets");
                    int reps = rs.getInt("Reps");
                    double weight = rs.getDouble("Weight");

                    return new Exercise(name, description, sets, reps, weight,id);
                }
            }
        }

        return null; // Not found
    }

    public List<Exercise> getAllExercises() throws SQLException {
        List<Exercise> exercises = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String name = rs.getString("Name");
                String description = rs.getString("Description");
                int sets = rs.getInt("Sets");
                int reps = rs.getInt("Reps");
                double weight = rs.getDouble("Weight");
                int id = rs.getInt("ID");

                Exercise exercise = new Exercise(name, description, sets, reps, weight,id);
                exercises.add(exercise);
            }
        }

        return exercises;
    }

    public boolean deleteExercise(int id) throws SQLException {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE ID = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        }
    }
}