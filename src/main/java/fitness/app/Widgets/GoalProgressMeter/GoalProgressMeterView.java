package fitness.app.Widgets.GoalProgressMeter;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatProgressBar;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;


public class GoalProgressMeterView {
    public GoalProgressMeterView() {
        JPanel widgetMenu = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,275"));
        widgetMenu.putClientProperty(FlatClientProperties.STYLE, "arc:20;" + "background:lighten(@background,5%)");
    }

}
