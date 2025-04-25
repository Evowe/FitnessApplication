package fitness.app.Metrics.Statistics;

import fitness.app.BadProjectStructureSection.Objects.Account;
import fitness.app.BadProjectStructureSection.Widgets.Graph.GraphView;
import fitness.app.BadProjectStructureSection.Widgets.SideMenu.SideMenuView;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StatsView extends JPanel {
    private static JPanel mainPanel;
    private StatsViewModel viewModel;
    private GraphView calorieGraph;
    private GraphView sleepGraph;
    private GraphView weightGraph;
    private JPanel statsPanel;

    public StatsView(Account acc) {
        setLayout(new MigLayout("fill, insets 20"));
        putClientProperty(FlatClientProperties.STYLE, "background:@background");

        viewModel = new StatsViewModel(acc);
        viewModel.loadTestData(10);

        mainPanel = new JPanel(new MigLayout("fill", "[grow]", "[grow]"));
        mainPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background" );

        JPanel menuPanel = new JPanel(new MigLayout("insets 0"));
        menuPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        menuPanel.add(new SideMenuView(), "growy, pushy");

        statsPanel = new JPanel(new MigLayout("wrap 3", "[grow][grow][grow]", "[]"));
        statsPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");

        //DISPLAY CALORIES
        JPanel calDis = new JPanel(new MigLayout("fill,insets 20", "left", "Top"));
        calDis.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        JPanel CdisMen = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,275"));
        CdisMen.putClientProperty(FlatClientProperties.STYLE, "arc:20;");
        JLabel cdistitle = new JLabel("Daily Calories: " + acc.getCalories());
        cdistitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +6");
        CdisMen.add(cdistitle);
        calDis.add(CdisMen);
        statsPanel.add(calDis);

        //DISPLAY SLEEP
        JPanel sleepDis = new JPanel(new MigLayout("fill,insets 20", "center", "Top"));
        sleepDis.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        JPanel SleepDisMen = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,275"));
        SleepDisMen.putClientProperty(FlatClientProperties.STYLE, "arc:20;");
        JLabel Sdistitle = new JLabel("Total Sleep: " + acc.getSleep());
        Sdistitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +6");
        SleepDisMen.add(Sdistitle);
        sleepDis.add(SleepDisMen);
        statsPanel.add(sleepDis);

        //WEIGHT DISPLAY
        JPanel weightDis = new JPanel(new MigLayout("fill,insets 20", "center", "Top"));
        weightDis.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        JPanel weightDisMen = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,275"));
        weightDisMen.putClientProperty(FlatClientProperties.STYLE, "arc:20;");
        JLabel Wdistitle = new JLabel("Current Weight: " + acc.getWeight());
        Wdistitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +6");
        weightDisMen.add(Wdistitle);
        weightDis.add(weightDisMen);
        statsPanel.add(weightDis);

        //Graphs - Creating graph panels
        JPanel cg = new JPanel(new MigLayout("fill,insets 20", "Left", "Center"));
        cg.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        JPanel cgm = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,275"));
        cgm.putClientProperty(FlatClientProperties.STYLE, "arc:20;");
        calorieGraph = new GraphView(viewModel.getx(), viewModel.gety("calories"),"Day of the Month","Calories", "Calorie Graph");
        cgm.add(calorieGraph.getPanel());
        cg.add(cgm);
        statsPanel.add(cg);

        JPanel wg = new JPanel(new MigLayout("fill,insets 20", "Center", "Center"));
        wg.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        JPanel wgm = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,275"));
        wgm.putClientProperty(FlatClientProperties.STYLE, "arc:20;");
        sleepGraph = new GraphView(viewModel.getx(), viewModel.gety("sleep"),"Day of the Month","Sleep", "Sleep Graph");
        wgm.add(sleepGraph.getPanel());
        wg.add(wgm);
        statsPanel.add(wg);

        JPanel sg = new JPanel(new MigLayout("fill,insets 20", "Right", "Center"));
        sg.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        JPanel sgm = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,275"));
        sgm.putClientProperty(FlatClientProperties.STYLE, "arc:20;");
        weightGraph = new GraphView(viewModel.getx(), viewModel.gety("weight"),"Day of the Month","Weight", "Weight Graph");
        sgm.add(weightGraph.getPanel());
        sg.add(sgm);
        statsPanel.add(sg);

        //Calorie Panel
        JPanel calPanel = new JPanel(new MigLayout("fill,insets 20", "left", "bot"));
        calPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        JPanel CalMenu = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,275"));
        CalMenu.putClientProperty(FlatClientProperties.STYLE, "arc:20;");

        JLabel title = new JLabel("Update Calories");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +6");

        JLabel Cdescription = new JLabel("All fields required");
        Cdescription.putClientProperty(FlatClientProperties.STYLE, "foreground:@foreground");

        JTextField CaloriesField = new JTextField();
        CaloriesField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Calories");

        JButton csubmitButton = new JButton("Submit");
        csubmitButton.putClientProperty(FlatClientProperties.STYLE, "background:@accent;");
        csubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    int Calories = Integer.parseInt(CaloriesField.getText());
                    String errorMessage = viewModel.updateCalories(Calories);
                    if (errorMessage != null) {
                        CaloriesField.setText("");
                        Cdescription.setText(errorMessage);
                        Cdescription.setForeground(Color.RED);
                        Cdescription.putClientProperty(FlatClientProperties.STYLE, "font:-4");
                    }
                    else {
                        acc.setCalories(acc.getCalories() + Calories);
                        cdistitle.setText("Daily Calories: " + acc.getCalories());
                        Cdescription.setText("All fields required");
                        Cdescription.putClientProperty(FlatClientProperties.STYLE, "foreground:@foreground");
                        // Refresh the calorie graph
                        refreshGraph("calories");
                    }
                } catch (NumberFormatException e) {
                    CaloriesField.setText("");
                    Cdescription.setText("Please enter a valid number");
                    Cdescription.setForeground(Color.RED);
                    Cdescription.putClientProperty(FlatClientProperties.STYLE, "font:-4");
                }
            }
        });
        CalMenu.add(title);
        CalMenu.add(Cdescription, "gapy 10");
        CalMenu.add(CaloriesField, "gapy 10");
        CalMenu.add(csubmitButton);
        calPanel.add(CalMenu);
        statsPanel.add(calPanel);

        //SLEEP PANEL
        JPanel sleepPanel = new JPanel(new MigLayout("fill,insets 20", "center", "Bottom"));
        sleepPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        JPanel SMenu = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,275"));
        SMenu.putClientProperty(FlatClientProperties.STYLE, "arc:20;");

        JLabel Stitle = new JLabel("Update Sleep");
        Stitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +6");

        JLabel Sdescription = new JLabel("All fields required");
        Sdescription.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground");

        JTextField sleepField = new JTextField();
        sleepField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Sleep");

        JButton ssubmitButton = new JButton("Submit");
        ssubmitButton.putClientProperty(FlatClientProperties.STYLE, "background:@accent;");
        ssubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    Double sl = Double.parseDouble(sleepField.getText());
                    String errorMessage = viewModel.updateSleep(sl);
                    if (errorMessage != null) {
                        sleepField.setText("");
                        Sdescription.setText(errorMessage);
                        Sdescription.setForeground(Color.RED);
                        Sdescription.putClientProperty(FlatClientProperties.STYLE, "font:-4");
                    }
                    else {
                        acc.setSleep(sl);
                        Sdistitle.setText("Total Sleep: " + acc.getSleep());
                        Sdescription.setText("All fields required");
                        Sdescription.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground");
                        // Refresh the sleep graph
                        refreshGraph("sleep");
                    }
                } catch (NumberFormatException e) {
                    sleepField.setText("");
                    Sdescription.setText("Please enter a valid number");
                    Sdescription.setForeground(Color.RED);
                    Sdescription.putClientProperty(FlatClientProperties.STYLE, "font:-4");
                }
            }
        });
        SMenu.add(Stitle);
        SMenu.add(Sdescription, "gapy 10");
        SMenu.add(sleepField, "gapy 10");
        SMenu.add(ssubmitButton);
        sleepPanel.add(SMenu);
        statsPanel.add(sleepPanel);

        //Weight panel
        JPanel weightPanel = new JPanel(new MigLayout("fill,insets 15", "right", "Bottom"));
        weightPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        JPanel WMenu = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,275"));
        WMenu.putClientProperty(FlatClientProperties.STYLE, "arc:20;");

        JLabel Wtitle = new JLabel("Update Weight");
        Wtitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +6");

        JLabel Wdescription = new JLabel("All fields required");
        Wdescription.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground");

        JTextField weightField = new JTextField();
        weightField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Weight");

        JButton wsubmitButton = new JButton("Submit");
        wsubmitButton.putClientProperty(FlatClientProperties.STYLE, "background:@accent;");
        wsubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    Double weight = Double.parseDouble(weightField.getText());
                    String errorMessage = viewModel.updateWeight(weight);
                    if (errorMessage != null) {
                        weightField.setText("");
                        Wdescription.setText(errorMessage);
                        Wdescription.setForeground(Color.RED);
                        Wdescription.putClientProperty(FlatClientProperties.STYLE, "font:-4");
                    }
                    else {
                        acc.setWeight(weight);
                        Wdistitle.setText("Current Weight: " + acc.getWeight());
                        Wdescription.setText("All fields required");
                        Wdescription.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground");
                        refreshGraph("weight");
                    }
                } catch (NumberFormatException e) {
                    weightField.setText("");
                    Wdescription.setText("Please enter a valid number");
                    Wdescription.setForeground(Color.RED);
                    Wdescription.putClientProperty(FlatClientProperties.STYLE, "font:-4");
                }
            }
        });
        WMenu.add(Wtitle);
        WMenu.add(Wdescription, "gapy 10");
        WMenu.add(weightField, "gapy 10");
        WMenu.add(wsubmitButton);
        weightPanel.add(WMenu);
        statsPanel.add(weightPanel);

        mainPanel.add(statsPanel);

        add(menuPanel, "growy, pushy");
        add(mainPanel);
    }


    private void refreshGraph(String metric) {
        ArrayList<Integer> xData = viewModel.getx();
        ArrayList<Integer> yData = viewModel.gety(metric);

        switch(metric.toLowerCase()) {
            case "calories":
                calorieGraph = new GraphView(xData, yData, "Day", "Calories", "Calorie Graph");
                Container parent = calorieGraph.getPanel().getParent();
                if (parent != null) {
                    parent.removeAll();
                    parent.add(calorieGraph.getPanel());
                    parent.revalidate();
                    parent.repaint();
                }
                break;
            case "sleep":
                sleepGraph = new GraphView(xData, yData, "Day", "Sleep", "Sleep Graph");
                Container sleepParent = sleepGraph.getPanel().getParent();
                if (sleepParent != null) {
                    sleepParent.removeAll();
                    sleepParent.add(sleepGraph.getPanel());
                    sleepParent.revalidate();
                    sleepParent.repaint();
                }
                break;
            case "weight":
                weightGraph = new GraphView(xData, yData, "Day", "Weight", "Weight Graph");
                Container weightParent = weightGraph.getPanel().getParent();
                if (weightParent != null) {
                    weightParent.removeAll();
                    weightParent.add(weightGraph.getPanel());
                    weightParent.revalidate();
                    weightParent.repaint();
                }
                break;
        }
        statsPanel.revalidate();
        statsPanel.repaint();
    }

    public JPanel getViewPanel() {
        return mainPanel;
    }
}