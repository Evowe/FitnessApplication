package fitness.app.Goals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Goal {
    String associatedUsername;
    String Type;
    Integer Value;
    Date date;
    Boolean completed = false;

    public Goal(String associatedUsername, String Type, Integer Value, Date date, Boolean completed) {
        this.associatedUsername = associatedUsername;
        this.Type = Type;
        this.Value = Value;
        this.date = date;
        this.completed = completed;
    }

    public Goal(String associatedUsername, String Type, Integer Value, String dateString, Boolean completed) throws ParseException {
        this.associatedUsername = associatedUsername;
        this.Type = Type;
        this.Value = Value;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        this.date = dateFormat.parse(dateString);

        this.completed = completed;
    }

    private Date convertStringToDate(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            dateFormat.setLenient(false);
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            System.err.println("Error parsing date: " + dateString + ". " + e.getMessage());
            return new Date();
        }
    }
    public String getDateString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(this.date);
    }



    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Integer getValue() {
        return Value;
    }

    public void setValue(Integer value) {
        Value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDate(String dateString) {
        this.date = convertStringToDate(dateString);
    }
    public Boolean getCompleted() {
        return completed;
    }
    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
