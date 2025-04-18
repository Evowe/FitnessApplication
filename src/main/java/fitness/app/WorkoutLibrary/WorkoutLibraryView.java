package fitness.app.WorkoutLibrary;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;
import fitness.app.ExerciseLibrary.ExerciseLibraryModel;
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

public class WorkoutLibraryView extends JPanel{
    public WorkoutLibraryView() {
        //Setup Main Panel Layout
        setLayout(new MigLayout("insets 20", "left", "top"));
        putClientProperty(FlatClientProperties.STYLE, "background:@background");

        add(new SideMenuView(), "growy, pushy");

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
        JTable table = new JTable();
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

        main.add(buttonPanel, BorderLayout.SOUTH);

        add(main, "growy, pushy");
    }
}


