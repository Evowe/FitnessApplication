package Application.TheSwoleSection.WorkoutSchedule;

import Application.Utility.Objects.Account;
import Application.Utility.Objects.Workout;
import Application.Utility.Objects.WorkoutPlan;
import Application.Utility.Widgets.Calendar.CalendarView;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatLabel;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Calendar;

public class WorkoutScheduleView extends JPanel {
    private static WorkoutScheduleViewModel viewModel;
    private Account user;
    private static WorkoutPlan equippedWorkoutPlan;
    private static JPanel centerBottom;
    private static CalendarView calendarPanel;
    private static JPanel main;
    private static JPanel center;

    public WorkoutScheduleView(Account user) {
        //Link page to the current user
        this.user = user;

        viewModel = new WorkoutScheduleViewModel();
        equippedWorkoutPlan = viewModel.getCurrentWorkoutPlan(user.getUsername());

        //Set standard layout
        setLayout(new MigLayout("fill, insets 0", "center", "center"));
        putClientProperty(FlatClientProperties.STYLE, "background:@background");


        main = new JPanel();
        main.setLayout(new BorderLayout());
        main.putClientProperty(FlatClientProperties.STYLE, "background:@background");

        //North Panel Setup - Title
        JPanel titlePanel = new JPanel();
        titlePanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        FlatLabel title = new FlatLabel();
        title.setText("Workout Schedule");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +25");
        titlePanel.add(title);

        main.add(titlePanel, BorderLayout.NORTH);

        center = new JPanel();
        center.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        center.setLayout(new BorderLayout());
        center.setPreferredSize(new Dimension(1000, 500));


        calendarPanel = new CalendarView(CalendarView.Type.BUTTONED);
        //ADD TOP PANE
        center.add(calendarPanel, BorderLayout.CENTER);

        //TODO: Replace with a new calendar variant
        //center.add(new CalendarView(CalendarView.Type.PANELED), "gapy 20, growx, growy, pushx, pushy");


        // Center Bottom Setup - Scheduled Workouts Table View
        centerBottom = new JPanel();
        centerBottom.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        centerBottom.setPreferredSize(new Dimension(1200, 300));
        centerBottom.setLayout(new GridLayout(2, 1));

        reloadDay(calendarPanel.getDate());

        /*
        //Check if user has a plan equipped
        if(equippedWorkoutPlan != null) {
            //Check if the plan date is still valid
            if(viewModel.isDateValid(equippedWorkoutPlan, calendarPanel.getDate())){
                int day = viewModel.getDayOfWeek(calendarPanel.getDate());
                Workout workout = null;

                if(day == Calendar.MONDAY){
                    workout = equippedWorkoutPlan.getWorkoutSchedule().get(0);
                } else if(day == Calendar.TUESDAY){
                    workout = equippedWorkoutPlan.getWorkoutSchedule().get(1);
                } else if(day == Calendar.WEDNESDAY){
                    workout = equippedWorkoutPlan.getWorkoutSchedule().get(2);
                } else if(day == Calendar.THURSDAY){
                    workout = equippedWorkoutPlan.getWorkoutSchedule().get(3);
                } else if(day == Calendar.FRIDAY){
                    workout = equippedWorkoutPlan.getWorkoutSchedule().get(4);
                } else if(day == Calendar.SATURDAY){
                    workout = equippedWorkoutPlan.getWorkoutSchedule().get(5);
                } else if(day == Calendar.SUNDAY){
                    workout = equippedWorkoutPlan.getWorkoutSchedule().get(6);
                }


                JPanel workoutPanel = new JPanel();
                workoutPanel.setLayout(new GridLayout(4, 1));

                JLabel workoutLabel = new JLabel("Workout Name: " + workout.getName());
                workoutLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +12");
                workoutPanel.add(workoutLabel);

                JLabel durationLabel = new JLabel("Duration: " + workout.getDuration());
                durationLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +12");
                workoutPanel.add(durationLabel);

                JLabel descriptionLabel = new JLabel("Description: " + workout.getDescription());
                descriptionLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +12");
                workoutPanel.add(descriptionLabel);

                JLabel exerciseLabel = new JLabel("Exercises:");
                exerciseLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +12");
                workoutPanel.add(exerciseLabel);

                centerBottom.add(workoutPanel);

                JTable workoutExercisesTable =
                        new JTable(viewModel.getExerciseData(workout), viewModel.getExerciseColumns()){
                            public String getToolTipText( MouseEvent e )
                            {
                                int row = rowAtPoint( e.getPoint() );
                                int column = columnAtPoint( e.getPoint() );

                                Object value = getValueAt(row, column);
                                return value == null ? null : value.toString();
                            }

                        };
                workoutExercisesTable.setRowHeight(75);
                workoutExercisesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

                workoutExercisesTable.getColumnModel().getColumn(0).setPreferredWidth(50);
                workoutExercisesTable.getColumnModel().getColumn(1).setPreferredWidth(100);
                workoutExercisesTable.getColumnModel().getColumn(2).setPreferredWidth(5);
                workoutExercisesTable.getColumnModel().getColumn(3).setPreferredWidth(5);
                workoutExercisesTable.getColumnModel().getColumn(4).setPreferredWidth(5);

                workoutExercisesTable.setDefaultEditor(Object.class, null);

                for(int i = 0; i < workoutExercisesTable.getColumnCount(); i++) {
                    TableColumn col = workoutExercisesTable.getColumnModel().getColumn(i);
                    DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
                    dtcr.setHorizontalAlignment(SwingConstants.CENTER);
                    col.setCellRenderer(dtcr);
                }
                workoutExercisesTable.setShowGrid(true);

                workoutExercisesTable.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value,
                                                                   boolean isSelected, boolean hasFocus,
                                                                   int row, int column) {
                        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                        // Change font here (example: bold, size 14)
                        c.setFont(new Font("Monospaces", Font.BOLD, 15));

                        return c;
                    }
                });


                JScrollPane scrollPane = new JScrollPane(workoutExercisesTable);
                scrollPane.setPreferredSize(new Dimension(1200, 200));
                scrollPane.setMaximumSize(new Dimension(1200, 200));

                centerBottom.add(scrollPane);
            }

            center.add(centerBottom);
            main.add(center, BorderLayout.CENTER);

        } else{
            JLabel titleLabel = new JLabel("No Scheduled Workouts");
            titleLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +12");
            centerBottom.add(titleLabel);
            center.add(centerBottom);

            main.add(center, BorderLayout.CENTER);
        }
        */

        add(main, "growy, pushy");

    }

