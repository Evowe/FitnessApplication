package Application.TheSwoleSection.CreateLiveWorkout;

import java.awt.*;
import javax.swing.*;

import Application.TheSwoleSection.WorkoutView;
import Application.Utility.Widgets.SideMenu.SideMenuView;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;

import Application.Main;
import Application.Utility.Objects.LiveWorkout;
import net.miginfocom.swing.MigLayout;

public class NewLiveWorkoutView extends JPanel {
    NewLiveWorkoutViewModel viewModel;

    public NewLiveWorkoutView() {
        viewModel = new NewLiveWorkoutViewModel();
        //setLayout(new MigLayout("insets 20", "[grow]", "top"));
        setLayout(new MigLayout("fill, insets 20", "[]20[]", "center"));

        putClientProperty(FlatClientProperties.STYLE, "background:@background");

        // Side Menu
        //add(new SideMenuView(), "dock west, growy");
        add(new SideMenuView(), "growy, pushy");

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(1200, 650));
        mainPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");

        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        FlatLabel title = new FlatLabel();
        title.setText("Create New Live Workout");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +25");
        titlePanel.add(title);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Center Form Panel using MigLayout
        JPanel formPanel = new JPanel(new MigLayout("wrap 2", "[right][grow,fill]", "[]15[]15[]15[]15[]15[]15[]"));
        formPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");

        // Name
        formPanel.add(new JLabel("Live Workout Name:"));
        JTextField nameField = new JTextField();
        nameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Workout Name");
        formPanel.add(nameField);

        // Description
        formPanel.add(new JLabel("Workout Description:"));
        JTextField descriptionField = new JTextField();
        descriptionField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Workout Description");
        formPanel.add(descriptionField);

        // Duration
        formPanel.add(new JLabel("Workout Duration:"));
        JTextField durationField = new JTextField();
        durationField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Workout Duration (min)");
        formPanel.add(durationField);

        // Time
        formPanel.add(new JLabel("Workout Start Time:"));
        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        timePanel.setOpaque(false);
        JTextField hourField = new JTextField(2);
        hourField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "HH");
        hourField.setPreferredSize(new Dimension(40, 25));
        timePanel.add(hourField);

        timePanel.add(new JLabel(":"));

        JTextField minuteField = new JTextField(2);
        minuteField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "MM");
        minuteField.setPreferredSize(new Dimension(40, 25));
        timePanel.add(minuteField);

        JComboBox<String> amPmBox = new JComboBox<>(new String[] { "AM", "PM" });
        timePanel.add(amPmBox);
        formPanel.add(timePanel);

        // Save Button
        formPanel.add(new JLabel()); // placeholder for alignment
        FlatButton saveWorkout = new FlatButton();
        saveWorkout.setText("Save Workout");
        formPanel.add(saveWorkout, "align left");

        // Action Listener
        saveWorkout.addActionListener(e -> {
            String hour = hourField.getText();
            String minute = minuteField.getText();
            String amPm = (String) amPmBox.getSelectedItem();
            String time = hour + ":" + minute + " " + amPm;

            LiveWorkout workout = new LiveWorkout();
            workout.setName(nameField.getText());
            workout.setDescription(descriptionField.getText());
            workout.setDuration(Integer.parseInt(durationField.getText()));
            workout.setStartTime(time);

            viewModel.addWorkout(workout, Main.getCurrentUser().getId());
            Main.setWindow("Workout");
            WorkoutView.setView("LiveWorkouts");

        });

        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel, "growy, pushy");
    }
}
