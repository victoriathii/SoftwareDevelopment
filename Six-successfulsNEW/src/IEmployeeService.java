package src;
// defines the business‑logic operations our application supports

import java.util.List;

public interface IEmployeeService {
    boolean insertEmployee(Employee employee) throws Exception;
    List<Employee> searchEmployee(String searchInput) throws Exception;
    boolean updateEmployee(Employee employee) throws Exception;
    int updateSalaryRange(double percentage, double minSalary, double maxSalary) throws Exception;
}
