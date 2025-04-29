package Application.BonusFeatures.BattlePass;

import Application.BonusFeatures.CurrencyShop.currencyshopview;
import Application.Utility.Objects.Account;

import javax.swing.*;

public class BattlePassViewModel {
    private static currencyshopview BPinterface;
    private static Account acc;


    public static JPanel getBPView() {
        BPinterface = new currencyshopview(acc);
        return BPinterface.get();
    }
}
