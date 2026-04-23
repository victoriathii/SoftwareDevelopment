package src;
// service layer for all reporting operations
// responsible for:
// - validating input parameters for report generation
// - enforcing business rules related to reporting (e.g., valid month/year)
// - delegating report generation to the DAO and returning results to the UI
// - throw meaningful exceptions

import java.util.List;

public class ReportService implements IReportService {

    private final IReportDAO reportDAO;

    public ReportService(IReportDAO reportDAO) {
        this.reportDAO = reportDAO;
    }

    @Override
    public List<String> generateEmployeePayHistory(int empId) throws Exception {
        if (empId <= 0)
            throw new Exception("Invalid employee ID.");
        return reportDAO.getEmployeePayHistory(empId);
    }

    @Override
    public List<String> generateMonthlyPayByJobTitle(int month, int year) throws Exception {
        validateMonthYear(month, year);
        return reportDAO.getMonthlyPayByJobTitle(month, year);
    }

    @Override
    public List<String> generateMonthlyPayByDivision(int month, int year) throws Exception {
        validateMonthYear(month, year);
        return reportDAO.getMonthlyPayByDivision(month, year);
    }

    private void validateMonthYear(int month, int year) throws Exception {
        if (month < 1 || month > 12)
            throw new Exception("Month must be between 1 and 12.");
        if (year < 1900 || year > 2100)
            throw new Exception("Invalid year.");
    }
}
