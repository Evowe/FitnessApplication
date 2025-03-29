package fitness.app.Home;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;
import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.swing.JLabel.*;

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

        FlatButton classesButton = new FlatButton();
        classesButton.setText("Classes");
        classesButton.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%);");
        classesButton.setIcon(HomeViewModel.getIcon("classes"));

        FlatButton workoutsButton = new FlatButton();
        workoutsButton.setText("Workouts");
        workoutsButton.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%);");
        workoutsButton.setIcon(HomeViewModel.getIcon("workouts"));

        FlatButton statisticsButton = new FlatButton();
        statisticsButton.setText("Statistics");
        statisticsButton.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%);");
        statisticsButton.setIcon(HomeViewModel.getIcon("statistics"));

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

        FlatButton logOutButton = new FlatButton();
        logOutButton.setText("Log Out");
        logOutButton.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%);");
        logOutButton.setIcon(HomeViewModel.getIcon("log-out"));

        menu.add(title, "wrap, gapy 10");
        menu.add(generalLabel, "wrap, gap 0 0 10 0");
        menu.add(homeButton, "wrap, gapy 0");
        menu.add(classesButton, "wrap, gapy 10");
        menu.add(workoutsButton, "wrap, gapy 10");
        menu.add(statisticsButton, "wrap, gapy 10");
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
