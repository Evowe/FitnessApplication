package fitness.app.CurrencyShop;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;
import fitness.app.Microtransactions.TransactionViewModel;
import fitness.app.Objects.*;
import fitness.app.Widgets.SideMenu.SideMenuView;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import static fitness.app.Objects.Account.updateWallet;

public class currencyshopview extends JPanel {
    private static JPanel mainPanel;
        public currencyshopview(Account acc) {
            setLayout(new MigLayout("fill, insets 20"));
            putClientProperty(FlatClientProperties.STYLE, "background:@background;");

            JPanel menuPanel = new JPanel(new MigLayout("insets 0"));
            menuPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background;");
            menuPanel.add(new SideMenuView(), "growy, pushy");

            JPanel everythingElsePanel = new JPanel(new MigLayout("insets 0", "center",  "center"));
            everythingElsePanel.putClientProperty(FlatClientProperties.STYLE, "background:@background;");


            JPanel shopMenu = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,275"));
            shopMenu.putClientProperty(FlatClientProperties.STYLE, "arc:20;");

            FlatLabel title = new FlatLabel();
            title.setText("Select currency pack");
            title.putClientProperty(FlatClientProperties.STYLE, "font:bold +6");

            FlatLabel subtext = new FlatLabel();
            subtext.setText("adds a %5 credit card fee to listed price");
            subtext.putClientProperty(FlatClientProperties.STYLE, "font:bold +3");

            // Create and add multiple text boxes to the panel
            FlatButton five = new FlatButton();
            five.setBorderPainted(false);
            five.setText("499 Rocket Bucks" + "\n" + "$5.00");
            five.putClientProperty(FlatClientProperties.STYLE, "background:@accent;");

            FlatButton ten = new FlatButton();
            ten.setBorderPainted(false);
            ten.setText("999 Rocket Bucks" + "\n" + "$10.00");
            ten.putClientProperty(FlatClientProperties.STYLE, "background:@accent;");

            FlatButton twenty = new FlatButton();
            twenty.setBorderPainted(false);
            twenty.setText("1999 Rocket Bucks" + "\n" + "$20.00");
            twenty.putClientProperty(FlatClientProperties.STYLE, "background:@accent;");

            FlatButton fifty = new FlatButton();
            fifty.setBorderPainted(false);
            fifty.setText("4999 Rocket Bucks" + "\n" + "$50.00");
            fifty.putClientProperty(FlatClientProperties.STYLE, "background:@accent;");

            FlatButton oneHundred = new FlatButton();
            oneHundred.setBorderPainted(false);
            oneHundred.setText("9999 Rocket Bucks" + "\n" + "$100.00");
            oneHundred.putClientProperty(FlatClientProperties.STYLE, "background:@accent;");

            shopMenu.add(title);
            shopMenu.add(subtext);
            shopMenu.add(five);
            shopMenu.add(ten);
            shopMenu.add(twenty);
            shopMenu.add(fifty);
            shopMenu.add(oneHundred);

            five.addActionListener(createPurchaseListener(acc, 499, 5.00));
            ten.addActionListener(createPurchaseListener(acc, 999, 10.00));
            twenty.addActionListener(createPurchaseListener(acc, 1999, 20.00));
            fifty.addActionListener(createPurchaseListener(acc, 4999, 50.00));
            oneHundred.addActionListener(createPurchaseListener(acc, 9999, 100.00));

            everythingElsePanel.add(shopMenu);

            add(menuPanel, "growy, pushy");
            add(everythingElsePanel);
        }

        public static JPanel get() {return mainPanel;}


    private void handlePurchase(Account acc, int rocketBucks, double price) {
        JFrame window = new JFrame("Rocket Health");
        window.setSize(new Dimension(1000, 625));
        window.setLocationRelativeTo(null);
        window.add(TransactionViewModel.getTransactionView());
        window.getRootPane().putClientProperty("apple.awt.transparentTitleBar", true);
        window.getRootPane().putClientProperty("apple.awt.windowTitleVisible", false);
        window.setVisible(true);

        // Background thread to monitor card addition
        new Thread(() -> {
            while (!acc.hasCard()) {
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
                    updateWallet(acc.getUsername(), rocketBucks);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                JOptionPane.showMessageDialog(null, "Rocket Bucks added!");
                window.setVisible(false);
                window.dispose();
            });
        }).start();
    }

        private ActionListener createPurchaseListener(Account acc, int rocketBucks, double price) {
            return new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handlePurchase(acc, rocketBucks, price);
                }
            };
        }
}