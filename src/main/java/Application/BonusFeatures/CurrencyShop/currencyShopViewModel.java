package Application.BonusFeatures.CurrencyShop;

import Application.Utility.Objects.Account;
import Application.BonusFeatures.Microtransactions.TransactionViewModel;

import javax.swing.*;
import java.sql.SQLException;

public class currencyShopViewModel {
    private static currencyShopModel shopModel = new currencyShopModel();
    private static currencyShopView shopView;

    public static JPanel getCurrencyView(Account currentUser) {
        // Set up the model with the current user
        shopModel.setCurrentUser(currentUser);

        // Set up transaction handling
        TransactionViewModel transact = new TransactionViewModel();
        transact.getCardUser(currentUser);

        // Create the view
        shopView = new currencyShopView(shopModel);
        return shopView;
    }

    public static void addToCart(int rocketBucks, double price) {
        shopModel.addToCart(rocketBucks, price);
        updateViews();
    }

    public static void removeFromCart(int index) {
        shopModel.removeFromCart(index);
        updateViews();
    }

    public static void clearCart() {
        shopModel.clearCart();
        updateViews();
    }

    private static void updateViews() {
        if (shopView != null) {
            shopView.updateCartDisplay();
        }
    }

    public static void checkout() {
        if (shopModel.isCartEmpty()) return;

        processTransaction();
    }

    private static void processTransaction() {
        JFrame window = new JFrame("Rocket Health");
        window.setSize(new java.awt.Dimension(1000, 625));
        window.setLocationRelativeTo(null);
        window.add(TransactionViewModel.getTransactionView());
        window.getRootPane().putClientProperty("apple.awt.transparentTitleBar", true);
        window.getRootPane().putClientProperty("apple.awt.windowTitleVisible", false);
        window.setVisible(true);

        // Background thread to monitor card addition
        new Thread(() -> {
            while (!shopModel.hasCardInfo()) {
                try {
                    Thread.sleep(500); // Check every 500ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }

            // Card is now available — update wallet on the Swing UI thread
            SwingUtilities.invokeLater(() -> {
                try {
                    boolean success = shopModel.updateUserWallet();
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Rocket Bucks added!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to add Rocket Bucks!",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }

                window.setVisible(false);
                window.dispose();
                updateViews();
            });
        }).start();
    }
}