package fitness.app.Workout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.fonts.roboto_mono.FlatRobotoMonoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import net.miginfocom.swing.MigLayout;

public class NewWorkoutView {
	private static JPanel mainPanel;
	
	private static void createView() {
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
		
	}
		
}
