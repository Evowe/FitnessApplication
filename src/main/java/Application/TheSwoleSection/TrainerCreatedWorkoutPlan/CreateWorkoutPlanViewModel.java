package Application.TheSwoleSection.TrainerCreatedWorkoutPlan;

import Application.Utility.Objects.Workout;
import Application.Utility.Objects.WorkoutPlan;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreateWorkoutPlanViewModel {
    CreateWorkoutPlanModel model;

    public CreateWorkoutPlanViewModel() {
        model = new CreateWorkoutPlanModel();
    }

    public List<Workout> getAllWorkouts(){
        try {
            return model.getAllWorkouts();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addWorkoutPlan(WorkoutPlan workoutPlan){
        model.addWorkoutPlan(workoutPlan);
    }
}
