package fitness.app.Widgets.Battlepass;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatProgressBar;
import com.formdev.flatlaf.fonts.roboto_mono.FlatRobotoMonoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import fitness.app.Login.LoginViewModel;
import fitness.app.Objects.AccountsDB;
import fitness.app.Objects.CreditCardDB;
import fitness.app.Objects.DatabaseManager;
import fitness.app.Objects.ExerciseDB;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BattlepassView extends JPanel {
    public BattlepassView() {
        setLayout(new MigLayout("fill,insets 10", "center", "center"));
        putClientProperty(FlatClientProperties.STYLE, "arc:20;");

        JPanel itemsPanel = new JPanel(new MigLayout("fill,insets 0"));
        itemsPanel.add(getItem(1));
        itemsPanel.add(getItem(2));
        itemsPanel.add(getItem(3));
        itemsPanel.add(getItem(4));
        itemsPanel.add(getItem(5));
        itemsPanel.add(getItem(6), "wrap");

        FlatProgressBar progressBar = new FlatProgressBar();
        progressBar.setValue(25);
        itemsPanel.add(progressBar, "span, growx");

        FlatButton previousButton = new FlatButton();
        previousButton.putClientProperty(FlatClientProperties.STYLE, "background:@secondaryAccent");
        previousButton.setBorderPainted(false);
        previousButton.setText("<");


        FlatButton nextButton = new FlatButton();
        nextButton.putClientProperty(FlatClientProperties.STYLE, "background:@secondaryAccent");
        nextButton.setBorderPainted(false);
        nextButton.setText(">");

        add(previousButton, "growy");
        add(itemsPanel);
        add(nextButton, "wrap, growy");
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
