package fitness.app.Admin;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import fitness.app.Main;
import fitness.app.Home.HomeViewModel;
import fitness.app.Widgets.SideMenu.SideMenuModel;
import net.miginfocom.swing.MigLayout;

public class AdminHomeView extends JPanel{
	public void createView() {
		//Init whole window with 2 cols with unequal ratio. 
		//20% - menu bar, 80% - content
		setLayout(new MigLayout("", "[20%] [80%]", "[grow]"));
		
		//Button menu sidebar (consistent with home page)
			//TODO: play around with padding (insets)
			//TODO: grow sidebar in x dir to a certain extent
			JPanel bMenu = new JPanel(new MigLayout("fillx, insets 10", "fill, 125"));
			bMenu.putClientProperty(FlatClientProperties.STYLE, "arc:20;" + "background:lighten(@background,5%)");
			
			//Title for menu
			JLabel title = new JLabel("Rocket Health", SideMenuModel.getIcon("rocket"), JLabel.LEFT);
	        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +6");
	        
	        //Button to view all users (including trainers)
	        //TODO: implement icons
	        FlatButton allUsers = new FlatButton();
	        allUsers.setText("All Users");
	        allUsers.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%);");
	        allUsers.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		Main.setWindow("AdminUsers");
	        	}
	        });
	        
	        //Log out button
	        FlatButton logOutButton = new FlatButton();
	        logOutButton.setText("Log Out");
	        logOutButton.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%);");
	        logOutButton.setIcon(SideMenuModel.getIcon("log-out"));
	        logOutButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent event) {
	                Main.setWindow("LoginPage");
	            }
	        });
	        
	        //Add menu buttons to menu bar
	        bMenu.add(title, "wrap, gapy 10");
	        bMenu.add(allUsers, "wrap, gapy 10");
	        bMenu.add(logOutButton, "aligny bottom, push");
	        
	        //Requests panel
	        JPanel contentPanel = new JPanel(new MigLayout("", "[grow]", ""));
	        
	        JLabel contentTitle = new JLabel("Requests");
	        contentTitle.setFont(new Font("Arial", Font.BOLD, 36));
	        
	        
	        //TODO: implement with db
	        JPanel requests = new JPanel();
	        requests.setLayout(new BoxLayout(requests, BoxLayout.Y_AXIS));
	        
	        JScrollPane requestsScroll = new JScrollPane(requests);
	   
	        contentPanel.add(contentTitle, "wrap, growx");
	        contentPanel.add(requestsScroll, "growx");	        

	        //Add components to main panel
	        add(bMenu, "grow, push");
	        add(contentPanel, "grow, push");
	}
	
}
