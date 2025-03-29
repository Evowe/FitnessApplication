package fitness.app;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto_mono.FlatRobotoMonoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import fitness.app.CreateAccount.CreateAccountViewModel;
import fitness.app.Home.HomeViewModel;
import fitness.app.Login.LoginViewModel;
import fitness.app.Objects.AccountsDB;
import fitness.app.Objects.DatabaseManager;
import fitness.app.Statistics.StatsViewModel;

import javax.swing.*;
import java.awt.*;

public class Main {
    private static JFrame window;

    public static void main(String[] args) {
        System.setProperty("apple.awt.application.name", "Rocket Health");
        System.setProperty("apple.awt.application.appearance", "system");

        DatabaseManager.addDatabase("accounts", new AccountsDB("accounts"));
        System.out.println("Database initialized successfully.");

        //FlatLaf setup & settings
        FlatRobotoMonoFont.install();
        FlatLaf.registerCustomDefaultsSource("FlatLafSettings");
        UIManager.put("defaultFont", new Font(FlatRobotoMonoFont.FAMILY, Font.PLAIN, 13));
        FlatMacDarkLaf.setup();

        //Application window
        window = new JFrame("Rocket Health");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(new Dimension(1200, 700));
        window.setLocationRelativeTo(null);
        window.add(LoginViewModel.getLoginView());
        window.getRootPane().putClientProperty("apple.awt.transparentTitleBar", true);
        window.getRootPane().putClientProperty("apple.awt.windowTitleVisible", false);

        window.setVisible(true);
    }

    public static void setWindow(String windowName) {
        window.getContentPane().removeAll();
        switch (windowName) {
            case "LoginPage" -> {
                window.add(LoginViewModel.getLoginView());
            }
            case "CreateAccountPage" -> {
                window.add(CreateAccountViewModel.getCreateAccountView());
            }
            case "HomePage" -> {
                window.add(HomeViewModel.getHomeView());
            }
            case "StatsPage" -> {
                window.add(StatsViewModel.getStatsView());
            }
        }
        window.revalidate();
        window.repaint();
    }
}
