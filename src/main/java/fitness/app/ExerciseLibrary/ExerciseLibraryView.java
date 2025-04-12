package fitness.app.ExerciseLibrary;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;
import fitness.app.Main;
import fitness.app.Widgets.SideMenu.SideMenuView;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ExerciseLibraryView extends JPanel {

    public ExerciseLibraryView() {

        setLayout(new MigLayout("insets 20", "left", "top"));
        putClientProperty(FlatClientProperties.STYLE, "background:darken(@background, 1%)");

        add(new SideMenuView(), "growy, pushy");


        //Setup Main Panel Layout
        //setLayout(new BorderLayout());
        //putClientProperty(FlatClientProperties.STYLE, "background:@background");

        //add(new SideMenuView(), BorderLayout.WEST);
        JPanel main = new JPanel();
        main.putClientProperty(FlatClientProperties.STYLE, "background:@background");

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
        JTable table = new JTable(getRowData("sample_exercises.csv"), getColumNames("sample_exercises.csv"));
        table.setRowHeight(75);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1200, 650));
        scrollPane.setMaximumSize(new Dimension(1200, 650));

        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(Color.BLACK);
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
        buttonPanel.add(createWorkout);

        main.add(buttonPanel, BorderLayout.SOUTH);

        add(main, "growy, pushy");
    }


    private Object [] getColumNames(String fileName){
        String[] columNames = null;
        try {
            Scanner scanner = new Scanner(new File(fileName));
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                columNames = line.split(",");
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return columNames;
    }

    private Object [][] getRowData(String fileName){
        ArrayList<String[]> rows = new ArrayList<>();
        try{
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");
                rows.add(values);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Object [][] data = new Object[rows.size() - 1][];
        for (int i = 1; i < rows.size(); i++) {
            data[i - 1] = rows.get(i);
        }

        return data;
    }

}
