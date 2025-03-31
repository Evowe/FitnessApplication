package fitness.app.CreateExercise;

import fitness.app.Objects.DatabaseManager;
import fitness.app.Objects.Exercise;
import fitness.app.Objects.ExerciseDB;

import java.sql.SQLException;

public class CreateExercise {
    private static final String DB_NAME = "exercises";

    public Exercise CreateExerciseCall(String name, String desc, Integer type, int valA, double valB) {
        Exercise exercise = null;
        switch(type) {
            case 1: // reps
                exercise = new Exercise(name, desc, 0, valA, 0);
                break;
            case 2: // sets w/ weight
                exercise = new Exercise(name, desc, valA, 0, valB);
                break;

            case 3: // sets no weight
                exercise = new Exercise(name, desc, valA, 0, 0);
                break;

            default:
                System.out.println("Invalid exercise type");
                return null;
        }
        System.out.println("Exercise created");

        try {
            ExerciseDB exerciseDB = (ExerciseDB) DatabaseManager.getDatabase(DB_NAME);
            if (exerciseDB == null) { // Fixed logic error - only return null if exerciseDB is null
                System.out.println("Exercise database not found");
                return null;
            }

            int exerciseId = exerciseDB.saveExercise(exercise); // Removed username parameter
            if (exerciseId > 0) {
                System.out.println("Exercise saved with ID: " + exerciseId);
                return exercise;
            } else {
                System.out.println("Exercise creation failed");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Exercise[] getAllExercises() {
        try {
            ExerciseDB exerciseDB = (ExerciseDB) DatabaseManager.getDatabase(DB_NAME);
            if (exerciseDB == null) {
                System.out.println("Exercise database not found");
                return new Exercise[0];
            }

            return exerciseDB.getAllExercises().toArray(new Exercise[0]);
        } catch (SQLException e) {
            System.out.println("Error retrieving all exercises: " + e.getMessage());
            e.printStackTrace();
            return new Exercise[0];
        }
    }

    public Exercise getExercise(int id) {
        try {
            ExerciseDB exerciseDB = (ExerciseDB) DatabaseManager.getDatabase(DB_NAME);
            if (exerciseDB == null) {
                System.out.println("Exercise database not found");
                return null;
            }

            return exerciseDB.getExercise(id);
        } catch (SQLException e) {
            System.out.println("Error retrieving exercise: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteExercise(int id) {
        try {
            ExerciseDB exerciseDB = (ExerciseDB) DatabaseManager.getDatabase(DB_NAME);
            if (exerciseDB == null) {
                System.out.println("Exercise database not found");
                return false;
            }

            return exerciseDB.deleteExercise(id);
        } catch (SQLException e) {
            System.out.println("Error deleting exercise: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        CreateExercise exercise = new CreateExercise();
        Exercise newExer = exercise.CreateExerciseCall("Temp", "Description", 3, 1, 0);
        if (newExer != null) {
            System.out.println(newExer.getName());
        } else {
            System.out.println("Failed to create exercise");
        }
    }
}