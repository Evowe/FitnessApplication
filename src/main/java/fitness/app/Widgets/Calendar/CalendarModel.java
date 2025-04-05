package fitness.app.Widgets.Calendar;

import javax.swing.*;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;

//test
public class CalendarModel {
    private static class CalendarFormat {
        private ArrayList<String[]> calendar;

        private void initCalendar(YearMonth input) {
            calendar = new ArrayList<>();
            LocalDate date = input.atDay(1);

            //Day labels
            calendar.add(new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"});
            while (date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                date = date.minusDays(1);
            }

            //Week labels
            do {
                String[] week = new String[7];
                for (int i = 0; i < 7; ++i) {
                    week[i] = toString().valueOf(date.getDayOfMonth());
                    date = date.plusDays(1);
                }
                calendar.add(week);
            } while (date.getMonth() == input.getMonth());
        }

        //Default Constructor
        public CalendarFormat() {
            initCalendar(YearMonth.now());
        }

        //Custom Constructor
        public CalendarFormat(YearMonth input) {
            initCalendar(input);
        }

        @Override
        public String toString() {
            StringBuilder output = new StringBuilder();
            for (String[] week : calendar) {
                for (String day : week) {
                    output.append(day + "\t");
                }
                if (week != calendar.getLast()) {
                    output.append("\n");
                }
            }
            return output.toString();
        }
    }

    private CalendarFormat format;

    //Default Constructor
    public CalendarModel() {
        format = new CalendarFormat();
    }

    //Custom Constructor
    public CalendarModel(YearMonth input) {
        format = new CalendarFormat(input);
    }


    public void setYearMonth(YearMonth input) {
        format = new CalendarFormat(input);
    }

    public ArrayList<String[]> getCalendar() {
        return format.calendar;
    }

    @Override
    public String toString() {
        return format.toString();
    }
}