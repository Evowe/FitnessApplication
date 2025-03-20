package fitness.app.Model;

import java.util.List;


public class Workout {
    private int duration;
    private String description;
    private List<Exercise> exercises;

    public Workout(int duration, String description, List<Exercise> exercises) {
        this.duration = duration;
        this.description = description;
        this.exercises = exercises;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}


