package fitness.app.BattlePass;

import com.formdev.flatlaf.FlatClientProperties;
import fitness.app.Widgets.SideMenu.SideMenuView;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class BattlePassView extends JPanel {
    public BattlePassView() {
        setLayout(new MigLayout("insets 20", "left", "top"));
        putClientProperty(FlatClientProperties.STYLE, "background:@background");

        add(new SideMenuView(), "growy, pushy");


    }
}
