package Application.TheSwoleSection.WorkoutSchedule;

import Application.Databases.ExerciseDB;
import Application.Databases.WorkoutDB;
import Application.Databases.WorkoutPlanDB;
import Application.Databases.WorkoutPlanLogDB;
import Application.Utility.Objects.Exercise;
import Application.Utility.Objects.Workout;
import Application.Utility.Objects.WorkoutPlan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class WorkoutScheduleModel {
    public WorkoutPlanDB workoutPlanDB;
    public WorkoutPlanLogDB workoutPlanLogDB;
    public WorkoutDB workoutDB;
    public ExerciseDB exerciseDB;

    public WorkoutScheduleModel() {
        workoutPlanDB = new WorkoutPlanDB();
        workoutPlanLogDB = new WorkoutPlanLogDB();
        workoutDB = new WorkoutDB();
        exerciseDB = new ExerciseDB();
    }

    public WorkoutPlan getCurrentWorkoutPlan(String username) {
        String name = null;
        Map<WorkoutPlan, String> workoutPlans = null;
        WorkoutPlan result = null;

        try{
            name = workoutPlanLogDB.getCurrentPlan(username);
            workoutPlans = workoutPlanDB.getWorkoutPlan(name);

            for(WorkoutPlan workoutPlan : workoutPlans.keySet()){
                String workoutsString = workoutPlans.get(workoutPlan);

                workoutsString = workoutsString.replace("[", "").replace("]", "");

                String[] workoutsArray = workoutsString.split(",");


                List<Workout> workouts = new ArrayList<>();
                for(int i = 0; i < workoutsArray.length; i++){
                    String string = workoutsArray[i];
                    if(i > 0){
                        string = string.substring(1, string.length());
                    }

                    Workout workout = workoutDB.getWorkout(string);
                    if(workout != null){
                        workouts.add(workout);
                    }
                }

                workoutPlan.setWorkoutSchedule(workouts);

                result = workoutPlan;
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    public void setCurrentWorkoutPlan(String workoutPlan, String dateEquipped, String username){
        try{
            workoutPlanLogDB.equipWorkoutPlan(workoutPlan, dateEquipped, username);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void updateCurrentWorkoutPlan(String workoutPlan, String dateEquipped, String username){
        try{
            workoutPlanLogDB.updateCurrentPlan(username, workoutPlan, dateEquipped);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void deleteCurrentWorkoutPlan(String username){
        try{
            workoutPlanLogDB.deleteCurrentPlan(username);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public Boolean isDateValid(WorkoutPlan workoutPlan, String selectedDate){
        String workoutPlanEquipDate;

        try{
            workoutPlanEquipDate = workoutPlanLogDB.getEquipDate(workoutPlan.getName());

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(workoutPlanEquipDate);
            SimpleDateFormat formatter2 = new SimpleDateFormat("MM/dd/yyyy");
            String date2 = formatter2.format(date);

            String start = findNextMonday(date2);
            System.out.println(start);
            String [] startDateArray = start.split("/");

            LocalDate startDate = LocalDate.of(Integer.parseInt(startDateArray[2]),
                    Integer.parseInt(startDateArray[0]), Integer.parseInt(startDateArray[1]));


            String [] selectedDateArray = selectedDate.split("/");

            LocalDate selected = LocalDate.of(Integer.parseInt(selectedDateArray[2]),
                    Integer.parseInt(selectedDateArray[0]), Integer.parseInt(selectedDateArray[1]));

            LocalDate endDate = startDate.plusWeeks(workoutPlan.getDurationInWeeks());

            //TODO:CHECK THAT SELECTED DATE IS BEFORE END DATE AND ON OR AFTER START DATE
            if((selected.isAfter(startDate) && selected.isBefore(endDate))
                    || selected.isEqual(startDate) || selected.isEqual(endDate)){
                return true;
            } else {
                return false;
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public String findNextMonday(String date) throws ParseException {
        String result;
        Date resultDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        System.out.println(date);
        Date selectedDate = sdf.parse(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedDate);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        if(dayOfWeek == Calendar.SATURDAY){
            calendar.add(Calendar.DATE, 2);
        } else if(dayOfWeek == Calendar.SUNDAY){
            calendar.add(Calendar.DATE, 1);
        } else if(dayOfWeek == Calendar.TUESDAY){
            calendar.add(Calendar.DATE, 6);
        } else if(dayOfWeek == Calendar.WEDNESDAY){
            calendar.add(Calendar.DATE, 5);
        } else if(dayOfWeek == Calendar.THURSDAY){
            calendar.add(Calendar.DATE, 4);
        } else if(dayOfWeek == Calendar.FRIDAY){
            calendar.add(Calendar.DATE, 3);
        }

        resultDate = calendar.getTime();
        result = sdf.format(resultDate);

        return result;
    }

    public int getDayOfWeek(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date resultDate = null;
        try{
            resultDate = sdf.parse(date);
        } catch(ParseException e){
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(resultDate);

        int day = calendar.get(Calendar.DAY_OF_WEEK);

        return day;
    }

    public Object [][] getExerciseData(Workout workout){
        Object [][] data = null;

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

        List<Exercise> ex = workout.getExerciseList();

        data = new Object[ex.size()][5];

        for(int i = 0; i < ex.size(); i++){
            data[i][0] = ex.get(i).getName();
            data[i][1] = ex.get(i).getDescription();
            data[i][2] = ex.get(i).getSets();
            data[i][3] = ex.get(i).getReps();
            data[i][4] = ex.get(i).getWeight();
        }

        return data;
    }

    public Object[] getExerciseColumns(){
        Object[] columns = new Object[5];

        columns[0] = "Name";
        columns[1] = "Description";
        columns[2] = "Sets";
        columns[3] = "Reps";
        columns[4] = "Weight";

        return columns;
    }


}
