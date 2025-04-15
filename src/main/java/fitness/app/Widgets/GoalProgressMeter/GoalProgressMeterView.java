package fitness.app.Widgets.GoalProgressMeter;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatProgressBar;
import fitness.app.Objects.Account;
import fitness.app.Objects.DatabaseManager;
import fitness.app.Objects.Databases.GoalsDB;
import fitness.app.Objects.Goal;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.sql.SQLException;
import java.text.ParseException;

public class GoalProgressMeterView extends JPanel {
    public GoalProgressMeterView(Account currentUser) {
        GoalsDB goalsDB = DatabaseManager.getGoalsDB();
        try {
            Goal WeightGoal;
            Goal DistanceGoal;
            goalsDB.ensureDefaultGoals(currentUser.getUsername());
            WeightGoal = goalsDB.getGoalByTypeAndUsername(currentUser.getUsername(), "Weight");
            DistanceGoal = goalsDB.getGoalByTypeAndUsername(currentUser.getUsername(), "Distance");
            setLayout(new MigLayout("wrap,fillx,insets 30", "fill,275"));
            putClientProperty(FlatClientProperties.STYLE, "arc:20;");
            add(new JLabel("Weight Goal "), "wrap, gapy 5");
            FlatProgressBar progressBar = new FlatProgressBar();
            //TODO get from database
            progressBar.setValue((int)(WeightGoal.getValue()));
            add(progressBar, "wrap");
            add(new JLabel("Distance Goal "), "wrap, gapy 5");
            progressBar = new FlatProgressBar();
            //TODO get from database
            progressBar.setValue((int)DistanceGoal.getValue());
            add(progressBar, "wrap");
        }
        catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
}
