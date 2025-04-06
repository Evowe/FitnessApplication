package fitness.app.Admin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.fonts.roboto_mono.FlatRobotoMonoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import fitness.app.Main;
import fitness.app.CreateAccount.CreateAccountViewModel;
import fitness.app.Home.HomeViewModel;
import fitness.app.Login.LoginViewModel;
import fitness.app.Statistics.StatsViewModel;
import net.miginfocom.swing.MigLayout;

public class AdminHomeView {
	private static JPanel mainPanel;

	public static JPanel getMainPanel() {
		if (mainPanel == null) {
			createView();
		}
		return mainPanel;
	}
	
	
	//Testing
	private static JFrame window;
	
	
	public static void createView() {		
		//Init whole window with 2 cols with unequal ratio. 
		//20% - menu bar, 80% - content
		mainPanel = new JPanel(new MigLayout("", "[20%] [80%]", "[grow]"));
		
		//Button menu sidebar (consistent with home page)
			//TODO: play around with padding (insets)
			//TODO: grow sidebar in x dir to a certain extent
			JPanel bMenu = new JPanel(new MigLayout("fillx, insets 10", "fill, 125"));
			bMenu.putClientProperty(FlatClientProperties.STYLE, "arc:20;" + "background:lighten(@background,5%)");
			
			//Title for menu
			JLabel title = new JLabel("Rocket Health", HomeViewModel.getIcon("rocket"), JLabel.LEFT);
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
	        logOutButton.setIcon(HomeViewModel.getIcon("log-out"));
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
	        
	        
	        //Test
	        for (int i = 0; i < 60; i++) {
	        	requests.add(new JLabel("test" + i));
	        }
	        
	        JScrollPane requestsScroll = new JScrollPane(requests);
	    
	        
	        contentPanel.add(contentTitle, "wrap, growx");
	        contentPanel.add(requestsScroll, "growx");

	        //Test
	        contentPanel.setBorder(new LineBorder(Color.RED, 2));
	        

	        //Add components to main panel
	        mainPanel.add(bMenu, "grow, push");
	        mainPanel.add(contentPanel, "grow, push");	
	}
	
	
	//Testing
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
