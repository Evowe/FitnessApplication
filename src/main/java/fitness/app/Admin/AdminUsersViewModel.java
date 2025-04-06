package fitness.app.Admin;

import java.util.ArrayList;
import java.util.List;

import fitness.app.Objects.Account;
import fitness.app.Objects.AccountsDB;

public class AdminUsersViewModel {
	private AdminUsersModel model = new AdminUsersModel();
	
	public List<Object[]> getUsers() {
		List<Object[]> users = new ArrayList<>();
 		
		for (Account acc : model.getUsers()) {
			String promote;
			
			if (acc.getRole().equals("user")) {
				promote = "Promote";
				
			} else {
				promote = "";
			}
			
			users.add(new Object[] {acc.getUsername(), acc.getRole(), promote, "Reset Password"});
		}
		
		return users;
	}
	
	
}
