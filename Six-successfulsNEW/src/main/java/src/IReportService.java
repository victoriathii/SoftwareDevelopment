package src;
// defines the business‑logic operations required to generate all three reports in
// Employee Management System
// - employee pay history
// - monthly pay by job title
// - monthly pay by division

import java.util.List;

public interface IReportService {
    List<String> generateEmployeePayHistory(int empId) throws Exception;
    List<String> generateMonthlyPayByJobTitle(int month, int year) throws Exception;
    List<String> generateMonthlyPayByDivision(int month, int year) throws Exception;
}
