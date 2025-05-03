package Application.TheSwoleSection.CreateLiveWorkout;

import Application.TheSwoleSection.CreateWorkout.NewWorkoutModel;
import Application.Utility.Objects.LiveWorkout;
import Application.Utility.Objects.Workout;

public class NewLiveWorkoutViewModel {
	NewLiveWorkoutModel model;

    NewLiveWorkoutViewModel() {
        model = new NewLiveWorkoutModel();
    }

    public void addWorkout(LiveWorkout workout, Integer trainerId) {
        model.addWorkout(workout, trainerId);
    }

}
