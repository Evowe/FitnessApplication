package fitness.app.Widgets.Calendar;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatMenuItem;
import com.formdev.flatlaf.extras.components.FlatPopupMenu;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import static fitness.app.Main.dark;
import static java.lang.Integer.parseInt;

public class CalendarView extends JPanel {
    private final CalendarViewModel viewModel;
    private JButton currentSelection;

    public CalendarView() {
        //Panel Layout & Settings
        setLayout(new MigLayout("wrap,fillx,insets 15"));
        putClientProperty(FlatClientProperties.STYLE, "arc:20;");

        //Intialize ViewModel
        viewModel = new CalendarViewModel();

        //Create Menu Bar
        JPanel menuBar = new JPanel(new MigLayout("fill, insets 0" , "fill"));

        //Month Selection Button
        FlatButton monthButton = new FlatButton();
        monthButton.setMargin(new Insets(0, 0, 0, 0));
        monthButton.setMinimumHeight(36);
        monthButton.setBorderPainted(false);
        monthButton.setHorizontalAlignment(SwingConstants.RIGHT);
        monthButton.setMinimumSize(new Dimension(95, 30));
        monthButton.setBorderPainted(false);
        char[] buttonName = viewModel.getMonth().toString().toLowerCase().toCharArray();
        buttonName[0] = Character.toUpperCase(buttonName[0]);
        monthButton.setText(new String(buttonName));
        monthButton.putClientProperty(FlatClientProperties.STYLE, "background:@secondaryBackground; foreground:@foreground;");

        //Month Selection Menu
        FlatPopupMenu monthPopupMenu = new FlatPopupMenu();
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August",  "September", "October", "November", "December"};
        for (String month : months) {
            FlatMenuItem item = new FlatMenuItem();
            item.setText(month);
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    viewModel.setMonth(Month.valueOf(item.getText().toUpperCase()));
                    monthButton.setText(item.getText());
                    remove(getComponentCount() - 1);
                    setCalendar();
                }
            });
            monthPopupMenu.add(item);
        }

        //Link Month Selection Button and Menu
        monthButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                monthPopupMenu.show(monthButton, 0, monthButton.getHeight());
            }
        });

        //Year Label
        JLabel yearLabel = new JLabel(String.valueOf(viewModel.getYear()));
        yearLabel.setMinimumSize(new Dimension(55, 30));
        yearLabel.setHorizontalAlignment(SwingConstants.LEFT);

        //Previous Month Button
        FlatButton lastMonthButton = new FlatButton();
        lastMonthButton.setBorderPainted(false);
        lastMonthButton.setMargin(new Insets(0, 0, 0, 0));
        lastMonthButton.setMinimumSize(new Dimension(36, 36));
        lastMonthButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (viewModel.getMonth() == Month.JANUARY) {
                    viewModel.setYear(viewModel.getYear() - 1);
                    yearLabel.setText(String.valueOf(viewModel.getYear()));
                }
                viewModel.setMonth(viewModel.getMonth().minus(1));
                char[] buttonName = viewModel.getMonth().toString().toLowerCase().toCharArray();
                buttonName[0] = Character.toUpperCase(buttonName[0]);
                monthButton.setText(new String(buttonName));

                remove(getComponentCount() - 1);
                setCalendar();
            }
        });
        lastMonthButton.setText("<");

        //Previous Year Button
        FlatButton lastYearButton = new FlatButton();
        lastYearButton.setBorderPainted(false);
        lastYearButton.setMargin(new Insets(0, 0, 0, 0));
        lastYearButton.setMinimumSize(new Dimension(36, 36));
        lastYearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewModel.setYear(viewModel.getYear() - 1);
                yearLabel.setText(String.valueOf(viewModel.getYear()));

                remove(getComponentCount() - 1);
                setCalendar();
            }
        });
        lastYearButton.setText("<<");

        //Next Year Button
        FlatButton nextYearButton = new FlatButton();
        nextYearButton.setBorderPainted(false);
        nextYearButton.setMargin(new Insets(0, 0, 0, 0));
        nextYearButton.setMinimumSize(new Dimension(36, 36));
        nextYearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewModel.setYear(viewModel.getYear() + 1);
                yearLabel.setText(String.valueOf(viewModel.getYear()));

                remove(getComponentCount() - 1);
                setCalendar();
            }
        });
        nextYearButton.setText(">>");

        //Next Month Button
        FlatButton nextMonthButton = new FlatButton();
        nextMonthButton.setBorderPainted(false);
        nextMonthButton.setMargin(new Insets(0, 0, 0, 0));
        nextMonthButton.setMinimumSize(new Dimension(36, 36));
        nextMonthButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (viewModel.getMonth() == Month.DECEMBER) {
                    viewModel.setYear(viewModel.getYear() + 1);
                    yearLabel.setText(String.valueOf(viewModel.getYear()));
                }
                viewModel.setMonth(viewModel.getMonth().plus(1));
                char[] buttonName = viewModel.getMonth().toString().toLowerCase().toCharArray();
                buttonName[0] = Character.toUpperCase(buttonName[0]);
                monthButton.setText(new String(buttonName));

                remove(getComponentCount() - 1);
                setCalendar();
            }
        });
        nextMonthButton.setText(">");

        menuBar.add(lastMonthButton);
        menuBar.add(lastYearButton);
        menuBar.add(monthButton);
        menuBar.add(yearLabel);
        menuBar.add(nextYearButton);
        menuBar.add(nextMonthButton);

        add(menuBar);

        setCalendar();
    }

    private void setCalendar() {
        JPanel calendarPanel = new JPanel(new MigLayout("wrap, fillx, insets 0", "fill"));
        ArrayList<String[]> format = viewModel.getCalendar();

        JPanel dayLabels = new JPanel(new MigLayout("fill, insets 0", "center"));
        String[] labels = format.removeFirst();
        for (String label : labels) {
            JLabel day = new JLabel(label);
            day.putClientProperty(FlatClientProperties.STYLE, "font:bold");
            dayLabels.add(day);
        }
        calendarPanel.add(dayLabels);

        for (String[] week : format) {
            JPanel dateLabels = new JPanel(new MigLayout("fill, insets 0", "center"));
            for (String day : week) {
                JButton dayButton = new JButton();
                dayButton.putClientProperty(FlatClientProperties.STYLE, "background:@secondaryBackground;");
                if(!dark){
                    dayButton.setForeground(new Color(193, 18, 31));
                }
                dayButton.setBorderPainted(false);
                dayButton.setMargin(new Insets(0, 0, 0, 0));
                dayButton.setMinimumSize(new Dimension(36, 36));
                dayButton.setText(day);
                if ((week.equals(format.getFirst()) && parseInt(day) > 7 ) || (week.equals(format.getLast()) && parseInt(day) < 7)) {
                    dayButton.setFocusable(false);
                    dayButton.putClientProperty(FlatClientProperties.STYLE, "background:@secondaryBackground; foreground:@secondaryForeground;");
                }
                else {
                    if (parseInt(day) == viewModel.getDay()) {
                        currentSelection = dayButton;
                        currentSelection.putClientProperty(FlatClientProperties.STYLE, "background:@accent;");
                    }
                    dayButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            viewModel.setDay(parseInt(day));
                            currentSelection.putClientProperty(FlatClientProperties.STYLE, "background:@secondaryBackground;");
                            currentSelection = dayButton;
                            currentSelection.putClientProperty(FlatClientProperties.STYLE, "background:@accent;");
                        }
                    });
                }
                dateLabels.add(dayButton);
            }
            calendarPanel.add(dateLabels);
        }

        add(calendarPanel, "growx");
    }
    public String getDate()
    {
        LocalDate date = viewModel.getDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate = date.format(formatter);
        //System.out.println(formattedDate);
        viewModel.getDay();
        return formattedDate;
    }
}