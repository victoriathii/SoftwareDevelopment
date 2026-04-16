import java.util.List;

public interface IReportService {
    List<String> generateEmployeePayHistory(int empId) throws Exception;
    List<String> generateMonthlyPayByJobTitle(int month, int year) throws Exception;
    List<String> generateMonthlyPayByDivision(int month, int year) throws Exception;
}
