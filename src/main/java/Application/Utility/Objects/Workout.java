package Application.Utility.Objects;


public class Workout {
    private String name;
    private int duration;
    private String description;
    private int CaloriesBurned;
    private String Date;
    private String exerciseId;
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

    public void setExercises(String exerciseId) {
        this.exerciseId = exerciseId;
    }


    public void addExercise(Exercise exercise) {
        this.exerciseId = exerciseId + "," + exercise.getId();
    }
}