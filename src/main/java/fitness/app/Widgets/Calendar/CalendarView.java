package fitness.app.Widgets.Calendar;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatMenuItem;
import com.formdev.flatlaf.extras.components.FlatPopupMenu;
import com.formdev.flatlaf.fonts.roboto_mono.FlatRobotoMonoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import fitness.app.Login.LoginViewModel;
import fitness.app.Objects.AccountsDB;
import fitness.app.Objects.DatabaseManager;
import fitness.app.Objects.ExerciseDB;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;

public class CalendarView {
    private static JPanel mainPanel;

    public CalendarView() {
        mainPanel = new JPanel(new MigLayout("fill,insets 20", "center", "center"));

        JPanel calendarMenu = new JPanel(new MigLayout("wrap,fillx,insets 10", "fill"));
        calendarMenu.putClientProperty(FlatClientProperties.STYLE, "arc:20;" + "background:lighten(@background,5%)");

        //Top Row of Calendar Menu
        JPanel menuBar = new JPanel(new MigLayout());
        menuBar.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,5%)");

        FlatButton lastMonthButton = new FlatButton();
        lastMonthButton.setText("<");
        lastMonthButton.setMaximumSize(new Dimension(30, 30));
        FlatButton lastYearButton = new FlatButton();
        lastYearButton.setText("<<");
        lastYearButton.setMaximumSize(new Dimension(30, 30));

        FlatButton monthButton = new FlatButton();
        monthButton.setText("January");
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
                    monthButton.setText(e.getActionCommand());
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

        JLabel yearLabel = new JLabel("2025");
        yearLabel.setMinimumSize(new Dimension(55, 30));
        yearLabel.setHorizontalAlignment(SwingConstants.LEFT);
        FlatButton nextYearButton = new FlatButton();
        nextYearButton.setText(">>");
        nextYearButton.setMaximumSize(new Dimension(30, 30));
        FlatButton nextMonthButton = new FlatButton();
        nextMonthButton.setText(">");
        nextMonthButton.setMaximumSize(new Dimension(30, 30));
        menuBar.add(lastMonthButton);
        menuBar.add(lastYearButton);
        menuBar.add(monthButton);
        menuBar.add(yearLabel);
        menuBar.add(nextMonthButton);
        menuBar.add(nextYearButton);

        calendarMenu.add(menuBar);

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

        CalendarModel test = new CalendarModel(2025, 1);
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


        mainPanel.add(calendarMenu);
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
