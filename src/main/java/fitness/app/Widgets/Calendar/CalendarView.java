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
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class CalendarView extends JPanel {
    private final CalendarViewModel viewModel;
    private FlatButton currentSelection;

    public CalendarView() {
        //Panel Layout & Settings
        setLayout(new MigLayout("insets 10"));
        putClientProperty(FlatClientProperties.STYLE, "arc:20;" + "background:lighten(@background,5%)");

        //Intialize ViewModel
        viewModel = new CalendarViewModel();

        //Create Menu Bar
        JPanel menuBar = new JPanel(new MigLayout());
        menuBar.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,5%)");

        //Month Selection Button
        FlatButton monthButton = new FlatButton();
        monthButton.setHorizontalAlignment(SwingConstants.RIGHT);
        monthButton.setMinimumSize(new Dimension(95, 30));
        monthButton.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,5%)");
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
            }
        });
        lastMonthButton.setText("<");

        //Previous Year Button
        FlatButton lastYearButton = new FlatButton();
        lastYearButton.setMaximumSize(new Dimension(30, 30));
        lastYearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewModel.setYear(viewModel.getYear() - 1);
                yearLabel.setText(String.valueOf(viewModel.getYear()));
            }
        });
        lastYearButton.setText("<<");

        //Next Year Button
        FlatButton nextYearButton = new FlatButton();
        nextYearButton.setMaximumSize(new Dimension(30, 30));
        nextYearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewModel.setYear(viewModel.getYear() + 1);
                yearLabel.setText(String.valueOf(viewModel.getYear()));
            }
        });
        nextYearButton.setText(">>");

        //Next Month Button
        FlatButton nextMonthButton = new FlatButton();
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
            }
        });
        nextMonthButton.setText(">");

        add(lastMonthButton);
        add(lastYearButton);
        add(monthButton);
        add(yearLabel);
        add(nextYearButton);
        add(nextMonthButton);
    }

    private void setCalendar() {
        JPanel calendarPanel = new JPanel(new MigLayout());
        ArrayList<String[]> format = viewModel.getCalendar();

        String[] labels = format.get(0);
        for (String label : labels) {
            if (label.equals(labels[7])) {
                calendarPanel.add(new JLabel(label), "wrap");
            }
            else{
                calendarPanel.add(new JLabel(label));
            }
        }
        format.removeFirst();

        for (String[] week : format) {
            for (String day : week) {
                FlatButton dayButton = new FlatButton();
                dayButton.setMaximumSize(new Dimension(30, 30));
                dayButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        viewModel.setDay(Integer.parseInt(day));
                        currentSelection.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,5%)");
                        currentSelection = dayButton;
                        currentSelection.setForeground(Color.BLUE);
                    }
                });
            }
        }
    }

    //test
    private void update() {
        remove(getComponentCount() - 1);

        setCalendar();

        revalidate();
        repaint();
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