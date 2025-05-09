package Application.Metrics.Report;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.components.FlatLabel;
import com.formdev.flatlaf.fonts.roboto_mono.FlatRobotoMonoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import Application.Utility.Objects.Account;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportView extends JPanel {
    private ReportViewModel viewModel;
    private Account currentUser;
    public ReportView(Account user) {
        setLayout(new MigLayout("fill, insets 20", "center", "center"));
        putClientProperty(FlatClientProperties.STYLE, "background:@background;");
        //WorkoutLogDB workoutLogDB = DatabaseManager.getWorkoutLogDB();
        currentUser = user;
        viewModel = new ReportViewModel(currentUser);
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

//        Workout w1 = new Workout("PullUps", "veryHard", 500, 50, sdf.format(d).toString(), "1,2,3,4");
//        Workout w2 = new Workout("PushUps", "medium", 300, 30, sdf.format(d).toString(), "2,3");
//        Workout w3 = new Workout("Squats", "hard", 450, 40, sdf.format(d).toString(), "3,4,5");
//        Workout w4 = new Workout("Deadlift", "veryHard", 600, 45, sdf.format(d).toString(), "1,4,6");
//        Workout w5 = new Workout("Plank", "easy", 200, 20, sdf.format(d).toString(), "2,5");
//        Workout w6 = new Workout("Burpees", "hard", 400, 35, sdf.format(d).toString(), "2,3,6");
//        Workout w7 = new Workout("Lunges", "medium", 350, 30, sdf.format(d).toString(), "3,4");
//        Workout w8 = new Workout("BenchPress", "veryHard", 550, 40, sdf.format(d).toString(), "1,3,5");
//        Workout w9 = new Workout("MountainClimbers", "medium", 300, 25, sdf.format(d).toString(), "2,5,6");
//        Workout w10 = new Workout("JumpRope", "easy", 250, 20, sdf.format(d).toString(), "5,6");
//        viewModel.addWorkout(w2,user.getUsername());
//        viewModel.addWorkout(w3,user.getUsername());
//        viewModel.addWorkout(w4,user.getUsername());
//        viewModel.addWorkout(w5,user.getUsername());
//        viewModel.addWorkout(w6,user.getUsername());
//        viewModel.addWorkout(w7,user.getUsername());
//        viewModel.addWorkout(w8,user.getUsername());
//        viewModel.addWorkout(w9,user.getUsername());
//        viewModel.addWorkout(w10,user.getUsername());


        currentUser = user;
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
        JTable table = new JTable(viewModel.getWorkoutData(), viewModel.getColumns()){
            public String getToolTipText( MouseEvent e )
            {
                int row = rowAtPoint( e.getPoint() );
                int column = columnAtPoint( e.getPoint() );

                Object value = getValueAt(row, column);
                return value == null ? null : value.toString();
            }

        };
        table.setRowSelectionAllowed(false);
        table.setColumnSelectionAllowed(false);
        table.setCellSelectionEnabled(false);
        table.setRowHeight(75);

        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(70);
        table.getColumnModel().getColumn(3).setPreferredWidth(3);
        table.getColumnModel().getColumn(4).setPreferredWidth(5);
        table.getColumnModel().getColumn(5).setPreferredWidth(5);
        table.getColumnModel().getColumn(6).setPreferredWidth(400);


        table.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Change font here (example: bold, size 14)
                c.setFont(new Font("Monospaces", Font.BOLD, 15));

                return c;
            }
        });


        table.setDefaultEditor(Object.class, null);

        for(int i = 0; i < table.getColumnCount(); i++) {
            TableColumn col = table.getColumnModel().getColumn(i);
            DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
            dtcr.setHorizontalAlignment(SwingConstants.CENTER);
            col.setCellRenderer(dtcr);
        }
        table.setShowGrid(true);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1200, 650));
        scrollPane.setMaximumSize(new Dimension(1200, 650));

        JPanel tablePanel = new JPanel();
        tablePanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        //tablePanel.setBackground(Color.BLACK);
        tablePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        tablePanel.add(scrollPane);

        main.add(tablePanel, BorderLayout.CENTER);
        add(tablePanel);
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

