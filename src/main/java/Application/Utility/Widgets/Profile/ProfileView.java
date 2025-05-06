package Application.Utility.Widgets.Profile;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;
import Application.Main;
import Application.Databases.DatabaseManager;
import Application.Databases.ItemsDB;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;

public class ProfileView extends JPanel {
    public ProfileView() {
        setLayout(new MigLayout("fillx, insets 15", "center", "center"));
        putClientProperty(FlatClientProperties.STYLE, "arc:20;");

        FlatButton profileButton = new FlatButton();
        profileButton.putClientProperty(FlatClientProperties.STYLE, "background:@secondaryBackground; arc:250;");
        profileButton.setBorderPainted(false);
        profileButton.setMinimumSize(new Dimension(250,250));


        String iconPath = null;
        ItemsDB itemsDB = DatabaseManager.getItemsDB();
        try {
            iconPath = itemsDB.getEquippedRocketIconPath(Main.getCurrentUser().getUsername());
        } catch (Exception e) {
            System.err.println("Error loading equipped rocket: " + e.getMessage());
        }

        if (iconPath != null) {
            // Load the rocket image
            try {
                ImageIcon rocketIcon = new ImageIcon(iconPath);
                Image scaledImg = rocketIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
                profileButton.setIcon(new ImageIcon(scaledImg));
            } catch (Exception e) {
                // Fallback to default if image loading fails
                System.err.println("Error loading rocket image: " + e.getMessage());
                FlatSVGIcon icon = new FlatSVGIcon("Icons/user-circle.svg", 250, 250);
                profileButton.setIcon(icon);
            }
        } else {
            // No equipped rocket, use default SVG
            FlatSVGIcon icon = new FlatSVGIcon("Icons/user-circle.svg", 250, 250);
            profileButton.setIcon(icon);
        }

        JPanel textPanel = new JPanel(new MigLayout("wrap, insets 0, gapy 0"));

        FlatLabel profileName = new FlatLabel();
        profileName.putClientProperty(FlatClientProperties.STYLE, "font:bold +20;");
        profileName.setText(Main.getCurrentUser().getUsername());

        FlatLabel profileTitle = new FlatLabel();
        profileTitle.putClientProperty(FlatClientProperties.STYLE, "font:italics +10; foreground:@accent;");

        // Get the equipped title name from the database
        String titleName = "No Title";
        try {
            String equippedTitleName = itemsDB.getEquippedTitleName(Main.getCurrentUser().getUsername());
            if (equippedTitleName != null && !equippedTitleName.isEmpty()) {
                titleName = equippedTitleName;
            }
        } catch (Exception e) {
            System.err.println("Error loading equipped title: " + e.getMessage());
        }

        profileTitle.setText(titleName);

        textPanel.add(profileName);
        textPanel.add(profileTitle);

        add(profileButton);
        add(textPanel);
    }
}