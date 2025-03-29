package fitness.app.Statistics;


import fitness.app.Login.LoginView;
import fitness.app.Objects.Account;

import javax.swing.*;

public class StatsViewModel extends JFrame {
    private Account acc;
    private static StatsView statsView;

    public StatsViewModel(Account acc) {
        this.acc = acc;
        statsView = new StatsView(acc);
    }
    public Account getAcc() {
        return acc;
    }
    public void setAcc(Account acc) {
        this.acc = acc;
    }
    public static JPanel getStatsView() {
        Account a = new Account("Bob", "Smith");
        a.setSleep(0.0);
        a.setCalories(0);
        a.setWeight(0.0);
        StatsView sl = new StatsView(a);
        return sl.getViewPanel();
    }
//    public static void main(String[] args) {
//        JFrame frame = new JFrame("UpdateSleepInterface");
//        Account a = new Account("Bob", "Smith");
//        a.setSleep(0.0);
//        a.setCalories(0);
//        a.setWeight(0.0);
//        StatsView sl = new StatsView(a);
//    }
}