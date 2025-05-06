package Application.BonusFeatures.CurrencyShop;

import Application.BonusFeatures.Microtransactions.TransactionViewModel;
import Application.Utility.Objects.Account;
import Application.Utility.Objects.CreditCard;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;
import Application.Utility.Objects.CartItem;
import Application.Utility.Widgets.SideMenu.SideMenuView;
import Application.Databases.AccountsDB;
import Application.Databases.DatabaseManager;
import Application.Databases.ItemsDB;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class currencyShopView extends JPanel {
    private static JPanel mainPanel;
    private currencyShopModel shopModel;
    private JButton cartButton;
    private JLabel balanceLabel;
    private JPanel featuredSkinsPanel;
    private JPanel currencyPacksPanel;
    private Map<Integer, FlatButton> buyButtonMap = new HashMap<>();

    public currencyShopView(currencyShopModel model) {
        this.shopModel = model;
        mainPanel = this;

        setLayout(new MigLayout("fill, insets 20"));
        putClientProperty(FlatClientProperties.STYLE, "background:@background;");

        JPanel menuPanel = new JPanel(new MigLayout("insets 0"));
        menuPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background;");
        menuPanel.add(new SideMenuView(), "growy, pushy");

        JPanel contentPanel = new JPanel(new MigLayout("wrap, insets 0", "[grow, fill]", "[]10[]10[]10[]"));
        contentPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background;");

        try {
            // Create header with balance and centered title
            JPanel headerPanel = createHeaderPanel();

            // Create featured skins panel
            featuredSkinsPanel = createFeaturedSkinsPanel();

            // Create currency packs panel
            currencyPacksPanel = createCurrencyPacksPanel();

            // Create cart button
            cartButton = new JButton("Cart");
            cartButton.putClientProperty(FlatClientProperties.STYLE,
                    "font:bold; arc:8; background:@accent; foreground:#FFFFFF;");
            cartButton.setToolTipText("View Cart");
            cartButton.addActionListener(e -> showCartDialog());

            JPanel cartPanel = new JPanel(new MigLayout("insets 0"));
            cartPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background;");
            cartPanel.add(cartButton, "width 150, height 40, center");

            // Add all panels to content panel
            contentPanel.add(headerPanel, "growx, wrap");
            contentPanel.add(featuredSkinsPanel, "growx, wrap");
            contentPanel.add(currencyPacksPanel, "growx, wrap");
            contentPanel.add(cartPanel, "center, wrap");
        } catch (Exception e) {
            System.err.println("Error initializing shop view: " + e.getMessage());
            e.printStackTrace();

            // Add a simple error message if initialization fails
            contentPanel.add(new JLabel("Error loading shop. Please try again later."), "center");
        }

        add(menuPanel, "growy, pushy");
        add(contentPanel, "grow, push");
    }

    private JPanel createHeaderPanel() {
        // Use a simple flow layout with center alignment for the entire header
        JPanel panel = new JPanel(new BorderLayout());
        panel.putClientProperty(FlatClientProperties.STYLE, "background:@background;");

        // Create balance display on the left
        balanceLabel = new FlatLabel();
        updateBalanceDisplay(); // Initialize balance text
        balanceLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground; font:bold +2;");
        panel.add(balanceLabel, BorderLayout.WEST);

        // Create title in the center with its own panel for better centering
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.putClientProperty(FlatClientProperties.STYLE, "background:@background;");

        FlatLabel titleLabel = new FlatLabel();
        titleLabel.setText("Shop");
        titleLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +6; foreground:@foreground;");
        titlePanel.add(titleLabel);

        panel.add(titlePanel, BorderLayout.CENTER);

        // Add an empty panel on the right for balance
        JPanel rightPanel = new JPanel();
        rightPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background;");
        rightPanel.setPreferredSize(balanceLabel.getPreferredSize());
        panel.add(rightPanel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createFeaturedSkinsPanel() {
        JPanel panel = new JPanel(new MigLayout("wrap, insets 20", "[fill, 50%][fill, 50%]"));
        panel.putClientProperty(FlatClientProperties.STYLE, "arc:20; background:darken(@background,3%);");

        // Featured skins header
        FlatLabel featuredTitle = new FlatLabel();
        featuredTitle.setText("Featured Limited Time Offers");
        featuredTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +4; foreground:#FF4040;");
        panel.add(featuredTitle, "span 2, center, gapbottom 15");

        // Get featured skins
        List<currencyShopModel.Item> featuredItems = getFeaturedItems();

        if (featuredItems.isEmpty()) {
            FlatLabel emptyLabel = new FlatLabel();
            emptyLabel.setText("No featured skins available at this time.");
            emptyLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground;");
            panel.add(emptyLabel, "span 2, center, gapy 20");
        } else {
            // Add up to 2 featured skins
            for (int i = 0; i < Math.min(featuredItems.size(), 2); i++) {
                currencyShopModel.Item item = featuredItems.get(i);
                panel.add(createFeaturedItemPanel(item), "grow, push");
            }
        }

        return panel;
    }

    private List<currencyShopModel.Item> getFeaturedItems() {
        try {
            // Get all items from model
            List<currencyShopModel.Item> allItems = shopModel.getAllItems();

            allItems.removeIf(item ->
                    "Default Rocket".equals(item.getName()) ||
                            item.getIcon() == null ||
                            item.getIcon().isEmpty()
            );

            if (allItems.size() <= 2) {
                return allItems;
            }

            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_YEAR);
            int year = calendar.get(Calendar.YEAR);
            Random random = new Random(day + year * 1008);

            // Select 2 random items
            List<currencyShopModel.Item> featuredItems = new ArrayList<>();

            for (int i = 0; i < 2; i++) {
                if (allItems.isEmpty()) break;

                int randomIndex = random.nextInt(allItems.size());
                featuredItems.add(allItems.get(randomIndex));
                allItems.remove(randomIndex);
            }

            return featuredItems;
        } catch (Exception e) {
            System.err.println("Error getting featured items: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private JPanel createFeaturedItemPanel(currencyShopModel.Item item) {
        JPanel panel = new JPanel(new MigLayout("wrap, fillx, insets 15", "[center]"));
        panel.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,5%); arc:15;");

        // Item icon
        JLabel iconLabel = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(item.getIcon());
            Image scaledImg = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            iconLabel.setIcon(new ImageIcon(scaledImg));
        } catch (Exception e) {
            iconLabel.setText("SKIN IMAGE");
            iconLabel.setPreferredSize(new Dimension(120, 120));
            iconLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold;");
        }

        // Item name
        FlatLabel nameLabel = new FlatLabel();
        nameLabel.setText(item.getName());
        nameLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +1;");

        // Price display - only show the actual price
        int price = item.getPrice() * 10; // Featured price is 10x the original

        FlatLabel priceLabel = new FlatLabel();
        priceLabel.setText(price + " RB");
        priceLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold; foreground:#FF6060;");

        // Buy Now button
        FlatButton buyButton = new FlatButton();
        buyButton.setText("Buy Now");
        buyButton.putClientProperty(FlatClientProperties.STYLE,
                "arc:8; background:@accent; foreground:#FFFFFF;");

        // Store the button in the map to disable it later
        buyButtonMap.put(item.getId(), buyButton);

        // Check if user already owns this skin
        try {
            String username = shopModel.getCurrentUser().getUsername();
            ItemsDB itemsDB = DatabaseManager.getItemsDB();

            if (itemsDB.userHasItem(username, item.getId())) {
                // User already owns this skin
                buyButton.setEnabled(false);
                buyButton.setText("Purchased");
            }
        } catch (SQLException e) {
            System.err.println("Error checking if user owns item: " + e.getMessage());
        }

        buyButton.addActionListener(e -> {
            try {
                purchaseFeaturedSkin(item.getId(), price);
                updateBalanceDisplay();

                // Disable the button after purchase
                buyButton.setEnabled(false);
                buyButton.setText("Purchased");

                JOptionPane.showMessageDialog(this,
                        "Skin purchased successfully!",
                        "Purchase Complete",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Failed to purchase skin: " + ex.getMessage(),
                        "Purchase Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(iconLabel);
        panel.add(nameLabel);
        panel.add(priceLabel, "gapy 5");
        panel.add(buyButton, "width 120, gapy 5");

        return panel;
    }

    private void purchaseFeaturedSkin(int itemId, int price) throws SQLException {
        // Check if user has enough funds
        AccountsDB accountsDB = DatabaseManager.getAccountsDB();
        String username = shopModel.getCurrentUser().getUsername();
        int currentBalance = accountsDB.getWallet(username);

        if (currentBalance < price) {
            throw new IllegalStateException("Not enough Rocket Bucks to purchase this skin.");
        }

        // Deduct the price from wallet
        int newBalance = currentBalance - price;
        accountsDB.updateWallet(username, newBalance);

        // Give the item to the user
        ItemsDB itemsDB = DatabaseManager.getItemsDB();
        itemsDB.giveItemToUser(username, itemId);
    }

    private JPanel createCurrencyPacksPanel() {
        JPanel panel = new JPanel(new MigLayout("wrap, fillx, insets 30", "fill"));
        panel.putClientProperty(FlatClientProperties.STYLE, "arc:20; background:darken(@background,3%);");

        FlatLabel title = new FlatLabel();
        title.setText("Select currency pack");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +4;");

        FlatLabel subtext = new FlatLabel();
        subtext.setText("adds a 5% credit card fee to listed price");
        subtext.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground; font:bold +2;");

        panel.add(title, "center");
        panel.add(subtext, "center, gapbottom 10");

        // Add currency packs
        addCurrencyPack(panel, 499, 5.00, false);
        addCurrencyPack(panel, 999, 10.00, false);
        addCurrencyPack(panel, 1999, 20.00, false);
        addCurrencyPack(panel, 4999, 50.00, true);  // Recommended
        addCurrencyPack(panel, 9999, 100.00, false);

        return panel;
    }

    private void addCurrencyPack(JPanel panel, int rocketBucks, double price, boolean recommended) {
        FlatButton packButton = new FlatButton();
        packButton.setBorderPainted(false);
        packButton.setText(rocketBucks + " Rocket Bucks $" + String.format("%.2f", price));

        // Make all currency pack buttons red like in the screenshot
        packButton.putClientProperty(FlatClientProperties.STYLE, "background:@accent;");

        packButton.addActionListener(e -> {
            // Check if the user already has a card
            try {
                Account currentAccount = shopModel.getCurrentUser();
                currentAccount.loadCreditCard();

                if (currentAccount.hasCard()) {
                    // User has a card, add to cart normally
                    addToCart(rocketBucks, price);

                    JOptionPane.showMessageDialog(this,
                            rocketBucks + " Rocket Bucks added to cart!",
                            "Added to Cart",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // User doesn't have a card, ask if they want to add one now
                    int option = JOptionPane.showConfirmDialog(
                            this,
                            "You need to add a credit card first. Would you like to add one now?",
                            "Credit Card Required",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (option == JOptionPane.YES_OPTION) {
                        // Save the currency pack info for the transaction view to use
                        shopModel.setPendingCurrencyPack(rocketBucks, price);

                        // Create a dialog to hold the transaction view
                        JDialog cardDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add Credit Card", true);
                        cardDialog.setSize(1000, 800);
                        cardDialog.setLocationRelativeTo(this);

                        // Use the TransactionViewModel to get the view
                        TransactionViewModel viewModel = new TransactionViewModel();
                        viewModel.getCardUser(currentAccount, cardDialog, shopModel);
                        JPanel transactionPanel = TransactionViewModel.getTransactionView();

                        cardDialog.add(transactionPanel);

                        // Show the dialog
                        cardDialog.setVisible(true);

                        // After dialog is closed, update the balance display
                        updateBalanceDisplay();
                    }
                }
            } catch (SQLException ex) {
                System.err.println("Error checking credit card: " + ex.getMessage());

                // Default behavior if there's an error
                addToCart(rocketBucks, price);

                JOptionPane.showMessageDialog(this,
                        rocketBucks + " Rocket Bucks added to cart!",
                        "Added to Cart",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        panel.add(packButton);
    }

    private void addToCart(int rocketBucks, double price) {
        shopModel.addToCart(rocketBucks, price);
    }

    public void updateBalanceDisplay() {
        try {
            // Get the current user's wallet balance from the database
            if (shopModel.getCurrentUser() != null) {
                AccountsDB accountsDB = DatabaseManager.getAccountsDB();
                String username = shopModel.getCurrentUser().getUsername();
                int currentBalance = accountsDB.getWallet(username);
                balanceLabel.setText("Current Balance: " + currentBalance + " Rocket Bucks");
            } else {
                balanceLabel.setText("Current Balance: Unknown");
            }
        } catch (SQLException e) {
            System.err.println("Error updating balance display: " + e.getMessage());
            balanceLabel.setText("Current Balance: Unknown");
        }
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
                shopModel.removeFromCart(selectedIndex);

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
            try {
                checkout();
                cartDialog.dispose();
                updateBalanceDisplay();
                JOptionPane.showMessageDialog(this,
                        "Purchase completed successfully!",
                        "Checkout Complete",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Checkout failed: " + ex.getMessage(),
                        "Checkout Error",
                        JOptionPane.ERROR_MESSAGE);
            }
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

    private void checkout() throws SQLException {
        // First check if user has a credit card on file
        Account currentAccount = shopModel.getCurrentUser();

        // Load the credit card for the current user
        currentAccount.loadCreditCard();

        // Check if the user has a valid credit card
        if (!currentAccount.hasCard()) {
            // No credit card on file, show option to add one
            int option = JOptionPane.showConfirmDialog(
                    this,
                    "You need to add a credit card before checkout. Would you like to add one now?",
                    "Credit Card Required",
                    JOptionPane.YES_NO_OPTION
            );

            if (option == JOptionPane.YES_OPTION) {
                // Create a dialog to hold the transaction view
                JDialog cardDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add Credit Card", true);
                cardDialog.setSize(1000, 800);
                cardDialog.setLocationRelativeTo(this);

                // Save the current cart items as the pending currency pack
                if (!shopModel.isCartEmpty()) {
                    int totalRocketBucks = shopModel.getTotalRocketBucks();
                    double totalPrice = shopModel.getTotalPrice();
                    shopModel.setPendingCurrencyPack(totalRocketBucks, totalPrice);
                }

                // Use the TransactionViewModel to get the view, passing the dialog and shop model
                TransactionViewModel viewModel = new TransactionViewModel();
                viewModel.getCardUser(currentAccount, cardDialog, shopModel);
                JPanel transactionPanel = TransactionViewModel.getTransactionView();

                cardDialog.add(transactionPanel);

                // Show the dialog
                cardDialog.setVisible(true);

                // After dialog is closed, check again if the user has a card
                currentAccount.loadCreditCard();
                if (!currentAccount.hasCard()) {
                    throw new SQLException("Credit card information is required for checkout");
                }

                // The TransactionView will have added the pending items to the user's balance
                // and will have closed the dialog automatically, so we can just return
                return;
            } else {
                throw new SQLException("Credit card information is required for checkout");
            }
        }

        // At this point, user has a credit card on file
        // Show payment confirmation dialog
        double totalPrice = shopModel.getTotalPrice();
        int totalRocketBucks = shopModel.getTotalRocketBucks();

        // Get the credit card from the account - it should be loaded at this point
        CreditCard userCard = currentAccount.loadCreditCard();

        // Get last 4 digits of card number
        String cardNum = userCard.getCardNumber().replaceAll("\\s", "");
        String lastFourDigits = cardNum.substring(Math.max(0, cardNum.length() - 4));

        String message = String.format(
                "Your credit card ending in %s will be charged $%.2f for %d Rocket Bucks. Proceed?",
                lastFourDigits,
                totalPrice,
                totalRocketBucks
        );

        int confirmOption = JOptionPane.showConfirmDialog(
                this,
                message,
                "Confirm Purchase",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmOption != JOptionPane.YES_OPTION) {
            throw new SQLException("Purchase was cancelled");
        }


        AccountsDB accountsDB = DatabaseManager.getAccountsDB();
        String username = currentAccount.getUsername();
        int currentBalance = accountsDB.getWallet(username);
        int newBalance = currentBalance + totalRocketBucks;

        // Update the user's wallet
        accountsDB.updateWallet(username, newBalance);

        // Clear the cart
        shopModel.clearCart();
    }

    public static JPanel get() {
        return mainPanel;
    }
}