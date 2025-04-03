package fitness.app.Widgets.Calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

public class CalendarModel {
    private static class Calendar {
        private ArrayList<String[]> calendar;

        private void initCalendar(LocalDate input) {
            calendar = new ArrayList<>();
            LocalDate date = input;

            //Day labels
            calendar.add(new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"});
            while (date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                date = date.minusDays(1);
            }

            do {
                String[] week = new String[7];
                for (int i = 0; i < 7; ++i) {
                    week[i] = toString().valueOf(date.getDayOfMonth());
                    date = date.plusDays(1);
                }
                calendar.add(week);
            } while (date.getMonth() == input.getMonth());
        }

        public Calendar() {
            LocalDate date = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
            initCalendar(date);
        }

        public Calendar(YearMonth yearMonth) {
            LocalDate date = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
            initCalendar(date);
        }

        @Override
        public String toString() {
            StringBuilder output = new StringBuilder();
            for (String[] week : calendar) {
                for (String day : week) {
                    output.append(day + "\t");
                }
                output.append("\n");
            }

            return output.toString();
        }
    }
    private YearMonth yearMonth;
    private Calendar calendar;

    public CalendarModel() {
        yearMonth = YearMonth.now();
        calendar = new Calendar();
    }

    public CalendarModel(int year, int month) {
        yearMonth = YearMonth.of(year, month);
        calendar = new Calendar(yearMonth);
    }

    public void setYearMonth(int year, int month) {
        yearMonth = YearMonth.of(year, month);
        calendar = new Calendar(yearMonth);
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public ArrayList<String[]> getCalendar() {
        return calendar.calendar;
    }

    @Override
    public String toString() {
        return yearMonth.getMonth() + " " + yearMonth.getYear() + "\n" + calendar.toString();
    }
}
