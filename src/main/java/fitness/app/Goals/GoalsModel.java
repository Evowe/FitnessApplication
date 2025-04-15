package fitness.app.Goals;

import com.kitfox.svg.SVGUniverse;
import com.kitfox.svg.app.beans.SVGIcon;
import fitness.app.Objects.DatabaseManager;
import fitness.app.Objects.Databases.GoalsDB;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.Date;

public class GoalsModel {
    private String goal;
    private Date deadline;
    private static SVGUniverse images = new SVGUniverse();
    private GoalsDB goalsDB = DatabaseManager.getGoalsDB();

    public static SVGIcon getIcon(String iconName) {
        String filePath = "src/main/resources/Icons/" + iconName + ".svg";
        FileInputStream inFS;

        try {
            inFS = new FileInputStream(new File(filePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        URI imageURI;
        try {
            imageURI = images.loadSVG(inFS, iconName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SVGIcon output = new SVGIcon();
        output.setSvgUniverse(images);
        output.setSvgURI(imageURI);
        output.setAntiAlias(true);
        output.setPreferredSize(new Dimension(32, 32));

        return output;
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
