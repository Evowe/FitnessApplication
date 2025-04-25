package fitness.app.AccountManagement.Admin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fitness.app.Utility.Objects.Account;
import fitness.app.Utility.Databases.AccountsDB;
import fitness.app.Utility.Objects.DatabaseManager;

public class AdminUsersModel {
	private AccountsDB accDB = DatabaseManager.getAccountsDB();
	
	public List<Account> getUsers() {
		List<Account> allUsers = new ArrayList<>();
		
		//uses db object to get all user accounts
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
			//System.out.println(acc.getId());
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
}
