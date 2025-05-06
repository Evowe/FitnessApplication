package Application.TheSwoleSection.LiveWorkout;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;
import Application.Main;
import Application.Utility.Widgets.SideMenu.SideMenuView;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//Made from scratch from WorkoutLibraryView
public class LiveWorkoutView extends JPanel {
    private LiveWorkoutViewModel viewModel;
	private String[] cols = {"Name", "Description", "Duration", "Start Time", "Join"};

    public LiveWorkoutView() {
        //View Model
        viewModel = new LiveWorkoutViewModel();

		Main.getCurrentUser().setRole("trainer");
        
        System.out.println(Main.getCurrentUser().getRole());

        //Setup Main Panel Layout
        setLayout(new MigLayout("fill, insets 20", "center", "center"));
        putClientProperty(FlatClientProperties.STYLE, "background:@background");

        JPanel main = new JPanel();
        main.putClientProperty(FlatClientProperties.STYLE, "background:@background");
		main.setLayout(new BorderLayout());

        //North Panel Setup - Title
        JPanel titlePanel = new JPanel();
        titlePanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        FlatLabel title = new FlatLabel();
        title.setText("Live Workouts");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +25");
        titlePanel.add(title);

        main.add(titlePanel, BorderLayout.NORTH);

        //Center Panel Setup - Exercise List/Table
        //Need to be able to use workout db before this can be updated
       JTable table = new JTable(viewModel.getWorkoutData(), cols) {
    	   @Override
    	   public boolean isCellEditable(int row, int column) {
    		   return false;
    	   }
       };
       
       table.setCellSelectionEnabled(true);
       table.setRowHeight(75);
       
       table.getColumn("Join").setCellRenderer(new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
				Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
				
				c.setForeground(Color.green);
				
				return c;
			}
			
		});
		
		//Allow admin to click promote/reset password to perform action for user
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				int col = table.columnAtPoint(e.getPoint());
				
				if (col == 4) {
					Object val = table.getValueAt(row, col);
					
					if (val.equals("Join")) {
						if (Main.getCurrentUser().getRole().equals("trainer")) {
							viewModel.startStream();
							
						} else {
							viewModel.joinStream();
						}
					}
				
				
				}
			}
		});
		

       JScrollPane scrollPane = new JScrollPane(table);
       scrollPane.setPreferredSize(new Dimension(1200, 650));
       scrollPane.setMaximumSize(new Dimension(1200, 650));

       JPanel tablePanel = new JPanel();
	   tablePanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");

	   //tablePanel.setBackground(Color.BLACK);
       tablePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
       tablePanel.add(scrollPane);

       main.add(tablePanel, BorderLayout.CENTER);
        
       //Only trainers are allowed to create live workouts
       if (Main.getCurrentUser().getRole().equals("trainer")) {
    	   //South Panel Setup - Buttons
	       JPanel buttonPanel = new JPanel();
	       buttonPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
	       buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
	        
	       FlatButton createWorkout = new FlatButton();
	       createWorkout.setMinimumHeight(35);
	       createWorkout.setMinimumWidth(200);
	       createWorkout.setText("+ Create Live Workout");
	       createWorkout.addActionListener(new ActionListener() {
	           @Override
	           public void actionPerformed(ActionEvent event) {

				   Main.setWindow("NewLiveWorkout");
	           }
	       });
	
	       buttonPanel.add(createWorkout);
	
	       main.add(buttonPanel, BorderLayout.SOUTH);
       }

       add(main, "growy, pushy");
    }
}


