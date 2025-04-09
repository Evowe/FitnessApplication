package fitness.app.Widgets.SideMenu;

import com.kitfox.svg.app.beans.SVGIcon;
import fitness.app.Home.HomeModel;
import fitness.app.Home.HomeView;
import javax.swing.*;

public class SideMenuViewModel {
    private final SideMenuModel sideMenuModel;

    public SideMenuViewModel() {
        sideMenuModel = new SideMenuModel();
    }

    public SVGIcon getIcon(String iconName) {
        return sideMenuModel.getIcon(iconName);
    }
}
