package Application.TheSwoleSection.TrainerCreatedWorkoutPlan;

import Application.Databases.WorkoutDB;
import Application.Databases.WorkoutPlanDB;
import Application.Utility.Objects.Workout;
import Application.Utility.Objects.WorkoutPlan;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreateWorkoutPlanModel {
    WorkoutDB workoutDB;
    WorkoutPlanDB workoutPlanDB;

    public CreateWorkoutPlanModel() {
        workoutDB = new WorkoutDB();
        workoutPlanDB = new WorkoutPlanDB();
    }

    public List<Workout> getAllWorkouts() throws SQLException {

        List<Workout> workouts = new ArrayList<>();
        try{
            workouts = workoutDB.getAllWorkouts();
        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        return workouts;
    }


    public void addWorkoutPlan(WorkoutPlan workoutPlan){
        try{
            workoutPlanDB.addWorkoutPlan(workoutPlan);
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

}
