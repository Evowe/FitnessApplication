package fitness.app;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.fonts.roboto_mono.FlatRobotoMonoFont;
import fitness.app.Admin.AdminAddUserView;
import fitness.app.Admin.AdminHomeView;
import fitness.app.Admin.AdminUsersView;
import fitness.app.CreateAccount.CreateAccountView;
import fitness.app.CreateExercise.CreateExcerciseView;
import fitness.app.CurrencyShop.currencyShopViewModel;
import fitness.app.CurrencyShop.currencyshopview;
import fitness.app.ExerciseLibrary.ExerciseLibraryView;
import fitness.app.Goals.GoalsView;
import fitness.app.Home.HomeView;
import fitness.app.Login.LoginView;
import fitness.app.Microtransactions.TransactionViewModel;
import fitness.app.Settings.SettingsViewModel;
import fitness.app.Social.CreateMessageView;
import fitness.app.Social.SocialView;
import fitness.app.Statistics.StatsView;
import fitness.app.Objects.*;
import fitness.app.Workout.NewWorkoutView;
import fitness.app.WorkoutLibrary.WorkoutLibraryView;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class Main {
    public static boolean dark = true;
    private static JFrame window;
    private static Account currentUser;

    public static void main(String[] args) {
        System.setProperty("apple.awt.application.name", "Rocket Health");
        System.setProperty("apple.awt.application.appearance", "system");
        DatabaseManager.initializeDatabases();

        addTestAdminAccount();

        //FlatLaf setup & settings
        FlatRobotoMonoFont.install();
        FlatLaf.registerCustomDefaultsSource("FlatLafSettings");

        try {
            if (dark) {
                UIManager.setLookAndFeel(new FlatDarkLaf());
            }
            else {
                UIManager.setLookAndFeel(new FlatLightLaf());
            }
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        UIManager.put("defaultFont", new Font(FlatRobotoMonoFont.FAMILY, Font.PLAIN, 13));

        //Application window
        window = new JFrame("Rocket Health");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setSize(1200, 700);
        window.setLocationRelativeTo(null);
        window.add(new LoginView());
        window.getRootPane().putClientProperty("apple.awt.transparentTitleBar", true);
        window.getRootPane().putClientProperty("apple.awt.windowTitleVisible", false);
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
                window.add(new HomeView(currentUser));
            }
            case "StatsPage" -> {
                window.add(new StatsView(currentUser));
            }
            case "AdminPage" -> {
                window.add(new AdminHomeView());
            }
            case "currencyShopPage" -> {
                currencyShopViewModel view = new currencyShopViewModel();
                TransactionViewModel transact = new TransactionViewModel();
                transact.getCardUser(currentUser);
                view.findCurrUser(currentUser);
                window.add(new currencyshopview(currentUser));
            }
            case "GoalsPage" -> {
                window.add(new GoalsView(currentUser));
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
            case "WorkoutLibrary" -> {
                window.add(new WorkoutLibraryView());
            }
            case "CreateExercise" -> {
                window.add(new CreateExcerciseView());
            }
            case "NewWorkout" -> {
                window.add(new NewWorkoutView());
            }
            case "CreateMessage" -> {
                window.add(new CreateMessageView());
                //window.add(new NewWorkoutView());
            }
            case "SocialView" -> {
                window.add(new SocialView());
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
    /*
    public static void updateTheme(){
        try {
            //CHANGE WAS HERE
            if (dark) {
                UIManager.setLookAndFeel(new FlatDarkLaf());
            }
            else {
                UIManager.setLookAndFeel(new FlatLightLaf());
            }
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        SwingUtilities.updateComponentTreeUI(SwingUtilities.windowForComponent(window));

    }

     */


    public static Account getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(Account account) {
        currentUser = account;
    }
}
