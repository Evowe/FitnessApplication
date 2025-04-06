package fitness.app.Admin;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.fonts.roboto_mono.FlatRobotoMonoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import net.miginfocom.swing.MigLayout;

//TODO: search filter for username and sort by

public class AdminUsersView {
	private static AdminUsersViewModel viewModel = new AdminUsersViewModel();
	
	private static JPanel mainPanel;
	
	public static JPanel getView() {
		if (mainPanel == null) {
			createView();
		}
		return mainPanel;
	}
	
	public static void createView() {
		mainPanel = new JPanel(new MigLayout("", "grow", ""));
		
		//Top Bar (Back Button, Title, Add User Button)
		JPanel topBar = new JPanel(new MigLayout("", "[] [] push[]", ""));
		
		//Top bar items
		//TODO: Implement icon
		FlatButton backButton = new FlatButton();
		backButton.setText("<-Back");
		
		JLabel title = new JLabel("All Users");
		title.setFont(new Font("Arial", Font.BOLD, 36));
		
		FlatButton addUser = new FlatButton();
		addUser.setText("+ Add User");
		
		//Add top bar items to top bar panel
		topBar.add(backButton, "grow, push, gapright 20");
		topBar.add(title, "grow, push");
		topBar.add(addUser, "align right, grow, push");
		
		//Content Panel
		JPanel content = new JPanel();
		
		//Create table
		String[] cols = {"User", "Role", "Promote", "Reset Password"};
		
		//get users from db and loop
		//TODO: implement by getting data from db
		List<Object[]> data = viewModel.getUsers();
		
		//Testing
		for (int i = 0; i < 50; i++) {
			data.add(new Object[] {"User name", "User role", "Promote", "Reset Password"});
		}
		
		//Init table
		JTable table = new JTable(data.toArray(new Object[data.size()][4]), cols) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setCellSelectionEnabled(true);
		
		//set column text for promote and reset password to red
		table.getColumn("Promote").setCellRenderer(new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
				Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
				
				c.setForeground(Color.red);
				
				return c;
			}
		});
		
		table.getColumn("Reset Password").setCellRenderer(new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
				Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
				
				c.setForeground(Color.red);
				
				return c;
			}
			
		});
		
		//Allow admin to click promote/reset password to perform action for user
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				int col = table.columnAtPoint(e.getPoint());
				
				if (col == 2 || col == 3) {
					Object val = table.getValueAt(row, col);
					
					if (cols[2].equals(val)) {
						//method for promotion
						System.out.println("promote");
					} else if (cols[3].equals(val)) {
						//method for reset password
						System.out.println("reset password");
					}
				}
				
				
			}
		});
		
		JScrollPane scrollTable = new JScrollPane(table);
		
		//Add components to main frame
		mainPanel.add(topBar, "aligny top, growx, wrap");
		mainPanel.add(scrollTable, "grow, push, gap 20 20 10 20");
	}
	
	
	//Testing
	private static JFrame window;
	public static void main(String[] args) {
		//FlatLaf setup & settings
		FlatRobotoMonoFont.install();
		FlatLaf.registerCustomDefaultsSource("FlatLafSettings");
		UIManager.put("defaultFont", new Font(FlatRobotoMonoFont.FAMILY, Font.PLAIN, 13));
		FlatMacDarkLaf.setup();

		//Application window
		window = new JFrame("Rocket Health");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(new Dimension(1200, 700));
		window.setLocationRelativeTo(null);
		createView();
		window.add(mainPanel);
		window.getRootPane().putClientProperty("apple.awt.transparentTitleBar", true);
		window.getRootPane().putClientProperty("apple.awt.windowTitleVisible", false);

		window.setVisible(true);
	}
	
	
}
