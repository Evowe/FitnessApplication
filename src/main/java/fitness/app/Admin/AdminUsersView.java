package fitness.app.Admin;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import fitness.app.Main;
import net.miginfocom.swing.MigLayout;

//TODO: search filter for username and sort by
//TODO: depromote users
//TODO: confirmation dialog for promotion

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
		addUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.setWindow("AdminAddUser");
			}
		});
		
		//Add top bar items to top bar panel
		topBar.add(backButton, "grow, push, gapright 20");
		topBar.add(title, "grow, push");
		topBar.add(addUser, "align right, grow, push");
		
		//Content Panel
		JPanel content = new JPanel();
		
		//Create table
		String[] cols = {"User", "Role", "Promotion", "Reset Password"};
		
		//get users from db and loop
		//TODO: implement by getting data from db
		List<Object[]> data = viewModel.getUsers();
		
		//Init table
		JTable table = new JTable(data.toArray(new Object[data.size()][4]), cols) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setCellSelectionEnabled(true);
		
		//set column text for promote and reset password to red
		table.getColumn("Promotion").setCellRenderer(new DefaultTableCellRenderer() {
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
					String user = (String) table.getValueAt(row, 0);
					
					if ("Promote".equals(val)) {
						//Promotes user
						viewModel.promoteUser(user);	
						
					} else if ("Depromote".equals(val)) {
						//Depromotes user
						viewModel.depromoteUser(user);
						
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
}
