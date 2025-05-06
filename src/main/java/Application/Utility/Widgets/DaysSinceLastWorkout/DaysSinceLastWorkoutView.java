package Application.Utility.Widgets.DaysSinceLastWorkout;

import Application.Databases.WorkoutLogDB;
import Application.Utility.Objects.Workout;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DaysSinceLastWorkoutView extends JPanel {
    private JLabel daysCounter;
    private String username;
    private WorkoutLogDB workoutLogDB;

    public DaysSinceLastWorkoutView(String username) {
        this.username = username;
        this.workoutLogDB = new WorkoutLogDB();

        setLayout(new MigLayout("wrap, fillx, insets 20", "center", "center"));
        putClientProperty(FlatClientProperties.STYLE, "arc:20; background:@secondaryBackground");

        JLabel title = new JLabel("Days Since Last Workout");
        title.putClientProperty(FlatClientProperties.STYLE, "font: +24");

        daysCounter = new JLabel("--");
        daysCounter.putClientProperty(FlatClientProperties.STYLE, "font: +90");

        add(title, "align center");
        add(daysCounter, "align center");

        // Calculate and update days since last workout
        updateDaysCounter();
    }

    public void updateDaysCounter() {
        try {
            Workout mostRecentWorkout = workoutLogDB.getMostRecentWorkout(username);

            if (mostRecentWorkout != null) {
                // Parse the date from the workout
                String dateStr = mostRecentWorkout.getDate();
                LocalDate workoutDate = parseDate(dateStr);
                LocalDate today = LocalDate.now();

                // Calculate days difference
                int daysSince = (int) ChronoUnit.DAYS.between(workoutDate, today);

                // Update the counter
                daysCounter.setText(String.valueOf(daysSince));

            } else {
                // No workout found - handle this case
                daysCounter.setText("0");
                daysCounter.setForeground(new Color(150, 150, 150)); // Gray
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving most recent workout: " + e.getMessage());
            daysCounter.setText("!");
            daysCounter.setForeground(Color.RED);
        }
    }

    private LocalDate parseDate(String dateStr) {
        // Adjust the formatter pattern based on your actual date format in the database
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(dateStr, formatter);
        } catch (Exception e) {
            // Try alternative format if the first one fails
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                return LocalDate.parse(dateStr.split("T")[0]);
            } catch (Exception ex) {
                System.err.println("Error parsing date: " + e.getMessage());
                return LocalDate.now(); // Default to today
            }
        }
    }

    // Method to refresh the widget (can be called after workout is added/updated)
    public void refresh() {
        updateDaysCounter();
    }
}