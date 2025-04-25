package Application.Utility.Widgets.SideMenu;

import com.kitfox.svg.app.beans.SVGIcon;

public class SideMenuViewModel {
    private final SideMenuModel sideMenuModel;

    public SideMenuViewModel() {
        sideMenuModel = new SideMenuModel();
    }

    public SVGIcon getIcon(String iconName) {
        return sideMenuModel.getIcon(iconName);
    }
}
