package fitness.app.Home;

import javax.swing.*;

public class HomeViewModel {
    private static HomeView homeView;

    public static JPanel getHomeView() {
        homeView = new HomeView();
        return HomeView.getMainPanel();
    }
}
