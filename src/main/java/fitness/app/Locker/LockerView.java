package fitness.app.Locker;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;
import fitness.app.Objects.Account;
import fitness.app.Widgets.SideMenu.SideMenuView;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LockerView extends JPanel {
    private final LockerViewModel viewModel;
    private final JTabbedPane tabbedPane;
    private final JPanel catalogPanel;
    private final JPanel inventoryPanel;
    private final JPanel equippedPanel;

    public LockerView(Account currentUser) {
        viewModel = new LockerViewModel(currentUser.getUsername());

        // Main layout with side menu
        setLayout(new MigLayout("insets 20", "left", "top"));
        putClientProperty(FlatClientProperties.STYLE, "background:@background");

        // Add side menu
        add(new SideMenuView(), "growy, pushy");

        // Main content panel
        JPanel contentPanel = new JPanel(new MigLayout("wrap, insets 2, fill"));
        contentPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background;");

        // Create header
        JPanel headerPanel = new JPanel(new MigLayout("insets 0", "left", "center"));
        headerPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background;");

        FlatLabel titleLabel = new FlatLabel();
        titleLabel.setText("Locker");
        titleLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +6");

        FlatButton refreshButton = new FlatButton();
        refreshButton.setText("Refresh");
        refreshButton.putClientProperty(FlatClientProperties.STYLE, "arc:10;");
        refreshButton.addActionListener(e -> {
            viewModel.refreshData();
            updateUI();
        });

        headerPanel.add(titleLabel, "");
        headerPanel.add(refreshButton, "gapx 20");

        contentPanel.add(headerPanel, "growx, pushx, wrap");

        // Create tabbed pane for different views
        tabbedPane = new JTabbedPane();
        tabbedPane.putClientProperty(FlatClientProperties.STYLE, "tabHeight:40; tabIconPlacement:leading; background:@background;");
        tabbedPane.putClientProperty(FlatClientProperties.TABBED_PANE_TAB_ICON_PLACEMENT, "leading");

        // Initialize panels with scroll panes
        JScrollPane catalogScrollPane = new JScrollPane();
        catalogScrollPane.putClientProperty(FlatClientProperties.STYLE, "border:0,0,0,0");
        catalogScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        catalogPanel = new JPanel(new MigLayout("wrap, fillx, insets 15", "[fill]"));
        catalogPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background;");
        catalogScrollPane.setViewportView(catalogPanel);

        JScrollPane inventoryScrollPane = new JScrollPane();
        inventoryScrollPane.putClientProperty(FlatClientProperties.STYLE, "border:0,0,0,0");
        inventoryScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        inventoryPanel = new JPanel(new MigLayout("wrap, fillx, insets 15", "[fill]"));
        inventoryPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background;");
        inventoryScrollPane.setViewportView(inventoryPanel);

        JScrollPane equippedScrollPane = new JScrollPane();
        equippedScrollPane.putClientProperty(FlatClientProperties.STYLE, "border:0,0,0,0");
        equippedScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        equippedPanel = new JPanel(new MigLayout("wrap, fillx, insets 15", "[fill]"));
        equippedPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background;");
        equippedScrollPane.setViewportView(equippedPanel);

        // Add panels to tabbed pane
        tabbedPane.addTab("Shop", catalogScrollPane);
        tabbedPane.addTab("Inventory", inventoryScrollPane);
        tabbedPane.addTab("Equipped", equippedScrollPane);

        // Add tabbed pane to content panel
        contentPanel.add(tabbedPane, "grow, push");

        // Add content panel to main layout
        add(contentPanel, "grow, push");

        // Initial UI update
        updateUI();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (viewModel != null) {
            SwingUtilities.invokeLater(() -> {
                updateCatalogPanel();
                updateInventoryPanel();
                updateEquippedPanel();
            });
        }
    }

    private void updateCatalogPanel() {
        catalogPanel.removeAll();

        // Add a header for the catalog
        FlatLabel catalogHeader = new FlatLabel();
        catalogHeader.setText("Available Items");
        catalogHeader.putClientProperty(FlatClientProperties.STYLE, "font:bold +2");
        catalogPanel.add(catalogHeader, "growx, pushx, gapy 10");

        // Add all catalog items
        List<LockerModel.Item> items = viewModel.getCatalogItems();
        for (LockerModel.Item item : items) {
            catalogPanel.add(createCatalogItemPanel(item), "growx, pushx, gapy 5");
        }

        catalogPanel.revalidate();
        catalogPanel.repaint();
    }

    private void updateInventoryPanel() {
        inventoryPanel.removeAll();

        List<LockerModel.Item> ownedItems = viewModel.getOwnedItems();

        if (ownedItems.isEmpty()) {
            FlatLabel emptyLabel = new FlatLabel();
            emptyLabel.setText("You don't own any items yet. Visit the Shop to purchase items.");
            emptyLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground;");

            inventoryPanel.add(emptyLabel, "gapy 20");
        } else {
            // Add a header for the inventory
            FlatLabel inventoryHeader = new FlatLabel();
            inventoryHeader.setText("Your Items");
            inventoryHeader.putClientProperty(FlatClientProperties.STYLE, "font:bold +2");
            inventoryPanel.add(inventoryHeader, "growx, pushx, gapy 10");

            // Add all owned items
            for (LockerModel.Item item : ownedItems) {
                inventoryPanel.add(createInventoryItemPanel(item), "growx, pushx, gapy 5");
            }
        }

        inventoryPanel.revalidate();
        inventoryPanel.repaint();
    }

    private void updateEquippedPanel() {
        equippedPanel.removeAll();

        List<LockerModel.Item> equippedItems = viewModel.getEquippedItems();

        if (equippedItems.isEmpty()) {
            FlatLabel emptyLabel = new FlatLabel();
            emptyLabel.setText("You haven't equipped any items yet. Go to your Inventory to equip items.");
            emptyLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground;");

            equippedPanel.add(emptyLabel, "gapy 20");
        } else {
            // Add a header for the equipped items
            FlatLabel equippedHeader = new FlatLabel();
            equippedHeader.setText("Equipped Items");
            equippedHeader.putClientProperty(FlatClientProperties.STYLE, "font:bold +2");
            equippedPanel.add(equippedHeader, "growx, pushx, gapy 10");

            // Add all equipped items
            for (LockerModel.Item item : equippedItems) {
                equippedPanel.add(createEquippedItemPanel(item), "growx, pushx, gapy 5");
            }
        }

        equippedPanel.revalidate();
        equippedPanel.repaint();
    }

    private JPanel createCatalogItemPanel(LockerModel.Item item) {
        JPanel itemPanel = new JPanel(new MigLayout("fillx, insets 10", "[50][]push[]"));
        itemPanel.putClientProperty(FlatClientProperties.STYLE, "background:darken(@background,3%); arc:10;");

        // Item icon
        JLabel iconLabel = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(item.getIcon());
            Image scaledImg = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            iconLabel.setIcon(new ImageIcon(scaledImg));
        } catch (Exception e) {
            // Use a default icon if the specified one can't be loaded
            iconLabel.setText("Icon");
        }

        // Item details
        JPanel detailsPanel = new JPanel(new MigLayout("fillx, insets 0, wrap 1", "[fill]"));
        detailsPanel.putClientProperty(FlatClientProperties.STYLE, "background:darken(@background,3%);");

        FlatLabel nameLabel = new FlatLabel();
        nameLabel.setText(item.getName());
        nameLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold;");

        FlatLabel descLabel = new FlatLabel();
        descLabel.setText(item.getDescription());
        descLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground;");

        FlatLabel typeLabel = new FlatLabel();
        typeLabel.setText("Type: " + item.getType());
        typeLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground; font:-1;");

        detailsPanel.add(nameLabel);
        detailsPanel.add(descLabel);
        detailsPanel.add(typeLabel);

        // Action panel
        JPanel actionPanel = new JPanel(new MigLayout("insets 0, wrap 1", "[100, center]"));
        actionPanel.putClientProperty(FlatClientProperties.STYLE, "background:darken(@background,3%);");

        FlatLabel priceLabel = new FlatLabel();
        priceLabel.setText(item.getPrice() + " coins");
        priceLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold;");

        FlatButton purchaseButton = new FlatButton();
        purchaseButton.setText("Purchase");
        purchaseButton.putClientProperty(FlatClientProperties.STYLE, "arc:10;");

        if (viewModel.hasItem(item.getId())) {
            purchaseButton.setText("Owned");
            purchaseButton.setEnabled(false);
        }

        purchaseButton.addActionListener(e -> {
            boolean success = viewModel.purchaseItem(item.getId());
            if (success) {
                JOptionPane.showMessageDialog(
                        SwingUtilities.getWindowAncestor(this),
                        "You have successfully purchased " + item.getName(),
                        "Purchase Successful",
                        JOptionPane.INFORMATION_MESSAGE
                );
                updateUI();
            }
        });

        actionPanel.add(priceLabel);
        actionPanel.add(purchaseButton);

        // Add components to item panel
        itemPanel.add(iconLabel);
        itemPanel.add(detailsPanel);
        itemPanel.add(actionPanel);

        return itemPanel;
    }

    private JPanel createInventoryItemPanel(LockerModel.Item item) {
        JPanel itemPanel = new JPanel(new MigLayout("fillx, insets 10", "[50][]push[]"));
        itemPanel.putClientProperty(FlatClientProperties.STYLE, "background:darken(@background,3%); arc:10;");

        // Item icon
        JLabel iconLabel = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(item.getIcon());
            Image scaledImg = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            iconLabel.setIcon(new ImageIcon(scaledImg));
        } catch (Exception e) {
            iconLabel.setText("Icon");
        }

        JPanel detailsPanel = new JPanel(new MigLayout("fillx, insets 0, wrap 1", "[fill]"));
        detailsPanel.putClientProperty(FlatClientProperties.STYLE, "background:darken(@background,3%);");

        FlatLabel nameLabel = new FlatLabel();
        nameLabel.setText(item.getName());
        nameLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold;");

        FlatLabel descLabel = new FlatLabel();
        descLabel.setText(item.getDescription());
        descLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground;");

        FlatLabel typeLabel = new FlatLabel();
        typeLabel.setText("Type: " + item.getType());
        typeLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground; font:-1;");

        detailsPanel.add(nameLabel);
        detailsPanel.add(descLabel);
        detailsPanel.add(typeLabel);

        JPanel actionPanel = new JPanel(new MigLayout("insets 0", "[100, center]"));
        actionPanel.putClientProperty(FlatClientProperties.STYLE, "background:darken(@background,3%);");

        FlatButton actionButton = new FlatButton();
        if (viewModel.hasEquipped(item.getId())) {
            actionButton.setText("Unequip");
            actionButton.addActionListener(e -> {
                viewModel.unequipItem(item.getId());
                updateUI();
            });
        } else {
            actionButton.setText("Equip");
            actionButton.addActionListener(e -> {
                viewModel.equipItem(item.getId());
                updateUI();
            });
        }
        actionButton.putClientProperty(FlatClientProperties.STYLE, "arc:10;");

        actionPanel.add(actionButton);

        // Add components to item panel
        itemPanel.add(iconLabel);
        itemPanel.add(detailsPanel);
        itemPanel.add(actionPanel);

        return itemPanel;
    }

    private JPanel createEquippedItemPanel(LockerModel.Item item) {
        JPanel itemPanel = new JPanel(new MigLayout("fillx, insets 10", "[50][]push[]"));
        itemPanel.putClientProperty(FlatClientProperties.STYLE, "background:darken(@background,3%); arc:10;");

        // Item icon
        JLabel iconLabel = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(item.getIcon());
            Image scaledImg = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            iconLabel.setIcon(new ImageIcon(scaledImg));
        } catch (Exception e) {
            iconLabel.setText("Icon");
        }

        // Item details
        JPanel detailsPanel = new JPanel(new MigLayout("fillx, insets 0, wrap 1", "[fill]"));
        detailsPanel.putClientProperty(FlatClientProperties.STYLE, "background:darken(@background,3%);");

        FlatLabel nameLabel = new FlatLabel();
        nameLabel.setText(item.getName());
        nameLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold;");

        FlatLabel descLabel = new FlatLabel();
        descLabel.setText(item.getDescription());
        descLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground;");

        FlatLabel typeLabel = new FlatLabel();
        typeLabel.setText("Type: " + item.getType());
        typeLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground; font:-1;");

        detailsPanel.add(nameLabel);
        detailsPanel.add(descLabel);
        detailsPanel.add(typeLabel);

        JPanel actionPanel = new JPanel(new MigLayout("insets 0", "[100, center]"));
        actionPanel.putClientProperty(FlatClientProperties.STYLE, "background:darken(@background,3%);");

        FlatButton unequipButton = new FlatButton();
        unequipButton.setText("Unequip");
        unequipButton.putClientProperty(FlatClientProperties.STYLE, "arc:10;");
        unequipButton.addActionListener(e -> {
            viewModel.unequipItem(item.getId());
            updateUI();
        });

        actionPanel.add(unequipButton);

        itemPanel.add(iconLabel);
        itemPanel.add(detailsPanel);
        itemPanel.add(actionPanel);

        return itemPanel;
    }
}