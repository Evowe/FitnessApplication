package fitness.app.BonusFeatures.CurrencyShop;

import fitness.app.BadProjectStructureSection.Objects.Account;

import javax.swing.*;

public class currencyShopViewModel {
    private static currencyshopview currencyInterface;
    private static Account acc;

    public Account findCurrUser ( Account acc ) {
        currencyShopViewModel.acc = acc;
        return acc;
    }

    public static JPanel getCurrencyView() {
        currencyInterface = new currencyshopview(acc);
        return currencyInterface.get();
    }
}
