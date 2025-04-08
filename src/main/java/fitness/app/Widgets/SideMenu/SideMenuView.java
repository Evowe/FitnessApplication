package fitness.app.Widgets.SideMenu;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;
import fitness.app.Home.HomeViewModel;
import fitness.app.Main;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
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
        homeButton.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%);");
        homeButton.setIcon(HomeViewModel.getIcon("home"));
        homeButton.setMinimumHeight(35);
        homeButton.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%);");
        // Create a border with proper padding on the left
        homeButton.setBorder(BorderFactory.createEmptyBorder(5, 25, 5, 5));
        // Set alignment properties
        homeButton.setIconTextGap(10);
        homeButton.setHorizontalAlignment(SwingConstants.LEFT);
        homeButton.setHorizontalTextPosition(SwingConstants.RIGHT);

        FlatButton workoutsButton = new FlatButton();
        workoutsButton.setText("Workouts");
        workoutsButton.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%);");
        workoutsButton.setIcon(HomeViewModel.getIcon("workouts"));
        workoutsButton.setMinimumHeight(35);
        workoutsButton.setBorder(BorderFactory.createEmptyBorder(5, 25, 5, 5));
        workoutsButton.setIconTextGap(10);
        workoutsButton.setHorizontalAlignment(SwingConstants.LEFT);
        workoutsButton.setHorizontalTextPosition(SwingConstants.RIGHT);

        FlatButton goalsButton = new FlatButton();
        goalsButton.setText("Goals");
        goalsButton.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%);");
        goalsButton.setIcon(HomeViewModel.getIcon("goals"));
        goalsButton.setMinimumHeight(35);
        goalsButton.setBorder(BorderFactory.createEmptyBorder(5, 25, 5, 5));
        goalsButton.setIconTextGap(10);
        goalsButton.setHorizontalAlignment(SwingConstants.LEFT);
        goalsButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        goalsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.setWindow("GoalsPage");
            }
        });

        FlatButton statisticsButton = new FlatButton();
        statisticsButton.setText("Statistics");
        statisticsButton.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%);");
        statisticsButton.setIcon(HomeViewModel.getIcon("statistics"));
        statisticsButton.setMinimumHeight(35);
        statisticsButton.setBorder(BorderFactory.createEmptyBorder(5, 25, 5, 5));
        statisticsButton.setIconTextGap(10);
        statisticsButton.setHorizontalAlignment(SwingConstants.LEFT);
        statisticsButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        statisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Main.setWindow("StatsPage" );
            }
        });

        FlatButton shopButton = new FlatButton();
        shopButton.setText("Shop");
        shopButton.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%);");
        shopButton.setIcon(HomeViewModel.getIcon("Cart"));
        shopButton.setMinimumHeight(35);
        shopButton.setBorder(BorderFactory.createEmptyBorder(5, 25, 5, 5));
        shopButton.setIconTextGap(10);
        shopButton.setHorizontalAlignment(SwingConstants.LEFT);
        shopButton.setHorizontalTextPosition(SwingConstants.RIGHT);
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
        profileButton.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%);");
        profileButton.setIcon(HomeViewModel.getIcon("profile"));
        profileButton.setMinimumHeight(35);
        profileButton.setBorder(BorderFactory.createEmptyBorder(5, 25, 5, 5));
        profileButton.setIconTextGap(10);
        profileButton.setHorizontalAlignment(SwingConstants.LEFT);
        profileButton.setHorizontalTextPosition(SwingConstants.RIGHT);

        FlatButton settingsButton = new FlatButton();
        settingsButton.setText("Settings");
        settingsButton.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%);");
        settingsButton.setIcon(HomeViewModel.getIcon("settings"));
        settingsButton.setMinimumHeight(35);
        settingsButton.setBorder(BorderFactory.createEmptyBorder(5, 25, 5, 5));
        settingsButton.setIconTextGap(10);
        settingsButton.setHorizontalAlignment(SwingConstants.LEFT);
        settingsButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Main.setWindow("SettingsPage" );
            }
        });

        FlatButton logOutButton = new FlatButton();
        logOutButton.setText("Log Out");
        logOutButton.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%);");
        logOutButton.setIcon(HomeViewModel.getIcon("log-out"));
        logOutButton.setMinimumHeight(35);
        logOutButton.setBorder(BorderFactory.createEmptyBorder(5, 25, 5, 5));
        logOutButton.setIconTextGap(10);
        logOutButton.setHorizontalAlignment(SwingConstants.LEFT);
        logOutButton.setHorizontalTextPosition(SwingConstants.RIGHT);
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
