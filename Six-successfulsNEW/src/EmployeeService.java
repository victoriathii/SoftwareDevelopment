package src;
// business logic layer of application
// responsible for:
// - validating employee data before insertion or updates
// - enforcing business rules (e.g., salary must be positive, SSN format)   
// - cleaning data (like SSN)
// - preventing invalid updates
// - delegating safe, validated data to the DAO

import java.util.List;

public class EmployeeService implements IEmployeeService {

    private final IEmployeeDAO employeeDAO;

    public EmployeeService(IEmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Override
    public boolean insertEmployee(Employee employee) throws Exception {

        if (employee.getFirstName() == null || employee.getFirstName().isBlank()) {
            throw new Exception("First name is required.");
        }

        if (employee.getLastName() == null || employee.getLastName().isBlank()) {
            throw new Exception("Last name is required.");
        }

        if (employee.getSsn() == null || employee.getSsn().isBlank()) {
            throw new Exception("SSN is required.");
        }

        if (employee.getJobTitle() == null || employee.getJobTitle().isBlank()) {
            throw new Exception("Job title is required.");
        }

        if (employee.getDivision() == null || employee.getDivision().isBlank()) {
            throw new Exception("Division is required.");
        }

        if (employee.getSalary() <= 0) {
            throw new Exception("Salary must be greater than zero.");
        }

        String cleanedSsn = employee.getSsn().replace("-", "").trim();

        if (!cleanedSsn.matches("\\d{9}")) {
            throw new Exception("SSN must contain exactly 9 digits.");
        }

        employee.setSsn(cleanedSsn);

        return employeeDAO.insertEmployee(employee);
    }

    @Override
    public List<Employee> searchEmployee(String searchInput) throws Exception {

        if (searchInput == null || searchInput.isBlank()) {
            throw new Exception("Search input cannot be empty.");
        }

        return employeeDAO.searchEmployee(searchInput.trim());
    }

    @Override
    public boolean updateEmployee(Employee employee) throws Exception {

        if (employee.getEmpId() <= 0) {
            throw new Exception("A valid employee ID is required for updates.");
        }

        if (employee.getSalary() < 0) {
            throw new Exception("Salary cannot be negative.");
        }

        if (employee.getSsn() != null) {
            String cleaned = employee.getSsn().replace("-", "").trim();
            if (!cleaned.matches("\\d{9}")) {
                throw new Exception("SSN must contain exactly 9 digits.");
            }
            employee.setSsn(cleaned);
        }

        return employeeDAO.updateEmployee(employee);
    }

    @Override
    public int updateSalaryRange(double percentage, double minSalary, double maxSalary) throws Exception {

        if (percentage <= 0) {
            throw new Exception("Percentage must be greater than zero.");
        }

        if (minSalary < 0 || maxSalary < 0) {
            throw new Exception("Salary range cannot be negative.");
        }

        if (minSalary >= maxSalary) {
            throw new Exception("Minimum salary must be less than maximum salary.");
        }

        return employeeDAO.updateSalaryRange(percentage, minSalary, maxSalary);
    }
}
