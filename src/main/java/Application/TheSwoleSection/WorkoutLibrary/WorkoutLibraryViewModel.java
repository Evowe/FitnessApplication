package Application.TheSwoleSection.WorkoutLibrary;

import Application.Utility.Objects.Workout;

public class WorkoutLibraryViewModel {
    private WorkoutLibraryModel model;

    public WorkoutLibraryViewModel() {
        model = new WorkoutLibraryModel();
    }


    public Object [][] getWorkoutData(){
        return model.getWorkoutData();
    }

    public Object[] getWorkoutColumns(){
        return model.getWorkoutColumns();
    }


    public void recordWorkout(String workoutName, String username){
        model.recordWorkout(workoutName, username);
    }

}
