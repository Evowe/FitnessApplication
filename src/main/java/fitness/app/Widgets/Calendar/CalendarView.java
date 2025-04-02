package fitness.app.Widgets.Calendar;

import javax.swing.*;

public class CalendarView {
    private static JFrame mainPanel;

    public CalendarView() {
        mainPanel = new JFrame();
    }

    public static JFrame getCelendarView() {
        return mainPanel;
    }
}
