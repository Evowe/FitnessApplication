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
import java.time.Month;
import java.util.ArrayList;
import static fitness.app.Main.dark;
import static java.lang.Integer.parseInt;

public class CalendarView extends JPanel {
    private FlatButton yearButton;
    private final CalendarViewModel viewModel;
    private JButton currentSelection;
    private JPanel calendarMenu;

    public CalendarView() {
        setLayout(new MigLayout("insets 15"));
        putClientProperty(FlatClientProperties.STYLE, "arc:20;");

        //Panel Layout & Settings
        calendarMenu = new JPanel(new MigLayout("wrap, fillx, insets 0"));
        calendarMenu.putClientProperty(FlatClientProperties.STYLE, "arc:20;");

        //Intialize ViewModel
        viewModel = new CalendarViewModel();

        //Create Menu Bar
        JPanel menuBar = new JPanel(new MigLayout("fill, insets 0" , "fill"));

        //Month Selection Button
        FlatButton monthButton = new FlatButton();
        monthButton.setMinimumSize(new Dimension(36, 36));
        monthButton.setMargin(new Insets(0, 0, 0, 0));
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
                    calendarMenu.remove(getComponentCount());
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

        //Year Selection Button
        yearButton = new FlatButton();
        yearButton.setBorderPainted(false);
        yearButton.setMinimumSize(new Dimension(36, 36));
        yearButton.setMargin(new Insets(0, 0, 0, 0));
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
        lastMonthButton.setBorderPainted(false);
        lastMonthButton.setMinimumSize(new Dimension(36, 36));
        lastMonthButton.setMargin(new Insets(0, 0, 0, 0));
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

                calendarMenu.remove(getComponentCount());
                setCalendar();
            }
        });
        lastMonthButton.setText("<");

        //Previous Year Button
        FlatButton lastYearButton = new FlatButton();
        lastYearButton.setBorderPainted(false);
        lastYearButton.setMinimumSize(new Dimension(36, 36));
        lastYearButton.setMargin(new Insets(0, 0, 0, 0));
        lastYearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewModel.setYear(viewModel.getYear() - 1);
                yearButton.setText(String.valueOf(viewModel.getYear()));

                calendarMenu.remove(getComponentCount());
                setCalendar();
            }
        });
        lastYearButton.setText("<<");

        //Next Year Button
        FlatButton nextYearButton = new FlatButton();
        nextYearButton.setBorderPainted(false);
        nextYearButton.setMinimumSize(new Dimension(36, 36));
        nextYearButton.setMargin(new Insets(0, 0, 0, 0));
        nextYearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewModel.setYear(viewModel.getYear() + 1);
                yearButton.setText(String.valueOf(viewModel.getYear()));

                calendarMenu.remove(getComponentCount());
                setCalendar();
            }
        });
        nextYearButton.setText(">>");

        //Next Month Button
        FlatButton nextMonthButton = new FlatButton();
        nextMonthButton.setBorderPainted(false);
        nextMonthButton.setMinimumSize(new Dimension(36, 36));
        nextMonthButton.setMargin(new Insets(0, 0, 0, 0));
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

                calendarMenu.remove(getComponentCount());
                setCalendar();
            }
        });
        nextMonthButton.setText(">");

        menuBar.add(lastMonthButton);
        menuBar.add(lastYearButton);
        menuBar.add(monthButton, "growx, pushx");
        menuBar.add(yearButton, "growx, pushx");
        menuBar.add(nextYearButton);
        menuBar.add(nextMonthButton);

        calendarMenu.add(menuBar, "growx");
        add(calendarMenu, "growx, pushx");

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
                dayButton.setMinimumSize(new Dimension(36, 36));
                dayButton.setMargin(new Insets(0, 0, 0, 0));
                dayButton.putClientProperty(FlatClientProperties.STYLE, "background:@secondaryBackground;");
                if(!dark){
                    dayButton.setForeground(new Color(193, 18, 31));
                }
                dayButton.setBorderPainted(false);
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

        calendarMenu.add(calendarPanel, "growx");
    }

    public String getDate() {
        return viewModel.getDate();
    }

    public FlatPopupMenu initYearMenu() {
        FlatPopupMenu yearPopupMenu = new FlatPopupMenu();
        for (int i = viewModel.getYear() - 5; i <= viewModel.getYear() + 5; ++i) {
            FlatMenuItem item = new FlatMenuItem();
            item.setText(String.valueOf(i));
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    viewModel.setYear(Integer.parseInt(item.getText()));
                    yearButton.setText(item.getText());
                    calendarMenu.remove(getComponentCount());
                    setCalendar();
                }
            });
            yearPopupMenu.add(item);
        }

        return yearPopupMenu;
    }
}