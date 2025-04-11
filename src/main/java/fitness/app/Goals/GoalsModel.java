package fitness.app.Goals;

import fitness.app.Objects.DatabaseManager;

import java.sql.SQLException;
import java.util.Date;

public class GoalsModel {
    private String goal;
    private Date deadline;
    public void checkDB() throws SQLException {
        GoalsDB goalsDB = (GoalsDB) DatabaseManager.getDatabase("goals");

        if (goalsDB == null) {
            goalsDB = new GoalsDB("Goals");
            DatabaseManager.addDatabase("goals", goalsDB);
        }

    }
    public GoalsModel(String goal, Date deadline) {
        this.goal = goal;
        this.deadline = deadline;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getGoal() {
        return goal;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getDeadline() {
        return deadline;
    }

}
