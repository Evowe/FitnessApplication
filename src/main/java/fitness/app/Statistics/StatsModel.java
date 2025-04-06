package fitness.app.Statistics;

import fitness.app.Objects.Account;

import java.util.ArrayList;

public class StatsModel {
    Account acc;
    StatsModel(Account acc) {
        this.acc = acc;
    }
    public static String verifyCalories(int calories) {
        if( calories >= 0 && calories <= 10000)
        {
            return null;
        }
        return "Bad Input";
    }

    public static String verifySleep(Double sl) {
        if( sl >= 0 && sl <= 15)
        {
            return null;
        }
        return "Bad Input";
    }

    public static String verifyWeight(Double we) {
        if( we >= 0 && we <= 800)
        {
            return null;
        }
        return "Bad Input";
    }
    //TODO : READ FROM HEALTH LOG INSTEAD
    public ArrayList<Integer> getXData()
    {
        ArrayList<Integer> xData = new ArrayList<>();
        for(int i =0 ; i < 10; i++)
        {
            xData.add(i);
        }
        return xData;
    }
    //TODO : READ FROM HEALTH LOG INSTEAD
    public ArrayList<Integer> getYData()
    {
        ArrayList<Integer> yData = new ArrayList<>();
        for(int i =0 ; i < 10; i++)
        {
            yData.add(i*50+200);
        }
        return yData;
    }
}
