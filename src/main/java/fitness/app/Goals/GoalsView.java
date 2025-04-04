package fitness.app.Goals;

import javax.swing.*;

public class GoalsView {
    private static JFrame mainPanel;

    public GoalsView() {
        mainPanel = new JFrame();

    }

    public static JFrame getGoalsView() {
        return mainPanel;
    }
}
