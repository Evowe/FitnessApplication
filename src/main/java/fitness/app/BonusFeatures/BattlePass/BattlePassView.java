package fitness.app.BonusFeatures.BattlePass;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatLabel;
import fitness.app.Utility.Databases.BattlePassDB;
import fitness.app.Utility.Widgets.SideMenu.SideMenuView;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BattlePassView extends JPanel {

    private static JPanel mainPanel;
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

        JPanel tierList = new JPanel(new MigLayout("insets 20, gapx 20", ""));
        tierList.putClientProperty(FlatClientProperties.STYLE, "background:@background;");

        try (Connection conn = BattlePassDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM BattlePass ORDER BY TierNumber ASC")) {

            while (rs.next()) {
                int tierNumber = rs.getInt("TierNumber");
                String titleText = rs.getString("Title");
                String description = rs.getString("Description");
                int isUnlocked = rs.getInt("IsUnlocked");

                JPanel tierCard = new JPanel(new MigLayout("wrap, insets 10", "fill"));
                tierCard.setPreferredSize(new Dimension(180, 220));
                tierCard.putClientProperty(FlatClientProperties.STYLE, "arc:20;");

                FlatLabel tierTitle = new FlatLabel();
                tierTitle.setText(titleText);
                tierTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +2;");

                FlatLabel tierContent = new FlatLabel();
                tierContent.setText(description);
                tierContent.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground; font:+1;");

                FlatLabel unlockLabel = new FlatLabel();
                unlockLabel.setText(isUnlocked == 1 ? "Unlocked" : "Locked");
                unlockLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:@secondaryForeground; font:+1;");

                tierCard.add(tierTitle);
                tierCard.add(tierContent);
                tierCard.add(unlockLabel);

                tierCard.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        showTierPopup(tierNumber, titleText, description, isUnlocked);
                    }
                });

                tierList.add(tierCard);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(tierList);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);

        tierList.setLayout(new BoxLayout(tierList, BoxLayout.X_AXIS));

        contentPanel.add(title, "dock north");
        contentPanel.add(scrollPane, "growx, height 300");

        add(menuPanel, "growy, pushy");
        add(contentPanel, "grow");
    }

    private void showTierPopup(int tierNumber, String title, String description, int isUnlocked) {
        JPanel panel = new JPanel(new MigLayout("wrap, insets 20", "[grow]"));
        panel.setPreferredSize(new Dimension(300, 220));
        panel.putClientProperty(FlatClientProperties.STYLE, "arc:20; padding:15;");

        FlatLabel titleLabel = new FlatLabel();
        titleLabel.setText("Tier " + tierNumber + ": " + title);
        titleLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +4;");

        FlatLabel descriptionLabel = new FlatLabel();
        descriptionLabel.setText("Description: " + description);
        descriptionLabel.putClientProperty(FlatClientProperties.STYLE, "font:+1;");

        JCheckBox unlockCheckBox = new JCheckBox("Unlocked");
        unlockCheckBox.setSelected(isUnlocked == 1);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int newState = unlockCheckBox.isSelected() ? 1 : 0;
                try (Connection conn = BattlePassDB.getConnection();
                     Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate("UPDATE BattlePass SET IsUnlocked = " + newState + " WHERE TierNumber = " + tierNumber);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        panel.add(titleLabel);
        panel.add(descriptionLabel);
        panel.add(unlockCheckBox);
        panel.add(saveButton);

        JOptionPane.showMessageDialog(this, panel, "Tier Details", JOptionPane.PLAIN_MESSAGE);
    }
    public static JPanel get() {return mainPanel;}

}
