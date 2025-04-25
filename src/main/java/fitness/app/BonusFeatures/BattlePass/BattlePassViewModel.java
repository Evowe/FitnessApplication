package fitness.app.BonusFeatures.BattlePass;

import fitness.app.BonusFeatures.CurrencyShop.currencyshopview;
import fitness.app.Utility.Objects.Account;

import javax.swing.*;

public class BattlePassViewModel {
    private static currencyshopview BPinterface;
    private static Account acc;


    public static JPanel getBPView() {
        BPinterface = new currencyshopview(acc);
        return BPinterface.get();
    }
}
