package fitness.app.Widgets.SideMenu;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;
import fitness.app.Home.HomeViewModel;
import fitness.app.Main;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SideMenuView extends JPanel {

    public SideMenuView() {
        //Button Menu
        setLayout(new MigLayout("fillx,insets 10", "fill, 125"));
        putClientProperty(FlatClientProperties.STYLE, "arc:20;" + "background:lighten(@background,5%)");

        JLabel title = new JLabel("Rocket Health", HomeViewModel.getIcon("rocket"), JLabel.LEFT);
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +6");

        JLabel generalLabel = new JLabel();
        generalLabel.setText("General");
        generalLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:darken(@foreground,33%)");

        FlatButton homeButton = new FlatButton();
        homeButton.setText("Home");
        homeButton.putClientProperty(FlatClientProperties.STYLE, "arc: 20; background:lighten(@background,10%);");
        homeButton.setIcon(HomeViewModel.getIcon("home"));
        homeButton.setHorizontalAlignment(SwingConstants.LEFT);
        homeButton.setMargin(new Insets(5, 25, 5, 5));
        homeButton.setMinimumHeight(35);
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.setWindow("HomePage");
            }
        });

        FlatButton workoutsButton = new FlatButton();
        workoutsButton.setText("Workouts");
        workoutsButton.putClientProperty(FlatClientProperties.STYLE, "arc: 20; background: lighten(@background,10%);");
        workoutsButton.setIcon(HomeViewModel.getIcon("workouts"));
        workoutsButton.setHorizontalAlignment(SwingConstants.LEFT);
        workoutsButton.setMargin(new Insets(5,25,5,5));
        workoutsButton.setMinimumHeight(35);

        FlatButton goalsButton = new FlatButton();
        goalsButton.setText("Goals");
        goalsButton.putClientProperty(FlatClientProperties.STYLE, "arc: 20; background:lighten(@background,10%);");
        goalsButton.setIcon(HomeViewModel.getIcon("goals"));
        goalsButton.setHorizontalAlignment(SwingConstants.LEFT);
        goalsButton.setMargin(new Insets(5,25,5,5));
        goalsButton.setMinimumHeight(35);
        goalsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.setWindow("GoalsPage");
            }
        });

        FlatButton statisticsButton = new FlatButton();
        statisticsButton.setText("Statistics");
        statisticsButton.putClientProperty(FlatClientProperties.STYLE, "arc: 20; background:lighten(@background,10%);");
        statisticsButton.setIcon(HomeViewModel.getIcon("statistics"));
        statisticsButton.setHorizontalAlignment(SwingConstants.LEFT);
        statisticsButton.setMargin(new Insets(5,25,5,5));
        statisticsButton.setMinimumHeight(35);
        statisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Main.setWindow("StatsPage" );
            }
        });

        FlatButton shopButton = new FlatButton();
        shopButton.setText("Shop");
        shopButton.putClientProperty(FlatClientProperties.STYLE, "arc: 20; background:lighten(@background,10%);");
        shopButton.setIcon(HomeViewModel.getIcon("Cart"));
        shopButton.setHorizontalAlignment(SwingConstants.LEFT);
        shopButton.setMargin(new Insets(5,25,5,5));
        shopButton.setMinimumHeight(35);
        shopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Main.setWindow("currencyShopPage" );
            }
        });

        FlatLabel accountLabel = new FlatLabel();
        accountLabel.setText("Account");
        accountLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:darken(@foreground,33%)");

        FlatButton profileButton = new FlatButton();
        profileButton.setText("Profile");
        profileButton.putClientProperty(FlatClientProperties.STYLE, "arc: 20; background:lighten(@background,10%);");
        profileButton.setIcon(HomeViewModel.getIcon("profile"));
        profileButton.setHorizontalAlignment(SwingConstants.LEFT);
        profileButton.setMargin(new Insets(5,25,5,5));
        profileButton.setMinimumHeight(35);

        FlatButton settingsButton = new FlatButton();
        settingsButton.setText("Settings");
        settingsButton.putClientProperty(FlatClientProperties.STYLE, "arc: 20; background:lighten(@background,10%);");
        settingsButton.setIcon(HomeViewModel.getIcon("settings"));
        settingsButton.setHorizontalAlignment(SwingConstants.LEFT);
        settingsButton.setMargin(new Insets(5,25,5,5));
        settingsButton.setMinimumHeight(35);
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Main.setWindow("SettingsPage" );
            }
        });

        FlatButton logOutButton = new FlatButton();
        logOutButton.setText("Log Out");
        logOutButton.putClientProperty(FlatClientProperties.STYLE, "arc: 20; background:lighten(@background,10%);");
        logOutButton.setIcon(HomeViewModel.getIcon("log-out"));
        logOutButton.setHorizontalAlignment(SwingConstants.LEFT);
        logOutButton.setMargin(new Insets(5,25,5,5));
        logOutButton.setMinimumHeight(35);
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
        add(shopButton, "wrap, gapy 10");
        add(accountLabel, "wrap, gap 0 0 10 0");
        add(profileButton, "wrap, gapy 0");
        add(settingsButton, "wrap, gapy 10");
        add(new JLabel(), "wrap, growy, pushy");
        add(logOutButton, "wrap, gapy 10");
    }

}
