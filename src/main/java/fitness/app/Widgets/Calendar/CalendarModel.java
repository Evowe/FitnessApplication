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

        calendar[0][0] = "Sun";
        calendar[0][1] = "Mon";
        calendar[0][2] = "Tue";
        calendar[0][3] = "Wed";
        calendar[0][4] = "Thu";
        calendar[0][5] = "Fri";
        calendar[0][6] = "Sat";

        int row = 1;
        int col = -1;
        switch (firstDay.getDayOfWeek()) {
            case SUNDAY -> {
                col = 0;
            }
            case MONDAY -> {
                col = 1;
            }
            case TUESDAY -> {
                col = 2;
            }
            case WEDNESDAY -> {
                col = 3;
            }
            case THURSDAY -> {
                col = 4;
            }
            case FRIDAY -> {
                col = 5;
            }
            case SATURDAY -> {
                col = 6;
            }
        }

        LocalDate date = firstDay.with(TemporalAdjusters.firstDayOfMonth());
        while (date.getMonth() == month.getMonth()) {
            calendar[row][col] = String.valueOf(date.getDayOfMonth());
            if (col == 6) {
                col = 0;
                ++row;
            }
            else {
                ++col;
            }
            date = date.plusDays(1);
        }
    }

    public static void main(String[] args) {
        CalendarModel model = new CalendarModel();
        System.out.println(model.calendar[1][2]);
    }
}
