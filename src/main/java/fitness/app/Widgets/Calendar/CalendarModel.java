package fitness.app.Widgets.Calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;

public class CalendarModel {
    private LocalDate today;
    private LocalDate firstDay;
    private LocalDate lastDay;

    private YearMonth month;
    private Year year;

    private String[][] calendar;

    public CalendarModel() {
        today = LocalDate.now();
        month = YearMonth.now();
        year = Year.now();

        firstDay = today.with(TemporalAdjusters.firstDayOfMonth());
        lastDay = today.with(TemporalAdjusters.lastDayOfMonth());
        calendar = new String[7][7];

        for (int i = 0; i < 7; ++i) {
            String day = String.valueOf(today.plusDays(i).getDayOfWeek()).substring(0,3);
            System.out.println(day);
        }

        LocalDate date = firstDay.with(TemporalAdjusters.firstDayOfMonth());
        int row = 1;
        int col = date.getDayOfMonth();
    }

    public static void main(String[] args) {
        CalendarModel model = new CalendarModel();
        System.out.println("Today is " + model.today);
        System.out.println("First date: " + model.firstDay);
        System.out.println("Last date: " + model.lastDay);
    }
}
