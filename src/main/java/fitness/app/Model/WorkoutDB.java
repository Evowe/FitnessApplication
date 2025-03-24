package fitness.app.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class WorkoutDB {
	void createTable() {
		//db url
		String url = "jdbc:sqlite:workouts.db";
		
		//create connection with sqlite db
		try (Connection conn = DriverManager.getConnection(url);
	             Statement statement = conn.createStatement()) {
			
			//creates table for workouts
			statement.execute(
					"CREATE TABLE IF NOT EXISTS Books ("
			                + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
			                + "Title TEXT NOT NULL,"
			                + "Author TEXT NOT NULL,"
			                + "Year INTEGER"
			                + ");"
					);
			
		} catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
}
