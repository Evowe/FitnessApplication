//import fitness.app.BadProjectStructureSection.Databases.DBTemplate;
//import fitness.app.BadProjectStructureSection.Objects.Workout;
//import fitness.app.BadProjectStructureSection.Databases.WorkoutDB;
//import org.junit.*;
//import java.sql.*;
//
//import static org.junit.Assert.*;
//
//public class WorkoutDBTest {
//
//    private static Connection conn;
//    private static Connection testConnection = null;
//    private static WorkoutDB workoutDB;
//    public void setTestConnection(Connection conn) {
//        testConnection = conn;
//    }
//
//    protected Connection getConnection() throws SQLException {
//        if (testConnection != null) {
//            return testConnection;
//        }
//        return DBTemplate.getConnection(); // your existing method
//    }
//    @BeforeClass
//    public static void setupDatabase() throws Exception {
//        conn = DriverManager.getConnection("jdbc:sqlite::memory:");
//        workoutDB = new WorkoutDB() {
//        };
//
//        Statement stmt = conn.createStatement();
//        stmt.execute("CREATE TABLE Workouts (" +
//                "WorkoutID INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                "Username VARCHAR(50), Name VARCHAR(100), Description VARCHAR(255), " +
//                "Duration INT, CaloriesBurned INT, Date VARCHAR(20), Exercises TEXT)");
//    }
//
//    @Before
//    public void resetTable() throws SQLException {
//        conn.createStatement().executeUpdate("DELETE FROM Workouts");
//    }
//
//    @Test
//    public void testAddWorkoutSuccess() throws Exception {
//        Workout workout = new Workout("Cardio Blast", "Intense cardio", 30, 300, "2025-04-22", "1");
//        int id = workoutDB.addWorkout(workout, "testuser");
//        assertTrue("Workout ID should be positive", id > 0);
//
//        // Verify in DB
//        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Workouts WHERE WorkoutID = ?");
//        stmt.setInt(1, id);
//        ResultSet rs = stmt.executeQuery();
//        assertTrue(rs.next());
//        assertEquals("testuser", rs.getString("Username"));
//        assertEquals("Cardio Blast", rs.getString("Name"));
//        assertEquals("Intense cardio", rs.getString("Description"));
//    }
//
//    @AfterClass
//    public static void tearDown() throws Exception {
//        conn.close();
//    }
//}
