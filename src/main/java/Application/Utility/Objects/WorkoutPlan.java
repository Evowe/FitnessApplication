package Application.Utility.Objects;

import java.util.List;

public class WorkoutPlan {
    String name = "";
    String goal = "";
    Integer durationInWeeks = 0;
    List<Workout> workoutSchedule;
    /// Edit: Decided to do a list (should be size 7) for the workout that you complete each day of the week,
    /// if it is null, its a rest day
    //This will be a list of a list of workouts for the week so it should be a list of
    // 7 workouts for each week in the WorkoutPlan, Workout is null for rest days??


    Integer intensity = 0;
    String Author = "";

    public WorkoutPlan(){}

    public WorkoutPlan(String name, String goal, Integer durationInWeeks, List<Workout> workoutSchedule, Integer intensity, String Author) {
        this.name = name;
        this.goal = goal;
        this.durationInWeeks = durationInWeeks;
        this.workoutSchedule = workoutSchedule;
        this.intensity = intensity;
        this.Author = Author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public Integer getDurationInWeeks() {
        return durationInWeeks;
    }

    public void setDurationInWeeks(Integer durationInWeeks) {
        this.durationInWeeks = durationInWeeks;
    }

    public List<Workout> getWorkoutSchedule() {
        return workoutSchedule;
    }

    public void setWorkoutSchedule(List<Workout> workoutSchedule) {
        this.workoutSchedule = workoutSchedule;
    }

    public Integer getIntensity() {
        return intensity;
    }

    public void setIntensity(Integer intensity) {
        this.intensity = intensity;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String Author) {
        this.Author = Author;
    }

    public void setDescription(String s) {

    }
}
