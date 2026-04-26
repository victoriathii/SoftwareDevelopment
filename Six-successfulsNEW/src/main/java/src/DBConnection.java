package src;

// Provides a single shared method for obtaining a SQLite database connection.
// Uses employeeData.db located in the project directory.

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:sqlite:employeeData.db";

    static {
        try {
            // Load SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver not found.");
            e.printStackTrace();
        }
    }

    // Returns a connection to the SQLite database
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Failed to connect to SQLite database.");
            e.printStackTrace();
            return null;
        }
    }
}
