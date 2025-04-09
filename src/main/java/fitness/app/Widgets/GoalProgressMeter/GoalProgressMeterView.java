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
    public class CircularProgressBar extends FlatProgressBarUI {
        @Override
        public void paint(Graphics g, JComponent c) {
            int width = c.getWidth();
            int height = c.getHeight();
            int size = Math.min(width, height);
            int thickness = 10;

            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int outerDiameter = size - thickness;
                int offsetX = (width - outerDiameter) / 2;
                int offsetY = (height - outerDiameter) / 2;

                g2.setColor(progressBar.getBackground());
                g2.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.draw(new Arc2D.Double(offsetX, offsetY, outerDiameter, outerDiameter, 90, 360, Arc2D.OPEN));

                int value = progressBar.getValue();
                int max = progressBar.getMaximum();
                double angle = 360.0 * value / max;

                g2.setColor(progressBar.getForeground());
                g2.draw(new Arc2D.Double(offsetX, offsetY, outerDiameter, outerDiameter, 90, -angle, Arc2D.OPEN));

                if (progressBar.isStringPainted()) {
                    String text = progressBar.getString();
                    g2.setFont(c.getFont().deriveFont(Font.BOLD, size / 5f));
                    FontMetrics fm = g2.getFontMetrics();
                    int textWidth = fm.stringWidth(text);
                    int textHeight = fm.getAscent();
                    g2.setColor(Color.WHITE);
                    g2.drawString(text, width / 2 - textWidth / 2, height / 2 + textHeight / 4);
                }
            } finally {
                g2.dispose();
            }
        }
    }

    public GoalProgressMeterView() {
        setLayout(new MigLayout("fill,insets 20 20 15 20"));
        putClientProperty(FlatClientProperties.STYLE, "arc:20; background:lighten(@background,5%)");

        FlatProgressBar progressBar1 = new FlatProgressBar();
        progressBar1.setUI(new CircularProgressBar());
        progressBar1.setPreferredSize(new Dimension(75, 75));
        progressBar1.setMaximum(100);
        progressBar1.setValue(63);
        progressBar1.setStringPainted(true);
        progressBar1.setString(progressBar1.getValue() + "%");
        progressBar1.setForeground(Color.RED);

        JLabel progressLabel1 = new JLabel("1550 / 2460 Calories");
        progressLabel1.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");

        add(progressBar1, "gapy 10");
        add(progressLabel1, "gapx 10, wrap");

        FlatProgressBar progressBar2 = new FlatProgressBar();
        progressBar2.setUI(new CircularProgressBar());
        progressBar2.setPreferredSize(new Dimension(75, 75));
        progressBar2.setMaximum(100);
        progressBar2.setValue(38);
        progressBar2.setStringPainted(true);
        progressBar2.setString(progressBar2.getValue() + "%");
        progressBar2.setForeground(Color.RED);

        JLabel progressLabel2 = new JLabel("3843 / 10,000 Steps");
        progressLabel2.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");

        add(progressBar2, "gapy 20");
        add(progressLabel2, "gapx 10, wrap");

        FlatProgressBar progressBar3 = new FlatProgressBar();
        progressBar3.setUI(new CircularProgressBar());
        progressBar3.setPreferredSize(new Dimension(75, 75));
        progressBar3.setMaximum(100);
        progressBar3.setValue(20);
        progressBar3.setStringPainted(true);
        progressBar3.setString(progressBar3.getValue() + "%");
        progressBar3.setForeground(Color.RED);

        JLabel progressLabel3 = new JLabel("1 / 5 Workouts");
        progressLabel3.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");

        add(progressBar3, "gapy 20");
        add(progressLabel3, "gapx 10, wrap");
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
