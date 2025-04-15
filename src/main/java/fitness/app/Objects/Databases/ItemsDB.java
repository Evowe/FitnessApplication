package fitness.app.Objects.Databases;

import java.sql.*;

public class ItemsDB extends DBTemplate {

    public ItemsDB(){
        super("locker");
    }

    @Override
    protected void createTables() throws SQLException {
        String[] columns = {
                "username TEXT NOT NULL",
                "item_id INTEGER NOT NULL",
                "equipped BOOLEAN DEFAULT 0",
                "FOREIGN KEY (item_id) REFERENCES items(id)",
                "UNIQUE (username, item_id)"
        };
        createTable("Items", columns);

    }
}
