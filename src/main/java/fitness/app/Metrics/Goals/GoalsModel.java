package fitness.app.Metrics.Goals;

import com.kitfox.svg.SVGUniverse;
import com.kitfox.svg.app.beans.SVGIcon;
import fitness.app.Utility.Databases.DatabaseManager;
import fitness.app.Utility.Databases.GoalsDB;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.Calendar;
import java.util.Date;

public class GoalsModel {
    private String goal;
    private Date deadline;
    private static SVGUniverse images = new SVGUniverse();
    private GoalsDB goalsDB = DatabaseManager.getGoalsDB();

    public static SVGIcon getIcon(String iconName) {
        String filePath = "src/main/resources/Icons/" + iconName + ".svg";
        FileInputStream inFS;

        try {
            inFS = new FileInputStream(new File(filePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        URI imageURI;
        try {
            imageURI = images.loadSVG(inFS, iconName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SVGIcon output = new SVGIcon();
        output.setSvgUniverse(images);
        output.setSvgURI(imageURI);
        output.setAntiAlias(true);
        output.setPreferredSize(new Dimension(32, 32));

        return output;
    }
    public GoalsModel(String goal, Date deadline) {
        this.goal = goal;
        this.deadline = deadline;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getGoal() {
        return goal;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getDeadline() {
        return deadline;
    }

    public static String verifyDistance(Double dist) {
        if( dist >= 0 && dist <= 10000)
        {
            return null;
        }
        return "Bad Input";
    }
    public static String verifyDate(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "Please enter a date.";
        }

        String trimmedInput = input.trim();

        // Check exact format first: MM/DD/YYYY
        if (!trimmedInput.matches("^\\d{2}/\\d{2}/\\d{4}$")) {
            return "Invalid date format. Please use MM/dd/yyyy.";
        }

        String[] parts = trimmedInput.split("/");
        int day = Integer.parseInt(parts[1]);
        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[2]);

        // Validate individual values
        if (month < 1 || month > 12) {
            return "Invalid month.";
        }

        if (day < 1 || day > 31) {
            return "Invalid day.";
        }

        // Use Calendar to strictly validate day-month-year
        Calendar cal = Calendar.getInstance();
        cal.setLenient(false);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);

        try {
            cal.getTime();
        } catch (Exception e) {
            return "Invalid date. Please check day and month values.";
        }

        // Check if it's in the future
        Date inputDate = cal.getTime();
        Date today = new Date();

        if (inputDate.after(today)) {
            return null; // Valid
        } else {
            return "Please enter a future date.";
        }
    }

}
