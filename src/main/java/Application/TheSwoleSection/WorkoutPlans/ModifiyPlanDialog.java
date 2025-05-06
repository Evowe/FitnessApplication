package Application.TheSwoleSection.WorkoutPlans;

import Application.TheSwoleSection.TrainerCreatedWorkoutPlan.CreateWorkoutPlanViewModel;
import Application.TheSwoleSection.WorkoutView;
import Application.Utility.Objects.Workout;
import Application.Utility.Objects.WorkoutPlan;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ModifiyPlanDialog extends JDialog {
    private JTable table;
    private CreateWorkoutPlanViewModel viewModel;
    private WorkoutPlansViewModel viewModelWP;

    public ModifiyPlanDialog(JTable owner) {
        super(SwingUtilities.windowForComponent(owner));
        viewModel = new CreateWorkoutPlanViewModel();
        viewModelWP = new WorkoutPlansViewModel();
        table = owner;
        createGUI();
    }

    private void createGUI() {
        setPreferredSize(new Dimension(1000, 1000));
        setLayout(new BorderLayout());
        setTitle("Modifiy Plan");


        WorkoutPlan plan = viewModelWP.getWorkoutPlan(table.getValueAt(table.getSelectedRow(), 0).toString());

        JPanel fields = new JPanel();
        fields.setLayout(new GridLayout(11, 1, 0, 2));


        //NAME PANEL
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new GridLayout(2, 1));

        JLabel name = new JLabel("Name:");
        name.putClientProperty(FlatClientProperties.STYLE, "font:+4");
        namePanel.add(name);

        JLabel nameField = new JLabel(plan.getName());
        namePanel.add(nameField);

        fields.add(namePanel);

        //GOAL PANEL
        JPanel goalPanel = new JPanel();
        goalPanel.setLayout(new GridLayout(2, 1));

        JLabel goal = new JLabel("Goal:");
        goal.putClientProperty(FlatClientProperties.STYLE, "font:+4");
        goalPanel.add(goal);

        FlatTextField goalField = new FlatTextField();
        goalField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Goal");
        goalPanel.add(goalField);

        fields.add(goalPanel);

        //DURATION PANEL
        JPanel durationPanel = new JPanel();
        durationPanel.setLayout(new GridLayout(2, 1));

        JLabel duration = new JLabel("Duration:");
        duration.putClientProperty(FlatClientProperties.STYLE, "font:+4");
        durationPanel.add(duration);

        FlatTextField durationField = new FlatTextField();
        durationField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Duration");
        durationPanel.add(durationField);

        fields.add(durationPanel);


        //Intensity Panel
        JPanel intensityPanel = new JPanel();
        intensityPanel.setLayout(new GridLayout(2, 1));

        JLabel intensity = new JLabel("Intensity:");
        intensity.putClientProperty(FlatClientProperties.STYLE, "font:+4");
        intensityPanel.add(intensity);

        FlatTextField intensityField = new FlatTextField();
        intensityField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Intensity");
        intensityPanel.add(intensityField);

        fields.add(intensityPanel);

        /// Days of the week
        //Monday
        JPanel monday = new JPanel();
        //monday.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        monday.setLayout(new GridLayout(2, 1));
        JLabel mondayLabel = new JLabel("Monday");
        mondayLabel.putClientProperty(FlatClientProperties.STYLE, "font:+4");
        monday.add(mondayLabel);
        JComboBox<Workout> mondayWorkoutComboBox = new JComboBox<>();

        List<Workout> workoutList;
        workoutList = viewModel.getAllWorkouts();


        workoutList.stream().forEach(workout -> {
            mondayWorkoutComboBox.addItem(workout);
        });
        monday.add(mondayWorkoutComboBox);

        fields.add(monday);

        //Tuesday
        JPanel tuesday = new JPanel();
        //tuesday.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        tuesday.setLayout(new GridLayout(2, 1));
        JLabel tuesdayLabel = new JLabel("Tuesday");
        tuesdayLabel.putClientProperty(FlatClientProperties.STYLE, "font:+4");
        tuesday.add(tuesdayLabel);
        JComboBox<Workout> tuesdayWorkoutComboBox = new JComboBox<>();

        workoutList.stream().forEach(workout -> {
            tuesdayWorkoutComboBox.addItem(workout);
        });

        tuesday.add(tuesdayWorkoutComboBox);
        fields.add(tuesday);

        //Wednesday
        JPanel wednesday = new JPanel();
        //wednesday.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        wednesday.setLayout(new GridLayout(2, 1));
        JLabel wednesdayLabel = new JLabel("Wednesday");
        wednesdayLabel.putClientProperty(FlatClientProperties.STYLE, "font:+4");
        wednesday.add(wednesdayLabel);
        JComboBox<Workout> wednesdayWorkoutComboBox = new JComboBox<>();

        workoutList.stream().forEach(workout -> {
            wednesdayWorkoutComboBox.addItem(workout);
        });

        wednesday.add(wednesdayWorkoutComboBox);

        fields.add(wednesday);

        //Thursday
        JPanel thursday = new JPanel();
        //thursday.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        thursday.setLayout(new GridLayout(2, 1));
        JLabel thursdayLabel = new JLabel("Thursday");
        thursdayLabel.putClientProperty(FlatClientProperties.STYLE, "font:+4");
        thursday.add(thursdayLabel);
        JComboBox<Workout> thursdayWorkoutComboBox = new JComboBox<>();

        workoutList.stream().forEach(workout -> {
            thursdayWorkoutComboBox.addItem(workout);
        });

        thursday.add(thursdayWorkoutComboBox);

        fields.add(thursday);

        //Friday
        JPanel friday = new JPanel();
        //friday.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        friday.setLayout(new GridLayout(2, 1));
        JLabel fridayLabel = new JLabel("Friday");
        fridayLabel.putClientProperty(FlatClientProperties.STYLE, "font:+4");
        friday.add(fridayLabel);
        JComboBox<Workout> fridayWorkoutComboBox = new JComboBox<>();

        workoutList.stream().forEach(workout -> {
            fridayWorkoutComboBox.addItem(workout);
        });

        friday.add(fridayWorkoutComboBox);

        fields.add(friday);

        //Saturday
        JPanel saturday = new JPanel();
        //saturday.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        saturday.setLayout(new GridLayout(2, 1));
        JLabel saturdayLabel = new JLabel("Saturday");
        saturdayLabel.putClientProperty(FlatClientProperties.STYLE, "font:+4");
        saturday.add(saturdayLabel);
        JComboBox<Workout> saturdayWorkoutComboBox = new JComboBox<>();

        workoutList.stream().forEach(workout -> {
            saturdayWorkoutComboBox.addItem(workout);
        });

        saturday.add(saturdayWorkoutComboBox);

        fields.add(saturday);

        //Sunday
        JPanel sunday = new JPanel();
        //sunday.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        sunday.setLayout(new GridLayout(2, 1));
        JLabel sundayLabel = new JLabel("Sunday");
        sundayLabel.putClientProperty(FlatClientProperties.STYLE, "font:+4");
        sundayLabel.setMinimumSize(new Dimension(100, 20));
        sunday.add(sundayLabel);
        JComboBox<Workout> sundayWorkoutComboBox = new JComboBox<>();

        workoutList.stream().forEach(workout -> {
            sundayWorkoutComboBox.addItem(workout);
        });

        sunday.add(sundayWorkoutComboBox);

        fields.add(sunday);


        add(fields, BorderLayout.CENTER);

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


        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));


        FlatButton submitButton = new FlatButton();
        submitButton.setText("Submit Changes");


        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                dispose();

                WorkoutView.setView("WorkoutPlans");

                JOptionPane.showMessageDialog(table, "Changes saved");
            }
        });

        buttonPane.add(submitButton);

        add(buttonPane, BorderLayout.SOUTH);

        pack();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}

