package fitness.app.Statistics;

import com.kitfox.svg.Title;
import fitness.app.Login.LoginViewModel;
import fitness.app.Main;
import fitness.app.Objects.Account;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto_mono.FlatRobotoMonoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import fitness.app.Widgets.Graph.GraphView;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatsView extends JPanel {
    private static JFrame window;
    private static JPanel mainPanel;

    public StatsView(Account acc) {
        StatsViewModel viewModel = new StatsViewModel(acc);
        //FlatLaf setup & settings
        FlatRobotoMonoFont.install();
        FlatLaf.registerCustomDefaultsSource("Components.Themes");
        UIManager.put("defaultFont", new Font(FlatRobotoMonoFont.FAMILY, Font.PLAIN, 13));
        FlatMacDarkLaf.setup();

        //Application window
//        window = new JFrame("Update Calories");
//        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        window.setSize(new Dimension(1200, 700));
//        window.setLocationRelativeTo(null);

        mainPanel = new JPanel(new MigLayout("wrap 3", "[grow][grow][grow]", "[]"));
        //DISPLAY CALORIES
        JPanel calDis = new JPanel(new MigLayout("fill,insets 20", "left", "Top"));
        JPanel CdisMen = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,275"));
        CdisMen.putClientProperty(FlatClientProperties.STYLE, "" + "arc:20;" + "background:lighten(@background,5%)");
        JLabel cdistitle = new JLabel("Daily Calories: " + acc.getCalories());
        cdistitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +6");
        CdisMen.add(cdistitle);
        calDis.add(CdisMen);
        mainPanel.add(calDis);
        //DISPLAY SLEEP
        JPanel sleepDis = new JPanel(new MigLayout("fill,insets 20", "center", "Top"));
        JPanel SleepDisMen = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,275"));
        SleepDisMen.putClientProperty(FlatClientProperties.STYLE, "" + "arc:20;" + "background:lighten(@background,5%)");
        JLabel Sdistitle = new JLabel("Total Sleep: " + acc.getSleep());
        Sdistitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +6");
        SleepDisMen.add(Sdistitle);
        sleepDis.add(SleepDisMen);
        mainPanel.add(sleepDis);
        //WEIGHT DISPLAY
        JPanel weightDis = new JPanel(new MigLayout("fill,insets 20", "center", "Top"));
        JPanel weightDisMen = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,275"));
        weightDisMen.putClientProperty(FlatClientProperties.STYLE, "" + "arc:20;" + "background:lighten(@background,5%)");
        JLabel Wdistitle = new JLabel("Current Weight: " + acc.getWeight());
        Wdistitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +6");
        weightDisMen.add(Wdistitle);
        weightDis.add(weightDisMen);
        mainPanel.add(weightDis);
        //Graphs

        JPanel cg = new JPanel(new MigLayout("fill,insets 20", "Left", "Center"));
        JPanel cgm = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,275"));
        cgm.putClientProperty(FlatClientProperties.STYLE, "" + "arc:20;" + "background:lighten(@background,5%)");
        GraphView g = new GraphView(viewModel.getx(), viewModel.gety(),"Day","Calories", "Calorie Graph");
        cgm.add(g.getPanel());
        cg.add(cgm);
        //cg.setPreferredSize(new Dimension(700, 800));
        mainPanel.add(cg);

        JPanel wg = new JPanel(new MigLayout("fill,insets 20", "Center", "Center"));
        JPanel wgm = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,275"));
        wgm.putClientProperty(FlatClientProperties.STYLE, "" + "arc:20;" + "background:lighten(@background,5%)");
        GraphView wgg = new GraphView(viewModel.getx(), viewModel.gety(),"Day","Sleep", "Sleep Graph");
        wgm.add(wgg.getPanel());
        wg.add(wgm);
        //cg.setPreferredSize(new Dimension(700, 800));
        mainPanel.add(wg);

        JPanel sg = new JPanel(new MigLayout("fill,insets 20", "Right", "Center"));
        JPanel sgm = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,275"));
        sgm.putClientProperty(FlatClientProperties.STYLE, "" + "arc:20;" + "background:lighten(@background,5%)");
        GraphView sgg = new GraphView(viewModel.getx(), viewModel.gety(),"Day","Weight", "Weight Graph");
        sgm.add(sgg.getPanel());
        sg.add(sgm);
        //cg.setPreferredSize(new Dimension(700, 800));
        mainPanel.add(sg);

        //mainPanel.add (new JLabel(""));
        //mainPanel.add (new JLabel(""));
        //mainPanel.add (new JLabel(""));

        //Calorie Panel
        JPanel calPanel = new JPanel(new MigLayout("fill,insets 20", "left", "bot"));

        JPanel CalMenu = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,275"));
        CalMenu.putClientProperty(FlatClientProperties.STYLE, "" + "arc:20;" + "background:lighten(@background,5%)");

        JLabel title = new JLabel("Update Calories");
        title.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +6");

        JLabel Cdescription = new JLabel("All fields required");
        Cdescription.putClientProperty(FlatClientProperties.STYLE, "" + "foreground:darken(@foreground,33%)");

        //CalMenu.add(description);
//        CalMenu.add(new JLabel("Date"), "gapy 8");
//        CalMenu.add(dateField);

        JTextField CaloriesField = new JTextField();
        CaloriesField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Calories");

        JButton csubmitButton = new JButton("Submit");
        csubmitButton.putClientProperty(FlatClientProperties.STYLE, "" + "background:lighten(@background,10%);");
        csubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                int Calories = Integer.parseInt(CaloriesField.getText());
                String errorMessage = StatsModel.verifyCalories(Calories);
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
                    Cdescription.putClientProperty(FlatClientProperties.STYLE, "" + "foreground:darken(@foreground,33%)");
                }
            }
        });
        CalMenu.add(title);
        CalMenu.add(Cdescription, "gapy 10");
        CalMenu.add(CaloriesField, "gapy 10");
        CalMenu.add(csubmitButton);
        calPanel.add(CalMenu);
        mainPanel.add(calPanel);

        //SLEEP PANEL
        JPanel sleepPanel = new JPanel(new MigLayout("fill,insets 20", "center", "Bottom"));

        JPanel SMenu = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,275"));
        SMenu.putClientProperty(FlatClientProperties.STYLE, "" + "arc:20;" + "background:lighten(@background,5%)");

        JLabel Stitle = new JLabel("Update Sleep");
        Stitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +6");

        JLabel Sdescription = new JLabel("All fields required");
        Sdescription.putClientProperty(FlatClientProperties.STYLE, "" + "foreground:darken(@foreground,33%)");

        JTextField sleepField = new JTextField();
        sleepField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Sleep");

        JButton ssubmitButton = new JButton("Submit");
        ssubmitButton.putClientProperty(FlatClientProperties.STYLE, "" + "background:lighten(@background,10%);");
        ssubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Double sl = Double.parseDouble(sleepField.getText());
                String errorMessage = StatsModel.verifySleep(sl);
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
                    Sdescription.putClientProperty(FlatClientProperties.STYLE, "" + "foreground:darken(@foreground,33%)");
                }
            }
        });
        SMenu.add(Stitle);
        SMenu.add(Sdescription, "gapy 10");
        SMenu.add(sleepField, "gapy 10");
        SMenu.add(ssubmitButton);
        sleepPanel.add(SMenu);
        mainPanel.add(sleepPanel);
        //WEight panel

        JPanel weightPanel = new JPanel(new MigLayout("fill,insets 20", "right", "Bottom"));

        JPanel WMenu = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,275"));
        WMenu.putClientProperty(FlatClientProperties.STYLE, "" + "arc:20;" + "background:lighten(@background,5%)");

        JLabel Wtitle = new JLabel("Update Weight");
        Wtitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +6");

        JLabel Wdescription = new JLabel("All fields required");
        Wdescription.putClientProperty(FlatClientProperties.STYLE, "" + "foreground:darken(@foreground,33%)");

