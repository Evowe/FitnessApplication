package Application.TheSwoleSection.CreateLiveWorkout;

import java.sql.SQLException;

import Application.Databases.LiveWorkoutDB;
import Application.Utility.Objects.LiveWorkout;

public class NewLiveWorkoutModel {
	LiveWorkoutDB db;

    NewLiveWorkoutModel(){
        db = new LiveWorkoutDB();
    }

    public void addWorkout(LiveWorkout workout, Integer trainerId){
        try {
            db.addWorkout(workout, trainerId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
