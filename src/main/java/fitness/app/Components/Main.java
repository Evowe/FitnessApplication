package fitness.app.Components;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto_mono.FlatRobotoMonoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import fitness.app.Components.CreateAccount.CreateAccountModel;
import fitness.app.Components.CreateAccount.CreateAccountView;
import fitness.app.Components.CreateAccount.CreateAccountViewModel;
import fitness.app.Components.Login.LoginView;
import fitness.app.Components.Login.LoginViewModel;

import javax.swing.*;
import java.awt.*;

public class Main {
    private static JFrame window;

    public static void main(String[] args) {
        System.setProperty("apple.awt.application.name", "Rocket Health");
        System.setProperty("apple.awt.application.appearance", "system");

        //FlatLaf setup & settings
        FlatRobotoMonoFont.install();
        FlatLaf.registerCustomDefaultsSource("Components.Themes");
        UIManager.put("defaultFont", new Font(FlatRobotoMonoFont.FAMILY, Font.PLAIN, 13));
        FlatMacDarkLaf.setup();

        //Application window
        window = new JFrame("Rocket Health");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(new Dimension(1200, 700));
        window.setLocationRelativeTo(null);
        window.add(LoginViewModel.getLoginView());
        window.getRootPane().putClientProperty("apple.awt.transparentTitleBar", true);
        window.getRootPane().putClientProperty("apple.awt.windowTitleVisible", false);

        window.setVisible(true);
    }

    public static void setWindow(String windowName) {
        switch (windowName) {
            case "LoginPage" -> {
                window.getContentPane().removeAll();
                window.add(LoginViewModel.getLoginView());
                window.revalidate();
                window.repaint();
            }
            case "CreateAccountPage" -> {
                window.getContentPane().removeAll();
                window.add(CreateAccountViewModel.getCreateAccountView());
                window.revalidate();
                window.repaint();
            }
        }
    }
}
