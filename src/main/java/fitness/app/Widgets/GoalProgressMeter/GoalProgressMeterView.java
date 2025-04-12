package fitness.app.Widgets.GoalProgressMeter;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatProgressBar;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;

public class GoalProgressMeterView extends JPanel {
    public GoalProgressMeterView() {
        setLayout(new MigLayout("wrap,fillx,insets 30", "fill,275"));
        putClientProperty(FlatClientProperties.STYLE, "arc:20;");
        for (int i = 1; i <= 5; ++i) {
            add(new JLabel("Goal " + i), "wrap, gapy 5");
            FlatProgressBar progressBar = new FlatProgressBar();
            progressBar.setValue((int)(Math.random() * (101)));
            add(progressBar, "wrap");
        }
    }
}
