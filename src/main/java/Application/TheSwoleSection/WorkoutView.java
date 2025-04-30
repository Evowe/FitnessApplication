package Application.TheSwoleSection;

import Application.Main;
import Application.TheSwoleSection.ExerciseLibrary.ExerciseLibraryView;
import Application.TheSwoleSection.TrainerCreatedWorkoutPlan.CreateWorkoutPlanView;
import Application.TheSwoleSection.WorkoutLibrary.WorkoutLibraryView;
import Application.TheSwoleSection.WorkoutSchedule.WorkoutScheduleView;
import Application.Utility.Widgets.SideMenu.SideMenuView;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatTabbedPane;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;

public class WorkoutView extends JPanel {
    public WorkoutView() {
        //Set standard layout
        setLayout(new MigLayout("fill, insets 20", "[]20[]"));
        putClientProperty(FlatClientProperties.STYLE, "background:@background");

        FlatTabbedPane tabbedPane = new FlatTabbedPane();
        tabbedPane.putClientProperty(FlatClientProperties.STYLE, "background:@background; foreground:@foreground");

        tabbedPane.add("Workout Schedule", new WorkoutScheduleView(Main.getCurrentUser()));
        //TODO: Create the workout plans page (find a workout plan from a user/trainer)
        tabbedPane.add("Workout Plans", new CreateWorkoutPlanView());
        //TODO: Add the trainer classes page
        tabbedPane.add("Classes", new JPanel());
        tabbedPane.add("Workout Library", new WorkoutLibraryView());
        tabbedPane.add("Exercise Library", new ExerciseLibraryView());

        add(new SideMenuView(), "growy, pushy");
        add(tabbedPane, "growx, growy, pushx, pushy");
    }
}
