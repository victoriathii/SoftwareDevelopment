package src;

// Handles all reporting queries for the Employee Management System.
// Provides:
// - Employee pay history
// - Monthly pay grouped by job title
// - Monthly pay grouped by division

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO implements IReportDAO {

    @Override
    public List<String> getEmployeePayHistory(int empId) {
        List<String> results = new ArrayList<>();

        String sql = """
            SELECT hireDate, salary
            FROM employees
            WHERE empId = ?
            ORDER BY hireDate ASC
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, empId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String line = "Date: " + rs.getString("hireDate")
                            + " | Salary: $" + rs.getDouble("salary");
                results.add(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }

    @Override
    public List<String> getMonthlyPayByJobTitle(int month, int year) {
        List<String> results = new ArrayList<>();

        String sql = """
            SELECT jobTitle, SUM(salary) AS totalPay
            FROM employees
            WHERE strftime('%m', hireDate) = printf('%02d', ?)
              AND strftime('%Y', hireDate) = ?
            GROUP BY jobTitle
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, month);
            stmt.setString(2, String.valueOf(year));

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String line = rs.getString("jobTitle")
                             + " — Total Pay: $" + rs.getDouble("totalPay");
                results.add(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }

    @Override
    public List<String> getMonthlyPayByDivision(int month, int year) {
        List<String> results = new ArrayList<>();

        String sql = """
            SELECT division, SUM(salary) AS totalPay
            FROM employees
            WHERE strftime('%m', hireDate) = printf('%02d', ?)
              AND strftime('%Y', hireDate) = ?
            GROUP BY division
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, month);
            stmt.setString(2, String.valueOf(year));

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String line = rs.getString("division")
                             + " — Total Pay: $" + rs.getDouble("totalPay");
                results.add(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }
}
