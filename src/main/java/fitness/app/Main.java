package fitness.app;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto_mono.FlatRobotoMonoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import fitness.app.Admin.AdminAddUserView;
import fitness.app.Admin.AdminHomeView;
import fitness.app.Admin.AdminUsersView;
import fitness.app.CreateAccount.CreateAccountViewModel;
import fitness.app.CurrencyShop.currencyShopViewModel;
import fitness.app.CurrencyShop.currencyshopview;
import fitness.app.Goals.GoalsViewModel;
import fitness.app.Home.HomeViewModel;
import fitness.app.Login.LoginViewModel;
import fitness.app.Microtransactions.TransactionViewModel;
import fitness.app.Settings.SettingsViewModel;
import fitness.app.Statistics.StatsView;
import fitness.app.Statistics.StatsViewModel;

import fitness.app.Objects.*;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

//Need to implement navigation method?
//	Use stack with Panels to implement navigation? (create navigationcontroller)

//handle errors with dialogs?

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
        System.out.println(currentUser);
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
                StatsView statsView = new StatsView(currentUser);
                window.add(statsView.getViewPanel());
            }
            case "AdminPage" -> {
                window.add(AdminHomeView.getMainPanel());
            }
            case "currencyShopPage" -> {
                currencyShopViewModel view = new currencyShopViewModel();
                TransactionViewModel transact = new TransactionViewModel();
                transact.getCardUser(currentUser);
                view.findCurrUser(currentUser);
                window.add(currencyShopViewModel.getCurrencyView());
            }
            case "GoalsPage" -> {
                window.add(GoalsViewModel.getGoalsView());
            }
            case "SettingsPage" -> {
                window.add(SettingsViewModel.getSettingsView());
            }
            case "AdminUsers" -> {
            	window.add(AdminUsersView.getView());
            }
            case "AdminAddUser" -> {
            	window.add(AdminAddUserView.getView());
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
