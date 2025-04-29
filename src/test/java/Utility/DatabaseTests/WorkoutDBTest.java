package Utility.DatabaseTests;

import Application.Databases.DatabaseManager;
import Application.Databases.WorkoutDB;
import Application.Utility.Objects.Account;
import Application.Utility.Objects.Workout;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WorkoutDBTest {

    private WorkoutDB WorkoutDB;
    private final String TEST_Workout_PREFIX = "test_workout_" + UUID.randomUUID().toString().substring(0, 8) + "_";

    @BeforeEach
    public void setUp() {
        WorkoutDB = DatabaseManager.getWorkoutDB();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        Connection conn = null;
        try {
            conn = WorkoutDB.getConnection();

            try (PreparedStatement pstmt = conn.prepareStatement(
                    "DELETE FROM Workouts WHERE Name LIKE ?")) {
                pstmt.setString(1, TEST_Workout_PREFIX + "%");
                pstmt.executeUpdate();
            }
        } finally {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
    }

    private String getTestWorkoutName(String baseName) {
        return TEST_Workout_PREFIX + baseName;
    }

    //THIS TEST ADD AND GET
    @Test
    public void addWorkoutANDGetWorkout() throws SQLException {
        String name = getTestWorkoutName("pushups");
        Date d = new Date();
        Workout testWorkout = new Workout(name,"doing stuff",5,5,"12","1,2,3,4" );
        Account a = new Account("Dob","Bob");
        int id = WorkoutDB.addWorkout(testWorkout, a.getUsername());
        assertTrue(id > 0, "Workout should be saved with valid ID");

          Workout retrievedWorkout = WorkoutDB.getWorkout("Dob", name);

          assertNotNull(retrievedWorkout);

    }
    @Test
    public void testGetAllWorkouts() throws SQLException {
        String name1 = getTestWorkoutName("Workout1");
        String name2 = getTestWorkoutName("Workout2");
        Account a = new Account("Dob","Bob");

        Workout testWorkout1 = new Workout(name1,"doing stuff",5,5,"12","1,2,3,4" );
        Workout testWorkout2 = new Workout(name2,"doing stuff",5,5,"12","1,2,3,4" );

        WorkoutDB.addWorkout(testWorkout1,a.getUsername());
        WorkoutDB.addWorkout(testWorkout2,a.getUsername());

        List<Workout> Workouts = WorkoutDB.getAllWorkouts(a.getUsername());

        assertNotNull(Workouts);
        assertTrue(Workouts.size() >= 2, "Should retrieve at least the two test Workouts");

        boolean foundWorkout1 = false;
        boolean foundWorkout2 = false;

        for (Workout Workout : Workouts) {
            if (Workout.getName().equals(name1)) {
                foundWorkout1 = true;
            }
            if (Workout.getName().equals(name2)) {
                foundWorkout2 = true;
            }
        }

        assertTrue(foundWorkout1, "Should find first test Workout");
        assertTrue(foundWorkout2, "Should find second test Workout");
    }
    @Test
    public void testDeleteWorkout() throws SQLException {
        String name = getTestWorkoutName("toDelete");
        Workout testWorkout1 = new Workout(name,"doing stuff",5,5,"12","1,2,3,4" );
        Account a = new Account("Dob","Bob");

        int id =WorkoutDB.addWorkout(testWorkout1,a.getUsername());

        assertTrue(id > 0);

        boolean deleted = WorkoutDB.deleteWorkout(a.getUsername(),name);
        assertTrue(deleted, "Workout should be deleted successfully");

        Workout retrievedWorkout = WorkoutDB.getWorkout(a.getUsername(),name);
        assertNull(retrievedWorkout, "Workout should not exist after deletion");
    }
    @Test
    public void testUpdateWorkout() throws SQLException
    {
        String name1 = getTestWorkoutName("toDelete");
        String name2 = getTestWorkoutName("toDelete");
        Workout testWorkout1 = new Workout(name1,"doing stuff",5,5,"12","1,2,3,4" );
        Workout testWorkout2 = new Workout(name2,"doing stuff",5,5,"12","1,2,3,4" );

        Account a = new Account("Dob","Bob");

        int id =WorkoutDB.addWorkout(testWorkout1,a.getUsername());
        assertTrue(id > 0);

        boolean b = WorkoutDB.updateWorkout(a.getUsername(),testWorkout2,name1);
        assertTrue(b);
        Workout retrievedWorkout = WorkoutDB.getWorkout(a.getUsername(),name2);
        assertNotNull(retrievedWorkout);
        assertTrue(retrievedWorkout.getName().equals(name2));
        assertTrue(retrievedWorkout.getDescription().equals(testWorkout2.getDescription()));

    }

}