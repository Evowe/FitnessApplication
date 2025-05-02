package Application.Utility.Widgets.Calendar;

import Application.TheSwoleSection.WorkoutSchedule.WorkoutScheduleViewModel;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarViewModel {
    private final CalendarModel model;
    private LocalDate date;

    public CalendarViewModel() {
        model = new CalendarModel();
        date = LocalDate.now();
    }

    public void setDate(LocalDate date) {
        this.date = date;
        model.setYearMonth(YearMonth.from(this.date));
    }

    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return date.format(formatter);
    }

    public void setDay(int day) {
        date = date.withDayOfMonth(day);
    }

    public int getDay() {
        //System.out.println(date.toString());
        return date.getDayOfMonth();
    }

    public void setMonth(Month month) {
        date = date.withMonth(month.getValue());
        model.setYearMonth(YearMonth.from(date));
    }

    public Month getMonth() {
        return date.getMonth();
    }

    public void setYear(int year) {
        date = date.withYear(year);
        model.setYearMonth(YearMonth.from(date));
    }

    public int getYear() {
        return date.getYear();
    }

    public ArrayList<String[]> getCalendar() {
        return model.getCalendar();
    }

}