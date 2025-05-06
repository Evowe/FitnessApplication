package Application.AccountManagement.Home;

import Application.Utility.Widgets.DaysSinceLastWorkout.DaysSinceLastWorkoutView;
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
        JPanel whole = new JPanel(new MigLayout("wrap, fillx, filly,insets 0", "[grow, fill]", " [grow, fill]"));
        whole.putClientProperty(FlatClientProperties.STYLE, "background:@background;");

        JPanel top = new JPanel(new MigLayout("insets 0", "left", "top"));
        top.putClientProperty(FlatClientProperties.STYLE, "background:@background;");
        top.add(new CalendarView(), "gapx 10, growx, growy, pushx, pushy");
        JPanel stack = new JPanel(new MigLayout("wrap, insets 0", "left", "top"));
        stack.putClientProperty(FlatClientProperties.STYLE, "background:@background;");
        stack.add(new BattlepassView(), "gapx 10, gapy 0, growx, growy, pushx, pushy");
        stack.add(new GoalProgressMeterView(currentUser), "gapx 10, gapy 10, growx, growy, pushx, pushy");
        top.add(stack, "gapx 0, growx, growy, pushx, pushy");
        whole.add(top, "growx, pushx");

        JPanel bottom = new JPanel(new MigLayout("fill, insets 0", "[grow,fill][grow,fill]", " [grow,fill]"));
        bottom.putClientProperty(FlatClientProperties.STYLE, "background:@background;");
        bottom.add(new ProfileView(), "gapy 10, gapx 10, growx, pushx, growy, pushy");
        bottom.add(new DaysSinceLastWorkoutView(), "gapy 10, gapx 10, growx, pushx, growy, pushy");
        whole.add(bottom, "growx, pushx");

        add(whole, "growx, pushx, growy, pushy");
    }
}
