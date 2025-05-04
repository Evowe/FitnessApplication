package Application.TheSwoleSection.WorkoutLibrary;

import Application.Databases.AccountsDB;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;
import Application.Main;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class WorkoutLibraryView extends JPanel{
    private WorkoutLibraryViewModel viewModel;

    public WorkoutLibraryView() {
        //View Model
        viewModel = new WorkoutLibraryViewModel();

        //Setup Main Panel Layout
        setLayout(new MigLayout("fill, insets 20", "center", "center"));
        putClientProperty(FlatClientProperties.STYLE, "background:@background");

        JPanel main = new JPanel();
        main.putClientProperty(FlatClientProperties.STYLE, "background:@background");

        //North Panel Setup - Title
        JPanel titlePanel = new JPanel();
        titlePanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        FlatLabel title = new FlatLabel();
        title.setText("Workout Library");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +25");
        titlePanel.add(title);

        main.add(titlePanel, BorderLayout.NORTH);

        //Center Panel Setup - Exercise List/Table
        //Need to be able to use workout db before this can be updated
        JTable table = new JTable(viewModel.getWorkoutData(),
                viewModel.getWorkoutColumns()){
            public String getToolTipText( MouseEvent e )
            {
                int row = rowAtPoint( e.getPoint() );
                int column = columnAtPoint( e.getPoint() );

                Object value = getValueAt(row, column);
                return value == null ? null : value.toString();
            }

        };
        table.setRowHeight(75);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        table.getColumnModel().getColumn(2).setPreferredWidth(3);
        table.getColumnModel().getColumn(3).setPreferredWidth(400);

        table.setDefaultEditor(Object.class, null);

        for(int i = 0; i < table.getColumnCount(); i++) {
            TableColumn col = table.getColumnModel().getColumn(i);
            DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
            dtcr.setHorizontalAlignment(SwingConstants.CENTER);
            col.setCellRenderer(dtcr);
        }
        table.setShowGrid(true);

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

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1200, 550));
        scrollPane.setMaximumSize(new Dimension(1200, 550));

        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(Color.BLACK);
        tablePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        //tablePanel.setPreferredSize(new Dimension(1200, 550));
        tablePanel.add(scrollPane);

        main.add(tablePanel, BorderLayout.CENTER);


        //South Panel Setup - Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));



        FlatButton createWorkout = new FlatButton();
        createWorkout.setMinimumHeight(35);
        createWorkout.setMinimumWidth(200);
        createWorkout.setText("+ Create Workout");
        createWorkout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Main.setWindow("NewWorkout");
            }
        });

        buttonPanel.add(createWorkout);

        FlatButton recordWorkout = new FlatButton();
        recordWorkout.setMinimumHeight(35);
        recordWorkout.setMinimumWidth(200);
        recordWorkout.setText("Record Workout");
        recordWorkout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {

                if(table.getSelectedRow() != -1) {
                    viewModel.recordWorkout(table.getValueAt(table.getSelectedRow(), 0).toString(),
                            Main.getCurrentUser().getUsername());

                    JOptionPane.showMessageDialog(null, "Workout recorded. " +
                            "You can view in the Workout History Page under Metrics.");

                    try {
                        AccountsDB.addxp(Main.getCurrentUser().getUsername(), 100); // Update DB by +100
                        Main.getCurrentUser().setXp(Main.getCurrentUser().getXp() + 100); // Sync local object
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    //Main.setWindow("MetricsPage");
                } else{
                    JOptionPane.showMessageDialog(null, "No Workout Selected");
                }
            }
        });

        buttonPanel.add(recordWorkout);

        main.add(buttonPanel, BorderLayout.SOUTH);


        add(main, "growy, pushy");
    }
}


