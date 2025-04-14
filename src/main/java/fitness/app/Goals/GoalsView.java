package fitness.app.Goals;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto_mono.FlatRobotoMonoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.kitfox.svg.pathcmd.Horizontal;
import fitness.app.Objects.Account;
import fitness.app.Objects.DatabaseManager;
import fitness.app.Objects.Goal;
import fitness.app.Widgets.Calendar.CalendarView;
import fitness.app.Objects.Databases.GoalsDB;
import fitness.app.Objects.Goal;
import fitness.app.Widgets.SideMenu.SideMenuView;
import net.miginfocom.swing.MigLayout;
import fitness.app.Widgets.SideMenu.SideMenuViewModel;
 
import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GoalsView extends JPanel {
    private static Account currentUser;
    //private static JPanel mainPanel;
    //private static JPanel goalsPanel;

    public GoalsView(Account acc) {
       //GoalsViewModel.checkDB();
        currentUser = acc;
        modifyGoalsPanel();
        //FlatRobotoMonoFont.install();
//        FlatLaf.registerCustomDefaultsSource("Components.Themes");
//        UIManager.put("defaultFont", new Font(FlatRobotoMonoFont.FAMILY, Font.PLAIN, 13));
//        FlatMacDarkLaf.setup();
//         JPanel mainPanel = (new JPanel(new MigLayout("wrap 1, align center, insets 0", "[center]", "push[]push")));
//        //DISPLAY CALORIES
//        JPanel box = new JPanel(new MigLayout("fill,insets 20", "center", "Center"));
//        JPanel boxMenu = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,275"));
//        boxMenu.putClientProperty(FlatClientProperties.STYLE, "" + "arc:20;" + "background:lighten(@background,5%)");
//        JButton createGoal = new JButton("Create Goal");
//        createGoal.addActionListener(e -> {
//            selectTypeMenu();
//        });
//
//        JButton modifyGoal = new JButton("Modify Goal");
//        modifyGoal.addActionListener(e -> {
//            //GoalsDB db = new GoalsDB("Goals.db");
//            modifyGoalsPanel();
//        });
//        createGoal.putClientProperty(FlatClientProperties.STYLE, "font:+2;arc:999;minimumHeight:40");
//        modifyGoal.putClientProperty(FlatClientProperties.STYLE, "font:+2;arc:999;minimumHeight:40");
//
//        //boxTitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +6");
//        //boxMenu.add(createGoal);
//        boxMenu.add(modifyGoal,"gapy 20");
//        box.add(boxMenu);
//        //frame.removeAll();
//        mainPanel.add(box);
//        add(mainPanel);
//        frame.add(mainPanel);
//        frame.setContentPane(mainPanel);

    }

    public void selectTypeMenu() {
        FlatLaf.registerCustomDefaultsSource("Components.Themes");
        UIManager.put("defaultFont", new Font(FlatRobotoMonoFont.FAMILY, Font.PLAIN, 13));
        FlatMacDarkLaf.setup();

        JPanel goalsPanel = new JPanel(new MigLayout("wrap 1, align center, insets 0", "[center]", "push[]push"));
        JPanel box = new JPanel(new MigLayout("fill,insets 20", "center", "Center"));
        JPanel boxMenu = new JPanel(new MigLayout("wrap,fillx,insets 30", "[center]"));
        boxMenu.putClientProperty(FlatClientProperties.STYLE, "arc:20; background:lighten(@background,5%)");

        JLabel boxTitle = new JLabel("Goals");
        boxTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +6");

        JButton weightGoal = new JButton("Create Weight Goal");
        JButton distanceGoal = new JButton("Create Distance Goal");

        weightGoal.putClientProperty(FlatClientProperties.STYLE, "font:+2;arc:999;minimumHeight:40");
        distanceGoal.putClientProperty(FlatClientProperties.STYLE, "font:+2;arc:999;minimumHeight:40");

        weightGoal.addActionListener(e -> setGoalsPanel("Weight"));
        distanceGoal.addActionListener(e -> setGoalsPanel("Distance"));

        boxMenu.add(boxTitle, "span, align center, gapbottom 20");
        boxMenu.add(weightGoal);
        boxMenu.add(distanceGoal, "gapy 20");

        box.add(boxMenu);
        goalsPanel.add(box);

        removeAll();           // Clear the current view
        add(goalsPanel);       // Add the new panel
        revalidate();
        repaint();
    }

    public void setGoalsPanel(String goalType)
    {
        GoalsDB db = (GoalsDB) DatabaseManager.getDatabase("goals");
        //GoalsDB db = new GoalsDB("Goals.db");
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
        boxMenu.setOpaque(true);

        JLabel boxTitle = new JLabel("Set " + goalType + " Goal");
        boxTitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +6");

        JLabel desc = new JLabel("");
        desc.putClientProperty(FlatClientProperties.STYLE, "" + "foreground:darken(@foreground,33%)");
        JTextField contentField = new JTextField();
        contentField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter " + goalType);

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        format.setLenient(false); // strict parsing

        JFormattedTextField dateField = new JFormattedTextField(new DateFormatter(format));
        dateField.setFocusLostBehavior(JFormattedTextField.PERSIST); // Prevents "fixing" bad input

        dateField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "MM/dd/yyyy ");

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
                    errorMessage = " asd";
                }
                String d = dateField.getText();
                errorMessage = GoalsViewModel.verifyDate(d);
                if (errorMessage != null) {
                    dateField.setText("");
                    desc.setText(errorMessage);
                    desc.setForeground(Color.RED);
                    desc.setForeground(Color.RED);
                    desc.putClientProperty(FlatClientProperties.STYLE, "font:-4");
                }
                else {
                    Date date = null;
                    try {
                        date = new SimpleDateFormat("MM/dd/yyyy").parse(dateField.getText());
                    if(boxTitle.getText() == "Distance")
                    {
                        db.updateGoal(new Goal(currentUser.getUsername(), "Distance",
                                Integer.valueOf(contentField.getText()), date, false));
                    }
                    else
                        db.updateGoal(new Goal(currentUser.getUsername(), "Weight",
                                Integer.valueOf(contentField.getText()), date, false));
                    } catch (ParseException | SQLException e) {
                        throw new RuntimeException(e);
                    }
                    JOptionPane.showMessageDialog(
                            null,
                            "Goal saved successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    goalsPanel.removeAll();
                    removeAll();
                    selectTypeMenu();
                    //Main.setWindow("HomePage");
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
        //add(goalsPanel);

        removeAll();
        add(goalsPanel);
        revalidate();
        repaint();
        //return goalsPanel;
    }
    public void modifyGoalsPanel() {
        GoalsDB goalsDB = (GoalsDB) DatabaseManager.getDatabase("goals");
        FlatLaf.registerCustomDefaultsSource("Components.Themes");
        UIManager.put("defaultFont", new Font(FlatRobotoMonoFont.FAMILY, Font.PLAIN, 13));
        FlatMacDarkLaf.setup();

        // Set layout with two columns: one for SideMenuView, one for main content
        setLayout(new MigLayout("insets 0, fill", "push[grow]push", "push[grow]push"));

        // Add SideMenuView to the first column, spanning all rows

        // Create mainPanel for content
        JPanel mainPanel = new JPanel(new MigLayout("wrap 3, fill", "[grow][grow][grow]", "[][][]"));
        mainPanel.putClientProperty(FlatClientProperties.STYLE, "arc:20;");

        // -- LEFT: Current Weight Goal --
//        JPanel weightPanel = new JPanel(new MigLayout("wrap 1", "right", "center"));
//        weightPanel.putClientProperty(FlatClientProperties.STYLE, "arc:20; background:lighten(@background,3%)");
//
//
//        weightPanel.add(weightTitle);
//        weightPanel.add(weightContent);

        // -- RIGHT: Current Distance Goal --
        JPanel distancePanel = new JPanel(new MigLayout("wrap 1", "right", "center"));
        distancePanel.putClientProperty(FlatClientProperties.STYLE, "arc:20; background:lighten(@background,3%)");
        JLabel weightTitle = new JLabel("Current Weight Goal");
        weightTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        SimpleDateFormat displayFormat = new SimpleDateFormat("MM/dd/yyyy");

        JLabel weightContent = new JLabel("");
        try {
            Goal weightGoal = goalsDB.getGoalByTypeAndUsername(currentUser.getUsername(), "Weight");
            if (weightGoal == null) {
                weightGoal = new Goal(currentUser.getUsername(), "Weight", 0, new Date(), false);
                goalsDB.addGoal(weightGoal);
            }
            String formattedDate = displayFormat.format(weightGoal.getDate());
            weightContent.setText(weightGoal.getValue() + " by " + formattedDate);
        } catch (SQLException e) {
            weightContent.setText("Error loading goal.");
        }
        JLabel distanceTitle = new JLabel("Current Distance Goal");
        distanceTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold");

        JLabel distanceContent = new JLabel("");
        try {
            Goal distanceGoal = goalsDB.getGoalByTypeAndUsername(currentUser.getUsername(), "Distance");
            if (distanceGoal == null) {
                distanceGoal = new Goal(currentUser.getUsername(), "Distance", 0, new Date(), false);
                goalsDB.addGoal(distanceGoal);
            }
            String formattedDate = displayFormat.format(distanceGoal.getDate());
            distanceContent.setText(distanceGoal.getValue() + " by " + formattedDate);
        } catch (SQLException e) {
            distanceContent.setText("Error loading goal.");
        }
        distancePanel.add(weightTitle);
        distancePanel.add(weightContent);
        distancePanel.add(distanceTitle);
        distancePanel.add(distanceContent);


        // -- CENTER: Editable Goal Form --
        JPanel centerPanel = new JPanel(new MigLayout("wrap, fillx, insets 30", "[grow,fill]", "[][][][][]"));
        centerPanel.putClientProperty(FlatClientProperties.STYLE, "arc:20; background:lighten(@background,5%)");

        JLabel formTitle = new JLabel("Modify a Goal");
        formTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +6");

        JLabel desc = new JLabel("");
        desc.putClientProperty(FlatClientProperties.STYLE, "foreground:darken(@foreground,33%)");

        JComboBox<String> goalTypeBox = new JComboBox<>(new String[]{"Distance", "Weight"});
        goalTypeBox.putClientProperty(FlatClientProperties.STYLE,
                "foreground:darken(@foreground,33%); background:darken(@background,27%)");
        JTextField valueField = new JTextField();
        valueField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter goal value");

        JPanel horiBox = new JPanel();
        JFormattedTextField dateField = new JFormattedTextField(new DateFormatter(new SimpleDateFormat("MM/dd/yyyy")));
        dateField.setFocusLostBehavior(JFormattedTextField.PERSIST);
        dateField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "MM/dd/yyyy");

        JButton submit = new JButton("Submit");
        submit.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%)");

        submit.addActionListener(e -> {
            try {
                double val = Double.parseDouble(valueField.getText());
                String dateStr = dateField.getText();
                String goalType = (String) goalTypeBox.getSelectedItem();

                String errorMessage = GoalsViewModel.verifyDistance(val);
                if (errorMessage != null) {
                    desc.setText(errorMessage);
                    desc.setForeground(Color.RED);
                    return;
                }

                errorMessage = GoalsViewModel.verifyDate(dateStr);
                if (errorMessage != null) {
                    desc.setText(errorMessage);
                    desc.setForeground(Color.RED);
                    return;
                }

                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                format.setLenient(false);
                Date goalDate = format.parse(dateStr);
                Goal existing = goalsDB.getGoalByTypeAndUsername(currentUser.getUsername(), goalType);

                if (existing == null) {
                    Goal newGoal = new Goal(currentUser.getUsername(), goalType, (int) val, dateStr, false);
                    goalsDB.addGoal(newGoal);
                } else {
                    existing.setValue((int) val);
                    existing.setDate(goalDate);
                    goalsDB.updateGoal(existing);
                }

                JOptionPane.showMessageDialog(null, "Goal updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
                modifyGoalsPanel();
            } catch (NumberFormatException | ParseException ex) {
                desc.setText("Invalid input.");
                desc.setForeground(Color.RED);
            } catch (SQLException ex) {
                desc.setText("Database error: " + ex.getMessage());
                desc.setForeground(Color.RED);
            }
        });

        centerPanel.add(formTitle, "gapbottom 20");
        centerPanel.add(goalTypeBox);
        centerPanel.add(valueField);
        centerPanel.add(dateField, "gapy 10");
        centerPanel.add(desc);
        centerPanel.add(submit, "gapy 20");

        mainPanel.add(centerPanel, "grow");
        mainPanel.add(distancePanel, "grow");

        // Add mainPanel to the second column, spanning all rows
        removeAll();
        add(new SideMenuView(), "cell 0 0 1 3, growy");
        add(mainPanel, "cell 1 1 1 1, grow");

        revalidate();
        repaint();
    }



    public static void main(String[] args) {
        DatabaseManager.addDatabase("goals", new GoalsDB("goals"));
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
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);

        window.setLocationRelativeTo(null);
        JPanel panel = new JPanel(new MigLayout("fill,insets 20", "center", "center"));
        window.add(new GoalsView(a));
        //panel.add(new GoalsView(a));
        window.getRootPane().putClientProperty("apple.awt.transparentTitleBar", true);
        window.getRootPane().putClientProperty("apple.awt.windowTitleVisible", false);
        window.setVisible(true);

    }
}
