package fitness.app.Metrics.Report;

import fitness.app.Utility.Objects.Account;
import fitness.app.Utility.Objects.Workout;

import java.sql.SQLException;

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




