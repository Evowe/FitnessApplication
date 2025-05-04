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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LockerView extends JPanel {
    private final LockerViewModel viewModel;
    private JPanel equippedPanel;
    private JPanel inventoryPanel;

    private final int INVENTORY_ICON_SIZE = 200;
    private final int INVENTORY_PANEL_WIDTH = 300;
    private final int INVENTORY_PANEL_HEIGHT = 300;
    private final int EQUIPPED_ICON_SIZE = 350;

    private final Map<String, ImageIcon> imageCache = new ConcurrentHashMap<>();
    private final ExecutorService imageLoadingExecutor = Executors.newFixedThreadPool(3);

    public LockerView(Account currentUser) {
        viewModel = new LockerViewModel(currentUser.getUsername());

        setLayout(new MigLayout("insets 20", "left", "top"));
        putClientProperty(FlatClientProperties.STYLE, "background:@background");

        add(new SideMenuView(), "growy, pushy");

        JPanel contentPanel = new JPanel(new MigLayout("wrap, insets 2, fill"));
        contentPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background;");

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

        JPanel mainContentPanel = new JPanel(new MigLayout("", "[30%][70%]", "[]"));
        mainContentPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background;");

        equippedPanel = new JPanel(new MigLayout("wrap, fillx, insets 15", "[fill]"));
        equippedPanel.putClientProperty(FlatClientProperties.STYLE, "background:darken(@background,3%); arc:20;");

        JScrollPane inventoryScrollPane = new JScrollPane();
        inventoryScrollPane.putClientProperty(FlatClientProperties.STYLE, "border:0,0,0,0");
        inventoryScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        inventoryPanel = new JPanel(new MigLayout("wrap 3, fillx, insets 15", "[" + INVENTORY_PANEL_WIDTH + "!][" + INVENTORY_PANEL_WIDTH + "!][" + INVENTORY_PANEL_WIDTH + "!]"));
        inventoryPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background;");
        inventoryScrollPane.setViewportView(inventoryPanel);

        mainContentPanel.add(equippedPanel, "grow");
        mainContentPanel.add(inventoryScrollPane, "grow");

        contentPanel.add(mainContentPanel, "grow, push");
        add(contentPanel, "grow, push");

        preloadAllImages();

        updateUI();
    }


     //Pre-loads all rocket skin images in background threads for better performance
    private void preloadAllImages() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                for (LockerModel.Item item : viewModel.getCatalogItems()) {
                    String iconPath = item.getIcon();
                    if (iconPath != null && !iconPath.isEmpty() && !imageCache.containsKey(iconPath)) {
                        imageLoadingExecutor.submit(() -> {
                            try {
                                loadAndCacheImage(iconPath, INVENTORY_ICON_SIZE);
                                loadAndCacheImage(iconPath, EQUIPPED_ICON_SIZE);
                            } catch (Exception e) {
                                System.out.println("Error preloading image: " + e.getMessage());
                            }
                        });
                    }
                }
                return null;
            }
        };
        worker.execute();
    }

    private void loadAndCacheImage(String path, int size) {
        if (path == null || path.isEmpty()) return;

        String cacheKey = path + "-" + size;

        if (!imageCache.containsKey(cacheKey)) {
            try {
                ImageIcon icon = new ImageIcon(path);
                Image scaledImg = icon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
                imageCache.put(cacheKey, new ImageIcon(scaledImg));
            } catch (Exception e) {
                System.out.println("Error loading image " + path + ": " + e.getMessage());
                imageCache.put(cacheKey, null);
            }
        }
    }

    private ImageIcon getScaledImageIcon(String path, int size) {
        if (path == null || path.isEmpty()) return null;

        String cacheKey = path + "-" + size;

        if (imageCache.containsKey(cacheKey)) {
            return imageCache.get(cacheKey);
        }

        loadAndCacheImage(path, size);
        return imageCache.get(cacheKey);
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

            // Rocket icon placeholder (will be replaced with actual image)
            JLabel iconLabel = new JLabel("Loading...");
            iconLabel.setPreferredSize(new Dimension(EQUIPPED_ICON_SIZE, EQUIPPED_ICON_SIZE));
            iconLabel.setMinimumSize(new Dimension(EQUIPPED_ICON_SIZE, EQUIPPED_ICON_SIZE));
            iconLabel.setMaximumSize(new Dimension(EQUIPPED_ICON_SIZE, EQUIPPED_ICON_SIZE));
            iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
            iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Load image asynchronously
            String iconPath = equippedItem.getIcon();
            ImageIcon cachedIcon = getScaledImageIcon(iconPath, EQUIPPED_ICON_SIZE);
            if (cachedIcon != null) {
                // If already cached, use it immediately
                iconLabel.setIcon(cachedIcon);
                iconLabel.setText(null);
            } else {
                // Otherwise, load it asynchronously
                imageLoadingExecutor.submit(() -> {
                    ImageIcon icon = getScaledImageIcon(iconPath, EQUIPPED_ICON_SIZE);
                    SwingUtilities.invokeLater(() -> {
                        if (icon != null) {
                            iconLabel.setIcon(icon);
                            iconLabel.setText(null);
                        } else {
                            iconLabel.setText("Rocket");
                        }
                    });
                });
            }

            // Rocket name
            FlatLabel nameLabel = new FlatLabel();
            nameLabel.setText(equippedItem.getName());
            nameLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +2;");
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Unequip button
            FlatButton unequipButton = new FlatButton();
            unequipButton.setText("Unequip");
            unequipButton.putClientProperty(FlatClientProperties.STYLE, "arc:10;");
            unequipButton.addActionListener(e -> {
                viewModel.unequipItem(equippedItem.getId());
                updateUI();
            });
            unequipButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            rocketPanel.add(iconLabel, "gapy 10, align center");
            rocketPanel.add(nameLabel, "align center");
            rocketPanel.add(unequipButton, "gapy 10, align center");

            equippedPanel.add(rocketPanel, "grow, push");
        }

        equippedPanel.revalidate();
        equippedPanel.repaint();
    }

    private void updateInventoryPanel() {
        inventoryPanel.removeAll();

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
            for (LockerModel.Item item : ownedItems) {
                inventoryPanel.add(createInventoryItemPanel(item), "width " + INVENTORY_PANEL_WIDTH + "!, height " + INVENTORY_PANEL_HEIGHT + "!");
            }
        }

        inventoryPanel.revalidate();
        inventoryPanel.repaint();
    }

    private JPanel createInventoryItemPanel(LockerModel.Item item) {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        itemPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        itemPanel.putClientProperty(FlatClientProperties.STYLE, "background:darken(@background,3%); arc:10;");

        itemPanel.setPreferredSize(new Dimension(INVENTORY_PANEL_WIDTH, INVENTORY_PANEL_HEIGHT));
        itemPanel.setMinimumSize(new Dimension(INVENTORY_PANEL_WIDTH, INVENTORY_PANEL_HEIGHT));
        itemPanel.setMaximumSize(new Dimension(INVENTORY_PANEL_WIDTH, INVENTORY_PANEL_HEIGHT));

        boolean isEquipped = viewModel.hasEquipped(item.getId());

        if (isEquipped) {
            itemPanel.putClientProperty(FlatClientProperties.STYLE, "background:darken(@accentColor,30%); arc:10;");
        }

        // Rocket icon placeholder (will be replaced with actual image)
        JLabel iconLabel = new JLabel("Loading...");
        iconLabel.setPreferredSize(new Dimension(INVENTORY_ICON_SIZE, INVENTORY_ICON_SIZE));
        iconLabel.setMinimumSize(new Dimension(INVENTORY_ICON_SIZE, INVENTORY_ICON_SIZE));
        iconLabel.setMaximumSize(new Dimension(INVENTORY_ICON_SIZE, INVENTORY_ICON_SIZE));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        String iconPath = item.getIcon();
        ImageIcon cachedIcon = getScaledImageIcon(iconPath, INVENTORY_ICON_SIZE);
        if (cachedIcon != null) {
            iconLabel.setIcon(cachedIcon);
            iconLabel.setText(null);
        } else {
            // Otherwise, load it asynchronously
            imageLoadingExecutor.submit(() -> {
                ImageIcon icon = getScaledImageIcon(iconPath, INVENTORY_ICON_SIZE);
                SwingUtilities.invokeLater(() -> {
                    if (icon != null) {
                        iconLabel.setIcon(icon);
                        iconLabel.setText(null);
                    } else {
                        iconLabel.setText("Icon");
                    }
                });
            });
        }

        JPanel iconPanel = new JPanel();
        iconPanel.setLayout(new BoxLayout(iconPanel, BoxLayout.X_AXIS));
        iconPanel.add(Box.createHorizontalGlue());
        iconPanel.add(iconLabel);
        iconPanel.add(Box.createHorizontalGlue());
        iconPanel.setOpaque(false);
        iconPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        FlatLabel nameLabel = new FlatLabel();
        nameLabel.setText(item.getName());
        nameLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold;");
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

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
        actionButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components with rigid areas for spacing
        itemPanel.add(iconPanel);
        itemPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        itemPanel.add(nameLabel);
        itemPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        itemPanel.add(actionButton);

        return itemPanel;
    }

    public void dispose() {
        imageLoadingExecutor.shutdown();
        imageCache.clear();
    }
}