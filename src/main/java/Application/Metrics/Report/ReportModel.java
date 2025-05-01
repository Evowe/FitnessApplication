package Application.Metrics.Report;

import Application.Databases.ExerciseDB;
import Application.Utility.Objects.Account;
import Application.Databases.DatabaseManager;
import Application.Databases.WorkoutLogDB;
import Application.Utility.Objects.Workout;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportModel {

    private final WorkoutLogDB workoutLogDB = DatabaseManager.getWorkoutLogDB();
    private final ExerciseDB exerciseDB = DatabaseManager.getExerciseDB();

    private Account acc;
    ReportModel(Account acc) {
        this.acc = acc;
    }
    public void addWorkout(Workout w,String username){
        try {
            workoutLogDB.addWorkout(w, username);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public  Object [][] getWorkouts(){
        List<Workout> workouts = null;
        try{
            workouts = workoutLogDB.getAllWorkouts(acc.getUsername());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Object[][] data = new Object[workouts.size()][6];
        for(int i = 0; i < workouts.size(); i++){
            data[i] = toString(workouts.get(i));
        }
        return data;
    }

    public Object[] toString(Workout w) {
        {
            Object [] str = new Object [6];
            str[0] = w.getName();
            str[1] = w.getDescription();
            str[2] = w.getDuration();
            str[3] = w.getCaloriesBurned();;
            str[4] = w.getDate();
            str[5] = w.getExercises();

            return str;
            //Name,Description,Type,Sets,Reps,Weight
        }
    }

    public Object [] getColumns(){
        Object[] colums = new Object[6];

        colums[0] = "Name";
        colums[1] = "Description";
        colums[2] = "Duration";
        colums[3] = "Calories Burned";
        colums[4] = "Date";
        colums[5] = "Exercises";

        return colums;
    }

    public Object [][] getWorkoutData(){
        List<Workout> workouts = new ArrayList<>();
        try {
            workouts = workoutLogDB.getAllWorkouts(acc.getUsername());
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

        Object[][] data = new Object[workouts.size()][6];
        for (int i = 0; i < workouts.size(); i++) {
            data[i][0] = workouts.get(i).getName();
            data[i][1] = workouts.get(i).getDescription();
            data[i][2] = workouts.get(i).getDuration();
            data[i][3] = workouts.get(i).getCaloriesBurned();
            data[i][4] = workouts.get(i).getDate();
            data[i][5] = workouts.get(i).getExerciseList().toString();
        }

        return data;
    }

}




