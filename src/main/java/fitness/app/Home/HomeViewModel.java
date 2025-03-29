package fitness.app.Home;

import com.kitfox.svg.app.beans.SVGIcon;

import javax.swing.*;

public class HomeViewModel {
    private static HomeView homeView;

    public static JPanel getHomeView() {
        homeView = new HomeView();
        return HomeView.getMainPanel();
    }

    public static SVGIcon getIcon(String iconName) {
        return HomeModel.getIcon(iconName);
    }
}
