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

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/main/resources/Images/RocketHealthLogo.PNG"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image scaledImg = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaledImg);

        JLabel title = new JLabel("Rocket Health", icon, JLabel.LEFT);
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +6");

        FlatButton homeButton = new FlatButton();
        homeButton.setText("Home");
        homeButton.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%);");

        FlatButton statisticsButton = new FlatButton();
        statisticsButton.setText("Statistics");
        statisticsButton.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%);");

        FlatButton classesButton = new FlatButton();
        classesButton.setText("Classes");
        classesButton.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%);");

        FlatButton workoutsButton = new FlatButton();
        workoutsButton.setText("Workouts");
        workoutsButton.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%);");

        FlatButton logOutButton = new FlatButton();
        logOutButton.setText("Log Out");
        logOutButton.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%);");

        menu.add(title, "wrap, gapy 10");
        menu.add(homeButton, "wrap, gapy 10");
        menu.add(statisticsButton, "wrap, gapy 10");
        menu.add(classesButton, "wrap, gapy 10");
        menu.add(workoutsButton, "wrap, gapy 10");

        menu.add(new JLabel(), "wrap, pushy");
        menu.add(logOutButton, "wrap, gapy 10");

        mainPanel.add(menu, "span, growy, gap 10 10 10 10");
    }

    public static JPanel getMainPanel() {
        return mainPanel;
    }
}
