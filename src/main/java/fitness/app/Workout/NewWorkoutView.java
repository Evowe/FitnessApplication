package fitness.app.Workout;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.TableColumn;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;
import com.formdev.flatlaf.fonts.roboto_mono.FlatRobotoMonoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import fitness.app.ExerciseLibrary.ExerciseLibraryModel;
import fitness.app.Main;
import fitness.app.Objects.DatabaseManager;
import fitness.app.Objects.Databases.ExerciseDB;
import fitness.app.Objects.Exercise;
import fitness.app.Objects.Workout;
import fitness.app.Widgets.SideMenu.SideMenuView;
import net.miginfocom.layout.Grid;
import net.miginfocom.swing.MigLayout;

public class NewWorkoutView extends JPanel {
	//private static JPanel mainPanel;
	
	public NewWorkoutView() {
		//Setup Main Panel Layout
		setLayout(new MigLayout("insets 20", "left", "top"));
		putClientProperty(FlatClientProperties.STYLE, "background:@background");


		//Add Navigation Menu
		add(new SideMenuView(), "growy, pushy");

		//Setup Main Panel
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
		main.putClientProperty(FlatClientProperties.STYLE, "background:@background");

		//North Panel Setup - Title
		JPanel titlePanel = new JPanel();
		titlePanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
		titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		FlatLabel title = new FlatLabel();
		title.setText("Create New Workout");
		title.putClientProperty(FlatClientProperties.STYLE, "font:bold +25");
		titlePanel.add(title);

		main.add(titlePanel, BorderLayout.NORTH);


		//Center Panel Setup - Exercise List/Table
		JTable table = new JTable(ExerciseLibraryModel.getExerciseData(), ExerciseLibraryModel.getExerciseColums());
		table.setRowHeight(75);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setRowSelectionAllowed(true);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(1200, 300));
		scrollPane.setMaximumSize(new Dimension(1200, 300));


		JPanel tablePanel = new JPanel();
		tablePanel.setBackground(Color.BLACK);
		tablePanel.setLayout(new GridLayout(2,1));
		JPanel workoutFieldsPanel = new JPanel();
		workoutFieldsPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
		workoutFieldsPanel.setLayout(new GridLayout(8,1));
		tablePanel.add(scrollPane);

		main.add(tablePanel, BorderLayout.CENTER);
		Workout workout = new Workout();

		FlatButton addExercise = new FlatButton();
		addExercise.setText("Add Exercise to Workout");
		addExercise.addActionListener(e -> {
			int[] selectedRows = table.getSelectedRows();
			for (int i = 0; i < selectedRows.length; i++) {
				ExerciseDB exerciseDB = DatabaseManager.getExerciseDB();
                List<Exercise> exercises = null;
                try {
                    exercises = exerciseDB.getAllExercises();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                Exercise exercise = exercises.get(selectedRows[i]);
				workout.addExercise(exercise);
			}
		});


		workoutFieldsPanel.add(addExercise);

		JLabel name = new JLabel("Workout Name");
		name.putClientProperty(FlatClientProperties.STYLE, "font:+6");
		workoutFieldsPanel.add(name);

		JTextField nameField = new JTextField();
		nameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Workout Name");
		workoutFieldsPanel.add(nameField);

		JLabel description = new JLabel("Workout Description");
		description.putClientProperty(FlatClientProperties.STYLE, "font:+6");
		workoutFieldsPanel.add(description);

		JTextField descriptionField = new JTextField();
		descriptionField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Workout Description");
		workoutFieldsPanel.add(descriptionField);

		JLabel duration = new JLabel("Workout Duration");
		duration.putClientProperty(FlatClientProperties.STYLE, "font:+6");
		workoutFieldsPanel.add(duration);

		JTextField durationField = new JTextField();
		durationField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Workout Duration");
		workoutFieldsPanel.add(durationField);

		FlatButton saveWorkout = new FlatButton();
		saveWorkout.setText("Save Workout");
		saveWorkout.addActionListener(e -> {
			workout.setDescription(descriptionField.getText());
			workout.setDuration(Integer.parseInt(durationField.getText()));
			workout.setTitle(nameField.getText());
			Main.setWindow("WorkoutLibrary" );
		});

		workoutFieldsPanel.add(saveWorkout);

		tablePanel.add(workoutFieldsPanel);




		//Add Main Panel
		add(main, "growy, pushy");

		//ANJANS CODE DO NOT TOUCH - ranch to ranch
		/*

		//Init view with mig layout
		mainPanel = new JPanel(new MigLayout());
		
		//Workout Name Text Field
		JPanel workoutName = new JPanel(new MigLayout("", "[grow, push]"));
		workoutName.setLayout(new BoxLayout(workoutName, BoxLayout.X_AXIS));
		
		JLabel textFieldLabel = new JLabel("Enter Workout Name ");
		textFieldLabel.setFont(new Font("Arial", Font.PLAIN, 42));
		
		JTextField nameField = new JTextField();
		nameField.setFont(new Font("Arial", Font.PLAIN, 42));
				
		workoutName.add(textFieldLabel);
		workoutName.add(nameField);
		
		//TODO: Add attributes based on account, need to implement user roles
		
		//Add exercises button
		//TODO: Add icon
		//TODO: Add action listener
		FlatButton nextButton = new FlatButton();
		nextButton.setText("Add Exercises ->");
		
		
		
		//Add components to main window
		mainPanel.add(workoutName, "aligny top, growx, push");
		//TODO: Add padding to button
		
		mainPanel.add(nextButton, "aligny bottom, alignx right");
		
		
		//Testing
		workoutName.setBorder(new LineBorder(Color.red, 2));


		 */
	}
		
}
