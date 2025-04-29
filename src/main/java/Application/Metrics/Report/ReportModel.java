package Application.Metrics.Report;

import Application.Utility.Objects.Account;
import Application.Databases.DatabaseManager;
import Application.Databases.WorkoutLogDB;
import Application.Utility.Objects.Workout;

import java.sql.SQLException;
import java.util.List;

public class ReportModel {

    private final WorkoutLogDB workoutLogDB = DatabaseManager.getWorkoutLogDB();
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

}




