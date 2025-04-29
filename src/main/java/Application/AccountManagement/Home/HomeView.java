package Application.AccountManagement.Home;

import com.formdev.flatlaf.FlatClientProperties;
import Application.Utility.Objects.Account;
import Application.Utility.Widgets.Battlepass.BattlepassView;
import Application.Utility.Widgets.Calendar.CalendarView;
import Application.Utility.Widgets.GoalProgressMeter.GoalProgressMeterView;
import Application.Utility.Widgets.Profile.ProfileView;
import Application.Utility.Widgets.SideMenu.SideMenuView;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;

public class HomeView extends JPanel {
    private final HomeViewModel homeViewModel;

    public HomeView(Account currentUser) {
        homeViewModel = new HomeViewModel();

        setLayout(new MigLayout("insets 20", "left", "top"));
        putClientProperty(FlatClientProperties.STYLE, "background:@background");

        add(new SideMenuView(), "growy, pushy");
        JPanel whole = new JPanel(new MigLayout("wrap, insets 2"));
        whole.putClientProperty(FlatClientProperties.STYLE, "background:@background;");

        JPanel top = new JPanel(new MigLayout("insets 0", "left", "top"));
        top.add(new CalendarView(CalendarView.Type.BUTTONED), "gapx 10");
        top.add(new BattlepassView(), "gapx 10, growx, pushx, wrap");
        top.putClientProperty(FlatClientProperties.STYLE, "background:@background;");
        whole.add(top);

        JPanel bottom = new JPanel(new MigLayout("insets 0", "left", "top"));
        bottom.add(new GoalProgressMeterView(currentUser), "gapy 5, gapx 10, growx, pushx");
        bottom.add(new ProfileView(), "gapy 5, gapx 10, growx, pushx, wrap");
        bottom.putClientProperty(FlatClientProperties.STYLE, "background:@background;");
        whole.add(bottom);

        add(whole, "growy, pushy");
    }
}
