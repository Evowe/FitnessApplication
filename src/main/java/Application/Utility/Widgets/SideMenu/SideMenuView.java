package Application.Utility.Widgets.SideMenu;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;
import Application.Main;
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
        homeButton.addActionListener(genButtonListener("HomePage"));


        FlatButton workoutsButton = new FlatButton();
        workoutsButton.setBorderPainted(false);
        workoutsButton.setText("Workouts");
        workoutsButton.setIcon(sideMenuViewModel.getIcon("workout-run"));
        workoutsButton.setHorizontalAlignment(SwingConstants.LEFT);
        workoutsButton.setMargin(new Insets(5,25,5,5));
        workoutsButton.setMinimumHeight(50);
        workoutsButton.addActionListener(genButtonListener("Workout"));


        FlatButton statisticsButton = new FlatButton();
        statisticsButton.setBorderPainted(false);
        statisticsButton.setText("Metrics");
        statisticsButton.setIcon(sideMenuViewModel.getIcon("chart-evaluation"));
        statisticsButton.setHorizontalAlignment(SwingConstants.LEFT);
        statisticsButton.setMargin(new Insets(5,25,5,5));
        statisticsButton.setMinimumHeight(50);
        statisticsButton.addActionListener(genButtonListener("MetricsPage"));


        FlatButton socialViewButton = new FlatButton();
        socialViewButton.setBorderPainted(false);
        socialViewButton.setText("Social");
        socialViewButton.setIcon(sideMenuViewModel.getIcon("user-circle"));
        socialViewButton.setHorizontalAlignment(SwingConstants.LEFT);
        socialViewButton.setMargin(new Insets(5,25,5,5));
        socialViewButton.setMinimumHeight(50);
        socialViewButton.addActionListener(genButtonListener("SocialView"));

        FlatButton shopButton = new FlatButton();
        shopButton.setBorderPainted(false);
        shopButton.setText("Shop");
        shopButton.setIcon(sideMenuViewModel.getIcon("shopping-cart"));
        shopButton.setHorizontalAlignment(SwingConstants.LEFT);
        shopButton.setMargin(new Insets(5,25,5,5));
        shopButton.setMinimumHeight(50);
        shopButton.addActionListener(genButtonListener("currencyShopPage"));

        FlatButton BPButton = new FlatButton();
        BPButton.setBorderPainted(false);
        BPButton.setText("Battle Pass");
        BPButton.setIcon(sideMenuViewModel.getIcon("equipment-gym"));
        BPButton.setHorizontalAlignment(SwingConstants.LEFT);
        BPButton.setMargin(new Insets(5,25,5,5));
        BPButton.setMinimumHeight(50);
        BPButton.addActionListener(genButtonListener("BattlePass"));


        FlatLabel accountLabel = new FlatLabel();
        accountLabel.setText("Account");
        accountLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground");

        FlatButton profileButton = new FlatButton();
        profileButton.setBorderPainted(false);
        profileButton.setText("Profile");
        profileButton.setIcon(sideMenuViewModel.getIcon("user-circle"));
        profileButton.setHorizontalAlignment(SwingConstants.LEFT);
        profileButton.setMargin(new Insets(5,25,5,5));
        shopButton.setMinimumHeight(50);
        profileButton.addActionListener(genButtonListener("Locker"));

        FlatButton settingsButton = new FlatButton();
        settingsButton.setBorderPainted(false);
        settingsButton.setText("Settings");
        settingsButton.setIcon(sideMenuViewModel.getIcon("setting"));
        settingsButton.setHorizontalAlignment(SwingConstants.LEFT);
        settingsButton.setMargin(new Insets(5,25,5,5));
        settingsButton.setMinimumHeight(50);
        settingsButton.addActionListener(genButtonListener("SettingsPage"));


        FlatButton logOutButton = new FlatButton();
        logOutButton.setBorderPainted(false);
        logOutButton.setText("Log Out");
        logOutButton.setIcon(sideMenuViewModel.getIcon("logout"));
        logOutButton.setHorizontalAlignment(SwingConstants.LEFT);
        logOutButton.setMargin(new Insets(5,25,5,5));
        logOutButton.setMinimumHeight(50);
        logOutButton.addActionListener(genButtonListener("LoginPage"));

        add(title, "wrap, gapy 10");
        add(generalLabel, "wrap, gap 0 0 10 0");
        add(homeButton, "wrap, gapy 0");
        add(workoutsButton, "wrap, gapy 10");
        add(statisticsButton, "wrap, gapy 10");
        add(socialViewButton, "wrap, gapy 10");
        add(shopButton, "wrap, gapy 10");
        add(BPButton, "wrap, gapy 10");
        add(accountLabel, "wrap, gap 0 0 10 0");
        add(profileButton, "wrap, gapy 10");
        add(settingsButton, "wrap, gapy 10");
        add(new JLabel(), "wrap, growy, pushy");
        add(logOutButton, "wrap, gapy 0");
    }

    private ActionListener genButtonListener(String page) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.setWindow(page);
                System.out.println();
            }
        };
    }
}
