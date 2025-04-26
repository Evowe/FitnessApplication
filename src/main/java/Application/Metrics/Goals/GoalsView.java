package Application.Metrics.Goals;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.fonts.roboto_mono.FlatRobotoMonoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import Application.Utility.Objects.Account;
import Application.Utility.Databases.DatabaseManager;
import Application.Utility.Objects.Goal;
import Application.Utility.Widgets.Calendar.CalendarView;
import Application.Utility.Databases.GoalsDB;
import Application.Utility.Widgets.SideMenu.SideMenuView;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GoalsView extends JPanel {
    private static Account currentUser;

    public boolean isFrameOpen() {
        return isFrameOpen;
    }

    public int setFrameOpen(boolean frameOpen) {
        isFrameOpen = frameOpen;
        return JFrame.DISPOSE_ON_CLOSE;
    }

    private boolean isFrameOpen = false;

    public GoalsView(Account acc) {
        //GoalsViewModel.checkDB();
        currentUser = acc;

        modifyGoalsPanel();
    }

    public void modifyGoalsPanel() {
        GoalsDB goalsDB = DatabaseManager.getGoalsDB();

        setLayout(new MigLayout("fill,insets 20"));
        putClientProperty(FlatClientProperties.STYLE, "arc:20");
        putClientProperty(FlatClientProperties.STYLE, "background:@background");

        JPanel mainPanel = new JPanel(new MigLayout());
        mainPanel.putClientProperty(FlatClientProperties.STYLE, "arc:20");


        // -- RIGHT: Current Distance Goal --
        JPanel distancePanel = new JPanel(new MigLayout("insets 0,wrap"));
        distancePanel.putClientProperty(FlatClientProperties.STYLE, "arc:20");

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
        JPanel centerPanel = new JPanel(new MigLayout("wrap, fillx, insets 30", "fill,275", "[][][][][]"));
        //centerPanel.putClientProperty(FlatClientProperties.STYLE, "arc:20; background:@accent");

        JLabel formTitle = new JLabel("Modify a Goal");
        formTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +6");

        JLabel desc = new JLabel("");
        desc.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground");

        JComboBox<String> goalTypeBox = new JComboBox<>(new String[]{"Distance", "Weight"});
        goalTypeBox.putClientProperty(FlatClientProperties.STYLE, "background:@secondaryBackground");
        JTextField valueField = new JTextField();
        valueField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter goal value");
        Dimension dim = valueField.getSize();
        JPanel horiBox = new JPanel(new BorderLayout());
        horiBox.setPreferredSize(dim);
        //horiBox.setMaximumSize(dim);
        //horiBox.setMinimumSize(dim);
        JFormattedTextField dateField = new JFormattedTextField(new DateFormatter(new SimpleDateFormat("MM/dd/yyyy")));
        dateField.setFocusLostBehavior(JFormattedTextField.PERSIST);
        dateField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "MM/dd/yyyy");
        dateField.setPreferredSize(dim);
        dateField.putClientProperty(FlatClientProperties.STYLE, "background:@secondaryBackground");


        JButton calButt = new JButton("");
        calButt.setIcon(GoalsViewModel.getIcon("calendar"));
        horiBox.add(dateField, BorderLayout.CENTER);
        horiBox.add(calButt, BorderLayout.EAST);
        calButt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isFrameOpen = true;
                JFrame popWindow = new JFrame();
                popWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                CalendarView cal = new CalendarView();
                new Thread(() -> {
                    while(isFrameOpen) {
                        popWindow.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                setFrameOpen(false);
                            }
                        });
                        try {
                            //System.out.println(cal.getDate());
                            dateField.setText(cal.getDate());
                            Thread.sleep(500); // Check every 500ms
                        } catch (InterruptedException b) {
                            b.printStackTrace();
                            return;
                        }
                        if(!isFrameOpen) {
                            break;
                        }
                    }

                }).start();
                popWindow.add(cal);
                popWindow.setSize(new Dimension(600, 400));
                popWindow.setLocation(200, 100);
                dateField.setText(cal.getDate());
                popWindow.setVisible(true);
            }
        });
        FlatButton submit = new FlatButton();
        submit.setText("Submit");
        submit.setBorderPainted(false);
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
        centerPanel.add(goalTypeBox , "gapy 10");
        centerPanel.add(valueField,"gapy 10");
        centerPanel.add(horiBox, "gapy 10");
        centerPanel.add(desc,"gapy 10");
        centerPanel.add(submit, "gapy 20");

        mainPanel.add(centerPanel, "grow");
        mainPanel.add(distancePanel, "grow");

        // Add mainPanel to the second column, spanning all rows
        removeAll();
        add(new SideMenuView(),"growy, pushy");
        add(mainPanel, "");

        revalidate();
        repaint();
    }



    public static void main(String[] args) throws UnsupportedLookAndFeelException {
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
        System.setProperty("apple.awt.application.appearance", "system");
        UIManager.setLookAndFeel(new FlatLightLaf());

        window.setLocationRelativeTo(null);
        JPanel panel = new JPanel(new MigLayout("fill,insets 20", "center", "center"));
        window.add(new GoalsView(a));
        //panel.add(new GoalsView(a));
        window.getRootPane().putClientProperty("apple.awt.transparentTitleBar", true);
        window.getRootPane().putClientProperty("apple.awt.windowTitleVisible", false);
        window.setVisible(true);

    }
}