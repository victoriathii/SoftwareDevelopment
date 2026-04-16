import java.util.List;

public interface IReportDAO {
    List<String> getEmployeePayHistory(int empId);
    List<String> getMonthlyPayByJobTitle(int month, int year);
    List<String> getMonthlyPayByDivision(int month, int year);
}
