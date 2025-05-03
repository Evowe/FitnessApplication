package Application.TheSwoleSection.CreateWorkout;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.text.PlainDocument;

import Application.TheSwoleSection.WorkoutView;
import Application.Utility.Widgets.SideMenu.SideMenuView;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;

import Application.TheSwoleSection.ExerciseLibrary.ExerciseLibraryModel;
import Application.Main;
import Application.Databases.DatabaseManager;
import Application.Databases.ExerciseDB;
import Application.Utility.Objects.Exercise;
import Application.Utility.Objects.Workout;
import net.miginfocom.swing.MigLayout;

public class NewWorkoutView extends JPanel {
	//private static JPanel mainPanel;
	NewWorkoutViewModel viewModel;

	public NewWorkoutView() {
		//Setup Main Panel Layout
		viewModel = new NewWorkoutViewModel();
		setLayout(new MigLayout("insets 20", "left", "top"));
		putClientProperty(FlatClientProperties.STYLE, "background:@background");

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
				workout.addExerciseToList(exercise);
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

		NumberTextField durationField = new NumberTextField();
		durationField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Workout Duration");
		workoutFieldsPanel.add(durationField);

		FlatButton saveWorkout = new FlatButton();
		saveWorkout.setText("Save Workout");
		saveWorkout.addActionListener(e -> {
			if(workout.getExerciseList().size() == 0) {
				JOptionPane.showMessageDialog(null,
						"No Exercises Selected. Add Exercise to Workout to proceed.");
			} else if(descriptionField.getText().isEmpty() || nameField.getText().isEmpty() || durationField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null,
						"All fields must be filled.");
			} else{
				workout.setDescription(descriptionField.getText());
				workout.setDuration(Integer.parseInt(durationField.getText()));
				workout.setName(nameField.getText());

				viewModel.addWorkoutToDatabase(workout, Main.getCurrentUser().getUsername());

				Main.setWindow("Workout");
				WorkoutView.setView("WorkoutLibrary");
			}
		});

		workoutFieldsPanel.add(saveWorkout);

		tablePanel.add(workoutFieldsPanel);


		//Add Main Panel
		add(main, "growy, pushy");

	}

	public class NumberTextField extends JTextField {

		public NumberTextField() {
			this.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					char c = e.getKeyChar();
					if (!Character.isDigit(c)) {
						e.consume(); // Ignore non-digit input
					}
				}
			});
		}
	}
		
}
