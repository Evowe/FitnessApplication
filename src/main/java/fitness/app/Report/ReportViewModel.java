package fitness.app.Report;

import fitness.app.Objects.DatabaseManager;
import fitness.app.Objects.Databases.ExerciseDB;
import fitness.app.Objects.Databases.WorkoutDB;
import fitness.app.Objects.Exercise;
import fitness.app.Objects.Workout;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class ReportViewModel {
private ReportModel reportModel;
ReportViewModel(){
    this.reportModel = new ReportModel();
}
    public  Object [][] getWorkouts() {
        return reportModel.getWorkouts();
    }

    public Object [] getColumns(){
        return reportModel.getColumns();
    }

}




