package fitness.app.Goals;

import javax.swing.*;

public class GoalsViewModel {
    private static GoalsView goalsView;

    public static JFrame getGoalsView() {
        goalsView = new GoalsView();
        return goalsView.getGoalsView();
    }
}
