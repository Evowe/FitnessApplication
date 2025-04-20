package fitness.app.BattlePass;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BattlePassPanel extends JPanel {

    public BattlePassPanel() {
        setLayout(new BorderLayout());

        // Main container for all 10 sets
        JTabbedPane tabbedPane = new JTabbedPane();

        // Create 10 sets of reward panels
        for (int setIndex = 0; setIndex < 10; setIndex++) {
            tabbedPane.add("Set " + (setIndex + 1), createRewardSetPanel(setIndex));
        }

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createRewardSetPanel(int setIndex) {
        JPanel container = new JPanel(new BorderLayout());

        // Horizontal container for rewards
        JPanel rewardsPanel = new JPanel();
        rewardsPanel.setLayout(new BoxLayout(rewardsPanel, BoxLayout.X_AXIS));

        for (int i = 0; i < 10; i++) {
            JPanel rewardSlot = createRewardSlot(setIndex, i);
            rewardsPanel.add(rewardSlot);
        }

        JScrollPane scrollPane = new JScrollPane(rewardsPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        container.add(scrollPane, BorderLayout.CENTER);
        return container;
    }

    private JPanel createRewardSlot(int setIndex, int itemIndex) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(100, 120));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.setLayout(new BorderLayout());

        // Placeholder image (should replace with real images)
        ImageIcon icon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
        JLabel imageLabel = new JLabel(icon);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel textLabel = new JLabel("Reward " + (itemIndex + 1));
        textLabel.setHorizontalAlignment(JLabel.CENTER);

        panel.add(imageLabel, BorderLayout.CENTER);
        panel.add(textLabel, BorderLayout.SOUTH);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Battle Pass Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 300);
            frame.add(new BattlePassPanel());
            frame.setVisible(true);
        });
    }
}

