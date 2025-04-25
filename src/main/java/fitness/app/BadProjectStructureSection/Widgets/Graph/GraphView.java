package fitness.app.BadProjectStructureSection.Widgets.Graph;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto_mono.FlatRobotoMonoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GraphView extends JFrame {
    JPanel panel = new JPanel();
    public GraphView(ArrayList<Integer> x, ArrayList<Integer> y, String xAxis, String yAxis, String title) {
        FlatRobotoMonoFont.install();
        FlatLaf.registerCustomDefaultsSource("Components.Themes");
        UIManager.put("defaultFont", new Font(FlatRobotoMonoFont.FAMILY, Font.PLAIN, 13));
        FlatMacDarkLaf.setup();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        int[] x = {1,2,3,4,5,6,7,8,9,10};
//        int[] y = {1,2,3,4,5,6,7,8,9,10};
        for(int i=0; i<x.size(); i++) {
            dataset.addValue(y.get(i),yAxis, Integer.toString(x.get(i)));
        }
//        dataset.addValue(1, "Series1", "2020");
//        dataset.addValue(3, "Series1", "2021");
//        dataset.addValue(2, "Series1", "2022");

        JFreeChart chart = ChartFactory.createLineChart(
                title,
                xAxis,
                yAxis,
                dataset
        );
        Color bg = UIManager.getColor("Panel.background");

        chart.setBackgroundPaint(bg);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(bg);
        plot.setDomainGridlinePaint(Color.GRAY);
        plot.setRangeGridlinePaint(Color.GRAY);

        // Axis label and tick styling
        plot.getDomainAxis().setTickLabelPaint(Color.WHITE);
        plot.getDomainAxis().setLabelPaint(Color.LIGHT_GRAY);
        plot.getRangeAxis().setTickLabelPaint(Color.WHITE);
        plot.getRangeAxis().setLabelPaint(Color.LIGHT_GRAY);

        chart.getTitle().setPaint(Color.WHITE);

        chart.getLegend().setBackgroundPaint(bg);
        chart.getLegend().setItemPaint(Color.LIGHT_GRAY);
        // Line color and style
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(255,45,45)); // Bright orange for fitness theme
        renderer.setDefaultStroke(new BasicStroke(2f));
        //setContentPane(panel);
        panel = new ChartPanel(chart);
        //setContentPane(panel);
    }
    public JPanel getPanel() {
        return panel;
    }
}
