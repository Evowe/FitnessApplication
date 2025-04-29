package Application.TheSwoleSection.TrainerCreatedWorkoutPlan;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatLabel;
import Application.Utility.Widgets.SideMenu.SideMenuView;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class CreateWorkoutPlanView extends JPanel {

    public CreateWorkoutPlanView() {
        //Setup Main Panel Layout
        setLayout(new MigLayout("insets 20", "left", "top"));
        putClientProperty(FlatClientProperties.STYLE, "background:@background");


        //Add Navigation Menu
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
        center.setLayout(new GridLayout(9, 1));

            //Name title & Text Field Setup
        JLabel name = new JLabel("Name");
        name.putClientProperty(FlatClientProperties.STYLE, "font:+6");
        center.add(name);

        JTextField nameField = new JTextField();
        nameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Workout Plan Name");
        center.add(nameField);

            //Goal title & Text Field Setup
        JLabel goal = new JLabel("Goal");
        goal.putClientProperty(FlatClientProperties.STYLE, "font:+6");
        center.add(goal);

        JTextField gloalField = new JTextField();
        gloalField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter a description of the goal of this" +
                "workout plan");
        center.add(gloalField);


            //Duration title & Text Field Setup
        JLabel duration = new JLabel("Duration");
        duration.putClientProperty(FlatClientProperties.STYLE, "font:+6");
        center.add(duration);

        JTextField durationField = new JTextField();
        durationField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Workout Plan Duration (in weeks)");
        center.add(durationField);


            //Intensity title & Text Field Setup
        JLabel intensity = new JLabel("Intensity");
        intensity.putClientProperty(FlatClientProperties.STYLE, "font:+6");
        center.add(intensity);

        JTextField intensityField = new JTextField();
        intensityField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Intensity Level");
        center.add(intensityField);





        main.add(center, BorderLayout.CENTER);


        add(main, "growy, pushy");
    }


}
