package Application.TheSwoleSection.Workout;

import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;

import Application.Utility.Widgets.SideMenu.SideMenuModel;
import net.miginfocom.swing.MigLayout;

public class WorkoutMenuView {
	private static JPanel mainPanel;
	
	//Testing
	private static JFrame window;
	
	/*
	 * Overall view template is consistent with landing page.
	 differences include button options and actual content
	 * TODO: figure out navigation setup. Back button or seperate window
	 */
	public static void createView() {
		//Init whole window with 2 cols with unequal ratio. 
		//20% - menu bar, 80% - workout content
		mainPanel = new JPanel(new MigLayout("", "[20%] [80%]", "[grow]"));
		
	//Button menu sidebar (consistent with home page)
		//TODO: play around with padding (insets)
		//TODO: grow sidebar in x dir to a certain extent
		JPanel bMenu = new JPanel(new MigLayout("fillx, insets 10", "fill, 125"));
		bMenu.putClientProperty(FlatClientProperties.STYLE, "arc:20;" + "background:lighten(@background,5%)");
		
		//Title for menu
		JLabel title = new JLabel("Rocket Health", SideMenuModel.getIcon("rocket"), JLabel.LEFT);
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +6");
        
        //Buttons to log workout/create a template
        //TODO: implement icons
        FlatButton logWorkout = new FlatButton();
        logWorkout.setText("Log Workout");
        logWorkout.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%);");
        
        FlatButton createTemplate = new FlatButton();
        createTemplate.setText("Create Template");
        createTemplate.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%);");
        
        //Add menu buttons to menu bar
        bMenu.add(title, "wrap, gapy 10");
        bMenu.add(logWorkout, "wrap, gapy 10");
        bMenu.add(createTemplate, "wrap, gapy 10");
        
    //Content
        //10% - Content title, 10% - Categories, 80% - Workouts (Scroll view)
        JPanel contentPanel = new JPanel(new MigLayout("", "", "[20%] [80%]"));
        JLabel contentTitle = new JLabel("Workouts");
        contentTitle.setFont(new Font("Arial", Font.BOLD, 36));
        		
        //Add labels and workouts to content panel
        contentPanel.add(contentTitle, "wrap, growy, push");
        
    //Categories panel
        JPanel categories = new JPanel(new MigLayout("", "[] [] []", ""));
        
    //Column 1
        JPanel col1 = new JPanel(new MigLayout("", "[grow]", ""));
        
        JLabel categ1 = new JLabel("Scheduled");
        categ1.setFont(new Font("Arial", Font.BOLD, 24));
        
        //Add workout UI objects to scroll view        
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
     
        JScrollPane col1Scroll = new JScrollPane(panel1);
        
        //Add category title and scroll view to col1 panel
        col1.add(categ1, "wrap, grow");
        col1.add(col1Scroll, "grow");
        
    //Column 2
        JPanel col2 = new JPanel(new MigLayout("", "[grow]", ""));
        
        JLabel categ2 = new JLabel("Templates");
        categ2.setFont(new Font("Arial", Font.BOLD, 24));
        
        //Add workout UI objects to scroll view        
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
     
        JScrollPane col2Scroll = new JScrollPane(panel2);
        
        //Add category title and scroll view to col2 panel
        col2.add(categ2, "wrap, grow");
        col2.add(col2Scroll, "grow");
        
    //Column 3
        JPanel col3 = new JPanel(new MigLayout("", "[grow]", ""));
        
        JLabel categ3 = new JLabel("Recents");
        categ3.setFont(new Font("Arial", Font.BOLD, 24));
        
        //Add workout UI objects to scroll view        
        JPanel panel3 = new JPanel();
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
     
        JScrollPane col3Scroll = new JScrollPane(panel3);
        
        //Add category title and scroll view to col3 panel
        col3.add(categ3, "wrap, grow");
        col3.add(col3Scroll, "grow");
        
        
        
        
        
        
        
        
    //Add components to containers
        //Add category columns to categories panel
        categories.add(col1, "grow, push");
        categories.add(col2, "grow, push");
        categories.add(col3, "grow, push");
        
        contentPanel.add(categories, "aligny top, grow, push");
        
        //add menu and content to window panel
        mainPanel.add(bMenu, "grow, push, gap 10 10 10 10");
        mainPanel.add(contentPanel, "grow, push");
        
        
        
        
        
        /*
        
        //Testing
        col1.setBorder(new LineBorder(Color.orange, 2));
        col2.setBorder(new LineBorder(Color.blue, 2));
        col3.setBorder(new LineBorder(Color.yellow, 2));
        categ1.setBorder(new LineBorder(Color.RED, 2));
        
        contentPanel.setBorder(new LineBorder(Color.BLUE, 2));
        bMenu.setBorder(new LineBorder(Color.YELLOW, 2));
        categories.setBorder(new LineBorder(Color.RED, 2));
        contentTitle.setBorder(new LineBorder(Color.RED, 2));
        */
        
	}
	
	
	
	
	
	
	
	
    /*
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
    */
}
