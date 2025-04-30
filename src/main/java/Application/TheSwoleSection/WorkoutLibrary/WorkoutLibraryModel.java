package Application.TheSwoleSection.WorkoutLibrary;

import Application.Databases.WorkoutDB;
import Application.Utility.Objects.Workout;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorkoutLibraryModel {
    private WorkoutDB workoutDB;

    public WorkoutLibraryModel() {
       workoutDB = new WorkoutDB();
    }

    public Object [][] getWorkoutData(String username){
        List<Workout> workouts = new ArrayList<>();
        try {
            workouts = workoutDB.getAllWorkouts(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Object[][] data = new Object[workouts.size()][4];
        for (int i = 0; i < workouts.size(); i++) {
            data[i][0] = workouts.get(i).getName();
            data[i][1] = workouts.get(i).getDescription();
            data[i][2] = workouts.get(i).getDuration();
            data[i][3] = workouts.get(i).getExercises();
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

}
