package fitness.app.Statistics;


import fitness.app.Objects.Account;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StatsViewModel extends JFrame {
    private Account acc;
    private final StatsModel statsModel;

    public StatsViewModel(Account acc) {
        this.acc = acc;
        //statsView = new StatsView(acc);
        this.statsModel = new StatsModel(acc);
    }

    public ArrayList<Integer> getx()
    {
        return statsModel.getXData();
    }
    public ArrayList<Integer> gety()
    {
        return statsModel.getYData();
    }
    public Account getAcc() {
        return acc;
    }
    public void setAcc(Account acc) {
        this.acc = acc;
    }
//    public static JPanel getStatsView() {
//        Account a = new Account("Bob", "Smith");
//        a.setSleep(0.0);
//        a.setCalories(0);
//        a.setWeight(0.0);
//        StatsView sl = new StatsView(a);
//        return sl.getViewPanel();
//    }
}