package Application.Metrics.Goals;

import com.kitfox.svg.app.beans.SVGIcon;

import javax.swing.*;

public class GoalsViewModel {
    private static GoalsView goalsView;
    private static GoalsModel gmodel;

    public static JFrame getGoalsView() {
        //goalsView = new GoalsView();
        //return goalsView.getGoalsView();
        return null;
    }

    public static SVGIcon getIcon(String iconName) {
        return GoalsModel.getIcon(iconName);
    }
    public static String verifyDistance(Double dist) {
        return GoalsModel.verifyDistance(dist);
    }
    public static String verifyDate(String input) {
        return GoalsModel.verifyDate(input);
    }

}
