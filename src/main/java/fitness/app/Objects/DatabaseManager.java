package fitness.app.Objects;

import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {
    private static final Map<String, DBTemplate> databases = new HashMap<>();

    public static void addDatabase(String name, DBTemplate db) {
        databases.put(name, db);
    }

    public static DBTemplate getDatabase(String name) {
        return databases.get(name);
    }
}
