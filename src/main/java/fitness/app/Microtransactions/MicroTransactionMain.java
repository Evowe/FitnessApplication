
package fitness.app.Microtransactions;


import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto_mono.FlatRobotoMonoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import fitness.app.Login.LoginView;
import fitness.app.Login.LoginViewModel;
import fitness.app.Objects.AccountsDB;
import fitness.app.Objects.DatabaseManager;
import fitness.app.Objects.ExerciseDB;

import javax.swing.*;
import java.awt.*;

public class MicroTransactionMain {
    private static JFrame window;

    public static void main(String[] args) {
        System.setProperty("apple.awt.application.name", "Rocket Health");
        System.setProperty("apple.awt.application.appearance", "system");

        //DatabaseManager.addDatabase("accounts", new AccountsDB("accounts"));
        //DatabaseManager.addDatabase("exercises", new ExerciseDB("exercises"));
        //System.out.println("Databases initialized successfully.");

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
        window.add(TransactionViewModel.getTransactionView());
        window.getRootPane().putClientProperty("apple.awt.transparentTitleBar", true);
        window.getRootPane().putClientProperty("apple.awt.windowTitleVisible", false);

        window.setVisible(true);
    }
}
