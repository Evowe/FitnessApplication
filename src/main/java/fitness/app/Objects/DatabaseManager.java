package fitness.app.Objects;

import fitness.app.Objects.Databases.*;

import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {
    private static AccountsDB accountsDB;
    private static ExerciseDB exerciseDB;
    private static GoalsDB goalsDB;
    private static CreditCardDB creditCardDB;
    private static StatsDB statsDB;
    private static WorkoutDB workoutDB;
    private static ItemsDB itemsDB;
    private static FriendsDB friendsDB;

    public static void initializeDatabases() {
        // Create instances of each database class
        accountsDB = new AccountsDB();
        exerciseDB = new ExerciseDB();
        creditCardDB = new CreditCardDB();
        goalsDB = new GoalsDB();
        statsDB = new StatsDB();
        workoutDB = new WorkoutDB();
        itemsDB = new ItemsDB();
        friendsDB = new FriendsDB();
    }

    public static AccountsDB getAccountsDB() {
        if (accountsDB == null) {
            accountsDB = new AccountsDB();
        }
        return accountsDB;
    }

    public static ExerciseDB getExerciseDB() {
        if (exerciseDB == null) {
            exerciseDB = new ExerciseDB();
        }
        return exerciseDB;
    }

    public static GoalsDB getGoalsDB() {
        if (goalsDB == null) {
            goalsDB = new GoalsDB();
        }
        return goalsDB;
    }

    public static CreditCardDB getCreditCardDB() {
        if (creditCardDB == null) {
            creditCardDB = new CreditCardDB();
        }
        return creditCardDB;
    }

    public static StatsDB getStatsDB() {
        if (statsDB == null) {
            statsDB = new StatsDB();
        }
        return statsDB;
    }

    public static WorkoutDB getWorkoutDB() {
        if (workoutDB == null) {
            workoutDB = new WorkoutDB();
        }
        return workoutDB;
    }

    public static ItemsDB getItemsDB() {
        if (itemsDB == null) {
            itemsDB = new ItemsDB();
        }
        return itemsDB;
    }

    public static FriendsDB getFriendsDB() {
        if (friendsDB == null) {
            friendsDB = new FriendsDB();
        }
        return friendsDB;
    }
}
