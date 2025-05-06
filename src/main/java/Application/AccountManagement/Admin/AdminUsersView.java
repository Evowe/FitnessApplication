package Application.AccountManagement.Admin;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.extras.components.FlatButton;

import Application.Main;
import net.miginfocom.swing.MigLayout;

public class AdminUsersView {
	private static AdminUsersViewModel viewModel = new AdminUsersViewModel();

	private static JPanel mainPanel;
	private static DefaultTableModel tableModel;
	private static JTable table;

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
		FlatButton backButton = new FlatButton();
		backButton.setText("Log Out");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.setWindow("LoginPage");
			}
		});

		JLabel title = new JLabel("All Users");
		title.setFont(new Font("Arial", Font.BOLD, 36));

		FlatButton addUser = new FlatButton();
		addUser.setText("+ Add User");
		addUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.setWindow("AdminAddUserView");
			}
		});

		//Add top bar items to top bar panel
		topBar.add(backButton, "grow, push, gapright 20");
		topBar.add(title, "grow, push");
		topBar.add(addUser, "align right, grow, push");

		//Content Panel
		JPanel content = new JPanel(new MigLayout("", "grow", "grow"));

		//Create table model with columns
		String[] cols = {"User", "Role", "Promotion", "Reset Password"};
		tableModel = new DefaultTableModel(cols, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		//get users from db and add to table model
		List<Object[]> data = viewModel.getUsers();
		for (Object[] row : data) {
			tableModel.addRow(row);
		}

		//Create table with model
		table = new JTable(tableModel);
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

				if (row >= 0 && col >= 0) {
					Object val = table.getValueAt(row, col);
					String user = (String) table.getValueAt(row, 0);

					if (col == 2) {
						if ("Promote".equals(val)) {
							// Confirmation dialog
							int confirm = JOptionPane.showConfirmDialog(
									mainPanel,
									"Are you sure you want to promote user '" + user + "' to trainer?",
									"Confirm Promotion",
									JOptionPane.YES_NO_OPTION
							);

							if (confirm == JOptionPane.YES_OPTION) {
								viewModel.promoteUser(user);
								refreshTableData();
							}
						} else if ("Depromote".equals(val)) {
							// Confirmation dialog
							int confirm = JOptionPane.showConfirmDialog(
									mainPanel,
									"Are you sure you want to demote admin '" + user + "' to regular user?",
									"Confirm Demotion",
									JOptionPane.YES_NO_OPTION
							);

							if (confirm == JOptionPane.YES_OPTION) {
								viewModel.depromoteUser(user);
								refreshTableData();
							}
						}
					} else if (col == 3 && "Reset Password".equals(val)) {
						// Create a password reset dialog
						JPanel passwordPanel = new JPanel(new MigLayout("", "[][grow]", "[][]"));

						JLabel newPassLabel = new JLabel("New Password:");
						JLabel confirmPassLabel = new JLabel("Confirm Password:");

						javax.swing.JPasswordField newPassField = new javax.swing.JPasswordField(20);
						javax.swing.JPasswordField confirmPassField = new javax.swing.JPasswordField(20);

						passwordPanel.add(newPassLabel, "cell 0 0");
						passwordPanel.add(newPassField, "cell 1 0, growx");
						passwordPanel.add(confirmPassLabel, "cell 0 1");
						passwordPanel.add(confirmPassField, "cell 1 1, growx");

						int result = JOptionPane.showConfirmDialog(
								mainPanel,
								passwordPanel,
								"Reset Password for " + user,
								JOptionPane.OK_CANCEL_OPTION,
								JOptionPane.PLAIN_MESSAGE);

						if (result == JOptionPane.OK_OPTION) {
							String newPass = new String(newPassField.getPassword());
							String confirmPass = new String(confirmPassField.getPassword());

							if (newPass.isEmpty()) {
								JOptionPane.showMessageDialog(
										mainPanel,
										"Password cannot be empty.",
										"Error",
										JOptionPane.ERROR_MESSAGE);
							} else if (!newPass.equals(confirmPass)) {
								JOptionPane.showMessageDialog(
										mainPanel,
										"Passwords do not match.",
										"Error",
										JOptionPane.ERROR_MESSAGE);
							} else {
								// Reset the password in the view model
								boolean success = viewModel.resetUserPassword(user, newPass);

								if (success) {
									JOptionPane.showMessageDialog(
											mainPanel,
											"Password for user '" + user + "' has been reset successfully.",
											"Success",
											JOptionPane.INFORMATION_MESSAGE);
								} else {
									JOptionPane.showMessageDialog(
											mainPanel,
											"Failed to reset password for user '" + user + "'.",
											"Error",
											JOptionPane.ERROR_MESSAGE);
								}
							}
						}
					}
				}
			}
		});

		JScrollPane scrollTable = new JScrollPane(table);
		content.add(scrollTable, "grow, push");

		//Add components to main frame
		mainPanel.add(topBar, "aligny top, growx, wrap");
		mainPanel.add(content, "grow, push, gap 20 20 10 20");
	}

	// Method to refresh only the table data
	public static void refreshTableData() {
		SwingUtilities.invokeLater(() -> {
			try {
				// Clear existing data
				tableModel.setRowCount(0);

				// Get fresh data from view model
				List<Object[]> freshData = viewModel.getUsers();

				// Add fresh data to table model
				for (Object[] row : freshData) {
					tableModel.addRow(row);
				}

				System.out.println("Table data refreshed with " + freshData.size() + " rows");
			} catch (Exception e) {
				System.err.println("Error refreshing table data: " + e.getMessage());
				e.printStackTrace();
			}
		});
	}

	public static void refreshView() {
		SwingUtilities.invokeLater(() -> {
			if (tableModel != null) {
				refreshTableData();
			} else {
				// If table model doesn't exist yet, recreate the view
				if (mainPanel != null) {
					mainPanel.removeAll();
					createView();
					mainPanel.revalidate();
					mainPanel.repaint();
				}
			}
		});
	}
}