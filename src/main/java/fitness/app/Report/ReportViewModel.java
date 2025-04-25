package fitness.app.Report;

import fitness.app.Objects.Account;
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
private Account acc;
ReportViewModel(Account acc){
    this.acc = acc;
    this.reportModel = new ReportModel(acc);
}
    public  Object [][] getWorkouts() {
        return reportModel.getWorkouts();
    }

    public Object [] getColumns(){
        return reportModel.getColumns();
    }
    public void addWorkout(Workout w,String username) throws SQLException {
        reportModel.addWorkout(w,username);
    }

}




