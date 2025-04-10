package fitness.app.Goals;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto_mono.FlatRobotoMonoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import fitness.app.Objects.Account;
import fitness.app.Widgets.Calendar.CalendarView;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class GoalsView extends JPanel {
    private static Account currentUser;
    //private static JPanel mainPanel;
    //private static JPanel goalsPanel;

    public GoalsView(Account acc) {
        currentUser = acc;
        //FlatRobotoMonoFont.install();
        FlatLaf.registerCustomDefaultsSource("Components.Themes");
        UIManager.put("defaultFont", new Font(FlatRobotoMonoFont.FAMILY, Font.PLAIN, 13));
        FlatMacDarkLaf.setup();
         JPanel mainPanel = (new JPanel(new MigLayout("wrap 1, align center, insets 0", "[center]", "push[]push")));
        //DISPLAY CALORIES
        JPanel box = new JPanel(new MigLayout("fill,insets 20", "center", "Center"));
        JPanel boxMenu = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,275"));
        boxMenu.putClientProperty(FlatClientProperties.STYLE, "" + "arc:20;" + "background:lighten(@background,5%)");
        JButton createGoal = new JButton("Create Goal");
        createGoal.addActionListener(e -> {
            selectTypeMenu();
        });
        JButton modifyGoal = new JButton("Modify Goal");
        createGoal.putClientProperty(FlatClientProperties.STYLE, "font:+2;arc:999;minimumHeight:40");
        modifyGoal.putClientProperty(FlatClientProperties.STYLE, "font:+2;arc:999;minimumHeight:40");

        //boxTitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +6");
        boxMenu.add(createGoal);
        boxMenu.add(modifyGoal,"gapy 20");
        box.add(boxMenu);
        //frame.removeAll();
        mainPanel.add(box);
        add(mainPanel);
//        frame.add(mainPanel);
//        frame.setContentPane(mainPanel);

    }

    public GoalsView() {

    }

    public void selectTypeMenu()
    {
        //removeall();
        //FlatRobotoMonoFont.install();
        FlatLaf.registerCustomDefaultsSource("Components.Themes");
        UIManager.put("defaultFont", new Font(FlatRobotoMonoFont.FAMILY, Font.PLAIN, 13));
        FlatMacDarkLaf.setup();
        JPanel goalsPanel = new JPanel(new MigLayout("wrap 1, align center, insets 0", "[center]", "push[]push"));
        //DISPLAY CALORIES
        JPanel box = new JPanel(new MigLayout("fill,insets 20", "center", "Center"));
        JPanel boxMenu = new JPanel(new MigLayout("wrap,fillx,insets 30", "[center]"));
        boxMenu.putClientProperty(FlatClientProperties.STYLE, "" + "arc:20;" + "background:lighten(@background,5%)");
        JLabel boxTitle = new JLabel("Goals");
        boxTitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +6");
        JButton WeightGoal = new JButton("Create Weight Goal");
        JButton DistanceGoal = new JButton("Create Distance Goal");
        WeightGoal.putClientProperty(FlatClientProperties.STYLE, "font:+2;arc:999;minimumHeight:40");
        DistanceGoal.putClientProperty(FlatClientProperties.STYLE, "font:+2;arc:999;minimumHeight:40");

        //boxTitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +6");
        boxMenu.add(boxTitle, "span, align center, gapbottom 20");
        boxMenu.add(WeightGoal);
        boxMenu.add(DistanceGoal,"gapy 20");
        box.add(boxMenu);
        goalsPanel.add(box);
        add(goalsPanel);
    }
    public JPanel setGoalsPanel(String goalType)
    {
        //FlatRobotoMonoFont.install();
        FlatLaf.registerCustomDefaultsSource("Components.Themes");
        UIManager.put("defaultFont", new Font(FlatRobotoMonoFont.FAMILY, Font.PLAIN, 13));
        FlatMacDarkLaf.setup();
        JPanel goalsPanel = new JPanel(new MigLayout("wrap 1, align center, insets 0", "[center]", "push[]push"));
        //DISPLAY CALORIES
        JPanel box = new JPanel(new MigLayout("fill,insets 20", "center", "Center"));
        JPanel boxMenu = new JPanel(new MigLayout("wrap,fillx,insets 30", "[grow,fill]")
        );
        boxMenu.putClientProperty(FlatClientProperties.STYLE, "" + "arc:20;" + "background:lighten(@background,5%)");
        JLabel boxTitle = new JLabel("Set " + goalType + " Goal");
        boxTitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +6");

        JLabel desc = new JLabel("");
        desc.putClientProperty(FlatClientProperties.STYLE, "" + "foreground:darken(@foreground,33%)");
        JTextField contentField = new JTextField();
        contentField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter " + goalType);

        JTextField dateField = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
        dateField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "dd/MM/yyyy ");

        JButton submit = new JButton("Submit");
        submit.putClientProperty(FlatClientProperties.STYLE, "" + "background:lighten(@background,10%);");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Double val = Double.parseDouble(contentField.getText());
                String errorMessage = GoalsViewModel.verifyDistance(val);
                if (errorMessage != null) {
                    contentField.setText("");
                    desc.setText(errorMessage);
                    desc.setForeground(Color.RED);
                    desc.setForeground(Color.RED);
                    desc.putClientProperty(FlatClientProperties.STYLE, "font:-4");
                }
                String d = dateField.getText();
                errorMessage = GoalsViewModel.verifyDate(d);
                if (errorMessage != null) {
                    contentField.setText("");
                    desc.setText(errorMessage);
                    desc.setForeground(Color.RED);
                    desc.setForeground(Color.RED);
                    desc.putClientProperty(FlatClientProperties.STYLE, "font:-4");
                }
                else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Goal saved successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    goalsPanel.removeAll();
                    // goalsPanel = selectTypeMenu(acc,frame);

                }
            }
        });
        //boxTitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +6");
        boxMenu.add(boxTitle, "span, align center, gapbottom 20");
        boxMenu.add(desc);
        boxMenu.add(contentField);
        boxMenu.add(dateField,"gapy 20");
        boxMenu.add(submit, "gapy 20");
        box.add(boxMenu);
        goalsPanel.add(box);
        return goalsPanel;
    }
//    public static JPanel getGoalsView() {
//        //return mainPanel;
//    }
    public static void main(String[] args) {

        //FlatLaf setup & settings
        FlatRobotoMonoFont.install();
        FlatLaf.registerCustomDefaultsSource("FlatLafSettings");
        UIManager.put("defaultFont", new Font(FlatRobotoMonoFont.FAMILY, Font.PLAIN, 13));
        FlatMacDarkLaf.setup();
        Account a = new Account("Bob", "Smith");

        //Application window
        JFrame window = new JFrame("Rocket Health");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(new Dimension(1200, 700));
        window.setLocationRelativeTo(null);
        JPanel panel = new JPanel(new MigLayout("fill,insets 20", "center", "center"));
        panel.add(new GoalsView(a));
        window.add(panel);
        window.getRootPane().putClientProperty("apple.awt.transparentTitleBar", true);
        window.getRootPane().putClientProperty("apple.awt.windowTitleVisible", false);
        window.setVisible(true);

    }
}
