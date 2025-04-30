package Application.TheSwoleSection.WorkoutLibrary;

public class WorkoutLibraryViewModel {
    private WorkoutLibraryModel model;

    public WorkoutLibraryViewModel() {
        model = new WorkoutLibraryModel();
    }


    public Object [][] getWorkoutData(String username){
        return model.getWorkoutData(username);
    }

    public Object[] getWorkoutColumns(){
        return model.getWorkoutColumns();
    }
}
