package fitness.app.Model;

import java.util.ArrayList;
import java.util.List;

public class Trainer extends Account {
    private List<Workout> createdWorkouts;

    //Will need a WorkoutClass and Plan later

    public Trainer(String username, String password, String status){
        super(username, password, status, "Trainer");
        this.createdWorkouts = new ArrayList<Workout>();
    }

    public Workout createWorkout(int duration, String description, List<Exercise> exercises){
        Workout newWorkout = new Workout(duration, description, exercises);
        createdWorkouts.add(newWorkout);
        return newWorkout;
    }


}
