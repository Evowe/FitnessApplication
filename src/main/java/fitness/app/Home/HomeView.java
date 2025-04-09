package fitness.app.Home;

import fitness.app.Widgets.Battlepass.BattlepassView;
import fitness.app.Widgets.Calendar.CalendarView;
import fitness.app.Widgets.SideMenu.SideMenuView;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;

public class HomeView extends JPanel {
    private final HomeViewModel homeViewModel;

    public HomeView() {
        homeViewModel = new HomeViewModel();

        setLayout(new MigLayout("insets 20", "left", "top"));

        add(new SideMenuView(), "growy, pushy");
        add(new CalendarView(), "gapx 10");
        add(new BattlepassView(), "gapx 10, growx, pushx");
    }
}
