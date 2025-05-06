package Application.TheSwoleSection.ExerciseLibrary;

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

public class ExerciseLibraryView extends JPanel {

    public ExerciseLibraryView() {
        //Setup Main Panel Layout
        setLayout(new MigLayout("fill, insets 20", "center", "center"));
        putClientProperty(FlatClientProperties.STYLE, "background:@background");


        //setLayout(new BorderLayout());
        //putClientProperty(FlatClientProperties.STYLE, "background:@background");

        //add(new SideMenuView(), BorderLayout.WEST);
        JPanel main = new JPanel();
        main.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        main.setLayout(new BorderLayout());

        //North Panel Setup - Title
        JPanel titlePanel = new JPanel();
        titlePanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        FlatLabel title = new FlatLabel();
        title.setText("Exercise Library");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +25");
        titlePanel.add(title);

        main.add(titlePanel, BorderLayout.NORTH);


        //Center Panel Setup - Exercise List/Table
        //JTable table = new JTable(getRowData("sample_exercises.csv"), getColumNames("sample_exercises.csv"));
        JTable table = new JTable(ExerciseLibraryModel.getExerciseData(), ExerciseLibraryModel.getExerciseColums()){
            public String getToolTipText( MouseEvent e )
            {
                int row = rowAtPoint( e.getPoint() );
                int column = columnAtPoint( e.getPoint() );

                Object value = getValueAt(row, column);
                return value == null ? null : value.toString();
            }

        };
        table.setRowHeight(75);

        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(165);
        table.getColumnModel().getColumn(2).setPreferredWidth(5);
        table.getColumnModel().getColumn(3).setPreferredWidth(5);
        table.getColumnModel().getColumn(4).setPreferredWidth(5);

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
        scrollPane.setPreferredSize(new Dimension(1200, 650));
        scrollPane.setMaximumSize(new Dimension(1200, 650));

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

        FlatButton addExercise = new FlatButton();
        addExercise.setMinimumHeight(35);
        addExercise.setMinimumWidth(200);
        addExercise.setText("+ Add Exercise");
        addExercise.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Main.setWindow("CreateExercise" );
            }
        });

        buttonPanel.add(addExercise);


        FlatButton createWorkout = new FlatButton();
        createWorkout.setMinimumHeight(35);
        createWorkout.setMinimumWidth(200);
        createWorkout.setText("+ Create Workout");
        createWorkout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Main.setWindow("NewWorkout" );
            }
        });

        buttonPanel.add(createWorkout);

        main.add(buttonPanel, BorderLayout.SOUTH);

        //add(main, "align center, growy, pushy");
        add(main, "growy, pushy");
    }




}
