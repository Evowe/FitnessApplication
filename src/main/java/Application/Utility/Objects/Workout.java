package Application.Utility.Objects;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Workout {
    protected String name;
    protected int duration;
    protected String description;
    private int CaloriesBurned;
    private String Date;
    private String exerciseId;
    private List<Exercise> exercises = new ArrayList<>();
    protected String Author;

    public Workout(String name, String description, int duration, int CaloriesBurned, String Date, List<Exercise> exercises) {
        this.name = name;
        this.duration = duration;
        this.description = description;
        this.CaloriesBurned = CaloriesBurned;
        this.Date = Date;
        this.exercises = exercises;
    }

    public Workout(String name, String description, int duration, int CaloriesBurned, String Date) {
        this.name = name;
        this.duration = duration;
        this.description = description;
        this.CaloriesBurned = CaloriesBurned;
        this.Date = Date;
    }

    public Workout(String name, String description, int duration, int CaloriesBurned, String Date,String exercises) {
        this.name = name;
        this.duration = duration;
        this.description = description;
        this.CaloriesBurned = CaloriesBurned;
        this.Date = Date;
        this.exerciseId = exercises;
//        if(exercises!=null) {
//            String[] parts = exercises.split(",");
//            exerciseId = new ArrayList<>(parts.length);
//            for (String part : parts) {
//                exerciseId.add(Integer.parseInt(part.trim()));
//            }
//        }

    }

    public Workout() {
        name = null;
        duration = 0;
        description = null;
        CaloriesBurned = 0;
        Date = null;
        exerciseId = null;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getCaloriesBurned() {
        return CaloriesBurned;
    }

    public void setCaloriesBurned(int caloriesBurned) {
        CaloriesBurned = caloriesBurned;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getExercises() {
        return exerciseId;
    }

    public List<Exercise> getExerciseList(){return exercises;}

    public void setExercises(String exerciseId) {
        this.exerciseId = exerciseId;
    }

    @Override
    public String toString() {
        return getName();
    }

    public void addExerciseToList(Exercise exercise) {
        this.exercises.add(exercise);
    }


    public void addExercise(Exercise exercise) {
        this.exerciseId = exerciseId + "," + exercise.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Workout)) return false;
        Workout workout = (Workout) o;

        return this.getName().equals(((Workout) o).getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName()); // or whatever unique field you used
    }
}