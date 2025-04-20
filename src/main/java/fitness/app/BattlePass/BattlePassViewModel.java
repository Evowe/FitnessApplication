package fitness.app.BattlePass;

import fitness.app.CurrencyShop.currencyShopViewModel;
import fitness.app.CurrencyShop.currencyshopview;
import fitness.app.Objects.Account;

import javax.swing.*;

public class BattlePassViewModel {
    private static currencyshopview BPinterface;
    private static Account acc;


    public static JPanel getBPView() {
        BPinterface = new currencyshopview(acc);
        return BPinterface.get();
    }
}
