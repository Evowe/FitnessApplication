package fitness.app;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto_mono.FlatRobotoMonoFont;
import fitness.app.Admin.AdminAddUserView;
import fitness.app.Admin.AdminHomeView;
import fitness.app.Admin.AdminUsersView;
import fitness.app.Admin.AdminUsersViewModel;
import fitness.app.CreateAccount.CreateAccountView;
import fitness.app.CreateExercise.CreateExcerciseView;
import fitness.app.CurrencyShop.currencyShopViewModel;
import fitness.app.ExerciseLibrary.ExerciseLibraryView;
import fitness.app.Goals.GoalsView;
import fitness.app.Home.HomeView;
import fitness.app.Login.LoginView;
import fitness.app.Microtransactions.TransactionViewModel;
import fitness.app.Objects.Databases.*;
import fitness.app.Settings.SettingsViewModel;
import fitness.app.Statistics.StatsView;
import fitness.app.Objects.*;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class Main {

    private static JFrame window;
    private static Account currentUser;

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
        FlatDarkLaf.setup();
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        UIManager.put("defaultFont", new Font(FlatRobotoMonoFont.FAMILY, Font.PLAIN, 13));

        //Application window
        window = new JFrame("Rocket Health");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setLocationRelativeTo(null);
        window.add(new LoginView());
        window.getRootPane().putClientProperty("apple.awt.transparentTitleBar", true);
        window.getRootPane().putClientProperty("apple.awt.windowTitleVisible", false);

        JButton test = new JButton("Test");

        window.setVisible(true);
    }

    public static void setWindow(String windowName) {
        System.out.println(currentUser);
        window.getContentPane().removeAll();
        switch (windowName) {
            case "LoginPage" -> {
                window.add(new LoginView());
            }
            case "CreateAccountPage" -> {
                window.add(new CreateAccountView());
            }
            case "HomePage" -> {
                window.add(new HomeView());
            }
            case "StatsPage" -> {
                StatsView statsView = new StatsView(currentUser);
                window.add(statsView.getViewPanel());
            }
            case "AdminPage" -> {
                window.add(new AdminHomeView());
            }
            case "currencyShopPage" -> {
                currencyShopViewModel view = new currencyShopViewModel();
                TransactionViewModel transact = new TransactionViewModel();
                transact.getCardUser(currentUser);
                view.findCurrUser(currentUser);
                window.add(currencyShopViewModel.getCurrencyView());
            }
            case "GoalsPage" -> {
                window.add(new GoalsView());
            }
            case "SettingsPage" -> {
                window.add(SettingsViewModel.getSettingsView());
            }
            case "AdminUsers" -> {
            	window.add(AdminUsersView.getView());
            }
            case "AdminAddUserView" -> {
            	window.add(new AdminAddUserView());
            }
            case "ExerciseLibrary" -> {
                window.add(new ExerciseLibraryView());
            }
            case "CreateExercise" -> {
                window.add(new CreateExcerciseView());
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

    public static Account getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(Account account) {
        currentUser = account;
    }
}
