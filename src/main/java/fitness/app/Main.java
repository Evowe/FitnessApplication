package fitness.app;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto_mono.FlatRobotoMonoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import fitness.app.Admin.AdminHomeView;
import fitness.app.CreateAccount.CreateAccountViewModel;
import fitness.app.Goals.GoalsViewModel;
import fitness.app.Home.HomeViewModel;
import fitness.app.Login.LoginViewModel;
import fitness.app.Statistics.StatsViewModel;

import fitness.app.Objects.*;
import fitness.app.Widgets.Calendar.CalendarViewModel;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

//Store user information somewhere?

public class Main {
    private static JFrame window;

    public static void main(String[] args) {
        System.setProperty("apple.awt.application.name", "Rocket Health");
        System.setProperty("apple.awt.application.appearance", "system");

        DatabaseManager.addDatabase("accounts", new AccountsDB("accounts"));
        DatabaseManager.addDatabase("exercises", new ExerciseDB("exercises"));
        DatabaseManager.addDatabase("creditCard", new CreditCardDB("creditCard"));
        System.out.println("Databases initialized successfully.");

        addTestAdminAccount();

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
            case "AdminPage" -> {
                window.add(AdminHomeView.getMainPanel());
            }
            case "GoalsPage" -> {
                window.add(GoalsViewModel.getGoalsView());
            }
        }
        window.revalidate();
        window.repaint();
    }

    public static void addTestAdminAccount() {
        try {
            // Create admin account with username "admin" and password "admin123"
            Account adminAccount = new Account("admin", "admin123", "active", "admin");

            // Check if admin account already exists
            if (!Account.usernameExists("admin")) {
                adminAccount.addAccount();
                System.out.println("Test admin account created successfully.");
            } else {
                System.out.println("Test admin account already exists.");
            }
        } catch (SQLException e) {
            System.out.println("Error creating test admin account: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
