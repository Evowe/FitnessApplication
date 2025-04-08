package fitness.app.Widgets.SideMenu;

import com.kitfox.svg.app.beans.SVGIcon;
import fitness.app.Home.HomeModel;
import fitness.app.Home.HomeView;

import javax.swing.*;

public class SideMenuViewModel {
    private static HomeView homeView;

    public static JPanel getHomeView() {
        homeView = new HomeView();
        return HomeView.getMainPanel();
    }

    public static SVGIcon getIcon(String iconName) {
        return HomeModel.getIcon(iconName);
    }
}
