package fitness.app.Widgets.Calendar;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatMenuItem;
import com.formdev.flatlaf.extras.components.FlatPopupMenu;
import com.formdev.flatlaf.fonts.roboto_mono.FlatRobotoMonoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;

public class CalendarView {
    private static JPanel mainPanel;
    private static JPanel calendarMenu;
    private static JPanel menuBar;
    private static YearMonth yearMonth;
    private static ArrayList<JPanel> dayMenu;

    public CalendarView() {
        yearMonth = YearMonth.now();
        mainPanel = new JPanel(new MigLayout("fill,insets 20", "center", "center"));

        calendarMenu = new JPanel(new MigLayout("wrap,fillx,insets 10", "fill"));
        calendarMenu.putClientProperty(FlatClientProperties.STYLE, "arc:20;" + "background:lighten(@background,5%)");

        setMenuBar();
        setDayMenu();

        mainPanel.add(calendarMenu);
    }

    private static void setMenuBar() {
        //Top Row of Calendar Menu
        menuBar = new JPanel(new MigLayout());
        menuBar.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,5%)");

        FlatButton lastMonthButton = new FlatButton();
        lastMonthButton.setText("<");
        lastMonthButton.setMaximumSize(new Dimension(30, 30));
        lastMonthButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yearMonth.minusMonths(1);
                calendarMenu.removeAll();
                setMenuBar();
                setDayMenu();
            }
        });
        FlatButton lastYearButton = new FlatButton();
        lastYearButton.setText("<<");
        lastYearButton.setMaximumSize(new Dimension(30, 30));
        lastYearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yearMonth.minusYears(1);
                calendarMenu.removeAll();
                setMenuBar();
                setDayMenu();
            }
        });

        FlatButton monthButton = new FlatButton();
        switch (yearMonth.getMonth()) {
            case JANUARY -> {
                monthButton.setText("January");
            }
            case FEBRUARY -> {
                monthButton.setText("February");
            }
            case MARCH -> {
                monthButton.setText("March");
            }
            case APRIL -> {
                monthButton.setText("April");
            }
            case MAY -> {
                monthButton.setText("May");
            }
            case JUNE -> {
                monthButton.setText("June");
            }
            case JULY -> {
                monthButton.setText("July");
            }
            case AUGUST -> {
                monthButton.setText("August");
            }
            case SEPTEMBER -> {
                monthButton.setText("September");
            }
            case OCTOBER -> {
                monthButton.setText("October");
            }
            case NOVEMBER -> {
                monthButton.setText("November");
            }
            case DECEMBER -> {
                monthButton.setText("December");
            }
        }

        monthButton.setHorizontalAlignment(SwingConstants.RIGHT);
        monthButton.setMinimumSize(new Dimension(95, 30));
        monthButton.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,5%)");
        monthButton.setBorderPainted(false);

        FlatPopupMenu monthPopupMenu = new FlatPopupMenu();
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August",  "September", "October", "November", "December"};
        for (String month : months) {
            FlatMenuItem item = new FlatMenuItem();
            item.setText(month);
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    switch (month) {
                        case "January" -> {
                            if (yearMonth.getMonth() != Month.JANUARY) {
                                yearMonth = yearMonth.withMonth(1);
                            }
                        }
                        case "February" -> {
                            if (yearMonth.getMonth() != Month.FEBRUARY) {
                                yearMonth = yearMonth.withMonth(2);
                            }
                        }
                        case "March" -> {
                            if (yearMonth.getMonth() != Month.MARCH) {
                                yearMonth = yearMonth.withMonth(3);
                            }
                        }
                        case "April" -> {
                            if (yearMonth.getMonth() != Month.APRIL) {
                                yearMonth = yearMonth.withMonth(4);
                            }

                        }
                        case "May" -> {
                            if (yearMonth.getMonth() != Month.MAY) {
                                yearMonth = yearMonth.withMonth(5);
                            }
                        }
                        case "June" -> {
                            if (yearMonth.getMonth() != Month.JUNE) {
                                yearMonth = yearMonth.withMonth(6);
                            }
                        }
                        case "July" -> {
                            if (yearMonth.getMonth() != Month.JULY) {
                                yearMonth = yearMonth.withMonth(7);
                            }

                        }
                        case "August" -> {
                            if (yearMonth.getMonth() != Month.AUGUST) {
                                yearMonth = yearMonth.withMonth(8);
                            }
                        }
                        case "September" -> {
                            if (yearMonth.getMonth() != Month.SEPTEMBER) {
                                yearMonth = yearMonth.withMonth(9);
                            }
                        }
                        case "October" -> {
                            if (yearMonth.getMonth() != Month.OCTOBER) {
                                yearMonth = yearMonth.withMonth(10);
                            }
                        }
                        case "November" -> {
                            if (yearMonth.getMonth() != Month.NOVEMBER) {
                                yearMonth = yearMonth.withMonth(11);
                            }
                        }
                        case "December" -> {
                            if (yearMonth.getMonth() != Month.DECEMBER) {
                                yearMonth = yearMonth.withMonth(12);
                            }
                        }
                    }
                    if (!month.equals(monthButton.getText())) {
                        monthButton.setText(item.getText());
                        calendarMenu.removeAll();
                        setMenuBar();
                        setDayMenu();
                    }
                }
            });
            monthPopupMenu.add(item);
        }

        monthButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                monthPopupMenu.show(monthButton, 0, monthButton.getHeight());
            }
        });

        JLabel yearLabel = new JLabel(String.valueOf(yearMonth.getYear()));
        yearLabel.setMinimumSize(new Dimension(55, 30));
        yearLabel.setHorizontalAlignment(SwingConstants.LEFT);
        FlatButton nextYearButton = new FlatButton();
        nextYearButton.setText(">>");
        nextYearButton.setMaximumSize(new Dimension(30, 30));
        nextYearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yearMonth.plusYears(1);
                calendarMenu.removeAll();
                setMenuBar();
                setDayMenu();
            }
        });

        FlatButton nextMonthButton = new FlatButton();
        nextMonthButton.setText(">");
        nextMonthButton.setMaximumSize(new Dimension(30, 30));
        nextMonthButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yearMonth.plusMonths(1);
                calendarMenu.removeAll();
                setMenuBar();
                setDayMenu();
            }
        });
        menuBar.add(lastMonthButton);
        menuBar.add(lastYearButton);
        menuBar.add(monthButton);
        menuBar.add(yearLabel);
        menuBar.add(nextMonthButton);
        menuBar.add(nextYearButton);

        calendarMenu.add(menuBar);
    }

    private static void setDayMenu() {
        //Day Label Row
        JPanel dayLabelBar = new JPanel(new MigLayout("fill, insets 0", "[grow,fill][grow,fill][grow,fill][grow,fill][grow,fill][grow,fill][grow,fill]", "fill"));
        dayLabelBar.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,5%)");

        String[] days = {"Sun", "Mon", "Tue",  "Wed", "Thu", "Fri", "Sat"};
        for (String day : days) {
            JLabel label = new JLabel(day);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.putClientProperty(FlatClientProperties.STYLE, "font:bold");
            dayLabelBar.add(label, "align center");
        }
        calendarMenu.add(dayLabelBar, "growx");

        CalendarModel test = new CalendarModel(yearMonth);
        ArrayList<String[]> calendar = test.getCalendar();

        for (int i = 1; i < calendar.size(); ++i) {
            String[] weekday = calendar.get(i);
            JPanel dayMenu = new JPanel(new MigLayout("fill, insets 0", "[grow,fill][grow,fill][grow,fill][grow,fill][grow,fill][grow,fill][grow,fill]", "fill"));
            dayMenu.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,5%)");
            for (int j = 0; j < 7; ++j) {
                FlatButton dayButton = new FlatButton();
                dayButton.setButtonType(FlatButton.ButtonType.roundRect);
                dayButton.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,5%)");
                dayButton.setHorizontalAlignment(SwingConstants.CENTER);
                dayButton.setMaximumSize(new Dimension(30, 30));
                dayButton.setText(weekday[j]);
                if ((i == 1 && Integer.parseInt(weekday[j]) > 7) || (i == calendar.size() - 1 && Integer.parseInt(weekday[j]) < 7)) {
                    dayButton.setForeground(Color.GRAY);
                }
                dayMenu.add(dayButton, "align center");
            }
            calendarMenu.add(dayMenu, "growx");
        }
    }

    public static JPanel getCalendarView() {
        return mainPanel;
    }

    public static void main(String[] args) {
        System.setProperty("apple.awt.application.name", "Rocket Health");
        System.setProperty("apple.awt.application.appearance", "system");

        //FlatLaf setup & settings
        FlatRobotoMonoFont.install();
        FlatLaf.registerCustomDefaultsSource("FlatLafSettings");
        UIManager.put("defaultFont", new Font(FlatRobotoMonoFont.FAMILY, Font.PLAIN, 13));
        FlatMacDarkLaf.setup();

        //Application window
        JFrame window = new JFrame("Rocket Health");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(new Dimension(1200, 700));
        window.setLocationRelativeTo(null);
        window.add(CalendarViewModel.getCalendarView());
        window.getRootPane().putClientProperty("apple.awt.transparentTitleBar", true);
        window.getRootPane().putClientProperty("apple.awt.windowTitleVisible", false);

        window.setVisible(true);
    }
}
