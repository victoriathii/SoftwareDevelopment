package src;
// creates a single reusable method (getConnection()) that
// opens a connection from Java program to MySQL database
// called employeeData.

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
        "jdbc:mysql://localhost:3306/employeeData?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String USER =
        System.getenv().getOrDefault("EMPLOYEE_DB_USER", "root");
    private static final String PASSWORD =
        System.getenv().getOrDefault("EMPLOYEE_DB_PASSWORD", "");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
