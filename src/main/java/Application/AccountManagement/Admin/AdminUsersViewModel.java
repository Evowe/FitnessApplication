package Application.AccountManagement.Admin;

import java.util.ArrayList;
import java.util.List;

import Application.Utility.Objects.Account;

public class AdminUsersViewModel {
	private AdminUsersModel model = new AdminUsersModel();
	
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
	}
	
	public void depromoteUser(String user) {
		model.depromoteUser(user);
	}

	public static void refreshUsersView() {
		AdminUsersView.refreshView();
	}
}
