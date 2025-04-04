package fitness.app.Widgets.Calendar;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto_mono.FlatRobotoMonoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import fitness.app.Objects.AccountsDB;
import fitness.app.Objects.DatabaseManager;
import fitness.app.Objects.ExerciseDB;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;

public class CalendarViewModel {
    private static CalendarView calendarView;
    private static CalendarModel calendarModel;

    public void setYearMonth(int year, int month) {
        calendarModel.setYearMonth(year, month);
    }

    public static JPanel getCalendarView() {
        calendarView = new CalendarView();
        return calendarView.getCalendarView();
    }

    public static ArrayList<String[]> getCalendar() {
        calendarModel = new CalendarModel();
        return calendarModel.getCalendar();
    }
}
