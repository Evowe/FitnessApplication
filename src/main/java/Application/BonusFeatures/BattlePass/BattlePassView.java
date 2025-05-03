package Application.BonusFeatures.BattlePass;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatLabel;
import Application.Databases.BattlePassDB;
import Application.Utility.Widgets.SideMenu.SideMenuView;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BattlePassView extends JPanel {

    private static final int TIERS_PER_PAGE = 5;

    private int currentPage = 0;
    private final List<TierData> allTiers = new ArrayList<>();
    private final JPanel tierListPanel = new JPanel();
    private final JButton prevButton = new JButton("Previous");
    private final JButton nextButton = new JButton("Next");

    public BattlePassView() {
        setLayout(new MigLayout("fill, insets 20"));
        putClientProperty(FlatClientProperties.STYLE, "background:@background;");

        JPanel menuPanel = new JPanel(new MigLayout("insets 0"));
        menuPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background;");
        menuPanel.add(new SideMenuView(), "growy, pushy");

        JPanel contentPanel = new JPanel(new MigLayout("fill, insets 0", "center", "center"));
        contentPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background;");

        FlatLabel title = new FlatLabel();
        title.setText("Battle Pass");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +6;");

        tierListPanel.setLayout(new BoxLayout(tierListPanel, BoxLayout.X_AXIS));
        tierListPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background;");

        loadTiersFromDatabase();
        updateTierList();

        prevButton.addActionListener(e -> {
            if (currentPage > 0) {
                currentPage--;
                updateTierList();
            }
        });

        nextButton.addActionListener(e -> {
            if ((currentPage + 1) * TIERS_PER_PAGE < allTiers.size()) {
                currentPage++;
                updateTierList();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);

        JScrollPane scrollPane = new JScrollPane(tierListPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);

        contentPanel.add(title, "dock north");
        contentPanel.add(scrollPane, "growx, height 360");
        contentPanel.add(buttonPanel, "dock south");

        add(menuPanel, "growy, pushy");
        add(contentPanel, "grow");
    }

    private void loadTiersFromDatabase() {
        try (Connection conn = BattlePassDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM BattlePass ORDER BY TierNumber ASC")) {

            while (rs.next()) {
                TierData data = new TierData(
                        rs.getInt("TierNumber"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getInt("IsUnlocked"),
                        rs.getString("ImagePath")
                );
                allTiers.add(data);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateTierList() {
        tierListPanel.removeAll();

        int start = currentPage * TIERS_PER_PAGE;
        int end = Math.min(start + TIERS_PER_PAGE, allTiers.size());

        for (int i = start; i < end; i++) {
            TierData tier = allTiers.get(i);
            tierListPanel.add(createTierCard(tier));
            tierListPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        }

        tierListPanel.revalidate();
        tierListPanel.repaint();

        prevButton.setEnabled(currentPage > 0);
        nextButton.setEnabled((currentPage + 1) * TIERS_PER_PAGE < allTiers.size());
    }

    private JPanel createTierCard(TierData tier) {
        JPanel card = new JPanel(new MigLayout("wrap, insets 10", "[fill, grow]"));
        card.setPreferredSize(new Dimension(180, 280));
        card.putClientProperty(FlatClientProperties.STYLE, "arc:20;");

        if (tier.imagePath != null && !tier.imagePath.isBlank()) {
            ImageIcon icon = new ImageIcon(tier.imagePath);
            Image scaled = icon.getImage().getScaledInstance(160, 100, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(scaled));
            card.add(imageLabel, "center");
        }

        JLabel titleLabel = new JLabel("<html><div style='text-align:center;'>" + tier.title + "</div></html>");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 14f));
        card.add(titleLabel);

        JLabel descLabel = new JLabel("<html><div style='width:160px;'>" + tier.description + "</div></html>");
        descLabel.setFont(descLabel.getFont().deriveFont(12f));
        card.add(descLabel);

        JLabel unlockLabel = new JLabel(tier.isUnlocked == 1 ? "Unlocked" : "Locked");
        unlockLabel.setFont(unlockLabel.getFont().deriveFont(Font.ITALIC, 11f));
        unlockLabel.setForeground(Color.GRAY);
        card.add(unlockLabel);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showTierPopup(tier);
            }
        });

        return card;
    }

    private void showTierPopup(TierData tier) {
        JPanel panel = new JPanel(new MigLayout("wrap, insets 20", "[grow]"));
        panel.setPreferredSize(new Dimension(400, 320));
        panel.putClientProperty(FlatClientProperties.STYLE, "arc:20;");

        JLabel titleLabel = new JLabel("<html><b>Tier " + tier.tierNumber + ": " + tier.title + "</b></html>");
        titleLabel.setFont(titleLabel.getFont().deriveFont(16f));
        panel.add(titleLabel);

        if (tier.imagePath != null && !tier.imagePath.trim().isEmpty()) {
            ImageIcon icon = new ImageIcon(tier.imagePath);
            Image scaled = icon.getImage().getScaledInstance(250, 150, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(scaled));
            panel.add(imageLabel, "center");
        }

        JLabel descriptionLabel = new JLabel("<html><div style='width:350px;'>" + tier.description + "</div></html>");
        descriptionLabel.setFont(descriptionLabel.getFont().deriveFont(13f));
        panel.add(descriptionLabel);

        JLabel unlockLabel = new JLabel(tier.isUnlocked == 1 ? "Unlocked" : "Locked");
        unlockLabel.setFont(unlockLabel.getFont().deriveFont(Font.ITALIC, 12f));
        unlockLabel.setForeground(Color.GRAY);
        panel.add(unlockLabel);

        JOptionPane.showMessageDialog(this, panel, "Tier Details", JOptionPane.PLAIN_MESSAGE);
    }

    private static class TierData {
        int tierNumber;
        String title;
        String description;
        int isUnlocked;
        String imagePath;

        public TierData(int tierNumber, String title, String description, int isUnlocked, String imagePath) {
            this.tierNumber = tierNumber;
            this.title = title;
            this.description = description;
            this.isUnlocked = isUnlocked;
            this.imagePath = imagePath;
        }
    }
}
