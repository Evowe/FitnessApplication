package fitness.app.Microtransactions;

import javax.swing.*;

public class TransactionViewModel {
    private static TransactionView transactionInterface;

    public static JPanel getTransactionView() {
        transactionInterface = new TransactionView();
        return transactionInterface.get();
    }
}
