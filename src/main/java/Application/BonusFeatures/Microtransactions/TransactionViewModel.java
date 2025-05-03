package Application.BonusFeatures.Microtransactions;

import Application.Utility.Objects.Account;
import java.sql.SQLException;
import javax.swing.*;

public class TransactionViewModel {
    private static TransactionView transactionInterface;
    private static Account acc;

    public Account getCardUser(Account acc) {
        TransactionViewModel.acc = acc;

        try {
            acc.loadCreditCard();
        } catch (SQLException e) {
            System.err.println("Error loading credit card: " + e.getMessage());
        }

        return acc;
    }

    public static JPanel getTransactionView() {
        transactionInterface = new TransactionView(acc);
        return transactionInterface.get();
    }
}