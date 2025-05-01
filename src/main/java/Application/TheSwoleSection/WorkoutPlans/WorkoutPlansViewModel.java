package Application.TheSwoleSection.WorkoutPlans;

import Application.Utility.Objects.WorkoutPlan;

public class WorkoutPlansViewModel {
    WorkoutPlansModel model ;

    public WorkoutPlansViewModel() {
        model = new WorkoutPlansModel();
    }

    public Object [][] getWorkoutPlanData(){
        return model.getWorkoutPlanData();
    }

    public Object[] getWorkoutPlanColumns(){
        return model.getWorkoutPlanColumns();
    }
/*
    public void recordWorkoutPlan(WorkoutPlan workoutPlan, String username){
        model.recordWorkoutPlan(workoutPlan, username);
    }

    public WorkoutPlan getWorkoutPlan(String name){
        return model.getWorkoutPlan(name);
    }

 */

    public WorkoutPlan getWorkoutPlan(String workoutPlanName){
        return model.getWorkoutPlan(workoutPlanName);
    }

    public void updateWorkoutPlan(WorkoutPlan workoutPlan){
        model.updateWorkoutPlan(workoutPlan);
    }

}
