package fitness.app.CreateExercise;

import fitness.app.Objects.Exercise;

public class CreateExercise {
    public Exercise CreateExerciseCall(String name, Integer type, int valA, double valB) {
        Exercise exercise = null;
        switch(type) {
            case 1: // reps
                exercise = new Exercise(name, "Null", 0, valA, 0);
                break;
            case 2: // sets w/ weight
                exercise = new Exercise(name, "Null", valA, 0, valB);
                break;

            case 3: // sets no weight
                exercise = new Exercise(name, "Null", valA, 0, 0);
                break;

            default:
                System.out.println("Invalid exercise type");
                break;
        }
        System.out.println("Exercise created");

        //System.out.println(exercise == null);

        return exercise;
    }

    public static void main(String[] args) {
        CreateExercise exercise = new CreateExercise();
        Exercise newExer = exercise.CreateExerciseCall("Ass", 3, 1, 0);
        System.out.println(newExer.getName());
    }
}
