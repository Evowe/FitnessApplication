package fitness.app.Goals;

import fitness.app.Widgets.SideMenu.SideMenuView;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class GoalsView extends JPanel {
    private static JFrame mainPanel;

    public GoalsView() {
        setLayout(new MigLayout());
        add(new SideMenuView(), "growy, pushy");
    }
}
