package fitness.app.Metrics;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.fonts.roboto_mono.FlatRobotoMonoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import fitness.app.Goals.GoalsView;
import fitness.app.Objects.Account;
import fitness.app.Report.ReportView;
import fitness.app.Statistics.StatsView;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class MetricsView extends JPanel{
    private Account currentUser;
    public MetricsView(Account currentUser)
    {
        this.currentUser = currentUser;
        JTabbedPane tabbedPane = new JTabbedPane();
        //tabbedPane.putClientProperty(FlatClientProperties.STYLE, "arc:20;");

        //tabbedPane.setTabPlacement(JTabbedPane.RIGHT);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        tabbedPane.addTab("Goals", new GoalsView(currentUser));
        tabbedPane.addTab("Stats", new StatsView(currentUser));
        tabbedPane.addTab("Workout History", new ReportView(currentUser));
        tabbedPane.putClientProperty("JTabbedPane.tabAlignment", "center");


        setLayout(new MigLayout());
        add(tabbedPane);



    }

}
