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
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class CalendarView extends JPanel {
    private final CalendarViewModel viewModel;
    private FlatButton currentSelection;

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
        monthButton.setBorderPainted(false);
        monthButton.setHorizontalAlignment(SwingConstants.RIGHT);
        monthButton.setMinimumSize(new Dimension(95, 30));
        monthButton.putClientProperty(FlatClientProperties.STYLE, "background: @secondaryAccent");
        monthButton.setBorderPainted(false);
        char[] buttonName = viewModel.getMonth().toString().toLowerCase().toCharArray();
        buttonName[0] = Character.toUpperCase(buttonName[0]);
        monthButton.setText(new String(buttonName));

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
        lastMonthButton.setMaximumSize(new Dimension(30, 30));
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
        lastYearButton.setMaximumSize(new Dimension(30, 30));
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
        nextYearButton.setMaximumSize(new Dimension(30, 30));
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
        nextMonthButton.setMaximumSize(new Dimension(30, 30));
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
                FlatButton dayButton = new FlatButton();
                dayButton.setBorderPainted(false);
                dayButton.setMaximumSize(new Dimension(30, 30));
                dayButton.setText(day);
                dayButton.putClientProperty(FlatClientProperties.STYLE, "background:@secondaryAccent");
                if ((week.equals(format.getFirst()) && parseInt(day) > 7 ) || (week.equals(format.getLast()) && parseInt(day) < 7)) {
                    dayButton.setFocusable(false);
                    dayButton.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground");
                }
                else {
                    if (parseInt(day) == viewModel.getDay()) {
                        currentSelection = dayButton;
                        currentSelection.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground");
                    }
                    dayButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            viewModel.setDay(parseInt(day));
                            currentSelection.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryAccent");
                            currentSelection = dayButton;
                            currentSelection.putClientProperty(FlatClientProperties.STYLE, "background:@primaryAccent");
                        }
                    });
                }
                dateLabels.add(dayButton);
            }
            calendarPanel.add(dateLabels);
        }

        add(calendarPanel, "growx");
    }

    public static void main(String[] args) {
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
        JPanel panel = new JPanel(new MigLayout("fill,insets 20", "center", "center"));
        panel.add(new CalendarView());
        window.add(panel);
        window.getRootPane().putClientProperty("apple.awt.transparentTitleBar", true);
        window.getRootPane().putClientProperty("apple.awt.windowTitleVisible", false);

        window.setVisible(true);
    }
}