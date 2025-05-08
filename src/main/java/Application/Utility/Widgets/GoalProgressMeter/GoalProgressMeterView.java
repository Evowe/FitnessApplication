package Application.Utility.Widgets.GoalProgressMeter;

import Application.Databases.StatsDB;
import Application.Databases.WorkoutLogDB;
import Application.Utility.Objects.Workout;
import Application.Utility.Objects.WorkoutPlan;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatProgressBar;
import Application.Utility.Objects.Account;
import Application.Databases.DatabaseManager;
import Application.Databases.GoalsDB;
import Application.Utility.Objects.Goal;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class GoalProgressMeterView extends JPanel {
    public GoalProgressMeterView(Account currentUser) {
        GoalsDB goalsDB = DatabaseManager.getGoalsDB();
        StatsDB statsDB = DatabaseManager.getStatsDB();
        WorkoutLogDB workoutLogDB = DatabaseManager.getWorkoutLogDB();
        try {
            Goal WeightGoal;
            Goal WorkoutGoal;
            goalsDB.ensureDefaultGoals(currentUser.getUsername());
            LocalDate d = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            WeightGoal = goalsDB.getGoalByTypeAndUsername(currentUser.getUsername(), "Weight");
            WorkoutGoal = goalsDB.getGoalByTypeAndUsername(currentUser.getUsername(), "Workout");
            LocalDate today = LocalDate.now();
            Map<String,Object> map = null;
            try {
                map = statsDB.getDailyMetrics(currentUser.getUsername(), today);
            }
            catch (SQLException e){
                System.err.println("Error retrieving data: " + e.getMessage());
                e.printStackTrace();
                return;
            }
            FlatProgressBar progressBar = new FlatProgressBar();
            if ( map.get("weight") != null ) {
                    double weight = (double) map.get("weight");
                    setLayout(new MigLayout("wrap,fillx,insets 30", "fill,275"));
                    putClientProperty(FlatClientProperties.STYLE, "arc:20;");
                    add(new JLabel("Weight Goal "), "wrap, gapy 5");
                    //TODO get from database
                    progressBar.setValue((int) (weight / (WeightGoal.getValue()) * 100));
                    add(progressBar, "wrap");
            }
            else {
                add(new JLabel("Set a goal to see your progress!"), "wrap, gapy 5");
            }
                progressBar = new FlatProgressBar();
                //TODO get from database
                List<Workout> workouts = null;

                try {
                    workouts = workoutLogDB.getAllWorkouts(currentUser.getUsername());
                } catch (SQLException e) {
                    System.err.println("Error retrieving data: " + e.getMessage());
                    e.printStackTrace();
                    return;
                }
                double w = workouts.size();
                if ( WorkoutGoal != null) {
                    add(new JLabel("Workout Goal "), "wrap, gapy 5");
                    progressBar.setValue((int) (w / (double) WorkoutGoal.getValue() * 100));
                    add(progressBar, "wrap");
                }

        }
        catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
}
