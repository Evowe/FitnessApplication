package Application.TheSwoleSection.WorkoutPlans;

import Application.Main;
import Application.TheSwoleSection.TrainerCreatedWorkoutPlan.CreateWorkoutPlanViewModel;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ModifiyWorkoutPlan extends JPanel {
    private CreateWorkoutPlanViewModel viewModel;
    private WorkoutPlansViewModel viewModelWP;
    private JTable table;

    public ModifiyWorkoutPlan(JTable owner) {
        viewModel = new CreateWorkoutPlanViewModel();
        viewModelWP = new WorkoutPlansViewModel();
        table = owner;

        WorkoutPlan plan = viewModelWP.getWorkoutPlan(table.getValueAt(table.getSelectedRow(), 0).toString());

        //Setup Main Panel Layout
        //setLayout(new MigLayout("insets 20", "left", "top"));
        setLayout(new MigLayout("fill, insets 20", "[]20[]", "center"));

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
        title.setText("Modifiy Workout Plan");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +25");
        titlePanel.add(title);

        main.add(titlePanel, BorderLayout.NORTH);


        //Center Panel Setup - questions

        JPanel center  = new JPanel();
        center.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        center.setLayout(new GridLayout(1, 2, 50, 0));


        JPanel centerLeft = new JPanel();
        centerLeft.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        centerLeft.setLayout(new GridLayout(14, 1));

        //Name title & Text Field Setup
        JLabel name = new JLabel("Name");
        name.putClientProperty(FlatClientProperties.STYLE, "font:+6");
        centerLeft.add(name);

        JLabel nameField = new JLabel(plan.getName());
        //nameField.setBackground();
        nameField.setToolTipText("Cannot modify workout plan name");
        nameField.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");
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

        JTextField durationField = new JTextField();
        durationField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Workout Plan Duration (in weeks)");
        centerLeft.add(durationField);


        //Intensity title & Text Field Setup
        JLabel intensity = new JLabel("Intensity");
        intensity.putClientProperty(FlatClientProperties.STYLE, "font:+6");
        centerLeft.add(intensity);

        JTextField intensityField = new JTextField();
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

        java.util.List<Workout> workoutList;
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


        if(plan.getName() == null){
            plan.setName("");
        }
        nameField.setText(plan.getName());
        if(plan.getGoal() == null){
            plan.setGoal("");
        }
        goalField.setText(plan.getGoal());
        if(plan.getDurationInWeeks() == null){
            plan.setDurationInWeeks(0);
        }
        durationField.setText(plan.getDurationInWeeks().toString());
        if(plan.getIntensity() == null){
            plan.setIntensity(0);
        }
        intensityField.setText(plan.getIntensity().toString());

        System.out.println("PRINTINT" + plan.getWorkoutSchedule().toString());

        List<Workout> workouts = plan.getWorkoutSchedule();


        mondayWorkoutComboBox.setSelectedItem(workouts.get(0));
        tuesdayWorkoutComboBox.setSelectedItem(workouts.get(1));
        wednesdayWorkoutComboBox.setSelectedItem(workouts.get(2));
        thursdayWorkoutComboBox.setSelectedItem(workouts.get(3));
        fridayWorkoutComboBox.setSelectedItem(workouts.get(4));
        saturdayWorkoutComboBox.setSelectedItem(workouts.get(5));
        sundayWorkoutComboBox.setSelectedItem(workouts.get(6));


        center.add(centerLeft);
        center.add(centerRight);
        main.add(center, BorderLayout.CENTER);


        //South Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(50, 50));
        buttonPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        FlatButton submitButton = new FlatButton();
        submitButton.setMinimumHeight(35);
        submitButton.setMinimumWidth(200);
        submitButton.setText("Submit Changes");
        WorkoutPlan workoutPlan = new WorkoutPlan();
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Main.getCurrentUser().getUsername().equals(plan.getAuthor())) {
                    if (nameField.getText().isEmpty() || goalField.getText().isEmpty()
                            || intensityField.getText().isEmpty() || durationField.getText().isEmpty()) {

                        JOptionPane.showMessageDialog(null,
                                "All fields must be filled.");

                    } else if (nameField.getText().length() > 100 || goalField.getText().length() > 100
                            || intensityField.getText().length() > 100 || durationField.getText().length() > 100) {

                        JOptionPane.showMessageDialog(null,
                                "No field can exceed 100 characters.");

                    } else {
                        plan.setGoal(goalField.getText());
                        plan.setDurationInWeeks(Integer.parseInt(durationField.getText()));
                        plan.setIntensity(Integer.parseInt(intensityField.getText()));

                        List<Workout> workoutSchedule = new ArrayList<>();

                        workoutSchedule.add((Workout) mondayWorkoutComboBox.getSelectedItem());
                        workoutSchedule.add((Workout) tuesdayWorkoutComboBox.getSelectedItem());
                        workoutSchedule.add((Workout) wednesdayWorkoutComboBox.getSelectedItem());
                        workoutSchedule.add((Workout) thursdayWorkoutComboBox.getSelectedItem());
                        workoutSchedule.add((Workout) fridayWorkoutComboBox.getSelectedItem());
                        workoutSchedule.add((Workout) saturdayWorkoutComboBox.getSelectedItem());
                        workoutSchedule.add((Workout) sundayWorkoutComboBox.getSelectedItem());

                        plan.setWorkoutSchedule(workoutSchedule);
                        viewModelWP.updateWorkoutPlan(plan);

                        JOptionPane.showMessageDialog(table, "Changes saved");

                        Main.setWindow("Workout");
                        WorkoutView.setView("WorkoutPlans");
                    }

                }
                else {
                    JOptionPane.showMessageDialog(table, "Error, you do not own this plan.");
                    Main.setWindow("Workout");
                    WorkoutView.setView("WorkoutPlans");
                }
            }
        });

        buttonPanel.add(submitButton);

        main.add(buttonPanel, BorderLayout.SOUTH);


        add(main, "growy, pushy");
    }







}
