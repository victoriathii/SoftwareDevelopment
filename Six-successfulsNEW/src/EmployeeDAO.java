package src;
// responsible for all database operations related to employees:
// - inserting new employee records
// - searching for employees by ID, name, or SSN
// - updating employee details
// - performing bulk salary updates based on specified criteria

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO implements IEmployeeDAO {

    @Override
    public boolean insertEmployee(Employee employee) {
        String sql = "INSERT INTO employee "
                   + "(firstName, lastName, ssn, jobTitle, division, salary, address, hireDate) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setString(3, employee.getSsn());
            statement.setString(4, employee.getJobTitle());
            statement.setString(5, employee.getDivision());
            statement.setDouble(6, employee.getSalary());
            statement.setString(7, employee.getAddress());
            statement.setDate(8, Date.valueOf(employee.getHireDate()));

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Employee> searchEmployee(String searchInput) {

        List<Employee> results = new ArrayList<>();
        String sql;

        if (searchInput.matches("\\d{9}")) {
            sql = "SELECT * FROM employee WHERE ssn = ?";
        } else if (searchInput.matches("\\d+")) {
            sql = "SELECT * FROM employee WHERE empId = ?";
        } else {
            sql = "SELECT * FROM employee WHERE firstName LIKE ? OR lastName LIKE ?";
        }

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            if (searchInput.matches("\\d{9}")) {
                statement.setString(1, searchInput);
            } else if (searchInput.matches("\\d+")) {
                statement.setInt(1, Integer.parseInt(searchInput));
            } else {
                String pattern = "%" + searchInput + "%";
                statement.setString(1, pattern);
                statement.setString(2, pattern);
            }

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmpId(rs.getInt("empId"));
                employee.setFirstName(rs.getString("firstName"));
                employee.setLastName(rs.getString("lastName"));
                employee.setSsn(rs.getString("ssn"));
                employee.setJobTitle(rs.getString("jobTitle"));
                employee.setDivision(rs.getString("division"));
                employee.setSalary(rs.getDouble("salary"));
                employee.setAddress(rs.getString("address"));
                employee.setHireDate(rs.getDate("hireDate").toLocalDate());

                results.add(employee);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return results;
    }

    @Override
    public boolean updateEmployee(Employee employee) {

        StringBuilder sql = new StringBuilder("UPDATE employee SET ");
        List<Object> params = new ArrayList<>();

        if (employee.getFirstName() != null) {
            sql.append("firstName = ?, ");
            params.add(employee.getFirstName());
        }

        if (employee.getLastName() != null) {
            sql.append("lastName = ?, ");
            params.add(employee.getLastName());
        }

        if (employee.getSsn() != null) {
            sql.append("ssn = ?, ");
            params.add(employee.getSsn());
        }

        if (employee.getJobTitle() != null) {
            sql.append("jobTitle = ?, ");
            params.add(employee.getJobTitle());
        }

        if (employee.getDivision() != null) {
            sql.append("division = ?, ");
            params.add(employee.getDivision());
        }

        if (employee.getSalary() > 0) {
            sql.append("salary = ?, ");
            params.add(employee.getSalary());
        }

        if (employee.getAddress() != null) {
            sql.append("address = ?, ");
            params.add(employee.getAddress());
        }

        if (employee.getHireDate() != null) {
            sql.append("hireDate = ?, ");
            params.add(Date.valueOf(employee.getHireDate()));
        }

        if (params.isEmpty()) {
            return false;
        }

        sql.setLength(sql.length() - 2);
        sql.append(" WHERE empId = ?");
        params.add(employee.getEmpId());

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                statement.setObject(i + 1, params.get(i));
            }

            return statement.executeUpdate() > 0;

        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    @Override
    public int updateSalaryRange(double percentage, double minSalary, double maxSalary) {

        String sql = "UPDATE employee "
                   + "SET salary = salary + (salary * ? / 100) "
                   + "WHERE salary >= ? AND salary < ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, percentage);
            statement.setDouble(2, minSalary);
            statement.setDouble(3, maxSalary);

            return statement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
            return 0;
        }
    }
}
