package fitness.app.Statistics;

import fitness.app.Objects.Account;

public class StatsModel {
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
}
