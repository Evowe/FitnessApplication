package fitness.app.Report;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.components.FlatLabel;
import com.formdev.flatlaf.fonts.roboto_mono.FlatRobotoMonoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import fitness.app.ExerciseLibrary.ExerciseLibraryModel;
import fitness.app.Goals.GoalsView;
import fitness.app.Objects.Account;
import fitness.app.Objects.DatabaseManager;
import fitness.app.Objects.Databases.GoalsDB;
import fitness.app.Objects.Databases.WorkoutDB;
import fitness.app.Objects.Workout;
import fitness.app.Widgets.SideMenu.SideMenuView;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportView extends JPanel {
    private ReportViewModel viewModel;
    private Account currentUser;
    ReportView(Account user) throws SQLException {
        WorkoutDB workoutDB = DatabaseManager.getWorkoutDB();
        currentUser = user;
        viewModel = new ReportViewModel(currentUser);
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        Workout w = new Workout("PullUps",
                "veryHard",500,50,
                sdf.format(d).toString(), "1,2,3,4");
        //workoutDB.addWorkout(w,currentUser.getUsername());
        //workoutDB.deleteWorkout(currentUser.getUsername(),"pushUps");
        //workoutDB.updateWorkout(currentUser.getUsername(),w,"pushUps");

        currentUser = user;
        FlatLaf.registerCustomDefaultsSource("Components.Themes");
        FlatMacDarkLaf.setup();
        putClientProperty(FlatClientProperties.STYLE, "background:@background");

        setLayout(new MigLayout("fill"));
        add(new SideMenuView(), "growy, pushy");
        JPanel main = new JPanel();
        main.putClientProperty(FlatClientProperties.STYLE, "background:@background");

        //North Panel Setup - Title
        JPanel titlePanel = new JPanel();
        titlePanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        FlatLabel title = new FlatLabel();
        title.setText("Workout History");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +25");
        titlePanel.add(title);

        main.add(titlePanel, BorderLayout.NORTH);

        //Center Panel Setup - Exercise List/Table
        //Need to be able to use workout db before this can be updated
        JTable table = new JTable(viewModel.getWorkouts(), viewModel.getColumns());
        table.setRowSelectionAllowed(false);
        table.setColumnSelectionAllowed(false);
        table.setCellSelectionEnabled(false);
        table.setRowHeight(75);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1200, 650));
        scrollPane.setMaximumSize(new Dimension(1200, 650));

        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(Color.BLACK);
        tablePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        tablePanel.add(scrollPane);

        main.add(tablePanel, BorderLayout.CENTER);
        add(main);
    }
    public static void main(String[] args) throws UnsupportedLookAndFeelException, SQLException {
        //FlatLaf setup & settings
        FlatRobotoMonoFont.install();
        FlatLaf.registerCustomDefaultsSource("FlatLafSettings");
        UIManager.put("defaultFont", new Font(FlatRobotoMonoFont.FAMILY, Font.PLAIN, 13));
        FlatMacDarkLaf.setup();
        Account a = new Account("Bob", "Smith");

        //Application window
        JFrame window = new JFrame("Rocket Health");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        UIManager.setLookAndFeel(new FlatLightLaf());

        window.setLocationRelativeTo(null);
        window.add(new ReportView(a));
        window.setVisible(true);

    }
}

