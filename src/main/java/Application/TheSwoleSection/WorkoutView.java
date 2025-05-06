package Application.TheSwoleSection;

import Application.AccountManagement.Admin.AdminAddUserView;
import Application.AccountManagement.Admin.AdminHomeView;
import Application.AccountManagement.Admin.AdminUsersView;
import Application.AccountManagement.CreateAccount.CreateAccountView;
import Application.AccountManagement.Home.HomeView;
import Application.AccountManagement.Login.LoginView;
import Application.AccountManagement.ResetPassword.ResetPasswordView;
import Application.AccountManagement.Settings.SettingsViewModel;
import Application.BonusFeatures.BattlePass.BattlePassView;
import Application.BonusFeatures.CurrencyShop.currencyShopViewModel;
//import Application.BonusFeatures.CurrencyShop.currencyshopview;
import Application.BonusFeatures.Locker.LockerView;
import Application.BonusFeatures.Microtransactions.TransactionViewModel;
import Application.BonusFeatures.Social.CreateMessageView;
import Application.BonusFeatures.Social.SendResponseView;
import Application.BonusFeatures.Social.SocialView;
import Application.Main;
import Application.Metrics.MetricsView;
import Application.Metrics.Statistics.StatsView;
import Application.TheSwoleSection.CreateExercise.CreateExcerciseView;
import Application.TheSwoleSection.CreateWorkout.NewWorkoutView;
import Application.TheSwoleSection.ExerciseLibrary.ExerciseLibraryView;
import Application.TheSwoleSection.LiveWorkout.LiveWorkoutView;
import Application.TheSwoleSection.TrainerCreatedWorkoutPlan.CreateWorkoutPlanView;
import Application.TheSwoleSection.WorkoutLibrary.WorkoutLibraryView;
import Application.TheSwoleSection.WorkoutPlans.WorkoutPlansView;
import Application.TheSwoleSection.WorkoutSchedule.WorkoutScheduleView;
import Application.Utility.Widgets.SideMenu.SideMenuView;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatTabbedPane;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;

public class WorkoutView extends JPanel {
    private static FlatTabbedPane tabbedPane;
    public WorkoutView() {
        //Set standard layout
        setLayout(new MigLayout("fill, insets 20", "[]20[]"));
        putClientProperty(FlatClientProperties.STYLE, "background:@background");

        tabbedPane = new FlatTabbedPane();
        //FlatTabbedPane tabbedPane = new FlatTabbedPane();
        tabbedPane.putClientProperty(FlatClientProperties.STYLE, "background:@background; foreground:@foreground");

        tabbedPane.add("Workout Schedule", new WorkoutScheduleView(Main.getCurrentUser()));
        //TODO: Create the workout plans page (find a workout plan from a user/trainer)
        tabbedPane.add("Workout Plans", new WorkoutPlansView());
        //TODO: Add the trainer classes page
        tabbedPane.add("Classes", new LiveWorkoutView());
        tabbedPane.add("Workout Library", new WorkoutLibraryView());
        tabbedPane.add("Exercise Library", new ExerciseLibraryView());
        //tabbedPane.add("Live Workouts", new LiveWorkoutView());



        add(new SideMenuView(), "growy, pushy");
        add(tabbedPane, "growx, growy, pushx, pushy");
    }


    public static void setView(String viewName) {
        switch (viewName) {
            case "WorkoutSchedule" -> {
                tabbedPane.setSelectedIndex(0);
            }
            case "WorkoutPlans" -> {
                tabbedPane.setSelectedIndex(1);
            }
            case "Classes" -> {
                tabbedPane.setSelectedIndex(2);
            }
            case "WorkoutLibrary" -> {
                tabbedPane.setSelectedIndex(3);
            }
            case "ExerciseLibrary" -> {
                tabbedPane.setSelectedIndex(4);
            }
        }
    }




}
