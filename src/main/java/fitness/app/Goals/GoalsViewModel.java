package fitness.app.Goals;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GoalsViewModel {
    private static GoalsView goalsView;

    public static JFrame getGoalsView() {
        //goalsView = new GoalsView();
        //return goalsView.getGoalsView();
        return null;
    }
    public static String verifyDistance(Double dist) {
        if( dist >= 0 && dist <= 10000)
        {
            return null;
        }
        return "Bad Input";
    }

    public static String verifyDate(String d) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        //String formattedDate = sdf.format(d);
        sdf.setLenient(true);
        try {
            Date date = sdf.parse(d);
            Date now = new Date();
            if (date.after(now)) {
                return null;
            } else {
                return "Please enter a future Date";
            }
        } catch (ParseException e) {
            return "Bad Date";
        }
    }
}
