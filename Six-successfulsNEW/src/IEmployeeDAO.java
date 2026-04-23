package src;
// interface that defines the contract for all database operations related to employees.

import java.util.List;

public interface IEmployeeDAO {
    boolean insertEmployee(Employee employee);
    List<Employee> searchEmployee(String searchInput);
    boolean updateEmployee(Employee employee);
    int updateSalaryRange(double percentage, double minSalary, double maxSalary);
}
