package Application.TheSwoleSection.WorkoutLibrary;

import Application.Databases.ExerciseDB;
import Application.Databases.WorkoutDB;
import Application.Databases.WorkoutLogDB;
import Application.Utility.Objects.Workout;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WorkoutLibraryModel {
    private WorkoutDB workoutDB;
    private ExerciseDB exerciseDB;
    private WorkoutLogDB workoutLogDB;

    public WorkoutLibraryModel() {
       workoutDB = new WorkoutDB();
       exerciseDB = new ExerciseDB();
       workoutLogDB = new WorkoutLogDB();
    }

    public Object [][] getWorkoutData(){
        List<Workout> workouts = new ArrayList<>();
        try {
            workouts = workoutDB.getAllWorkouts();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        workouts.forEach(workout -> {
            String exerciseList = workout.getExercises();
            exerciseList = exerciseList.replace("[", "").replace("]", "");
            exerciseList = exerciseList.replace("null", "");
            String[] exercises = exerciseList.split(",");

            for (String exercise : exercises) {
                if(!exercise.equals("")){
                    try{
                        workout.addExerciseToList(exerciseDB.getExercise(Integer.parseInt(exercise)));
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

        });

        Object[][] data = new Object[workouts.size()][4];
        for (int i = 0; i < workouts.size(); i++) {
            data[i][0] = workouts.get(i).getName();
            data[i][1] = workouts.get(i).getDescription();
            data[i][2] = workouts.get(i).getDuration();
            data[i][3] = workouts.get(i).getExerciseList().toString();
        }

        return data;
    }

    public Object[] getWorkoutColumns(){
        Object[] data = new Object[4];
        data[0] = "Name";
        data[1] = "Description";
        data[2] = "Duration";
        data[3] = "Exercises";
        return data;
    }


    public void recordWorkout(String workoutName, String username, int calories){

        try{
            Workout workout = workoutDB.getWorkout(workoutName);
            workout.setDate(LocalDate.now().toString());

            if(workout != null){
                workoutLogDB.addWorkout(workout, username, calories);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
