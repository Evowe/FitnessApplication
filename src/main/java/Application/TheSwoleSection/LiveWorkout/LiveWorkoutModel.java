package Application.TheSwoleSection.LiveWorkout;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Application.Databases.LiveWorkoutDB;
import Application.Utility.Objects.LiveWorkout;

public class LiveWorkoutModel {
	 private LiveWorkoutDB db;

	    public LiveWorkoutModel() {
	       db = new LiveWorkoutDB();
	    }

	    public Object [][] getWorkoutData(){
	        List<LiveWorkout> workouts = new ArrayList<>();
	        try {
	            workouts = db.getAllLiveWorkouts();
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }

	        Object[][] data = new Object[workouts.size()][5];
	        for (int i = 0; i < workouts.size(); i++) {
	            data[i][0] = workouts.get(i).getName();
	            data[i][1] = workouts.get(i).getDescription();
	            data[i][2] = workouts.get(i).getDuration();
	            data[i][3] = workouts.get(i).getStartTime();
	            data[i][4] = "Join";
	        
	        }

	        return data;
	    }

	    public Object[] getWorkoutColumns(){
	        Object[] data = new Object[6];
	        data[0] = "Name";
	        data[1] = "Description";
	        data[2] = "Duration";
	        data[3] = "Start Time";
	        data[4] = "Join";
	        return data;
	    }
}
