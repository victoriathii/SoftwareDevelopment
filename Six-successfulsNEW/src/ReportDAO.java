package src;
// data access layer for all reporting features
// It implements IReportDAO and performs SQL queries that:
// - Retrieve employee pay history
// - Calculate monthly pay totals grouped by job title
// - Calculate monthly pay totals grouped by division

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO implements IReportDAO {

    @Override
    public List<String> getEmployeePayHistory(int empId) {
        List<String> results = new ArrayList<>();

        String sql = "SELECT e.empId, e.firstName, e.lastName, p.effectiveDate, "
                   + "p.oldSalary, p.newSalary, p.changeReason "
                   + "FROM employee e "
                   + "JOIN payhistory p ON e.empId = p.empId "
                   + "WHERE e.empId = ? "
                   + "ORDER BY p.effectiveDate DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, empId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String line = String.format(
                    "Date: %s | Old: %.2f | New: %.2f | Reason: %s",
                    rs.getDate("effectiveDate"),
                    rs.getDouble("oldSalary"),
                    rs.getDouble("newSalary"),
                    rs.getString("changeReason")
                );
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

        String sql = "SELECT jobTitle, SUM(salary) AS totalPay "
                   + "FROM employee "
                   + "WHERE MONTH(hireDate) <= ? AND YEAR(hireDate) <= ? "
                   + "GROUP BY jobTitle";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, month);
            stmt.setInt(2, year);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String line = String.format(
                    "Job Title: %s | Total Pay: %.2f",
                    rs.getString("jobTitle"),
                    rs.getDouble("totalPay")
                );
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

        String sql = "SELECT division, SUM(salary) AS totalPay "
                   + "FROM employee "
                   + "WHERE MONTH(hireDate) <= ? AND YEAR(hireDate) <= ? "
                   + "GROUP BY division";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, month);
            stmt.setInt(2, year);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String line = String.format(
                    "Division: %s | Total Pay: %.2f",
                    rs.getString("division"),
                    rs.getDouble("totalPay")
                );
                results.add(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }
}
