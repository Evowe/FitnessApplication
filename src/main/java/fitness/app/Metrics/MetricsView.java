package fitness.app.Metrics;

import fitness.app.Metrics.Goals.GoalsView;
import fitness.app.BadProjectStructureSection.Objects.Account;
import fitness.app.Metrics.Report.ReportView;
import fitness.app.Metrics.Statistics.StatsView;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

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
