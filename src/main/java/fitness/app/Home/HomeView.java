package fitness.app.Home;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;
import fitness.app.Main;
import fitness.app.Statistics.StatsModel;
import fitness.app.Main;
import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.swing.JLabel.*;

//Implement menu bar as seperate method to abstract?

public class HomeView {
    private static JPanel mainPanel;

    public HomeView() {
        mainPanel = new JPanel(new MigLayout("filly"));

        //Button Menu
        JPanel menu = new JPanel(new MigLayout("fillx,insets 10", "fill, 125"));
        menu.putClientProperty(FlatClientProperties.STYLE, "arc:20;" + "background:lighten(@background,5%)");

        JLabel title = new JLabel("Rocket Health", HomeViewModel.getIcon("rocket"), JLabel.LEFT);
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +6");

        JLabel generalLabel = new JLabel();
        generalLabel.setText("General");
        generalLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:darken(@foreground,33%)");

        FlatButton homeButton = new FlatButton();
        homeButton.setText("Home");
        homeButton.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%);");
        homeButton.setIcon(HomeViewModel.getIcon("home"));

        FlatButton workoutsButton = new FlatButton();
        workoutsButton.setText("Workouts");
        workoutsButton.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%);");
        workoutsButton.setIcon(HomeViewModel.getIcon("workouts"));

        FlatButton goalsButton = new FlatButton();
        goalsButton.setText("Goals");
        goalsButton.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%);");
        goalsButton.setIcon(HomeViewModel.getIcon("goals"));
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
        statisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Main.setWindow("StatsPage" );
            }
        });

        FlatButton shopButton = new FlatButton();
        shopButton.setText("Buy Rocket Bucks");
        shopButton.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%);");
        //shopButton.setIcon(HomeViewModel.getIcon("buy Rocket Bucks"));
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

        FlatButton settingsButton = new FlatButton();
        settingsButton.setText("Settings");
        settingsButton.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%);");
        settingsButton.setIcon(HomeViewModel.getIcon("settings"));
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
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Main.setWindow("LoginPage");
            }
        });


        menu.add(title, "wrap, gapy 10");
        menu.add(generalLabel, "wrap, gap 0 0 10 0");
        menu.add(homeButton, "wrap, gapy 0");
        menu.add(goalsButton, "wrap, gapy 10");
        menu.add(workoutsButton, "wrap, gapy 10");
        menu.add(statisticsButton, "wrap, gapy 10");
        menu.add(shopButton, "wrap, gapy 10");
        menu.add(accountLabel, "wrap, gap 0 0 10 0");
        menu.add(profileButton, "wrap, gapy 0");
        menu.add(settingsButton, "wrap, gapy 10");
        menu.add(new JLabel(), "wrap, pushy");
        menu.add(logOutButton, "wrap, gapy 10");

        mainPanel.add(menu, "span, growy, gap 10 10 10 10");
    }

    public static JPanel getMainPanel() {
        return mainPanel;
    }
}
