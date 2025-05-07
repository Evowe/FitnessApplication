package Application.TheSwoleSection.WorkoutPlans;

import Application.Databases.WorkoutDB;
import Application.Databases.WorkoutPlanDB;
import Application.Databases.WorkoutPlanLogDB;
import Application.Main;
import Application.Utility.Objects.Workout;
import Application.Utility.Objects.WorkoutPlan;

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkoutPlansModel {
    WorkoutPlanDB workoutPlanDB;
    WorkoutDB workoutDB;
    WorkoutPlanLogDB workoutPlanLogDB;

    public WorkoutPlansModel() {
        workoutPlanDB = new WorkoutPlanDB();
        workoutDB = new WorkoutDB();
        workoutPlanLogDB = new WorkoutPlanLogDB();
    }

    public Object [][] getWorkoutPlanData(){
        Map<WorkoutPlan, String> workoutPlans = new HashMap<>();
        List<WorkoutPlan> workoutPlanList = new ArrayList<>();

        try {
            workoutPlans = workoutPlanDB.getAllWorkoutPlans();

            for(WorkoutPlan workoutPlan : workoutPlans.keySet()){
                String workoutsString = workoutPlans.get(workoutPlan);
                //System.out.println(workoutsString);
                workoutsString = workoutsString.replace("[", "").replace("]", "");

                String[] workoutsArray = workoutsString.split(",");
                //System.out.println(workoutsArray.length);


                List<Workout> workouts = new ArrayList<>();
                for(int i = 0; i < workoutsArray.length; i++){
                    String string = workoutsArray[i];
                    if(i > 0){
                        string = string.substring(1, string.length());
                    }

                    //System.out.println("[" + string + "]");
                    Workout workout = workoutDB.getWorkout(string);
                    if(workout != null){
                        workouts.add(workout);
                    }
                }

                workoutPlan.setWorkoutSchedule(workouts);

                workoutPlanList.add(workoutPlan);
            }

            Object[][] data = new Object[workoutPlanList.size()][5];
            for(int i = 0; i < workoutPlanList.size(); i++){
                data[i][0] = workoutPlanList.get(i).getName();
                data[i][1] = workoutPlanList.get(i).getGoal();
                data[i][2] = workoutPlanList.get(i).getDurationInWeeks().toString();
                data[i][3] = workoutPlanList.get(i).getIntensity().toString();
                data[i][4] = workoutPlanList.get(i).getWorkoutSchedule().toString();
            }

            return data;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Object [] getWorkoutPlanColumns(){
        Object[] data = new Object[5];
        data[0] = "Name";
        data[1] = "Goal";
        data[2] = "Duration";
        data[3] = "Intensity";
        data[4] = "Workout Schedule (MTWTFSS)";
        return data;
    }


    public WorkoutPlan getWorkoutPlan(String name){
        WorkoutPlan result = null;
        Map<WorkoutPlan, String> workoutPlans = new HashMap<>();
        try{
            workoutPlans = workoutPlanDB.getWorkoutPlan(name);

            for(WorkoutPlan workoutPlan : workoutPlans.keySet()){
                String workoutsString = workoutPlans.get(workoutPlan);
                //System.out.println(workoutsString);
                workoutsString = workoutsString.replace("[", "").replace("]", "");

                String[] workoutsArray = workoutsString.split(",");
                //System.out.println(workoutsArray.length);


                List<Workout> workouts = new ArrayList<>();
                for(int i = 0; i < workoutsArray.length; i++){
                    String string = workoutsArray[i];
                    if(i > 0){
                        string = string.substring(1, string.length());
                    }

                    //ystem.out.println("[" + string + "]");
                    Workout workout = workoutDB.getWorkout(string);
                    if(workout != null){
                        workouts.add(workout);
                    }
                }

                workoutPlan.setWorkoutSchedule(workouts);

                result = workoutPlan;
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    /*
    public void recordWorkoutPlan(WorkoutPlan workoutPlan){
        try{
            workoutPlanDB.recordWorkoutPlan(workoutPlan);
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

     */



    public void updateWorkoutPlan(WorkoutPlan workoutPlan){
       try{
           System.out.println("updateWorkoutPlan");
           workoutPlanDB.updateWorkoutPlan(workoutPlan);
       } catch (SQLException e){
           e.printStackTrace();
       }
    }

    public void equipWorkoutPlan(JTable table){
        String username = Main.getCurrentUser().getUsername();
        String workoutName = table.getValueAt(table.getSelectedRow(), 0).toString();
        String date = LocalDate.now().toString();

        try{
            if(workoutPlanLogDB.getCurrentPlan(username) == null){
                workoutPlanLogDB.equipWorkoutPlan(workoutName, date, username);
            } else{
                workoutPlanLogDB.updateCurrentPlan(username, workoutName, date);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
