package Application.TheSwoleSection;

import Application.TheSwoleSection.ExerciseLibrary.ExerciseLibraryView;
import Application.TheSwoleSection.WorkoutLibrary.WorkoutLibraryView;
import Application.Utility.Widgets.SideMenu.SideMenuView;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatTabbedPane;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class WorkoutView extends JPanel {
    public WorkoutView() {
        setLayout(new MigLayout("fill, insets 20", "center", "center"));
        putClientProperty(FlatClientProperties.STYLE, "background:@background");

        FlatTabbedPane tabbedPane = new FlatTabbedPane();
        tabbedPane.putClientProperty(FlatClientProperties.STYLE, "background:@background; foreground:@foreground");

        tabbedPane.add("Workout Library", new WorkoutLibraryView());
        tabbedPane.add("Exercise Library", new ExerciseLibraryView());

        add(new SideMenuView(), "growy, pushy");
        add(tabbedPane, "growx, growy, pushx, pushy");
    }
}
