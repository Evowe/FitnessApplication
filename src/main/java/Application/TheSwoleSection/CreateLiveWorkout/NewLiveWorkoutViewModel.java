package Application.TheSwoleSection.CreateLiveWorkout;

import Application.Utility.Objects.LiveWorkout;

public class NewLiveWorkoutViewModel {
	NewLiveWorkoutModel model;

    NewLiveWorkoutViewModel() {
        model = new NewLiveWorkoutModel();
    }

    public void addWorkout(LiveWorkout workout, Integer trainerId) {
        model.addWorkout(workout, trainerId);
    }

}
