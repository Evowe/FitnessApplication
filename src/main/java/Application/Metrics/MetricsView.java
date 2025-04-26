package Application.Metrics;

import Application.Metrics.Goals.GoalsView;
import Application.Utility.Objects.Account;
import Application.Metrics.Report.ReportView;
import Application.Metrics.Statistics.StatsView;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class MetricsView extends JPanel{
    private Account currentUser;
    public MetricsView(Account currentUser) {
        setLayout(new MigLayout());
        this.currentUser = currentUser;
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        tabbedPane.addTab("Goals", new GoalsView(currentUser));
        tabbedPane.addTab("Stats", new StatsView(currentUser));
        tabbedPane.addTab("Workout History", new ReportView(currentUser));
        tabbedPane.putClientProperty("JTabbedPane.tabAlignment", "center");

        add(tabbedPane);
    }

}
