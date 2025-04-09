package fitness.app.Home;

import fitness.app.Widgets.Battlepass.BattlepassView;
import fitness.app.Widgets.Calendar.CalendarView;
import fitness.app.Widgets.SideMenu.SideMenuView;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;


//Implement menu bar as seperate method to abstract?

public class HomeView {

    private static JPanel mainPanel;

    public HomeView() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new MigLayout("insets 20", "left", "top"));

        mainPanel.add(new SideMenuView(), "growy, pushy");
        mainPanel.add(new CalendarView(), "gapx 10");
        mainPanel.add(new BattlepassView(), "gapx 10, growx, pushx");
    }


    public static JPanel getMainPanel() {
        return mainPanel;
    }
}
