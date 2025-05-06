package Application.Utility.Widgets.DaysSinceLastWorkout;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.Random;

public class DaysSinceLastWorkoutView extends JPanel {
    public DaysSinceLastWorkoutView() {
        setLayout(new MigLayout("wrap, fillx, insets 20", "center", "center"));
        putClientProperty(FlatClientProperties.STYLE, "arc:20; background:@secondaryBackground");

        JLabel title =  new JLabel("Days Since Last Workout");
        title.putClientProperty(FlatClientProperties.STYLE, "font: +24");

        int random = new Random().nextInt(30);
        JLabel daysCounter = new JLabel(String.valueOf(random));
        daysCounter.putClientProperty(FlatClientProperties.STYLE, "font: +90");

        add(title, "align center");
        add(daysCounter, "align center");
    }

}
