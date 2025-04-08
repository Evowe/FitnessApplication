package fitness.app.Home;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;
import fitness.app.Main;
import fitness.app.Statistics.StatsModel;
import fitness.app.Main;
import fitness.app.Widgets.SideMenu.SideMenuView;
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
        mainPanel = new JPanel();
        mainPanel.setLayout(new MigLayout());

        mainPanel.add(new SideMenuView(), "growy, pushy");
    }


    public static JPanel getMainPanel() {
        return mainPanel;
    }
}
