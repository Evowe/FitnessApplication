package fitness.app.Home;

import javax.swing.*;

public class HomeViewModel {
    private HomeView homeView;
    public static JPanel getHomeView() {
        return HomeView.getMainPanel();
    }
}
