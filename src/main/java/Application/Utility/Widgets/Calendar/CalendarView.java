package Application.Utility.Widgets.Calendar;

import Application.TheSwoleSection.WorkoutSchedule.WorkoutScheduleView;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatMenuItem;
import com.formdev.flatlaf.extras.components.FlatPopupMenu;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Month;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class CalendarView extends JPanel {
    private FlatButton yearButton;
    private final CalendarViewModel viewModel;
    private JButton currentButton;
    public enum Type {PANELED, BUTTONED}

    public CalendarView(Type type) {
        //Set standard layout
        setLayout(new MigLayout("wrap, fillx, insets 20"));
        putClientProperty(FlatClientProperties.STYLE, "arc:20;");

        //Intialize ViewModel
        viewModel = new CalendarViewModel();

        add(initMenuBar(), "growx");
        if (type == Type.BUTTONED) {
            add(initButtonPage(), "growx, pushx");
        }
        else {
            add(initPanelPage(), "growx, pushx");
        }
    }

    private JPanel initMenuBar() {
        //Create Menu Bar
        JPanel menuBar = new JPanel(new MigLayout("fillx, filly, insets 0" ));

        //Month Selection Button
        FlatButton monthButton = new FlatButton();
        monthButton.setMargin(new Insets(0, 0, 0, 0));
        monthButton.setMinimumSize(new Dimension(36, 36));
        monthButton.setBorderPainted(false);
        monthButton.setHorizontalAlignment(SwingConstants.RIGHT);
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
                    add(initButtonPage(), "growy, pushy");
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

        //Year Selection Button
        yearButton = new FlatButton();
        yearButton.setMargin(new Insets(0, 0, 0, 0));
        yearButton.setMinimumSize(new Dimension(36, 36));
        yearButton.setBorderPainted(false);
        yearButton.setHorizontalAlignment(SwingConstants.LEFT);
        yearButton.setText(String.valueOf(viewModel.getYear()));
        yearButton.putClientProperty(FlatClientProperties.STYLE, "background:@secondaryBackground; foreground:@foreground;");

        //Link Year Selection Button and Menu
        yearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Year Selection Menu
                FlatPopupMenu yearPopupMenu = initYearMenu();
                yearPopupMenu.show(yearButton, 0, yearButton.getHeight());
            }
        });


        //Previous Month Button
        FlatButton lastMonthButton = new FlatButton();
        lastMonthButton.setMargin(new Insets(0, 0, 0, 0));
        lastMonthButton.setMinimumSize(new Dimension(36, 36));
        lastMonthButton.setBorderPainted(false);
        lastMonthButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (viewModel.getMonth() == Month.JANUARY) {
                    viewModel.setYear(viewModel.getYear() - 1);
                    yearButton.setText(String.valueOf(viewModel.getYear()));
                }
                viewModel.setMonth(viewModel.getMonth().minus(1));
                char[] buttonName = viewModel.getMonth().toString().toLowerCase().toCharArray();
                buttonName[0] = Character.toUpperCase(buttonName[0]);
                monthButton.setText(new String(buttonName));

                remove(getComponentCount() - 1);
                add(initButtonPage(), "growy, pushy");
            }
        });
        lastMonthButton.setText("<");

        //Previous Year Button
        FlatButton lastYearButton = new FlatButton();
        lastYearButton.setMargin(new Insets(0, 0, 0, 0));
        lastYearButton.setMinimumSize(new Dimension(36, 36));
        lastYearButton.setBorderPainted(false);
        lastYearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewModel.setYear(viewModel.getYear() - 1);
                yearButton.setText(String.valueOf(viewModel.getYear()));

                remove(getComponentCount() - 1);
                add(initButtonPage(), "growy, pushy");
            }
        });
        lastYearButton.setText("<<");

        //Next Year Button
        FlatButton nextYearButton = new FlatButton();
        nextYearButton.setMargin(new Insets(0, 0, 0, 0));
        nextYearButton.setMinimumSize(new Dimension(36, 36));
        nextYearButton.setBorderPainted(false);
        nextYearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewModel.setYear(viewModel.getYear() + 1);
                yearButton.setText(String.valueOf(viewModel.getYear()));

                remove(getComponentCount() - 1);
                add(initButtonPage(), "growy, pushy");
            }
        });
        nextYearButton.setText(">>");

        //Next Month Button
        FlatButton nextMonthButton = new FlatButton();
        nextMonthButton.setMargin(new Insets(0, 0, 0, 0));
        nextMonthButton.setMinimumSize(new Dimension(36, 36));
        nextMonthButton.setBorderPainted(false);
        nextMonthButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (viewModel.getMonth() == Month.DECEMBER) {
                    viewModel.setYear(viewModel.getYear() + 1);
                    yearButton.setText(String.valueOf(viewModel.getYear()));
                }
                viewModel.setMonth(viewModel.getMonth().plus(1));
                char[] buttonName = viewModel.getMonth().toString().toLowerCase().toCharArray();
                buttonName[0] = Character.toUpperCase(buttonName[0]);
                monthButton.setText(new String(buttonName));

                remove(getComponentCount() - 1);
                add(initButtonPage(), "growy, pushy");
            }
        });
        nextMonthButton.setText(">");

        menuBar.add(lastMonthButton);
        menuBar.add(lastYearButton);
        menuBar.add(monthButton, "growx, pushx");
        menuBar.add(yearButton, "growx, pushx");
        menuBar.add(nextYearButton);
        menuBar.add(nextMonthButton);

        return menuBar;
    }

    private JPanel initButtonPage() {
        JPanel calendarPanel = new JPanel(new MigLayout("wrap, fillx, insets 0", "fill", "fill"));
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
                dayButton.setMinimumSize(new Dimension(36, 36));
                dayButton.setMargin(new Insets(0, 0, 0, 0));
                dayButton.putClientProperty(FlatClientProperties.STYLE, "background:@secondaryBackground;");
                dayButton.setBorderPainted(false);
                dayButton.setText(day);
                if ((week.equals(format.getFirst()) && parseInt(day) > 7 ) || (week.equals(format.get(4)) && parseInt(day) < 14) || (week.equals(format.get(5)) && parseInt(day) < 14)) {
                    dayButton.setFocusable(false);
                    dayButton.putClientProperty(FlatClientProperties.STYLE, "background:@secondaryBackground; foreground:@secondaryForeground;");
                }
                else {
                    if (parseInt(day) == viewModel.getDay()) {
                        currentButton = dayButton;
                        currentButton.putClientProperty(FlatClientProperties.STYLE, "background:@accent;");
                    }
                    dayButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            viewModel.setDay(parseInt(day));
                            WorkoutScheduleView.reloadDay(viewModel.getDate());
                            currentButton.putClientProperty(FlatClientProperties.STYLE, "background:@secondaryBackground;");
                            currentButton = dayButton;
                            currentButton.putClientProperty(FlatClientProperties.STYLE, "background:@accent;");
                        }
                    });
                }
                dateLabels.add(dayButton);
            }
            calendarPanel.add(dateLabels, "growy, pushy");
        }

        return calendarPanel;
    }

    private JPanel initPanelPage() {
        JPanel calendarPanel = new JPanel(new MigLayout("wrap, fillx, insets 0", "fill", "fill"));
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
                JPanel dayPanel = new JPanel(new MigLayout("wrap, insets 3"));
                dayPanel.putClientProperty(FlatClientProperties.STYLE, "background:@secondaryBackground;");
                dayPanel.add(new JLabel(day));
                dateLabels.add(dayPanel);
            }
            calendarPanel.add(dateLabels, "growx, growy, pushx, pushy");
        }

        return calendarPanel;
    }

    public String getDate() {
        return viewModel.getDate();
    }

    private FlatPopupMenu initYearMenu() {
        FlatPopupMenu yearPopupMenu = new FlatPopupMenu();
        for (int i = viewModel.getYear() - 5; i <= viewModel.getYear() + 5; ++i) {
            FlatMenuItem item = new FlatMenuItem();
            item.setText(String.valueOf(i));
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    viewModel.setYear(Integer.parseInt(item.getText()));
                    yearButton.setText(item.getText());
                    remove(getComponentCount() - 1);
                    add(initButtonPage(), "growy, pushy");
                }
            });
            yearPopupMenu.add(item);
        }

        return yearPopupMenu;
    }
}