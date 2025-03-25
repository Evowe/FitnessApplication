package fitness.app.Objects;

import java.util.List;

public class WorkoutPlan {
    String name;
    String goal;
    Integer durationInWeeks;
    List<List<Workout>> workoutSchedule;
    //This will be a list of a list of workouts for the week so it should be a list of
    // 7 workouts for each week in the WorkoutPlan, Workout is null for rest days??
    Integer intensity;

    public WorkoutPlan(String name, String goal, Integer durationInWeeks, List<List<Workout>> workoutSchedule, Integer intensity) {
        this.name = name;
        this.goal = goal;
        this.durationInWeeks = durationInWeeks;
        this.workoutSchedule = workoutSchedule;
        this.intensity = intensity;
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

    public List<List<Workout>> getWorkoutSchedule() {
        return workoutSchedule;
    }

    public void setWorkoutSchedule(List<List<Workout>> workoutSchedule) {
        this.workoutSchedule = workoutSchedule;
    }

    public Integer getIntensity() {
        return intensity;
    }

    public void setIntensity(Integer intensity) {
        this.intensity = intensity;
    }
}
