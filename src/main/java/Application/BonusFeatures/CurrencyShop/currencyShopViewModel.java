package Application.BonusFeatures.CurrencyShop;

import Application.Utility.Objects.Account;

import javax.swing.*;

public class currencyShopViewModel {

    /**
     * Static method to get the currency shop view
     * This is used for navigation in the main application
     * @param currentUser The currently logged in user
     * @return The currencyShopView panel
     */
    public static JPanel getCurrencyView(Account currentUser) {
        // Create a new model and set the current user
        currencyShopModel model = new currencyShopModel();
        model.setCurrentUser(currentUser);

        // Create the view with the model
        currencyShopView view = new currencyShopView(model);
        return view.get();
    }

    // Inner class for currency packs
    public static class CurrencyPack {
        private final int rocketBucks;
        private final double price;
        private final boolean recommended;

        public CurrencyPack(int rocketBucks, double price, boolean recommended) {
            this.rocketBucks = rocketBucks;
            this.price = price;
            this.recommended = recommended;
        }

        public int getRocketBucks() {
            return rocketBucks;
        }

        public double getPrice() {
            return price;
        }

        public boolean isRecommended() {
            return recommended;
        }
    }
}