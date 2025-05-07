package Application.Utility.Widgets.DaysSinceLastWorkout;

import Application.Databases.WorkoutLogDB;
import Application.Utility.Objects.Workout;
import com.formdev.flatlaf.FlatClientProperties;

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

        setLayout(new BorderLayout());
        putClientProperty(FlatClientProperties.STYLE, "arc:20; background:@secondaryBackground");

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false); // Make it transparent

        contentPanel.add(Box.createVerticalGlue());

        JLabel title = new JLabel("Days Since Last Workout");
        title.putClientProperty(FlatClientProperties.STYLE, "font: +24");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        daysCounter = new JLabel("--");
        daysCounter.putClientProperty(FlatClientProperties.STYLE, "font: +90");
        daysCounter.setAlignmentX(Component.CENTER_ALIGNMENT);
        daysCounter.setHorizontalAlignment(SwingConstants.CENTER);

        contentPanel.add(title);
        contentPanel.add(Box.createVerticalStrut(10)); // Space between title and counter
        contentPanel.add(daysCounter);

        contentPanel.add(Box.createVerticalGlue());

        add(contentPanel, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

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

                daysCounter.setText(String.valueOf(daysSince));

            } else {
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
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(dateStr, formatter);
        } catch (Exception e) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                return LocalDate.parse(dateStr.split("T")[0]);
            } catch (Exception ex) {
                System.err.println("Error parsing date: " + e.getMessage());
                return LocalDate.now(); // Default to today
            }
        }
    }

    public void refresh() {
        updateDaysCounter();
    }
}