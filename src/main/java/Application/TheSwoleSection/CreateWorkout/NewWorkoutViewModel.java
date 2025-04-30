package Application.TheSwoleSection.CreateWorkout;

import Application.Utility.Objects.Workout;

public class NewWorkoutViewModel {
   NewWorkoutModel newWorkoutModel;

    NewWorkoutViewModel() {
        newWorkoutModel = new NewWorkoutModel();
    }

    public void addWorkoutToDatabase(Workout workout, String username) {
        newWorkoutModel.addWorkoutToDatabase(workout, username);
    }

}
