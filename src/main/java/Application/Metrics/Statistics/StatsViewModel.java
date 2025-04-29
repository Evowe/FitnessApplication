package Application.Metrics.Statistics;


import Application.Utility.Objects.Account;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class StatsViewModel extends JFrame {
    private Account acc;
    private final StatsModel statsModel;

    public boolean loadTestData(int daysOfData) {
        Random random = new Random();
        LocalDate today = LocalDate.now();
        boolean allSuccessful = true;

        // Generate data for specified number of past days
        for (int i = 0; i < daysOfData; i++) {
            LocalDate date = today.minusDays(i);

            // Generate random values
            int calories = 1500 + random.nextInt(1000); // 1500-2500 calories
            double sleep = 5 + random.nextInt(4) + random.nextDouble(); // 5-9 hours of sleep
            double weight = 150 - 2 + random.nextInt(5) + random.nextDouble(); // Weight around 150

            try {
                // Use the StatsDB to directly add metrics for specific dates
                boolean success = statsModel.statsDB.addOrUpdateMetric(
                        acc.getUsername(),
                        date,
                        calories,
                        sleep,
                        weight
                );

                if (!success) {
                    allSuccessful = false;
                    System.err.println("Failed to add test data for date: " + date);
                }
            } catch (Exception e) {
                allSuccessful = false;
                System.err.println("Error adding test data: " + e.getMessage());
            }
        }

        return allSuccessful;
    }

    public StatsViewModel(Account acc) {
        this.acc = acc;
        //statsView = new StatsView(acc);
        this.statsModel = new StatsModel(acc);
    }

    public ArrayList<Integer> getx()
    {
        return statsModel.getXData();
    }

    public ArrayList<Integer> gety(String metric)
    {
        return statsModel.getYData(metric);
    }

    public String updateWeight(Double weight) {
        return statsModel.verifyWeight(weight);
    }

    public String updateSleep(Double sleep) {
        return statsModel.verifySleep(sleep);
    }

    public String updateCalories(int calories) {
        return statsModel.verifyCalories(calories);
    }

    public Account getAcc() {
        return acc;
    }
    public void setAcc(Account acc) {
        this.acc = acc;
    }

    public String getDailyMetric(String metric) {
        return statsModel.getDailyMetric(metric);
    }
}