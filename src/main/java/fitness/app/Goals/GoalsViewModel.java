package fitness.app.Goals;

import com.kitfox.svg.app.beans.SVGIcon;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class GoalsViewModel {
    private static GoalsView goalsView;
    private static GoalsModel gmodel;

    public static JFrame getGoalsView() {
        //goalsView = new GoalsView();
        //return goalsView.getGoalsView();
        return null;
    }
    public static void checkDB() throws SQLException {
        gmodel.checkDB();
    }
    public static SVGIcon getIcon(String iconName) {
        return GoalsModel.getIcon(iconName);
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
        cal.set(Calendar.MONTH, month - 1); // Calendar months are 0-based
        cal.set(Calendar.DAY_OF_MONTH, day);

        try {
            cal.getTime(); // Throws if the date is invalid (e.g., 31/02/2025)
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
