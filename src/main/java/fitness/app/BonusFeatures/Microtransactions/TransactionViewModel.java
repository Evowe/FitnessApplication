package fitness.app.BonusFeatures.Microtransactions;

import fitness.app.Utility.Objects.Account;

import javax.swing.*;

public class TransactionViewModel {
    private static TransactionView transactionInterface;
    private static Account acc;

    public Account getCardUser(Account acc) {
        TransactionViewModel.acc = acc;
        return acc;
    }

    public static JPanel getTransactionView() {
        transactionInterface = new TransactionView(acc);
        return transactionInterface.get();
    }
}
