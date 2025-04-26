package Application.Metrics.Statistics;

import Application.Utility.Objects.Account;
import Application.Utility.Databases.DatabaseManager;
import Application.Utility.Databases.StatsDB;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class StatsModel {
    public Account acc;
    public final StatsDB statsDB;
    StatsModel(Account acc) {
        this.acc = acc;
        this.statsDB = DatabaseManager.getStatsDB();
    }

    public String verifyCalories(int calories) {
        if( calories >= 0 && calories <= 10000)
        {
            try {
                LocalDate today = LocalDate.now();
                boolean updated = statsDB.addOrUpdateMetric(acc.getUsername(), today, calories, null, null);
                if (updated){
                    return null;
                }
                else {
                    return "Database update failed";
                }
            } catch (SQLException e) {
                System.err.println("Error updating DB: " + e.getMessage());
                return "Database update failed";
            }
        }
        return "Bad Input";
    }

    public String verifySleep(Double sl) {
        if( sl >= 0 && sl <= 15)
        {
            try {
                LocalDate today = LocalDate.now();
                boolean updated = statsDB.addOrUpdateMetric(acc.getUsername(), today, null, sl, null);
                if (updated){
                    return null;
                } else {
                    return "Database update failed";
                }
            } catch (SQLException e){
                System.err.println("Error updating DB: " + e.getMessage());
                return "Database update failed";
            }
        }
        return "Bad Input";
    }

    public String verifyWeight(Double we) {
        if( we >= 0 && we <= 800)
        {
            try {
                LocalDate today = LocalDate.now();
                boolean updated = statsDB.addOrUpdateMetric(acc.getUsername(), today, null, null, we);
                if (updated){
                    return null;
                } else {
                    return "Database update failed";
                }
            } catch (SQLException e){
                System.err.println("Error updating DB: " + e.getMessage());
                return "Database update failed";
            }
        }
        return "Bad Input";
    }

    public ArrayList<Integer> getXData() {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(9);

        // Create an array with all 10 days in sequence
        ArrayList<Integer> xData = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            LocalDate currentDate = startDate.plusDays(i);
            xData.add(currentDate.getDayOfMonth());
        }

        return xData;
    }

    public ArrayList<Integer> getYData(String metricName) {
        try {
            LocalDate endDate = LocalDate.now();
            LocalDate startDate = endDate.minusDays(9);

            List<Map<String, Object>> metrics = statsDB.getMetricsRange(acc.getUsername(), startDate, endDate);
            ArrayList<Integer> yData = new ArrayList<>();

            // If completely empty, return 0's
            if (metrics.isEmpty()) {

                for (int i = 0; i < 10; i++) {
                    yData.add(0);
                }
                return yData;
            }


            java.util.Map<LocalDate, Integer> dateValueMap = new java.util.HashMap<>();

            // First populate the map with the data we have
            for (Map<String, Object> metric : metrics) {
                String dateStr = (String) metric.get("date");
                LocalDate date = LocalDate.parse(dateStr);

                Integer value = 0;

                switch (metricName.toLowerCase()) {
                    case "calories":
                        Integer calories = (Integer) metric.get("calories");
                        value = calories != null ? calories : 0;
                        break;
                    case "sleep":
                        Double sleep = (Double) metric.get("sleep");
                        // Just round to nearest integer
                        value = sleep != null ? (int)Math.round(sleep) : 0;
                        break;
                    case "weight":
                        Double weight = (Double) metric.get("weight");
                        // Convert weight to integer
                        value = weight != null ? weight.intValue() : 0;
                        break;
                }

                dateValueMap.put(date, value);
            }

            // Now iterate through all 10 days, filling in zeros for missing days
            for (int i = 0; i < 10; i++) {
                LocalDate currentDate = startDate.plusDays(i);
                Integer value = dateValueMap.getOrDefault(currentDate, 0);
                yData.add(value);
            }

            return yData;
        } catch (SQLException e) {
            System.err.println("Error retrieving data: " + e.getMessage());

            ArrayList<Integer> yData = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                yData.add(0);
            }
            return yData;
        }
    }


    public String getDailyMetric(String category) {
        LocalDate today = LocalDate.now();
        Map<String,Object> map;
        try {
            map = statsDB.getDailyMetrics(acc.getUsername(), today);
        }
        catch (SQLException e){
            System.err.println("Error retrieving data: " + e.getMessage());
            return "0";
        }
        switch (category.toLowerCase()) {
            case "calories":
                return  map.get("calories").toString();
            case "sleep":
                return String.format("%.1f", ((Double)map.get("sleep")));

            case "weight":
                return String.format("%.1f", ((Double)map.get("weight")));
        }
        return "0";
    }

}
