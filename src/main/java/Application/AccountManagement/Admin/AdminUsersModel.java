package Application.AccountManagement.Admin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Application.Utility.Objects.Account;
import Application.Databases.AccountsDB;
import Application.Databases.DatabaseManager;

public class AdminUsersModel {
	private AccountsDB accDB = DatabaseManager.getAccountsDB();

	public List<Account> getUsers() {
		List<Account> allUsers = new ArrayList<>();

		try {
			allUsers = accDB.getAllUsers();

		} catch (SQLException e) {
			System.out.println("Error");
			//e.printStackTrace();

		}

		return allUsers;
	}

	public void promoteUser(String user) {
		try {
			Account acc = accDB.getAccount(user);
			accDB.promoteUser(acc.getId());

		} catch (SQLException e) {
			System.out.println("Error: Could not get account");
		}
	}

	public void depromoteUser(String user) {
		try {
			Account acc = accDB.getAccount(user);
			accDB.depromoteUser(acc.getId());

		} catch (SQLException e) {
			System.out.println("Error: Could not get account");
		}
	}

	public boolean resetUserPassword(String username, String newPassword) {
		try {
			// Use the existing changePassword method in AccountsDB
			return accDB.changePassword(username, newPassword);
		} catch (SQLException e) {
			System.out.println("Error: Could not reset password for user " + username);
			e.printStackTrace();
			return false;
		}
	}
}