package fitness.app.Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WorkoutClass {
    Workout workout;
    Trainer trainer;
    //Set<User> attendees = new HashSet<User>;
    Date date;
    String className;
    Integer intensityLevel;
    String workoutType;

    public WorkoutClass(/*Set<User> attendees,*/ Workout workout, Trainer trainer, Date date, String className, Integer intensityLevel, String workoutType) {
        //this.attendees = attendees;
        this.workout = workout;
        this.trainer = trainer;
        this.date = date;
        this.className = className;
        this.intensityLevel = intensityLevel;
        this.workoutType = workoutType;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getIntensityLevel() {
        return intensityLevel;
    }

    public void setIntensityLevel(Integer intensityLevel) {
        this.intensityLevel = intensityLevel;
    }

    public String getWorkoutType() {
        return workoutType;
    }

    public void setWorkoutType(String workoutType) {
        this.workoutType = workoutType;
    }

    public void addAttendee(/*User attendee*/){
        //attendees.add(attendee);
    }

    public void removeAttendee(/*User attendee*/){
        //attendees.remove(attendee);
    }

}
