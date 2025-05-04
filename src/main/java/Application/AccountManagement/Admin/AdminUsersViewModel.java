package Application.AccountManagement.Admin;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

import Application.Utility.Objects.Account;

public class AdminUsersViewModel {
	private AdminUsersModel model = new AdminUsersModel();
	private DefaultTableModel tableModel; // Reference to the table model

	public void setTableModel(DefaultTableModel model) {
		this.tableModel = model;
	}

	public List<Object[]> getUsers() {
		List<Object[]> users = new ArrayList<>();

		for (Account acc : model.getUsers()) {
			String promote;

			if (acc.getRole().equals("user")) {
				promote = "Promote";
			} else {
				promote = "Depromote";
			}

			users.add(new Object[] {acc.getUsername(), acc.getRole(), promote, "Reset Password"});
		}

		return users;
	}

	public void promoteUser(String user) {
		model.promoteUser(user);
		refreshTableData();
	}

	public void depromoteUser(String user) {
		model.depromoteUser(user);
		refreshTableData();
	}

	public boolean resetUserPassword(String username, String newPassword) {
		try {
			// Call the model method to reset the password
			boolean success = model.resetUserPassword(username, newPassword);

			// No need to refresh the table since password changes don't affect displayed data
			return success;
		} catch (Exception e) {
			System.err.println("Error resetting password: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	private void refreshTableData() {
		if (tableModel != null) {
			// Clear the table
			tableModel.setRowCount(0);

			// Refill with fresh data
			for (Object[] row : getUsers()) {
				tableModel.addRow(row);
			}

			System.out.println("Table data refreshed");
		}
	}
}