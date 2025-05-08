package Application.Metrics.Report;

import Application.Databases.*;
import Application.Utility.Objects.Account;
import Application.Utility.Objects.Workout;
import Application.Utility.Objects.WorkoutPlan;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportModel {

    private final WorkoutLogDB workoutLogDB = DatabaseManager.getWorkoutLogDB();
    private final ExerciseDB exerciseDB = DatabaseManager.getExerciseDB();
    private WorkoutPlanLogDB workoutPlanLogDB;
    private WorkoutPlanDB workoutPlanDB;
    private WorkoutDB workoutDB;

    private Account acc;
    public ReportModel(Account acc) {
        this.acc = acc;
        workoutPlanLogDB = new WorkoutPlanLogDB();
        workoutPlanDB = new WorkoutPlanDB();
        workoutDB = new WorkoutDB();
    }
    public void addWorkout(Workout w,String username, int calories){
        try {
            workoutLogDB.addWorkout(w, username, calories);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public  Object [][] getWorkouts(){
        List<Workout> workouts = null;
        List<WorkoutPlan> workoutPlans = null;

        try{
            workouts = workoutLogDB.getAllWorkouts(acc.getUsername());
            String workoutPlanName = workoutPlanLogDB.getCurrentPlan(acc.getUsername());

            Map<WorkoutPlan, String> workoutPlan = workoutPlanDB.getWorkoutPlan(workoutPlanName);

            for(WorkoutPlan wp : workoutPlan.keySet()){
                String workoutsString = workoutPlan.get(wp);
                workoutsString = workoutsString.replace("[", "").replace("]", "");

                String[] workoutsArray = workoutsString.split(",");

                List<Workout> workoutsInPlan = new ArrayList<>();
                for(int i = 0; i < workoutsArray.length; i++){
                    String string = workoutsArray[i];
                    if(i > 0){
                        string = string.substring(1, string.length());
                    }

                    Workout workout = workoutDB.getWorkout(string);
                    if(workout != null){
                        workoutsInPlan.add(workout);
                    }
                }

                wp.setWorkoutSchedule(workoutsInPlan);
                workoutPlans.add(wp);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        Object[][] data = new Object[workouts.size() + workoutPlans.size()][7];

        for(int i = 0; i < workouts.size(); i++){
            data[i][0] = "Workout";
            data[i][1] = workouts.get(i).getName();
            data[i][2] = workouts.get(i).getDescription();
            data[i][3] = workouts.get(i).getDuration();
            data[i][4] = workouts.get(i).getCaloriesBurned();
            data[i][5] = workouts.get(i).getDate();
            data[i][6] = workouts.get(i).getExerciseList().toString();
        }
        for(int i = workouts.size() - 1; i < workouts.size() + workoutPlans.size(); i++){
            data[i][0] = "WorkoutPlan";
            data[i][1] = workoutPlans.get(i).getName();
            data[i][2] = workoutPlans.get(i).getGoal();
            data[i][3] = workoutPlans.get(i).getDurationInWeeks();
            data[i][4] = "N/A";
            data[i][5] = workoutPlanLogDB.getEquipDate(workoutPlans.get(i).getName());
            data[i][6] = "N/A";
        }

        return data;
    }

    public Object[] toString(Workout w) {
        {
            Object [] str = new Object [6];
            str[0] = w.getName();
            str[1] = w.getDescription();
            str[2] = w.getDuration();
            str[3] = w.getCaloriesBurned();;
            str[4] = w.getDate();
            str[5] = w.getExercises();

            return str;
            //Name,Description,Type,Sets,Reps,Weight
        }
    }

    public Object [] getColumns(){
        Object[] columns = new Object[7];

        columns[0] = "Type";
        columns[1] = "Name";
        columns[2] = "Description";
        columns[3] = "Duration";
        columns[4] = "Calories Burned";
        columns[5] = "Date";
        columns[6] = "Exercises";

        return columns;
    }

    public Object [][] getWorkoutData(){
        List<Workout> workouts = new ArrayList<>();
        List<WorkoutPlan> workoutPlans = new ArrayList<>();
        try {
            workouts = workoutLogDB.getAllWorkouts(acc.getUsername());

            String workoutPlanName = workoutPlanLogDB.getCurrentPlan(acc.getUsername());

            Map<WorkoutPlan, String> workoutPlan = workoutPlanDB.getWorkoutPlan(workoutPlanName);

            for(WorkoutPlan wp : workoutPlan.keySet()){
                String workoutsString = workoutPlan.get(wp);
                workoutsString = workoutsString.replace("[", "").replace("]", "");

                String[] workoutsArray = workoutsString.split(",");

                List<Workout> workoutsInPlan = new ArrayList<>();
                for(int i = 0; i < workoutsArray.length; i++){
                    String string = workoutsArray[i];
                    if(i > 0){
                        string = string.substring(1, string.length());
                    }

                    Workout workout = workoutDB.getWorkout(string);
                    if(workout != null){
                        workoutsInPlan.add(workout);
                    }
                }

                wp.setWorkoutSchedule(workoutsInPlan);
                workoutPlans.add(wp);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        workouts.forEach(workout -> {
            String exerciseList = workout.getExercises();
            exerciseList = exerciseList.replace("[", "").replace("]", "");
            exerciseList = exerciseList.replace("null", "");
            String[] exercises = exerciseList.split(",");

            for (String exercise : exercises) {
                if(!exercise.equals("")){
                    try{
                        workout.addExerciseToList(exerciseDB.getExercise(Integer.parseInt(exercise)));
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

        });
        int workoutPlansSize;
        if(workoutPlans == null){
            workoutPlansSize = 0;
        } else{
            workoutPlansSize = workoutPlans.size();
        }

        Object[][] data = new Object[workouts.size() + workoutPlansSize][7];

        for(int i = 0; i < workouts.size(); i++){
            data[i][0] = "Workout";
            data[i][1] = workouts.get(i).getName();
            data[i][2] = workouts.get(i).getDescription();
            data[i][3] = workouts.get(i).getDuration();
            data[i][4] = workouts.get(i).getCaloriesBurned();
            data[i][5] = workouts.get(i).getDate();
            data[i][6] = workouts.get(i).getExerciseList().toString();
        }

        int workoutsSize = workouts.size();
        if(workouts.size() == 0) {
            workoutsSize = 1;
        }
        if(workoutPlans.size() != 0) {
            for(int i = workoutsSize - 1; i < workouts.size() + workoutPlans.size(); i++){
                data[i][0] = "WorkoutPlan";
                data[i][1] = workoutPlans.get(0).getName();
                data[i][2] = workoutPlans.get(0).getGoal();
                data[i][3] = workoutPlans.get(0).getDurationInWeeks();
                data[i][4] = "N/A";
                data[i][5] = workoutPlanLogDB.getEquipDate(workoutPlans.get(0).getName());
                data[i][6] = "N/A";
            }
        }


        return data;
    }

}




