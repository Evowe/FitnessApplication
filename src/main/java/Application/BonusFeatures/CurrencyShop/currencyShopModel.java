package Application.BonusFeatures.CurrencyShop;

import Application.Utility.Objects.Account;
import Application.Utility.Objects.CartItem;
import Application.Utility.Objects.ShoppingCart;

import java.sql.SQLException;

import static Application.Utility.Objects.Account.updateWallet;

public class currencyShopModel {
    private Account currentUser;
    private ShoppingCart cart;

    public currencyShopModel() {
        this.cart = new ShoppingCart();
    }

    public void setCurrentUser(Account user) {
        this.currentUser = user;
    }

    public Account getCurrentUser() {
        return this.currentUser;
    }

    public ShoppingCart getCart() {
        return this.cart;
    }

    public void addToCart(int rocketBucks, double price) {
        CartItem item = new CartItem(rocketBucks, price);
        cart.addItem(item);
    }

    public void removeFromCart(int index) {
        cart.removeItem(index);
    }

    public void clearCart() {
        cart.clearCart();
    }

    public int getCartItemCount() {
        return cart.getItems().size();
    }

    public boolean isCartEmpty() {
        return cart.getItems().isEmpty();
    }

    public int getTotalRocketBucks() {
        return cart.getTotalRocketBucks();
    }

    public double getTotalPrice() {
        return cart.getTotalPrice();
    }

    public boolean updateUserWallet() throws SQLException {
        if (currentUser == null || isCartEmpty()) {
            return false;
        }

        updateWallet(currentUser.getUsername(), getTotalRocketBucks());
        clearCart();
        return true;
    }

    public boolean hasCardInfo() {
        try {
            return currentUser != null && currentUser.loadCreditCard() != null;
        } catch (SQLException e) {
            System.out.println("Error checking card info: " + e.getMessage());
            return false;
        }
    }

    public boolean processPayment() throws SQLException {
        if (!hasCardInfo()) {
            return false;
        }

        return updateUserWallet();

    }
}