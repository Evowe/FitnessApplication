package fitness.app.Admin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fitness.app.Objects.Account;
import fitness.app.Objects.AccountsDB;
import fitness.app.Objects.DatabaseManager;

public class AdminUsersModel {
	private AccountsDB accDB = (AccountsDB) DatabaseManager.getDatabase("accounts");
	
	public List<Account> getUsers() {
		List<Account> allUsers = new ArrayList<>();
		
		//uses db object to get all user accounts
		try {
			allUsers = accDB.getAllUsers();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return allUsers;
	}
}
