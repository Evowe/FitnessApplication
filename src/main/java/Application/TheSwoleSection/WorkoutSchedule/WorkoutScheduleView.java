package Application.TheSwoleSection.WorkoutSchedule;

import Application.Utility.Objects.Account;
import Application.Utility.Widgets.Calendar.CalendarView;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;

public class WorkoutScheduleView extends JPanel {
    private WorkoutScheduleViewModel viewModel;
    private Account user;

    public WorkoutScheduleView(Account user) {
        //Link page to the current user
        this.user = user;

        //Set standard layout
        setLayout(new MigLayout("fill, insets 0", "center", "center"));
        putClientProperty(FlatClientProperties.STYLE, "background:@background");

        //TODO: Replace with a new calendar variant
        add(new CalendarView(CalendarView.Type.PANELED), "gapy 20, growx, growy, pushx, pushy");
    }
}
