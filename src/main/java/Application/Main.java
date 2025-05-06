package Application;

import Application.AccountManagement.ResetPassword.ResetPasswordView;
import Application.TheSwoleSection.TrainerCreatedWorkoutPlan.CreateWorkoutPlanView;
import Application.TheSwoleSection.WorkoutPlans.ModifiyWorkoutPlan;
import Application.TheSwoleSection.WorkoutView;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.fonts.roboto_mono.FlatRobotoMonoFont;
import Application.AccountManagement.Admin.AdminAddUserView;
import Application.AccountManagement.Admin.AdminHomeView;
import Application.AccountManagement.Admin.AdminUsersView;
import Application.Utility.Objects.Account;
import Application.Databases.DatabaseManager;
import Application.BonusFeatures.BattlePass.BattlePassView;
import Application.AccountManagement.CreateAccount.CreateAccountView;
import Application.TheSwoleSection.CreateExercise.CreateExcerciseView;
import Application.TheSwoleSection.CreateLiveWorkout.NewLiveWorkoutView;
import Application.BonusFeatures.CurrencyShop.currencyShopViewModel;
import Application.TheSwoleSection.ExerciseLibrary.ExerciseLibraryView;
import Application.TheSwoleSection.LiveWorkout.LiveWorkoutView;
import Application.AccountManagement.Home.HomeView;
import Application.BonusFeatures.Locker.LockerView;
import Application.AccountManagement.Login.LoginView;
import Application.Metrics.MetricsView;
import Application.AccountManagement.Settings.SettingsViewModel;
import Application.BonusFeatures.Social.CreateMessageView;
import Application.BonusFeatures.Social.SendResponseView;
import Application.BonusFeatures.Social.SocialView;
import Application.Metrics.Statistics.StatsView;
import Application.TheSwoleSection.CreateWorkout.NewWorkoutView;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static boolean dark = true;
    private static JFrame window;
    private static Account currentUser;

    public static void main(String[] args) {
        System.setProperty("apple.awt.application.name", "Rocket Health");
        System.setProperty("apple.awt.application.appearance", "system");
        DatabaseManager.initializeDatabases();

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
            case "MetricsPage" -> {
                window.add(new MetricsView(currentUser));
            }
            case "AdminPage" -> {
                window.add(new AdminHomeView());
            }
            case "currencyShopPage" -> {
                window.add(currencyShopViewModel.getCurrencyView(currentUser));
            }
            case "SettingsPage" -> {
                window.add(SettingsViewModel.getSettingsView());
            }
            case "AdminUsers" -> {
                AdminUsersView.refreshView();
            	window.add(AdminUsersView.getView());
            }
            case "AdminAddUserView" -> {
            	window.add(new AdminAddUserView());
            }
            case "ExerciseLibrary" -> {
                window.add(new ExerciseLibraryView());
            }
            case "Workout" -> {
                window.add(new WorkoutView());
            }
            case "CreateExercise" -> {
                window.add(new CreateExcerciseView());
            }
            case "NewWorkout" -> {
                window.add(new NewWorkoutView());
            }
            case "CreateMessage" -> {
                window.add(new CreateMessageView());
            }
            case "SocialView" -> {
                window.add(new SocialView());
            }
            case "BattlePass" -> {
                window.add(new BattlePassView());
            }
            case "SendResponse" -> {
                window.add(new SendResponseView());
            }
            case "Locker" -> {
                window.add(new LockerView(currentUser));
            }
            case "ResetPasswordPage" -> {
                window.add(new ResetPasswordView());
            }
            case "CreateWorkoutPlan" -> {
                window.add(new CreateWorkoutPlanView());
            }
            case "NewLiveWorkout" -> {
            	window.add(new NewLiveWorkoutView());
            }
            case "LiveWorkouts" -> {
            	window.add(new LiveWorkoutView());
            }
        }
        window.revalidate();
        window.repaint();
    }

    public static void setWindow(String name, JTable owner){
        if(name.equals("ModifyWorkoutPlan")){
            window.getContentPane().removeAll();
            window.add(new ModifiyWorkoutPlan(owner));
            window.revalidate();
            window.repaint();
        }
    }

    public static Account getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(Account account) {
        currentUser = account;
    }
}
