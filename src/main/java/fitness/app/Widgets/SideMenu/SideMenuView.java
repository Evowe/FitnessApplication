package fitness.app.Widgets.SideMenu;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;
import fitness.app.Main;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SideMenuView extends JPanel {
    private final SideMenuViewModel sideMenuViewModel;

    public SideMenuView() {
        sideMenuViewModel = new SideMenuViewModel();

        //Button Menu
        setLayout(new MigLayout("insets 20, fillx", "fill, 125"));
        putClientProperty(FlatClientProperties.STYLE, "arc:20;");

        JLabel title = new JLabel("Rocket Health", sideMenuViewModel.getIcon("start-up"), JLabel.LEFT);
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +6");

        JLabel generalLabel = new JLabel();
        generalLabel.setText("General");
        generalLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground");

        FlatButton homeButton = new FlatButton();
        homeButton.setBorderPainted(false);
        homeButton.setText("Home");
        homeButton.setIcon(sideMenuViewModel.getIcon("home-03"));
        homeButton.setHorizontalAlignment(SwingConstants.LEFT);
        homeButton.setMargin(new Insets(5, 25, 5, 5));
        homeButton.setMinimumHeight(50);
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(homeButton.getSize());
                Main.setWindow("HomePage");
            }
        });

        FlatButton workoutsButton = new FlatButton();
        workoutsButton.setBorderPainted(false);
        workoutsButton.setText("Workouts");
        workoutsButton.setIcon(sideMenuViewModel.getIcon("workout-run"));
        workoutsButton.setHorizontalAlignment(SwingConstants.LEFT);
        workoutsButton.setMargin(new Insets(5,25,5,5));
        workoutsButton.setMinimumHeight(50);
        workoutsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Main.setWindow("WorkoutLibrary" );
            }
        });

        FlatButton goalsButton = new FlatButton();
        goalsButton.setBorderPainted(false);
        goalsButton.setText("Goals");
        goalsButton.setIcon(sideMenuViewModel.getIcon("contact"));
        goalsButton.setHorizontalAlignment(SwingConstants.LEFT);
        goalsButton.setMargin(new Insets(5,25,5,5));
        goalsButton.setMinimumHeight(50);
        goalsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.setWindow("GoalsPage");
            }
        });

        FlatButton statisticsButton = new FlatButton();
        statisticsButton.setBorderPainted(false);
        statisticsButton.setText("Statistics");
        statisticsButton.setIcon(sideMenuViewModel.getIcon("chart-evaluation"));
        statisticsButton.setHorizontalAlignment(SwingConstants.LEFT);
        statisticsButton.setMargin(new Insets(5,25,5,5));
        statisticsButton.setMinimumHeight(50);
        statisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Main.setWindow("StatsPage" );
            }
        });

        FlatButton exerciseLibraryButton = new FlatButton();
        exerciseLibraryButton.setBorderPainted(false);
        exerciseLibraryButton.setText("Exercise Library");
        exerciseLibraryButton.setIcon(sideMenuViewModel.getIcon("chart-evaluation"));
        exerciseLibraryButton.setHorizontalAlignment(SwingConstants.LEFT);
        exerciseLibraryButton.setMargin(new Insets(5,25,5,5));
        exerciseLibraryButton.setMinimumHeight(50);
        exerciseLibraryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Main.setWindow("ExerciseLibrary" );
            }
        });

        FlatButton shopButton = new FlatButton();
        shopButton.setBorderPainted(false);
        shopButton.setText("Shop");
        shopButton.setIcon(sideMenuViewModel.getIcon("shopping-cart"));
        shopButton.setHorizontalAlignment(SwingConstants.LEFT);
        shopButton.setMargin(new Insets(5,25,5,5));
        shopButton.setMinimumHeight(50);
        shopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Main.setWindow("currencyShopPage" );
            }
        });

        FlatLabel accountLabel = new FlatLabel();
        accountLabel.setText("Account");
        accountLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground");

        FlatButton profileButton = new FlatButton();
        profileButton.setBorderPainted(false);
        profileButton.setText("Profile");
        profileButton.setIcon(sideMenuViewModel.getIcon("user-circle"));
        profileButton.setHorizontalAlignment(SwingConstants.LEFT);
        profileButton.setMargin(new Insets(5,25,5,5));

        FlatButton settingsButton = new FlatButton();
        settingsButton.setBorderPainted(false);
        settingsButton.setText("Settings");
        settingsButton.setIcon(sideMenuViewModel.getIcon("setting"));
        settingsButton.setHorizontalAlignment(SwingConstants.LEFT);
        settingsButton.setMargin(new Insets(5,25,5,5));
        settingsButton.setMinimumHeight(50);
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Main.setWindow("SettingsPage" );
            }
        });

        FlatButton logOutButton = new FlatButton();
        logOutButton.setBorderPainted(false);
        logOutButton.setText("Log Out");
        logOutButton.setIcon(sideMenuViewModel.getIcon("logout"));
        logOutButton.setHorizontalAlignment(SwingConstants.LEFT);
        logOutButton.setMargin(new Insets(5,25,5,5));
        logOutButton.setMinimumHeight(50);
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Main.setWindow("LoginPage");
            }
        });

        add(title, "wrap, gapy 10");
        add(generalLabel, "wrap, gap 0 0 10 0");
        add(homeButton, "wrap, gapy 0");
        add(goalsButton, "wrap, gapy 10");
        add(workoutsButton, "wrap, gapy 10");
        add(statisticsButton, "wrap, gapy 10");
        add(exerciseLibraryButton, "wrap, gapy 10");
        add(shopButton, "wrap, gapy 10");
        add(accountLabel, "wrap, gap 0 0 10 0");
        add(profileButton, "wrap, gapy 0");
        add(settingsButton, "wrap, gapy 10");
        add(new JLabel(), "wrap, growy, pushy");
        add(logOutButton, "wrap, gapy 0");
    }

}
