package Application.Metrics.Report;

import Application.Utility.Objects.Account;
import Application.Utility.Objects.Workout;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportViewModel {
    private ReportModel reportModel;
    private Account acc;

    ReportViewModel(Account acc) {
        this.acc = acc;
        this.reportModel = new ReportModel(acc);
    }

    public Object[][] getWorkouts() {
        return reportModel.getWorkouts();
    }

    public Object[] getColumns() {
        return reportModel.getColumns();
    }

    public void addWorkout(Workout w, String username) {
        reportModel.addWorkout(w, username);
    }

    public Object[][] getWorkoutData() {
        return reportModel.getWorkoutData();
    }
}