//        CalMenu.add(new JLabel("Date"), "gapy 8");
//        CalMenu.add(dateField);
        JTextField weightField = new JTextField();
        weightField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Weight");

        JButton wsubmitButton = new JButton("Submit");
        wsubmitButton.putClientProperty(FlatClientProperties.STYLE, "" + "background:lighten(@background,10%);");
        wsubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Double weight = Double.parseDouble(weightField.getText());
                String errorMessage = StatsModel.verifyWeight(weight);
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
                    Wdescription.putClientProperty(FlatClientProperties.STYLE, "" + "foreground:darken(@foreground,33%)");
                }
            }
        });
        WMenu.add(Wtitle);
        WMenu.add(Wdescription, "gapy 10");
        WMenu.add(weightField, "gapy 10");
        WMenu.add(wsubmitButton);
        weightPanel.add(WMenu);
        mainPanel.add(weightPanel);

//        submitButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent event) {
//                Account account = new Account(CaloriesField.getText(), CaloriesField.getText());
//                //String errorMessage = LoginViewModel.logInRequest(account);
////                if (errorMessage != null) {
////                    description.setText(errorMessage);
////                    passwordField.setText("");
////                }
//            }
//        });

//        window.add(mainPanel);
//
//        EventQueue.invokeLater(() -> {
//            window.setVisible(true);
//            System.setProperty("apple.awt.application.name", "Rocket Health");
//            System.setProperty("apple.awt.application.appearance", "system");
//            window.getRootPane().putClientProperty("apple.awt.transparentTitleBar", true);
//            window.getRootPane().putClientProperty("apple.awt.windowTitleVisible", false);
//        });
    }
    public JPanel getViewPanel()
    {
        return mainPanel;
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("UpdateSleepInterface");
        Account a = new Account("Bob", "Smith");
        a.setSleep(0.0);
        a.setCalories(0);
        a.setWeight(0.0);
        StatsView sl = new StatsView(a);
        frame.setContentPane(sl.getViewPanel());
        frame.setSize(1200, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
