package Application.Utility.Objects;

import Application.Utility.Databases.*;

public class DatabaseManager {
    private static AccountsDB accountsDB;
    private static ExerciseDB exerciseDB;
    private static GoalsDB goalsDB;
    private static CreditCardDB creditCardDB;
    private static StatsDB statsDB;
    private static WorkoutDB workoutDB;
    private static MessagesDB messagesDB;
    private static ItemsDB itemsDB;
    private static FriendsDB friendsDB;
    private static BattlePassDB battlePassDB;
    private static WorkoutLogDB workoutLogDB;

    public static void initializeDatabases(){
        // Create instances of each database class
        accountsDB = new AccountsDB();
        exerciseDB = new ExerciseDB();
        creditCardDB = new CreditCardDB();
        goalsDB = new GoalsDB();
        statsDB = new StatsDB();
        workoutDB = new WorkoutDB();
        messagesDB = new MessagesDB();
        itemsDB = new ItemsDB();

        itemsDB.createDefaultRocketItems();
        itemsDB.giveDefaultRocketToAllUsers();
        friendsDB = new FriendsDB();
        battlePassDB = new BattlePassDB();
        workoutLogDB = new WorkoutLogDB();
        //battlePassDB.insertMockData();
    }

    public static AccountsDB getAccountsDB() {
        if (accountsDB == null) {
            accountsDB = new AccountsDB();
        }
        return accountsDB;
    }

    public static MessagesDB getMessagesDB() {
        if (messagesDB == null) {
            messagesDB = new MessagesDB();
        }
        return messagesDB;
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

    public static BattlePassDB getBattlePassDB() {
        if (battlePassDB == null) {
            battlePassDB = new BattlePassDB();
        }
        return battlePassDB;
    }

    public static WorkoutLogDB getWorkoutLogDB() {
        if(workoutDB == null) {
            workoutLogDB = new WorkoutLogDB();
        }
        return workoutLogDB;
    }
}
