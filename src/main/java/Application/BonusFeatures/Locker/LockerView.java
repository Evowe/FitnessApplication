package Application.BonusFeatures.Locker;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;
import com.formdev.flatlaf.extras.components.FlatTabbedPane;
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
    private JPanel rocketsPanel;
    private JPanel titlesPanel;
    private JTabbedPane tabbedPane;

    // Define fixed sizes for consistency
    private final int INVENTORY_ICON_SIZE = 200;
    private final int INVENTORY_PANEL_WIDTH = 300;
    private final int INVENTORY_PANEL_HEIGHT = 300;
    private final int EQUIPPED_ICON_SIZE = 350;

    // Image loading optimization
    private final Map<String, ImageIcon> imageCache = new ConcurrentHashMap<>();
    private final ExecutorService imageLoadingExecutor = Executors.newFixedThreadPool(3);

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

        // Create main content area with split panes
        JPanel mainContentPanel = new JPanel(new MigLayout("", "[30%][70%]", "[]"));
        mainContentPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background;");

        // Left panel for equipped items
        equippedPanel = new JPanel(new MigLayout("wrap, fillx, insets 15", "[fill]"));
        equippedPanel.putClientProperty(FlatClientProperties.STYLE, "background:darken(@background,3%); arc:20;");

        // Right panel with tabs for rockets and titles
        tabbedPane = new FlatTabbedPane();
        tabbedPane.putClientProperty(FlatClientProperties.STYLE, "tabHeight:40; tabWidthMode:preferred; showTabSeparators:true");

        // Rockets tab
        JScrollPane rocketsScrollPane = new JScrollPane();
        rocketsScrollPane.putClientProperty(FlatClientProperties.STYLE, "border:0,0,0,0");
        rocketsScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        rocketsPanel = new JPanel(new MigLayout("wrap 3, fillx, insets 15",
                "[" + INVENTORY_PANEL_WIDTH + "!][" + INVENTORY_PANEL_WIDTH + "!][" + INVENTORY_PANEL_WIDTH + "!]"));
        rocketsPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background;");
        rocketsScrollPane.setViewportView(rocketsPanel);

        // Titles tab
        JScrollPane titlesScrollPane = new JScrollPane();
        titlesScrollPane.putClientProperty(FlatClientProperties.STYLE, "border:0,0,0,0");
        titlesScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        titlesPanel = new JPanel(new MigLayout("wrap 1, fillx, insets 15", "[fill]"));
        titlesPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background;");
        titlesScrollPane.setViewportView(titlesPanel);

        // Add tabs to tabbed pane
        tabbedPane.addTab("Rocket Skins", rocketsScrollPane);
        tabbedPane.addTab("Titles", titlesScrollPane);

        mainContentPanel.add(equippedPanel, "grow");
        mainContentPanel.add(tabbedPane, "grow");

        contentPanel.add(mainContentPanel, "grow, push");
        add(contentPanel, "grow, push");

        // Pre-load all images in background for faster UI updates
        preloadAllImages();

        // Initial UI update
        updateUI();
    }

    /**
     * Pre-loads all rocket skin images in background threads for better performance
     */
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

    /**
     * Loads an image, scales it, and stores in cache
     */
    private void loadAndCacheImage(String path, int size) {
        if (path == null || path.isEmpty()) return;

        // Generate a cache key for this path and size combination
        String cacheKey = path + "-" + size;

        if (!imageCache.containsKey(cacheKey)) {
            try {
                ImageIcon icon = new ImageIcon(path);
                Image scaledImg = icon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
                imageCache.put(cacheKey, new ImageIcon(scaledImg));
            } catch (Exception e) {
                System.out.println("Error loading image " + path + ": " + e.getMessage());
                // Store null in cache to avoid repeated attempts to load broken images
                imageCache.put(cacheKey, null);
            }
        }
    }

    /**
     * Gets a scaled image icon from cache, or loads it if not cached
     */
    private ImageIcon getScaledImageIcon(String path, int size) {
        if (path == null || path.isEmpty()) return null;

        String cacheKey = path + "-" + size;

        // Try to get from cache first
        if (imageCache.containsKey(cacheKey)) {
            return imageCache.get(cacheKey);
        }

        // If not in cache, load synchronously (should rarely happen due to preloading)
        loadAndCacheImage(path, size);
        return imageCache.get(cacheKey);
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (viewModel != null) {
            SwingUtilities.invokeLater(() -> {
                updateEquippedPanel();
                updateRocketsPanel();
                updateTitlesPanel();
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

        // --- Equipped Rocket ---
        FlatLabel rocketHeader = new FlatLabel();
        rocketHeader.setText("Rocket Skin");
        rocketHeader.putClientProperty(FlatClientProperties.STYLE, "font:bold;");
        equippedPanel.add(rocketHeader, "gapy 10");

        List<LockerModel.Item> equippedRockets = viewModel.getEquippedItemsByType("rocket");

        if (equippedRockets.isEmpty()) {
            FlatLabel emptyLabel = new FlatLabel();
            emptyLabel.setText("No rocket equipped");
            emptyLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground;");
            equippedPanel.add(emptyLabel, "gapy 5");
        } else {
            // Display the equipped rocket
            LockerModel.Item equippedRocket = equippedRockets.get(0);
            equippedPanel.add(createEquippedRocketPanel(equippedRocket), "grow, gapy 5");
        }

        // --- Equipped Title ---
        FlatLabel titleHeader = new FlatLabel();
        titleHeader.setText("Title");
        titleHeader.putClientProperty(FlatClientProperties.STYLE, "font:bold;");
        equippedPanel.add(titleHeader, "gapy 20");

        List<LockerModel.Item> equippedTitles = viewModel.getEquippedItemsByType("title");

        if (equippedTitles.isEmpty()) {
            FlatLabel emptyLabel = new FlatLabel();
            emptyLabel.setText("No title equipped");
            emptyLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground;");
            equippedPanel.add(emptyLabel, "gapy 5");
        } else {
            // Display the equipped title
            LockerModel.Item equippedTitle = equippedTitles.get(0);
            equippedPanel.add(createEquippedTitlePanel(equippedTitle), "grow, gapy 5");
        }

        equippedPanel.revalidate();
        equippedPanel.repaint();
    }

    private JPanel createEquippedRocketPanel(LockerModel.Item rocket) {
        // Create panel for the equipped rocket
        JPanel rocketPanel = new JPanel(new MigLayout("wrap, fillx, insets 10", "[center]"));
        rocketPanel.putClientProperty(FlatClientProperties.STYLE, "background:darken(@background,5%); arc:10;");

        // Rocket icon (larger size for equipped)
        JLabel iconLabel = new JLabel("Loading...");
        iconLabel.setPreferredSize(new Dimension(EQUIPPED_ICON_SIZE, EQUIPPED_ICON_SIZE));
        iconLabel.setMinimumSize(new Dimension(EQUIPPED_ICON_SIZE, EQUIPPED_ICON_SIZE));
        iconLabel.setMaximumSize(new Dimension(EQUIPPED_ICON_SIZE, EQUIPPED_ICON_SIZE));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Load image asynchronously
        String iconPath = rocket.getIcon();
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
        nameLabel.setText(rocket.getName());
        nameLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +2;");
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Unequip button
        FlatButton unequipButton = new FlatButton();
        unequipButton.setText("Unequip");
        unequipButton.putClientProperty(FlatClientProperties.STYLE, "arc:10;");
        unequipButton.addActionListener(e -> {
            viewModel.unequipItem(rocket.getId());
            updateUI();
        });
        unequipButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        rocketPanel.add(iconLabel, "gapy 10, align center");
        rocketPanel.add(nameLabel, "align center");
        rocketPanel.add(unequipButton, "gapy 10, align center");

        return rocketPanel;
    }

    private JPanel createEquippedTitlePanel(LockerModel.Item title) {
        // Create panel for the equipped title
        JPanel titlePanel = new JPanel(new MigLayout("wrap, fillx, insets 10", "[center]"));
        titlePanel.putClientProperty(FlatClientProperties.STYLE, "background:darken(@background,5%); arc:10;");

        // Title name (large)
        FlatLabel nameLabel = new FlatLabel();
        nameLabel.setText(title.getName());
        nameLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +5;");
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Title description
        FlatLabel descLabel = new FlatLabel();
        descLabel.setText(title.getDescription());
        descLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground;");
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Unequip button
        FlatButton unequipButton = new FlatButton();
        unequipButton.setText("Unequip");
        unequipButton.putClientProperty(FlatClientProperties.STYLE, "arc:10;");
        unequipButton.addActionListener(e -> {
            viewModel.unequipItem(title.getId());
            updateUI();
        });
        unequipButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(nameLabel, "align center");
        titlePanel.add(descLabel, "align center, gapy 5");
        titlePanel.add(unequipButton, "gapy 15, align center");

        return titlePanel;
    }

    private void updateRocketsPanel() {
        rocketsPanel.removeAll();

        // Add a header for the inventory
        FlatLabel rocketsHeader = new FlatLabel();
        rocketsHeader.setText("Your Rockets");
        rocketsHeader.putClientProperty(FlatClientProperties.STYLE, "font:bold +2");
        rocketsPanel.add(rocketsHeader, "span, growx, pushx, gapy 10");

        List<LockerModel.Item> ownedRockets = viewModel.getOwnedItemsByType("rocket");

        if (ownedRockets.isEmpty()) {
            FlatLabel emptyLabel = new FlatLabel();
            emptyLabel.setText("You don't own any rocket skins yet.");
            emptyLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground;");
            rocketsPanel.add(emptyLabel, "span, gapy 20");
        } else {
            // Add all owned rockets in a grid layout
            for (LockerModel.Item item : ownedRockets) {
                rocketsPanel.add(createRocketItemPanel(item), "width " + INVENTORY_PANEL_WIDTH + "!, height " + INVENTORY_PANEL_HEIGHT + "!");
            }
        }

        rocketsPanel.revalidate();
        rocketsPanel.repaint();
    }

    private void updateTitlesPanel() {
        titlesPanel.removeAll();

        // Add a header for the titles
        FlatLabel titlesHeader = new FlatLabel();
        titlesHeader.setText("Your Titles");
        titlesHeader.putClientProperty(FlatClientProperties.STYLE, "font:bold +2");
        titlesPanel.add(titlesHeader, "growx, pushx, gapy 10");

        List<LockerModel.Item> ownedTitles = viewModel.getOwnedItemsByType("title");

        if (ownedTitles.isEmpty()) {
            FlatLabel emptyLabel = new FlatLabel();
            emptyLabel.setText("You don't own any titles yet.");
            emptyLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground;");
            titlesPanel.add(emptyLabel, "gapy 20");
        } else {
            // Add all owned titles in a list layout
            for (LockerModel.Item item : ownedTitles) {
                titlesPanel.add(createTitleItemPanel(item), "growx, gapy 5");
            }
        }

        titlesPanel.revalidate();
        titlesPanel.repaint();
    }

    private JPanel createRocketItemPanel(LockerModel.Item item) {
        // Use explicit fixed size constraints for the panel
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        itemPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        itemPanel.putClientProperty(FlatClientProperties.STYLE, "background:darken(@background,3%); arc:10;");

        // Set fixed size for the panel
        itemPanel.setPreferredSize(new Dimension(INVENTORY_PANEL_WIDTH, INVENTORY_PANEL_HEIGHT));
        itemPanel.setMinimumSize(new Dimension(INVENTORY_PANEL_WIDTH, INVENTORY_PANEL_HEIGHT));
        itemPanel.setMaximumSize(new Dimension(INVENTORY_PANEL_WIDTH, INVENTORY_PANEL_HEIGHT));

        boolean isEquipped = viewModel.hasEquipped(item.getId());

        if (isEquipped) {
            itemPanel.putClientProperty(FlatClientProperties.STYLE, "background:darken(@accentColor,30%); arc:10;");
        }

        // Rocket icon - standardized size
        JLabel iconLabel = new JLabel("Loading...");
        iconLabel.setPreferredSize(new Dimension(INVENTORY_ICON_SIZE, INVENTORY_ICON_SIZE));
        iconLabel.setMinimumSize(new Dimension(INVENTORY_ICON_SIZE, INVENTORY_ICON_SIZE));
        iconLabel.setMaximumSize(new Dimension(INVENTORY_ICON_SIZE, INVENTORY_ICON_SIZE));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Load image asynchronously
        String iconPath = item.getIcon();
        ImageIcon cachedIcon = getScaledImageIcon(iconPath, INVENTORY_ICON_SIZE);
        if (cachedIcon != null) {
            // If already cached, use it immediately
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

        // Center panel to hold the icon (ensures consistent positioning)
        JPanel iconPanel = new JPanel();
        iconPanel.setLayout(new BoxLayout(iconPanel, BoxLayout.X_AXIS));
        iconPanel.add(Box.createHorizontalGlue());
        iconPanel.add(iconLabel);
        iconPanel.add(Box.createHorizontalGlue());
        iconPanel.setOpaque(false);
        iconPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Rocket name
        FlatLabel nameLabel = new FlatLabel();
        nameLabel.setText(item.getName());
        nameLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold;");
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

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
        actionButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components with rigid areas for spacing
        itemPanel.add(iconPanel);
        itemPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        itemPanel.add(nameLabel);
        itemPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        itemPanel.add(actionButton);

        return itemPanel;
    }

    private JPanel createTitleItemPanel(LockerModel.Item item) {
        // Create a panel for the title item with a more consistent layout
        JPanel itemPanel = new JPanel(new MigLayout("fillx, insets 15", "[grow, fill][]"));
        itemPanel.putClientProperty(FlatClientProperties.STYLE, "background:darken(@background,3%); arc:10;");

        boolean isEquipped = viewModel.hasEquipped(item.getId());

        if (isEquipped) {
            itemPanel.putClientProperty(FlatClientProperties.STYLE, "background:darken(@accentColor,30%); arc:10;");
        }

        // Left panel for title info
        JPanel infoPanel = new JPanel(new MigLayout("insets 0", "[fill]"));
        infoPanel.setOpaque(false);

        // Title name and description
        FlatLabel nameLabel = new FlatLabel();
        nameLabel.setText(item.getName());
        nameLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +2;");

        FlatLabel descLabel = new FlatLabel();
        descLabel.setText(item.getDescription());
        descLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground;");

        infoPanel.add(nameLabel, "wrap");
        infoPanel.add(descLabel, "wrap");

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

        // Set a fixed width for the button to ensure alignment
        actionButton.setPreferredSize(new Dimension(100, actionButton.getPreferredSize().height));

        // Add components to the main panel with explicit width and alignment for the button
        itemPanel.add(infoPanel, "grow");
        itemPanel.add(actionButton, "width 100!, right");

        return itemPanel;
    }

}