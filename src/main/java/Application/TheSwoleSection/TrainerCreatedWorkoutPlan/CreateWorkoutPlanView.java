package Application.TheSwoleSection.TrainerCreatedWorkoutPlan;

import Application.Main;
import Application.TheSwoleSection.CreateWorkout.NewWorkoutView;
import Application.TheSwoleSection.WorkoutView;
import Application.Utility.Objects.Workout;
import Application.Utility.Objects.WorkoutPlan;
import Application.Utility.Widgets.SideMenu.SideMenuView;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CreateWorkoutPlanView extends JPanel {
    private CreateWorkoutPlanViewModel viewModel;

    public CreateWorkoutPlanView() {
        viewModel = new CreateWorkoutPlanViewModel();
        //Setup Main Panel Layout
        setLayout(new MigLayout("insets 20", "left", "top"));
        putClientProperty(FlatClientProperties.STYLE, "background:@background");

        //Add navigation bar
        add(new SideMenuView(), "growy, pushy");


        //Setup Main Panel
        JPanel main = new JPanel();
        main.setMinimumSize(new Dimension(1250, 700));
        main.setLayout(new BorderLayout());
        main.putClientProperty(FlatClientProperties.STYLE, "background:@background");

        //North Panel Setup - Title
        JPanel titlePanel = new JPanel();
        titlePanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        FlatLabel title = new FlatLabel();
        title.setText("Create Workout Plan");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +25");
        titlePanel.add(title);

        main.add(titlePanel, BorderLayout.NORTH);


        //Center Panel Setup - questions

        JPanel center  = new JPanel();
        center.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        center.setLayout(new GridLayout(1, 2, 50, 0));


        JPanel centerLeft = new JPanel();
        centerLeft.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        centerLeft.setLayout(new GridLayout(9, 1));

            //Name title & Text Field Setup
        JLabel name = new JLabel("Name");
        name.putClientProperty(FlatClientProperties.STYLE, "font:+6");
        centerLeft.add(name);

        JTextField nameField = new JTextField();
        nameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Workout Plan Name");
        centerLeft.add(nameField);

            //Goal title & Text Field Setup
        JLabel goal = new JLabel("Goal");
        goal.putClientProperty(FlatClientProperties.STYLE, "font:+6");
        centerLeft.add(goal);

        JTextField goalField = new JTextField();
        goalField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter a description of the goal of this " +
                "workout plan");
        centerLeft.add(goalField);


            //Duration title & Text Field Setup
        JLabel duration = new JLabel("Duration");
        duration.putClientProperty(FlatClientProperties.STYLE, "font:+6");
        centerLeft.add(duration);

        NewWorkoutView.NumberTextField durationField = new NewWorkoutView.NumberTextField();
        durationField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Workout Plan Duration (in weeks)");
        centerLeft.add(durationField);


            //Intensity title & Text Field Setup
        JLabel intensity = new JLabel("Intensity");
        intensity.putClientProperty(FlatClientProperties.STYLE, "font:+6");
        centerLeft.add(intensity);

        NewWorkoutView.NumberTextField intensityField = new NewWorkoutView.NumberTextField();
        intensityField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Intensity Level");
        centerLeft.add(intensityField);


        //CenterRight Panel
        JPanel centerRight = new JPanel();
        centerRight.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        centerRight.setLayout(new GridLayout(7, 1, 10, 10));

        //Monday
        JPanel monday = new JPanel();
        monday.setMaximumSize(new Dimension(100, 20));
        monday.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        monday.setLayout(new BorderLayout());
        JLabel mondayLabel = new JLabel("Monday");
        mondayLabel.putClientProperty(FlatClientProperties.STYLE, "font:+4");
        monday.add(mondayLabel, BorderLayout.NORTH);
        JComboBox<Workout> mondayWorkoutComboBox = new JComboBox<>();

        List<Workout> workoutList;
        workoutList = viewModel.getAllWorkouts();


        workoutList.stream().forEach(workout -> {
            mondayWorkoutComboBox.addItem(workout);
        });
        monday.add(mondayWorkoutComboBox, BorderLayout.CENTER);

        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(50, 20));
        spacer.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        monday.add(spacer, BorderLayout.EAST);

        //monday.add(spacer);
        centerRight.add(monday);

        //Tuesday
        JPanel tuesday = new JPanel();
        tuesday.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        tuesday.setLayout(new BorderLayout());
        JLabel tuesdayLabel = new JLabel("Tuesday");
        tuesdayLabel.putClientProperty(FlatClientProperties.STYLE, "font:+4");
        tuesday.add(tuesdayLabel, BorderLayout.NORTH);
        JComboBox<Workout> tuesdayWorkoutComboBox = new JComboBox<>();

        workoutList.stream().forEach(workout -> {
            tuesdayWorkoutComboBox.addItem(workout);
        });

        tuesday.add(tuesdayWorkoutComboBox, BorderLayout.CENTER);

        JPanel spacerT = new JPanel();
        spacerT.setPreferredSize(new Dimension(50, 20));
        spacerT.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        tuesday.add(spacerT, BorderLayout.EAST);

        centerRight.add(tuesday);

        //Wednesday
        JPanel wednesday = new JPanel();
        wednesday.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        wednesday.setLayout(new BorderLayout());
        JLabel wednesdayLabel = new JLabel("Wednesday");
        wednesdayLabel.putClientProperty(FlatClientProperties.STYLE, "font:+4");
        wednesday.add(wednesdayLabel, BorderLayout.NORTH);
        JComboBox<Workout> wednesdayWorkoutComboBox = new JComboBox<>();

        workoutList.stream().forEach(workout -> {
            wednesdayWorkoutComboBox.addItem(workout);
        });

        wednesday.add(wednesdayWorkoutComboBox, BorderLayout.CENTER);

        JPanel spacerW = new JPanel();
        spacerW.setPreferredSize(new Dimension(50, 20));
        spacerW.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        wednesday.add(spacerW, BorderLayout.EAST);
        centerRight.add(wednesday);

        //Thursday
        JPanel thursday = new JPanel();
        thursday.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        thursday.setLayout(new BorderLayout());
        JLabel thursdayLabel = new JLabel("Thursday");
        thursdayLabel.putClientProperty(FlatClientProperties.STYLE, "font:+4");
        thursday.add(thursdayLabel, BorderLayout.NORTH);
        JComboBox<Workout> thursdayWorkoutComboBox = new JComboBox<>();

        workoutList.stream().forEach(workout -> {
            thursdayWorkoutComboBox.addItem(workout);
        });

        thursday.add(thursdayWorkoutComboBox, BorderLayout.CENTER);

        JPanel spacerTh = new JPanel();
        spacerTh.setPreferredSize(new Dimension(50, 20));
        spacerTh.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        thursday.add(spacerTh, BorderLayout.EAST);

        centerRight.add(thursday);

        //Friday
        JPanel friday = new JPanel();
        friday.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        friday.setLayout(new BorderLayout());
        JLabel fridayLabel = new JLabel("Friday");
        fridayLabel.putClientProperty(FlatClientProperties.STYLE, "font:+4");
        friday.add(fridayLabel, BorderLayout.NORTH);
        JComboBox<Workout> fridayWorkoutComboBox = new JComboBox<>();

        workoutList.stream().forEach(workout -> {
            fridayWorkoutComboBox.addItem(workout);
        });

        friday.add(fridayWorkoutComboBox, BorderLayout.CENTER);

        JPanel spacerF = new JPanel();
        spacerF.setPreferredSize(new Dimension(50, 20));
        spacerF.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        friday.add(spacerF, BorderLayout.EAST);

        centerRight.add(friday);

        //Saturday
        JPanel saturday = new JPanel();
        saturday.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        saturday.setLayout(new BorderLayout());
        JLabel saturdayLabel = new JLabel("Saturday");
        saturdayLabel.putClientProperty(FlatClientProperties.STYLE, "font:+4");
        saturday.add(saturdayLabel, BorderLayout.NORTH);
        JComboBox<Workout> saturdayWorkoutComboBox = new JComboBox<>();

        workoutList.stream().forEach(workout -> {
            saturdayWorkoutComboBox.addItem(workout);
        });

        saturday.add(saturdayWorkoutComboBox, BorderLayout.CENTER);


        JPanel spacerS = new JPanel();
        spacerS.setPreferredSize(new Dimension(50, 20));
        spacerS.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        saturday.add(spacerS, BorderLayout.EAST);

        centerRight.add(saturday);

        //Sunday
        JPanel sunday = new JPanel();
        sunday.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        sunday.setLayout(new BorderLayout());
        JLabel sundayLabel = new JLabel("Sunday");
        sundayLabel.putClientProperty(FlatClientProperties.STYLE, "font:+4");
        sundayLabel.setMinimumSize(new Dimension(100, 20));
        sunday.add(sundayLabel, BorderLayout.NORTH);
        JComboBox<Workout> sundayWorkoutComboBox = new JComboBox<>();

        workoutList.stream().forEach(workout -> {
            sundayWorkoutComboBox.addItem(workout);
        });

        sunday.add(sundayWorkoutComboBox, BorderLayout.CENTER);

        JPanel spacerSu = new JPanel();
        spacerSu.setPreferredSize(new Dimension(50, 20));
        spacerSu.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        sunday.add(spacerSu, BorderLayout.EAST);



        centerRight.add(sunday);


        center.add(centerLeft);
        center.add(centerRight);
        main.add(center, BorderLayout.CENTER);


        //South Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        FlatButton submitButton = new FlatButton();
        submitButton.setMinimumHeight(35);
        submitButton.setMinimumWidth(200);
        submitButton.setText("Submit");
        WorkoutPlan workoutPlan = new WorkoutPlan();
        submitButton.addActionListener(e -> {

            if(nameField.getText().isEmpty() || goalField.getText().isEmpty()
                    || intensityField.getText().isEmpty() || durationField.getText().isEmpty()) {

                JOptionPane.showMessageDialog(null,
                        "All fields must be filled.");

            } else if(nameField.getText().length() > 100 || goalField.getText().length() > 100
                    || intensityField.getText().length() > 100 || durationField.getText().length() > 100) {

                JOptionPane.showMessageDialog(null,
                        "No field can exceed 100 characters.");

            } else{
                workoutPlan.setName(nameField.getText());
                workoutPlan.setGoal(goalField.getText());
                workoutPlan.setIntensity(Integer.parseInt(intensityField.getText()));
                workoutPlan.setDurationInWeeks(Integer.parseInt(durationField.getText()));

                List<Workout> workouts = new ArrayList<>();
                workouts.add((Workout) mondayWorkoutComboBox.getSelectedItem());
                workouts.add((Workout) tuesdayWorkoutComboBox.getSelectedItem());
                workouts.add((Workout) wednesdayWorkoutComboBox.getSelectedItem());
                workouts.add((Workout) thursdayWorkoutComboBox.getSelectedItem());
                workouts.add((Workout) fridayWorkoutComboBox.getSelectedItem());
                workouts.add((Workout) saturdayWorkoutComboBox.getSelectedItem());
                workouts.add((Workout) sundayWorkoutComboBox.getSelectedItem());

                workoutPlan.setWorkoutSchedule(workouts);

                viewModel.addWorkoutPlan(workoutPlan);
                Main.setWindow("Workout");
                WorkoutView.setView("WorkoutPlans");
            }

        });
        buttonPanel.add(submitButton);

        main.add(buttonPanel, BorderLayout.SOUTH);

        add(main, "growy, pushy");
    }


}
