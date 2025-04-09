package fitness.app.Home;

import fitness.app.Widgets.Battlepass.BattlepassView;
import fitness.app.Widgets.Calendar.CalendarView;
import fitness.app.Widgets.GoalProgressMeter.GoalProgressMeterView;
import fitness.app.Widgets.SideMenu.SideMenuView;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;

public class HomeView extends JPanel {
    private final HomeViewModel homeViewModel;

    public HomeView() {
        homeViewModel = new HomeViewModel();

        setLayout(new MigLayout("insets 20", "left", "top"));

        add(new SideMenuView(), "growy, pushy");
        JPanel whole = new JPanel(new MigLayout("wrap, insets 2"));

        JPanel top = new JPanel(new MigLayout("insets 0", "left", "top"));
        top.add(new CalendarView(), "gapx 10");
        top.add(new BattlepassView(), "gapx 10, growx, pushx, wrap");
        whole.add(top);

        JPanel bottom = new JPanel(new MigLayout("insets 0", "left", "top"));
        bottom.add(new GoalProgressMeterView(), "gapy 5, gapx 10, growx, pushx, wrap");
        whole.add(bottom);

        add(whole, "growy, pushy");
    }
}
