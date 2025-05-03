package Application.TheSwoleSection.CreateExercise;


import Application.TheSwoleSection.CreateWorkout.NewWorkoutView;
import Application.TheSwoleSection.WorkoutView;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;
import com.formdev.flatlaf.extras.components.FlatTextField;
import Application.Main;
import Application.Utility.Objects.Exercise;
import Application.Utility.Widgets.SideMenu.SideMenuView;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateExcerciseView extends JPanel {

    public CreateExcerciseView() {
        //Setup Main Panel
        setLayout(new MigLayout("insets 20", "left", "top"));
        putClientProperty(FlatClientProperties.STYLE, "background:@background");

        //Add navigation bar
        add(new SideMenuView(), "growy, pushy");


        //Setup Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        //mainPanel.setMinimumSize(new Dimension(500, 500));
        mainPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");

        //North Panel Setup
        JPanel titlePanel = new JPanel();
        titlePanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        FlatLabel title = new FlatLabel();
        title.setText("Create Exercise");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +25");
        titlePanel.add(title);

        mainPanel.add(titlePanel, BorderLayout.NORTH);


        //Center Panel Setup
        JPanel centerPanel = new JPanel();
        centerPanel.setMinimumSize(new Dimension(1200, 500));
        centerPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        centerPanel.setLayout(new GridLayout(15, 1));


        //Setup Exercise Name
        FlatLabel name = new FlatLabel();
        name.setText("Exercise Name");
        name.putClientProperty(FlatClientProperties.STYLE, "" + "font:regular +6");
        centerPanel.add(name);

        FlatTextField nameField = new FlatTextField();
        nameField.setMinimumSize(new Dimension(200, 12));
        nameField.setMaximumSize(new Dimension(200, 12));
        nameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Name");
        centerPanel.add(nameField);


        //Setup Exercise Description
        FlatLabel description = new FlatLabel();
        description.setText("Exercise Description");
        description.putClientProperty(FlatClientProperties.STYLE, "" + "font:regular +6");
        centerPanel.add(description);

        FlatTextField descriptionField = new FlatTextField();
        descriptionField.setMinimumSize(new Dimension(200, 12));
        descriptionField.setMaximumSize(new Dimension(200, 12));
        descriptionField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Description");
        centerPanel.add(descriptionField);


        //Setup Exercise Type
        JLabel dropdownTitle = new JLabel("Exercise Type");
        dropdownTitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:regular +6");
        centerPanel.add(dropdownTitle);

        String[] options = {"Select Type", "Sets", "Sets w/ Weight", "Sets w/o Weight"};
        JComboBox<String> dropdown = new JComboBox<>(options);
        dropdown.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Select Type");
        dropdown.setBackground(new Color(200,200,200));
        dropdown.setForeground(new Color(120, 120, 120));
        centerPanel.add(dropdown);

        mainPanel.add(centerPanel, BorderLayout.CENTER);


        //Setup Rep Amount
        JLabel repAmount = new JLabel("Rep Amount");
        repAmount.putClientProperty(FlatClientProperties.STYLE, "" + "font:regular +6");
        centerPanel.add(repAmount);

        NewWorkoutView.NumberTextField RepAmountField = new NewWorkoutView.NumberTextField();
        RepAmountField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Rep Amount");
        centerPanel.add(RepAmountField);

        //Setup Weight Amount
        JLabel weightAmount = new JLabel("Weight Amount");
        weightAmount.putClientProperty(FlatClientProperties.STYLE, "" + "font:regular +6");
        centerPanel.add(weightAmount);

        NewWorkoutView.NumberTextField WeightAmountField = new NewWorkoutView.NumberTextField();
        WeightAmountField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Weight Amount");
        centerPanel.add(WeightAmountField);


        //Setup Submit Button
        FlatButton submitButton = new FlatButton();
        submitButton.setText("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {

                if(nameField.getText().isEmpty() || descriptionField.getText().isEmpty()
                        || repAmount.getText().isEmpty() || weightAmount.getText().isEmpty()
                        || dropdown.getSelectedIndex() == 0) {

                    JOptionPane.showMessageDialog(null,
                            "All fields must be filled.");

                } else {
                    String Name = nameField.getText();
                    String Description = descriptionField.getText();
                    int RepAmount = Integer.parseInt(RepAmountField.getText());
                    double WeightAmount = Double.parseDouble(WeightAmountField.getText());
                    int type = dropdown.getSelectedIndex();
                    CreateExercise exercise = new CreateExercise () ;
                    Exercise newexercise = exercise.CreateExerciseCall
                            (Name, Description, type, RepAmount, WeightAmount);

                    Main.setWindow("Workout");
                    WorkoutView.setView("ExerciseLibrary");
                }
            }
        });


        JLabel space = new JLabel();
        space.setBackground(new Color(20,20,20));
        centerPanel.add(space);
        centerPanel.add(submitButton);
        add(mainPanel, "growy, pushy");

    }

}



