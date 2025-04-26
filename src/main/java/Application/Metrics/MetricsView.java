package Application.Metrics;

import Application.Metrics.Goals.GoalsView;
import Application.Utility.Objects.Account;
import Application.Metrics.Report.ReportView;
import Application.Metrics.Statistics.StatsView;
import Application.Utility.Widgets.SideMenu.SideMenuView;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatTabbedPane;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class MetricsView extends JPanel{
    private Account currentUser;
    public MetricsView(Account currentUser) {
        setLayout(new MigLayout("fill, insets 20", "[]20[]", "center"));
        putClientProperty(FlatClientProperties.STYLE, "background:@background");
        this.currentUser = currentUser;

        FlatTabbedPane tabbedPane = new FlatTabbedPane();
        tabbedPane.putClientProperty(FlatClientProperties.STYLE, "background:@background; foreground:@foreground");
        tabbedPane.putClientProperty("TabbedPane.underlineColor", "@accent");
        tabbedPane.addTab("Goals", new GoalsView(currentUser));
        tabbedPane.addTab("Stats", new StatsView(currentUser));
        tabbedPane.addTab("Workout History", new ReportView(currentUser));
        tabbedPane.putClientProperty("JTabbedPane.tabAlignment", "center");

        add(new SideMenuView(), "growy, pushy");
        add(tabbedPane, "growx, pushx");
    }

}
