
package fitness.app.Microtransactions;


import javax.swing.*;

public class MicroTransactionMain {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Micro Transaction Interface");
        TransactionInterface transaction = new TransactionInterface(frame);
    }
}
