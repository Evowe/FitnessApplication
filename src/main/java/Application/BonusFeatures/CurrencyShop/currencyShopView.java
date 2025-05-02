package Application.BonusFeatures.CurrencyShop;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;
import Application.Utility.Objects.CartItem;
import Application.Utility.Widgets.SideMenu.SideMenuView;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class currencyShopView extends JPanel {
    private static JPanel mainPanel;
    private currencyShopModel shopModel;
    private JLabel cartCountLabel;
    private JButton cartButton;

    public currencyShopView(currencyShopModel model) {
        this.shopModel = model;
        mainPanel = this;

        setLayout(new MigLayout("fill, insets 20"));
        putClientProperty(FlatClientProperties.STYLE, "background:@background;");

        JPanel menuPanel = new JPanel(new MigLayout("insets 0"));
        menuPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background;");
        menuPanel.add(new SideMenuView(), "growy, pushy");

        JPanel everythingElsePanel = new JPanel(new MigLayout("insets 0", "center", "center"));
        everythingElsePanel.putClientProperty(FlatClientProperties.STYLE, "background:@background;");

        JPanel topPanel = new JPanel(new MigLayout("fillx, insets 5"));
        topPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background;");

        cartButton = new JButton("Cart");
        cartButton.putClientProperty(FlatClientProperties.STYLE, "font:bold; arc:15; background:@accent; foreground:#FFFFFF;");
        cartButton.setToolTipText("View Cart");

        cartCountLabel = new JLabel(String.valueOf(shopModel.getCartItemCount()));
        cartCountLabel.putClientProperty(FlatClientProperties.STYLE,
                "background:@accent; foreground:#FFFFFF; arc:100; font:bold;");
        cartCountLabel.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));
        cartCountLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel cartPanel = new JPanel(new MigLayout("insets 0"));
        cartPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background;");
        cartPanel.add(cartButton);
        cartPanel.add(cartCountLabel, "pos (cartButton.x+cartButton.width-15) (cartButton.y-5)");

        topPanel.add(new JLabel(), "pushx, growx");
        topPanel.add(cartPanel);

        cartButton.addActionListener(e -> showCartDialog());

        JPanel shopMenu = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,275"));
        shopMenu.putClientProperty(FlatClientProperties.STYLE, "arc:20;");

        FlatLabel title = new FlatLabel();
        title.setText("Select currency pack");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +6;");

        FlatLabel subtext = new FlatLabel();
        subtext.setText("adds a 5% credit card fee to listed price");
        subtext.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground; font:bold +3;");

        FlatButton five = new FlatButton();
        five.setBorderPainted(false);
        five.setText("499 Rocket Bucks $5.00");

        FlatButton ten = new FlatButton();
        ten.setBorderPainted(false);
        ten.setText("999 Rocket Bucks $10.00");

        FlatButton twenty = new FlatButton();
        twenty.setBorderPainted(false);
        twenty.setText("1999 Rocket Bucks $20.00");

        FlatButton fifty = new FlatButton();
        fifty.setBorderPainted(false);
        fifty.setText("4999 Rocket Bucks $50.00");
        fifty.putClientProperty(FlatClientProperties.STYLE, "background:@accent;");

        FlatButton oneHundred = new FlatButton();
        oneHundred.setBorderPainted(false);
        oneHundred.setText("9999 Rocket Bucks $100.00");

        shopMenu.add(title);
        shopMenu.add(subtext);
        shopMenu.add(five);
        shopMenu.add(ten);
        shopMenu.add(twenty);
        shopMenu.add(fifty);
        shopMenu.add(oneHundred);

        five.addActionListener(e -> addToCart(499, 5.00));
        ten.addActionListener(e -> addToCart(999, 10.00));
        twenty.addActionListener(e -> addToCart(1999, 20.00));
        fifty.addActionListener(e -> addToCart(4999, 50.00));
        oneHundred.addActionListener(e -> addToCart(9999, 100.00));

        everythingElsePanel.add(topPanel, "dock north");
        everythingElsePanel.add(shopMenu, "center");

        add(menuPanel, "growy, pushy");
        add(everythingElsePanel);
    }

    public static JPanel get() {
        return mainPanel;
    }

    private void addToCart(int rocketBucks, double price) {
        currencyShopViewModel.addToCart(rocketBucks, price);

        JOptionPane.showMessageDialog(this,
                rocketBucks + " Rocket Bucks added to cart!",
                "Added to Cart",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void updateCartDisplay() {
        cartCountLabel.setText(String.valueOf(shopModel.getCartItemCount()));
    }

    private void showCartDialog() {
        JDialog cartDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Your Cart", true);
        cartDialog.setLayout(new MigLayout("fill, insets 20"));
        cartDialog.setSize(400, 500);
        cartDialog.setLocationRelativeTo(this);

        if (shopModel.isCartEmpty()) {
            cartDialog.add(new JLabel("Your cart is empty"), "center");

            JButton closeButton = new JButton("Close");
            closeButton.addActionListener(e -> cartDialog.dispose());
            cartDialog.add(closeButton, "dock south");

            cartDialog.setVisible(true);
            return;
        }

        DefaultListModel<CartItem> listModel = new DefaultListModel<>();
        for (CartItem item : shopModel.getCart().getItems()) {
            listModel.addElement(item);
        }

        JList<CartItem> itemList = new JList<>(listModel);
        itemList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof CartItem) {
                    setText(((CartItem) value).getDescription());
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(itemList);

        JPanel summaryPanel = new JPanel(new MigLayout("fillx, wrap 2", "[grow][right]"));
        summaryPanel.add(new JLabel("Total Rocket Bucks:"), "");
        summaryPanel.add(new JLabel(String.valueOf(shopModel.getTotalRocketBucks())), "");
        summaryPanel.add(new JLabel("Total Price:"), "");
        summaryPanel.add(new JLabel("$" + String.format("%.2f", shopModel.getTotalPrice())), "");

        JButton removeButton = new JButton("Remove Selected");
        removeButton.addActionListener(e -> {
            int selectedIndex = itemList.getSelectedIndex();
            if (selectedIndex >= 0) {
                listModel.remove(selectedIndex);
                currencyShopViewModel.removeFromCart(selectedIndex);

                summaryPanel.removeAll();
                summaryPanel.add(new JLabel("Total Rocket Bucks:"), "");
                summaryPanel.add(new JLabel(String.valueOf(shopModel.getTotalRocketBucks())), "");
                summaryPanel.add(new JLabel("Total Price:"), "");
                summaryPanel.add(new JLabel("$" + String.format("%.2f", shopModel.getTotalPrice())), "");
                summaryPanel.revalidate();
                summaryPanel.repaint();

                if (shopModel.isCartEmpty()) {
                    cartDialog.dispose();
                }
            }
        });

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.putClientProperty(FlatClientProperties.STYLE, "background:@accent;");
        checkoutButton.addActionListener(e -> {
            cartDialog.dispose();
            currencyShopViewModel.checkout();
        });

        JButton continueShoppingButton = new JButton("Continue Shopping");
        continueShoppingButton.addActionListener(e -> cartDialog.dispose());

        cartDialog.add(new JLabel("Your Cart"), "dock north");
        cartDialog.add(scrollPane, "grow, push");
        cartDialog.add(summaryPanel, "dock south");

        JPanel buttonPanel = new JPanel(new MigLayout("fillx, insets 10", "[grow][grow][grow]"));
        buttonPanel.add(removeButton, "");
        buttonPanel.add(continueShoppingButton, "");
        buttonPanel.add(checkoutButton, "");
        cartDialog.add(buttonPanel, "dock south");

        cartDialog.setVisible(true);
    }
}