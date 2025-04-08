package fitness.app.Home;

import fitness.app.Widgets.SideMenu.SideMenuView;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;


//Implement menu bar as seperate method to abstract?

public class HomeView {

    private static JPanel mainPanel;

    public HomeView() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new MigLayout());

        mainPanel.add(new SideMenuView(), "growy, pushy");
    }


    public static JPanel getMainPanel() {
        return mainPanel;
    }
}
