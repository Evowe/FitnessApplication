package fitness.app.Model;

// Used as the base class for all other classes
// Will need a user class later, but waiting because
// it will be connected to many classes below it
public class Account {
    private String username;
    private String password;
    // status could be "Active", "Suspended", etc...
    private String status;
    // Admin , Trainer, User
    private String role;

    public Account(String username, String password, String status, String role) {
        this.username = username;
        this.password = password;
        this.status = status;
        this.role = role;
    }

    //Default constructor
    public Account() {
        this.username = "";
        this.password = "";
        this.status = "";
        this.role = "";
    }

    // Might want to make this into a controller / separate class with
    // knowledge of the database later.
    public boolean authenticate(String enteredPassword) {
        return enteredPassword.equals(password);
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
