package fitness.app.Widgets.Profile;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class ProfileView extends JPanel {
    public ProfileView() {
        setLayout(new MigLayout("fillx, insets 15", "center", "center"));
        putClientProperty(FlatClientProperties.STYLE, "arc:20;");

        FlatButton profileButton = new FlatButton();
        profileButton.putClientProperty(FlatClientProperties.STYLE, "background:@secondaryBackground; arc:100;");
        profileButton.setBorderPainted(false);
        profileButton.setMinimumSize(new Dimension(100,100));
        FlatSVGIcon icon = new FlatSVGIcon("Icons/user-circle.svg", 90, 90);
        profileButton.setIcon(icon);

        JPanel textPanel = new JPanel(new MigLayout("wrap, insets 0, gapy 0"));

        FlatLabel profileName = new FlatLabel();
        profileName.putClientProperty(FlatClientProperties.STYLE, "font:bold +20;");
        profileName.setText("Username");

        FlatLabel profileTitle = new FlatLabel();
        profileTitle.putClientProperty(FlatClientProperties.STYLE, "font:italics +10; foreground:@accent;");
        profileTitle.setText("Title");

        textPanel.add(profileName);
        textPanel.add(profileTitle);

        add(profileButton);
        add(textPanel);
    }
}
