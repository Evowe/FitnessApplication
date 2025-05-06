package Application.BonusFeatures.Microtransactions;

import Application.Utility.Objects.Account;
import Application.BonusFeatures.CurrencyShop.currencyShopModel;

import javax.swing.*;

public class TransactionViewModel {
    private static TransactionView transactionView;
    private static Account currentUser;
    private static JDialog parentDialog;
    private static currencyShopModel shopModel;

    public void getCardUser(Account acc) {
        currentUser = acc;
        transactionView = new TransactionView(acc);
    }

    public void getCardUser(Account acc, JDialog dialog, currencyShopModel model) {
        currentUser = acc;
        parentDialog = dialog;
        shopModel = model;
        transactionView = new TransactionView(acc, dialog, model);
    }

    public static JPanel getTransactionView() {
        return transactionView.get();
    }

    // Helper method to set the parent dialog after view creation
    public static void setParentDialog(JDialog dialog) {
        if (transactionView != null) {
            transactionView.setParentDialog(dialog);
        }
        parentDialog = dialog;
    }

    // Helper method to set the shop model after view creation
    public static void setShopModel(currencyShopModel model) {
        if (transactionView != null) {
            transactionView.setShopModel(model);
        }
        shopModel = model;
    }
}