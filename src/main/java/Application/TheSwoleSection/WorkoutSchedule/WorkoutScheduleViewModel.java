package Application.TheSwoleSection.WorkoutSchedule;

import Application.Utility.Objects.Workout;
import Application.Utility.Objects.WorkoutPlan;

public class WorkoutScheduleViewModel {
    WorkoutScheduleModel model;

    public WorkoutScheduleViewModel() {
        model = new WorkoutScheduleModel();
    }

    public WorkoutPlan getCurrentWorkoutPlan(String username){
        return model.getCurrentWorkoutPlan(username);
    }

    public void setCurrentWorkoutPlan(String workoutPlan, String dateEquipped, String username ){
        model.setCurrentWorkoutPlan(workoutPlan, dateEquipped, username);
    }

    public void  updateCurrentWorkoutPlan(String workoutPlan, String dateEquipped, String username ){
        model.updateCurrentWorkoutPlan(workoutPlan, dateEquipped, username);
    }

    public void deleteCurrentWorkoutPlan(String username ){
        model.deleteCurrentWorkoutPlan(username);
    }

    public Boolean isDateValid(WorkoutPlan workoutPlan, String date){
        return model.isDateValid(workoutPlan, date);
    }

    public int getDayOfWeek(String date){
        return model.getDayOfWeek(date);
    }

    public Object [][] getExerciseData(Workout workout){
        return model.getExerciseData(workout);
    }

    public Object [] getExerciseColumns(){
        return model.getExerciseColumns();
    }

}
