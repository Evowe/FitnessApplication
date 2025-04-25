package fitness.app.TheSwoleSection.CreateExercise;

import fitness.app.BadProjectStructureSection.Objects.Exercise;
import fitness.app.BadProjectStructureSection.Databases.ExerciseDB;

import java.sql.SQLException;

public class CreateExercise {
    private ExerciseDB exerciseDB;

    public CreateExercise() {
        // Create a single instance of ExerciseDB
        this.exerciseDB = new ExerciseDB();
    }

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
            int exerciseId = exerciseDB.saveExercise(exercise);
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
            return exerciseDB.getAllExercises().toArray(new Exercise[0]);
        } catch (SQLException e) {
            System.out.println("Error retrieving all exercises: " + e.getMessage());
            e.printStackTrace();
            return new Exercise[0];
        }
    }

    public Exercise getExercise(int id) {
        try {
            return exerciseDB.getExercise(id);
        } catch (SQLException e) {
            System.out.println("Error retrieving exercise: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteExercise(int id) {
        try {
            return exerciseDB.deleteExercise(id);
        } catch (SQLException e) {
            System.out.println("Error deleting exercise: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}