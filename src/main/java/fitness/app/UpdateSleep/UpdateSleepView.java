package fitness.app.UpdateSleep;

import fitness.app.Objects.Account;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto_mono.FlatRobotoMonoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateSleepView extends JPanel {
    private static JFrame window;
    private static JTextField dateField;
    private static JTextField hoursField;
    private static JButton submitButton;
    //private static JButton createAccountButton;
    private static JPanel mainPanel;
//    private static JPanel logoPanel;
//    private static JPanel loginPanel;

    public UpdateSleepView() {
        //FlatLaf setup & settings
        FlatRobotoMonoFont.install();
        FlatLaf.registerCustomDefaultsSource("Components.Themes");
        UIManager.put("defaultFont", new Font(FlatRobotoMonoFont.FAMILY, Font.PLAIN, 13));
        FlatMacDarkLaf.setup();

        //Application window
        window = new JFrame("Update Sleep");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(new Dimension(1200, 700));
        window.setLocationRelativeTo(null);

        //Organizes the window into 2 halves

        //Left half
//        logoPanel = new JPanel(new MigLayout("fill,insets 20", "center", "center"));
//        logoPanel.putClientProperty(FlatClientProperties.STYLE, ""+ "[dark]background:darken(@background,5%)");

//        BufferedImage img = null;
//        try {
//            img = ImageIO.read(new File("src/main/resources/Images/RocketHealthLogo.PNG"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        Image scaledImg = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
//        ImageIcon icon = new ImageIcon(scaledImg);
//        JLabel logo = new JLabel(icon);
//
//        logoPanel.add(logo);
//
//        mainPanel.add(logoPanel);


        //Right half
        mainPanel = new JPanel(new MigLayout("fill,insets 20", "center", "center"));

        JPanel loginMenu = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,275"));
        loginMenu.putClientProperty(FlatClientProperties.STYLE, "" + "arc:20;" + "background:lighten(@background,5%)");

        JLabel title = new JLabel("Update Sleep");
        title.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +6");

        JLabel description = new JLabel("All fields required");
        description.putClientProperty(FlatClientProperties.STYLE, "" + "foreground:darken(@foreground,33%)");

        dateField = new JTextField();
        dateField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "MM/DD/YYYY");

        hoursField = new JTextField();
        hoursField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Total hours of sleep");

        submitButton = new JButton("Submit");
        submitButton.putClientProperty(FlatClientProperties.STYLE, "" + "background:lighten(@background,10%);");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Account account = new Account(hoursField.getText(), hoursField.getText());
                //String errorMessage = LoginViewModel.logInRequest(account);
//                if (errorMessage != null) {
//                    description.setText(errorMessage);
//                    passwordField.setText("");
//                }
            }
        });

        loginMenu.add(description);
        loginMenu.add(new JLabel("Date"), "gapy 8");
        loginMenu.add(dateField);
        loginMenu.add(new JLabel("Sleep"), "gapy 8");
        loginMenu.add(hoursField);
        loginMenu.add(submitButton, "gapy 10");
        mainPanel.add(loginMenu);

        window.add(mainPanel);

        EventQueue.invokeLater(() -> {
            window.setVisible(true);
            System.setProperty("apple.awt.application.name", "Rocket Health");
            System.setProperty("apple.awt.application.appearance", "system");
            window.getRootPane().putClientProperty("apple.awt.transparentTitleBar", true);
            window.getRootPane().putClientProperty("apple.awt.windowTitleVisible", false);
        });
    }
}
