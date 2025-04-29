package Utility.DatabaseTests;

import Application.Databases.*;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DatabaseManagerTest {

    @BeforeEach
    public void setUp() throws Exception {
        resetDatabaseManager();
    }

    @AfterEach
    public void tearDown() throws Exception {
        resetDatabaseManager();
    }

    // Interesting way to reset I found
    private void resetDatabaseManager() throws Exception {
        Class<?> managerClass = DatabaseManager.class;

        Field accountsDBField = managerClass.getDeclaredField("accountsDB");
        accountsDBField.setAccessible(true);
        accountsDBField.set(null, null);

        Field exerciseDBField = managerClass.getDeclaredField("exerciseDB");
        exerciseDBField.setAccessible(true);
        exerciseDBField.set(null, null);

        Field goalsDBField = managerClass.getDeclaredField("goalsDB");
        goalsDBField.setAccessible(true);
        goalsDBField.set(null, null);

        Field creditCardDBField = managerClass.getDeclaredField("creditCardDB");
        creditCardDBField.setAccessible(true);
        creditCardDBField.set(null, null);

        Field statsDBField = managerClass.getDeclaredField("statsDB");
        statsDBField.setAccessible(true);
        statsDBField.set(null, null);

        Field workoutDBField = managerClass.getDeclaredField("workoutDB");
        workoutDBField.setAccessible(true);
        workoutDBField.set(null, null);

        Field messagesDBField = managerClass.getDeclaredField("messagesDB");
        messagesDBField.setAccessible(true);
        messagesDBField.set(null, null);

        Field itemsDBField = managerClass.getDeclaredField("itemsDB");
        itemsDBField.setAccessible(true);
        itemsDBField.set(null, null);

        Field friendsDBField = managerClass.getDeclaredField("friendsDB");
        friendsDBField.setAccessible(true);
        friendsDBField.set(null, null);

        Field battlePassDBField = managerClass.getDeclaredField("battlePassDB");
        battlePassDBField.setAccessible(true);
        battlePassDBField.set(null, null);

        Field workoutLogDBField = managerClass.getDeclaredField("workoutLogDB");
        workoutLogDBField.setAccessible(true);
        workoutLogDBField.set(null, null);
    }

    @Test
    public void testInitializeDatabases() throws Exception {
        assertDatabaseFieldIsNull("accountsDB");
        assertDatabaseFieldIsNull("exerciseDB");
        assertDatabaseFieldIsNull("creditCardDB");

        DatabaseManager.initializeDatabases();

        assertNotNull(DatabaseManager.getAccountsDB());
        assertNotNull(DatabaseManager.getExerciseDB());
        assertNotNull(DatabaseManager.getGoalsDB());
        assertNotNull(DatabaseManager.getCreditCardDB());
        assertNotNull(DatabaseManager.getStatsDB());
        assertNotNull(DatabaseManager.getWorkoutDB());
        assertNotNull(DatabaseManager.getMessagesDB());
        assertNotNull(DatabaseManager.getItemsDB());
        assertNotNull(DatabaseManager.getFriendsDB());
        assertNotNull(DatabaseManager.getBattlePassDB());
        assertNotNull(DatabaseManager.getWorkoutLogDB());
    }

    private void assertDatabaseFieldIsNull(String fieldName) throws Exception {
        Field field = DatabaseManager.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        assertNull(field.get(null), fieldName + " should be null before initialization");
    }

    @Test
    public void testGetAccountsDB() {
        AccountsDB db1 = DatabaseManager.getAccountsDB();
        assertNotNull(db1);

        AccountsDB db2 = DatabaseManager.getAccountsDB();
        assertNotNull(db2);
        assertSame(db1, db2);
    }

    @Test
    public void testGetExerciseDB() {
        ExerciseDB db1 = DatabaseManager.getExerciseDB();
        assertNotNull(db1);

        ExerciseDB db2 = DatabaseManager.getExerciseDB();
        assertNotNull(db2);
        assertSame(db1, db2);
    }

    @Test
    public void testGetGoalsDB() {
        GoalsDB db1 = DatabaseManager.getGoalsDB();
        assertNotNull(db1);

        GoalsDB db2 = DatabaseManager.getGoalsDB();
        assertNotNull(db2);
        assertSame(db1, db2);
    }

    @Test
    public void testGetCreditCardDB() {
        CreditCardDB db1 = DatabaseManager.getCreditCardDB();
        assertNotNull(db1);

        CreditCardDB db2 = DatabaseManager.getCreditCardDB();
        assertNotNull(db2);
        assertSame(db1, db2);
    }

    @Test
    public void testGetStatsDB() {
        StatsDB db1 = DatabaseManager.getStatsDB();
        assertNotNull(db1);

        StatsDB db2 = DatabaseManager.getStatsDB();
        assertNotNull(db2);
        assertSame(db1, db2);
    }

    @Test
    public void testGetWorkoutDB() {
        WorkoutDB db1 = DatabaseManager.getWorkoutDB();
        assertNotNull(db1);

        WorkoutDB db2 = DatabaseManager.getWorkoutDB();
        assertNotNull(db2);
        assertSame(db1, db2);
    }

    @Test
    public void testGetMessagesDB() {
        MessagesDB db1 = DatabaseManager.getMessagesDB();
        assertNotNull(db1);

        MessagesDB db2 = DatabaseManager.getMessagesDB();
        assertNotNull(db2);
        assertSame(db1, db2);
    }

    @Test
    public void testGetItemsDB() {
        ItemsDB db1 = DatabaseManager.getItemsDB();
        assertNotNull(db1);

        ItemsDB db2 = DatabaseManager.getItemsDB();
        assertNotNull(db2);
        assertSame(db1, db2);
    }

    @Test
    public void testGetFriendsDB() {
        FriendsDB db1 = DatabaseManager.getFriendsDB();
        assertNotNull(db1);

        FriendsDB db2 = DatabaseManager.getFriendsDB();
        assertNotNull(db2);
        assertSame(db1, db2);
    }

    @Test
    public void testGetBattlePassDB() {
        BattlePassDB db1 = DatabaseManager.getBattlePassDB();
        assertNotNull(db1);

        BattlePassDB db2 = DatabaseManager.getBattlePassDB();
        assertNotNull(db2);
        assertSame(db1, db2);
    }

    @Test
    public void testInitializationWithoutErrors() {
        assertDoesNotThrow(() -> DatabaseManager.initializeDatabases());
    }

}