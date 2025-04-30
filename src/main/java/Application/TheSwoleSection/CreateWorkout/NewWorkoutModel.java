package Application.TheSwoleSection.CreateWorkout;

import Application.Databases.WorkoutDB;
import Application.Utility.Objects.Workout;

import java.sql.SQLException;

public class NewWorkoutModel {
    WorkoutDB workoutDB;

    NewWorkoutModel(){
        workoutDB = new WorkoutDB();
    }

    public void addWorkoutToDatabase(Workout workout, String username){
        try {
            workoutDB.addWorkout(workout, username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