    public static void reloadDay(String date){
        centerBottom.removeAll();

        if(equippedWorkoutPlan != null) {
            //Check if the plan date is still valid
            if(viewModel.isDateValid(equippedWorkoutPlan, calendarPanel.getDate())){
                int day = viewModel.getDayOfWeek(calendarPanel.getDate());
                Workout workout = null;

                if(day == Calendar.MONDAY){
                    workout = equippedWorkoutPlan.getWorkoutSchedule().get(0);
                } else if(day == Calendar.TUESDAY){
                    workout = equippedWorkoutPlan.getWorkoutSchedule().get(1);
                } else if(day == Calendar.WEDNESDAY){
                    workout = equippedWorkoutPlan.getWorkoutSchedule().get(2);
                } else if(day == Calendar.THURSDAY){
                    workout = equippedWorkoutPlan.getWorkoutSchedule().get(3);
                } else if(day == Calendar.FRIDAY){
                    workout = equippedWorkoutPlan.getWorkoutSchedule().get(4);
                } else if(day == Calendar.SATURDAY){
                    workout = equippedWorkoutPlan.getWorkoutSchedule().get(5);
                } else if(day == Calendar.SUNDAY){
                    workout = equippedWorkoutPlan.getWorkoutSchedule().get(6);
                }

                JPanel workoutPanel = new JPanel();
                workoutPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
                workoutPanel.setLayout(new GridLayout(4, 1));

                JLabel workoutLabel = new JLabel("Workout Name: " + workout.getName());
                workoutLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +12");
                workoutPanel.add(workoutLabel);

                JLabel durationLabel = new JLabel("Duration: " + workout.getDuration());
                durationLabel.putClientProperty(FlatClientProperties.STYLE, "font:regular +6");
                workoutPanel.add(durationLabel);

                JLabel descriptionLabel = new JLabel("Description: " + workout.getDescription());
                descriptionLabel.putClientProperty(FlatClientProperties.STYLE, "font:regular +6");
                workoutPanel.add(descriptionLabel);

                JLabel exerciseLabel = new JLabel("Exercises:");
                exerciseLabel.putClientProperty(FlatClientProperties.STYLE, "font:regular +6");
                workoutPanel.add(exerciseLabel);

                centerBottom.add(workoutPanel);

                JTable workoutExercisesTable =
                        new JTable(viewModel.getExerciseData(workout), viewModel.getExerciseColumns()){
                            public String getToolTipText( MouseEvent e )
                            {
                                int row = rowAtPoint( e.getPoint() );
                                int column = columnAtPoint( e.getPoint() );

                                Object value = getValueAt(row, column);
                                return value == null ? null : value.toString();
                            }

                        };
                workoutExercisesTable.setRowHeight(25);
                workoutExercisesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

                workoutExercisesTable.getColumnModel().getColumn(0).setPreferredWidth(50);
                workoutExercisesTable.getColumnModel().getColumn(1).setPreferredWidth(100);
                workoutExercisesTable.getColumnModel().getColumn(2).setPreferredWidth(5);
                workoutExercisesTable.getColumnModel().getColumn(3).setPreferredWidth(5);
                workoutExercisesTable.getColumnModel().getColumn(4).setPreferredWidth(5);

                workoutExercisesTable.setDefaultEditor(Object.class, null);

                for(int i = 0; i < workoutExercisesTable.getColumnCount(); i++) {
                    TableColumn col = workoutExercisesTable.getColumnModel().getColumn(i);
                    DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
                    dtcr.setHorizontalAlignment(SwingConstants.CENTER);
                    col.setCellRenderer(dtcr);
                }
                workoutExercisesTable.setShowGrid(true);

                workoutExercisesTable.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value,
                                                                   boolean isSelected, boolean hasFocus,
                                                                   int row, int column) {
                        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                        // Change font here (example: bold, size 14)
                        c.setFont(new Font("Monospaces", Font.BOLD, 15));

                        return c;
                    }
                });


                JScrollPane scrollPane = new JScrollPane(workoutExercisesTable);
                scrollPane.setPreferredSize(new Dimension(1200, 200));
                scrollPane.setMaximumSize(new Dimension(1200, 200));

                centerBottom.add(scrollPane);
            } else{
                JLabel titleLabel = new JLabel("No Scheduled Workouts");
                titleLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +12");
                centerBottom.add(titleLabel);
                //center.add(centerBottom);

                //main.add(center, BorderLayout.CENTER);
            }

            center.add(centerBottom, BorderLayout.SOUTH);
            main.add(center, BorderLayout.CENTER);

        } else{
            JLabel titleLabel = new JLabel("No Scheduled Workouts");
            titleLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +12");
            centerBottom.add(titleLabel);
            center.add(centerBottom, BorderLayout.SOUTH);

            main.add(center, BorderLayout.CENTER);
        }
    }
}
