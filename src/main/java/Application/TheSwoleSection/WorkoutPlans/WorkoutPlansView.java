package Application.TheSwoleSection.WorkoutPlans;

import Application.Main;
import Application.TheSwoleSection.WorkoutView;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class WorkoutPlansView extends JPanel {
    WorkoutPlansViewModel viewModel;

    public WorkoutPlansView() {
        viewModel = new WorkoutPlansViewModel();

        //Setup Main Panel Layout
        setLayout(new MigLayout("fill, insets 20", "center", "center"));
        putClientProperty(FlatClientProperties.STYLE, "background:@background");


        JPanel main = new JPanel();
        main.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        main.setLayout(new BorderLayout());

        //North Panel Setup - Title
        JPanel titlePanel = new JPanel();
        titlePanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        FlatLabel title = new FlatLabel();
        title.setText("Workout Plans");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +25");
        titlePanel.add(title);

        main.add(titlePanel, BorderLayout.NORTH);


        //Center Panel Setup
        JTable table = new JTable(viewModel.getWorkoutPlanData(), viewModel.getWorkoutPlanColumns()){
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

        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(30);
        table.getColumnModel().getColumn(2).setPreferredWidth(1);
        table.getColumnModel().getColumn(3).setPreferredWidth(1);
        table.getColumnModel().getColumn(4).setPreferredWidth(500);

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
        tablePanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        //tablePanel.setBackground(Color.BLACK);
        tablePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        tablePanel.add(scrollPane);

        main.add(tablePanel, BorderLayout.CENTER);


        //South Panel Setup - Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        FlatButton createWorkoutPlan = new FlatButton();
        createWorkoutPlan.setMinimumHeight(35);
        createWorkoutPlan.setMinimumWidth(200);
        createWorkoutPlan.setText("+ Create Workout Plan");
        createWorkoutPlan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Main.setWindow("CreateWorkoutPlan" );
            }
        });

        buttonPanel.add(createWorkoutPlan);



        FlatButton modifiyWorkoutPlan = new FlatButton();
        modifiyWorkoutPlan.setMinimumHeight(35);
        modifiyWorkoutPlan.setMinimumWidth(200);
        modifiyWorkoutPlan.setText("Modify Workout Plan");
        modifiyWorkoutPlan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                //Main.setWindow("WorkoutPlans" );
                if(table.getSelectedRow() != -1) {
                    //ModifiyPlanDialog dialog = new ModifiyPlanDialog(table);
                    //dialog.setVisible(true);
                    Main.setWindow("ModifyWorkoutPlan", table);
                } else{
                    JOptionPane.showMessageDialog(null, "No Plan Selected");
                }
            }
        });

        buttonPanel.add(modifiyWorkoutPlan);

        FlatButton equipWorkoutPlan = new FlatButton();
        equipWorkoutPlan.setMinimumHeight(35);
        equipWorkoutPlan.setMinimumWidth(200);
        equipWorkoutPlan.setText("Equip Workout Plan");
        equipWorkoutPlan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if(table.getSelectedRow() != -1) {
                    viewModel.equipWorkoutPlan(table);
                    JOptionPane.showMessageDialog(null,
                            table.getValueAt(table.getSelectedRow(), 0).toString() + " Equipped");
                    Main.setWindow("Workout");
                    WorkoutView.setView("WorkoutPlans");
                } else{
                    JOptionPane.showMessageDialog(null, "No Plan Selected");
                }
            }
        });



        buttonPanel.add(equipWorkoutPlan);





        main.add(buttonPanel, BorderLayout.SOUTH);

        add(main, "growy, pushy");
    }

}
