package Application.Utility.Widgets.Battlepass;

import Application.BonusFeatures.BattlePass.BattlePassModel;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatProgressBar;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BattlepassView extends JPanel {
    FlatButton previousButton;
    FlatButton nextButton;
    int midIndex = 1;

    private final BattlePassModel battlePassModel = new BattlePassModel();
    private final List<BattlePassModel.TierData> allTiers = battlePassModel.getTiers();
    public BattlepassView() {
        setLayout(new MigLayout("fill,insets 10", "center", "center"));
        putClientProperty(FlatClientProperties.STYLE, "arc:20;");

        previousButton = new FlatButton();
        previousButton.putClientProperty(FlatClientProperties.STYLE, "background:@secondaryBackground");
        previousButton.setBorderPainted(false);
        previousButton.setText("<");
        previousButton.addActionListener(e -> {
            if (midIndex > 1) {
                midIndex--;
                redraw();
            }
        });

        nextButton = new FlatButton();
        nextButton.putClientProperty(FlatClientProperties.STYLE, "background:@secondaryBackground");
        nextButton.setBorderPainted(false);
        nextButton.setText(">");
        nextButton.addActionListener(e -> {
            if (midIndex < 49) {
                midIndex++;
                redraw();
            }
        });

        redraw();
    }
    public void redraw() {
        removeAll();
        add(previousButton);
        add(createTierCard(allTiers.get(midIndex - 1)));
        add(createTierCard(allTiers.get(midIndex)));
        add(createTierCard(allTiers.get(midIndex + 1)));
        add(nextButton);
        revalidate();
        repaint();
    }

    private JPanel createTierCard(BattlePassModel.TierData tier) {
        JPanel card = new JPanel(new MigLayout("wrap, insets 10", "[fill, grow]"));
        card.setPreferredSize(new Dimension(100, 190));
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

        JLabel unlockLabel = new JLabel(tier.isUnlocked == 1 ? "Unlocked" : "Locked (XP: " + tier.requiredXP + ")");
        unlockLabel.setFont(unlockLabel.getFont().deriveFont(Font.ITALIC, 11f));
        unlockLabel.setForeground(Color.GRAY);

        if (tier.imagePath.isBlank()) {
            titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 24f));
            descLabel.setFont(descLabel.getFont().deriveFont(20f));
        }

        card.add(descLabel);
        card.add(unlockLabel);

        return card;
    }

    private JPanel getItem(int tier) {
        JPanel itemPanel = new JPanel(new MigLayout("wrap,fillx,insets 0", "center", "center"));

        JLabel itemLabel = new JLabel(String.valueOf(tier));
        itemPanel.add(itemLabel);

        FlatButton itemButton = new FlatButton();
        itemButton.setBorderPainted(false);
        itemButton.setMinimumSize(new Dimension(75, 75));
        itemButton.setIcon(BattlepassModel.getIcon("start-up"));
        itemPanel.add(itemButton);

        return itemPanel;
    }
}