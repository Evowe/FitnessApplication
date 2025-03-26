package fitness.app.Home;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class HomeView {
    private static JPanel mainPanel;
    private static JLabel title;

    public HomeView() {
        mainPanel = new JPanel(new GridLayout(1,1));
        title = new JLabel("Pls implement");

        title.setForeground(Color.WHITE);
        title.putClientProperty(FlatClientProperties.STYLE, "" + "font:+20");

        mainPanel.add(title);
    }

    public static JPanel getMainPanel() {
        return mainPanel;
    }
}
