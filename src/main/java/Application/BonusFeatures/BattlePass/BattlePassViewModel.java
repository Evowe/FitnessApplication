package Application.BonusFeatures.BattlePass;

import Application.BonusFeatures.CurrencyShop.currencyShopModel;
import Application.BonusFeatures.CurrencyShop.currencyShopView;
import Application.Utility.Objects.Account;

import javax.swing.*;

public class BattlePassViewModel {
    private static currencyShopView BPinterface;
    private static Account acc;


    public static JPanel getBPView() {
        currencyShopModel model = new currencyShopModel();
        model.setCurrentUser(acc);

        // Create the view with the model
        BPinterface = new currencyShopView(model);
        return BPinterface.get();
    }
}
