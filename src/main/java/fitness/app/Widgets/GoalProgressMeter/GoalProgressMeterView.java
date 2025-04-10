package fitness.app.Widgets.GoalProgressMeter;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.components.FlatProgressBar;
import com.formdev.flatlaf.fonts.roboto_mono.FlatRobotoMonoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.ui.FlatProgressBarUI;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;

public class GoalProgressMeterView extends JPanel {
    public GoalProgressMeterView() {
        setLayout(new MigLayout("wrap,fillx,insets 30", "fill,275"));
        putClientProperty(FlatClientProperties.STYLE, "arc:20; background:lighten(@background,5%)");
        for (int i = 1; i <= 5; ++i) {
            add(new JLabel("Goal " + i), "wrap, gapy 5");
            FlatProgressBar progressBar = new FlatProgressBar();
            progressBar.setValue((int)(Math.random() * (101)));
            //progressBar.setForeground(Color.RED);
            add(progressBar, "wrap");
        }
    }

    public static void main(String[] args) {
        System.setProperty("apple.awt.application.name", "Rocket Health");
        System.setProperty("apple.awt.application.appearance", "system");

        FlatRobotoMonoFont.install();
        FlatLaf.registerCustomDefaultsSource("FlatLafSettings");
        UIManager.put("defaultFont", new Font(FlatRobotoMonoFont.FAMILY, Font.PLAIN, 13));
        FlatMacDarkLaf.setup();

        JFrame window = new JFrame("Rocket Health");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(new Dimension(1200, 700));
        window.setLocationRelativeTo(null);
        window.getRootPane().putClientProperty("apple.awt.transparentTitleBar", true);
        window.getRootPane().putClientProperty("apple.awt.windowTitleVisible", false);

        JPanel panel = new JPanel(new MigLayout("fill,insets 20", "center", "center"));
        panel.add(new GoalProgressMeterView());
        window.add(panel);

        window.setVisible(true);
    }
}
