package Application.TheSwoleSection.LiveWorkout;

import Application.Databases.LiveWorkoutDB;
import Application.Utility.Objects.LiveWorkout;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class LiveWorkoutViewModel {
    private LiveWorkoutModel model;
    String streamURL = "https://8df9-129-62-66-75.ngrok-free.app";

    public LiveWorkoutViewModel() {
        model = new LiveWorkoutModel();
    }


    public Object [][] getWorkoutData(){
        return model.getWorkoutData();
    }

    public Object[] getWorkoutColumns(){
        return model.getWorkoutColumns();
    }
    
    public void startStream() {
    	try {
    		URI url = new URI(streamURL + "/streamer.html");
    		
    		if (Desktop.isDesktopSupported()) {
    			Desktop.getDesktop().browse(url);
    		} else {
    			System.out.println("Failed to launch url");
    		}
    		
    	} catch (URISyntaxException e1) {
    		e1.printStackTrace();
    		
    	} catch (IOException e2) {
			e2.printStackTrace();
		}
    }
    
    public void joinStream() {
    	try {
    		URI url = new URI(streamURL + "/viewer.html");

    		if (Desktop.isDesktopSupported()) {
    			Desktop.getDesktop().browse(url);
    		} else {
    			System.out.println("Failed to launch url");
    		}
    		
    	} catch (URISyntaxException e1) {
    		e1.printStackTrace();
    		
    	} catch (IOException e2) {
			e2.printStackTrace();
		}
    }

	public void addUserToWorkout(String workoutName) {
		try {
			LiveWorkout workout = model.getWorkoutByName(workoutName); // implement this in model
			if (workout != null) {
				LiveWorkoutDB db = new LiveWorkoutDB();
				db.incrementUserCountByNameAndStartTime(workout.getName(), workout.getStartTime());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
