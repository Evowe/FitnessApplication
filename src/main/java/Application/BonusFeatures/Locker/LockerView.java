package Application.BonusFeatures.Locker;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;
import Application.Utility.Objects.Account;
import Application.Utility.Widgets.SideMenu.SideMenuView;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LockerView extends JPanel {
    private final LockerViewModel viewModel;
    private JPanel equippedPanel;
    private JPanel inventoryPanel;

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
        titleLabel.setText("Rocket Skins");
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

        // Create main content area with split panes
        JPanel mainContentPanel = new JPanel(new MigLayout("", "[30%][70%]", "[]"));
        mainContentPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background;");

        // Left panel for equipped rocket
        equippedPanel = new JPanel(new MigLayout("wrap, fillx, insets 15", "[fill]"));
        equippedPanel.putClientProperty(FlatClientProperties.STYLE, "background:darken(@background,3%); arc:20;");

        // Right panel for inventory
        JScrollPane inventoryScrollPane = new JScrollPane();
        inventoryScrollPane.putClientProperty(FlatClientProperties.STYLE, "border:0,0,0,0");
        inventoryScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        inventoryPanel = new JPanel(new MigLayout("wrap 3, fillx, insets 15", "[fill][fill][fill]"));
        inventoryPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background;");
        inventoryScrollPane.setViewportView(inventoryPanel);

        mainContentPanel.add(equippedPanel, "grow");
        mainContentPanel.add(inventoryScrollPane, "grow");

        contentPanel.add(mainContentPanel, "grow, push");
        add(contentPanel, "grow, push");

        // Initial UI update
        updateUI();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (viewModel != null) {
            SwingUtilities.invokeLater(() -> {
                updateEquippedPanel();
                updateInventoryPanel();
            });
        }
    }

    private void updateEquippedPanel() {
        equippedPanel.removeAll();

        // Add a header for the equipped section
        FlatLabel equippedHeader = new FlatLabel();
        equippedHeader.setText("Currently Equipped");
        equippedHeader.putClientProperty(FlatClientProperties.STYLE, "font:bold +2");
        equippedPanel.add(equippedHeader, "growx, pushx, gapy 10");

        List<LockerModel.Item> equippedItems = viewModel.getEquippedItems();

        if (equippedItems.isEmpty()) {
            FlatLabel emptyLabel = new FlatLabel();
            emptyLabel.setText("No rocket equipped");
            emptyLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground;");
            equippedPanel.add(emptyLabel, "gapy 20");
        } else {
            // We'll only display the first rocket skin if multiple are equipped
            LockerModel.Item equippedItem = equippedItems.get(0);

            // Create a larger display for the equipped rocket
            JPanel rocketPanel = new JPanel(new MigLayout("wrap, fillx, insets 10", "[center]"));
            rocketPanel.putClientProperty(FlatClientProperties.STYLE, "background:darken(@background,5%); arc:10;");

            // Rocket icon (larger size for equipped)
            JLabel iconLabel = new JLabel();
            try {
                ImageIcon icon = new ImageIcon(equippedItem.getIcon());
                Image scaledImg = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                iconLabel.setIcon(new ImageIcon(scaledImg));
            } catch (Exception e) {
                iconLabel.setText("Rocket");
            }

            // Rocket name
            FlatLabel nameLabel = new FlatLabel();
            nameLabel.setText(equippedItem.getName());
            nameLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +2;");

            // Unequip button
            FlatButton unequipButton = new FlatButton();
            unequipButton.setText("Unequip");
            unequipButton.putClientProperty(FlatClientProperties.STYLE, "arc:10;");
            unequipButton.addActionListener(e -> {
                viewModel.unequipItem(equippedItem.getId());
                updateUI();
            });

            rocketPanel.add(iconLabel, "gapy 10");
            rocketPanel.add(nameLabel);
            rocketPanel.add(unequipButton, "gapy 10");

            equippedPanel.add(rocketPanel, "grow, push");
        }

        equippedPanel.revalidate();
        equippedPanel.repaint();
    }

    private void updateInventoryPanel() {
        inventoryPanel.removeAll();

        // Add a header for the inventory
        FlatLabel inventoryHeader = new FlatLabel();
        inventoryHeader.setText("Your Rockets");
        inventoryHeader.putClientProperty(FlatClientProperties.STYLE, "font:bold +2");
        inventoryPanel.add(inventoryHeader, "span, growx, pushx, gapy 10");

        List<LockerModel.Item> ownedItems = viewModel.getOwnedItems();

        if (ownedItems.isEmpty()) {
            FlatLabel emptyLabel = new FlatLabel();
            emptyLabel.setText("You don't own any rocket skins yet.");
            emptyLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground;");
            inventoryPanel.add(emptyLabel, "span, gapy 20");
        } else {
            // Add all owned items in a grid layout
            for (LockerModel.Item item : ownedItems) {
                inventoryPanel.add(createInventoryItemPanel(item), "gapy 5, gapx 5");
            }
        }

        inventoryPanel.revalidate();
        inventoryPanel.repaint();
    }

    private JPanel createInventoryItemPanel(LockerModel.Item item) {
        JPanel itemPanel = new JPanel(new MigLayout("wrap, fillx, insets 10", "[center]"));
        itemPanel.putClientProperty(FlatClientProperties.STYLE, "background:darken(@background,3%); arc:10;");

        boolean isEquipped = viewModel.hasEquipped(item.getId());

        if (isEquipped) {
            itemPanel.putClientProperty(FlatClientProperties.STYLE, "background:darken(@accentColor,30%); arc:10;");
        }

        // Rocket icon
        JLabel iconLabel = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(item.getIcon());
            Image scaledImg = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            iconLabel.setIcon(new ImageIcon(scaledImg));
        } catch (Exception e) {
            iconLabel.setText("Icon");
        }

        // Rocket name
        FlatLabel nameLabel = new FlatLabel();
        nameLabel.setText(item.getName());
        nameLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold;");

        // Action button
        FlatButton actionButton = new FlatButton();
        if (isEquipped) {
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

        itemPanel.add(iconLabel);
        itemPanel.add(nameLabel);
        itemPanel.add(actionButton);

        return itemPanel;
    }
}