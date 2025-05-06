package Application.TheSwoleSection.LiveWorkout;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class LiveWorkoutViewModel {
    private LiveWorkoutModel model;
    String streamURL = "https://2892-129-62-66-43.ngrok-free.app";

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
}
