package Application.TheSwoleSection.LiveWorkout;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import Application.Databases.LiveWorkoutDB;
import Application.Utility.Objects.LiveWorkout;

public class LiveWorkoutModel {
	 private LiveWorkoutDB db;

	    public LiveWorkoutModel() {
	       db = new LiveWorkoutDB();
	    }

	private List<LiveWorkout> cachedWorkouts = new ArrayList<>();

	public Object [][] getWorkoutData(){
		List<LiveWorkout> workouts;
		try {
			workouts = db.getAllLiveWorkouts();
			removeExpiredWorkouts(workouts);
			cachedWorkouts = workouts; // cache the fresh list
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

	public LiveWorkout getWorkoutByName(String name) {
		for (LiveWorkout workout : cachedWorkouts) {
			if (workout.getName().equals(name)) {
				return workout;
			}
		}
		return null;
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

	public static void removeExpiredWorkouts(List<LiveWorkout> workouts) {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");

		Iterator<LiveWorkout> iterator = workouts.iterator();
		while (iterator.hasNext()) {
			LiveWorkout workout = iterator.next();
			try {
				// Parse the time and combine with today's date
				LocalTime startTime = LocalTime.parse(workout.getStartTime(), timeFormatter);
				LocalDateTime startDateTime = LocalDate.now().atTime(startTime);

				// Compute end time
				LocalDateTime endTime = startDateTime.plusMinutes(workout.getDurationMinutes());

				// Remove if expired
				if (now.isAfter(endTime)) {
					iterator.remove();
				}

			} catch (Exception e) {
				System.err.println("Failed to parse workout time: " + workout.getStartTime());
				e.printStackTrace();
				iterator.remove(); // Remove malformed entry
			}
		}
	}

}
