package src;

import java.sql.Connection;
import java.sql.Statement;

public class SetupDatabase {

    public static void initialize() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS employees (
                    empId INTEGER PRIMARY KEY AUTOINCREMENT,
                    firstName TEXT NOT NULL,
                    lastName TEXT NOT NULL,
                    ssn TEXT NOT NULL,
                    jobTitle TEXT NOT NULL,
                    division TEXT NOT NULL,
                    salary REAL NOT NULL,
                    address TEXT,
                    hireDate TEXT NOT NULL
                );
            """);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
