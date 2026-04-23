package src;
// UI layer of application
// responsible for:
// - displaying the menu and prompts to the user
// - reading user input
// - calling the appropriate service methods based on user choices

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;



public class Main {

    public static void main(String[] args) {

        Scanner inputDevice = new Scanner(System.in);

        IEmployeeDAO employeeDAO = new EmployeeDAO();
        IEmployeeService employeeService = new EmployeeService(employeeDAO);

        IReportDAO reportDAO = new ReportDAO();
        IReportService reportService = new ReportService(reportDAO);

        while (true) {
            System.out.println("\n===== EMPLOYEE MANAGEMENT SYSTEM =====");
            System.out.println("1. Insert Employee");
            System.out.println("2. Search Employee");
            System.out.println("3. Update Employee");
            System.out.println("4. Update Salary by Percentage (Range)");
            System.out.println("5. Employee Pay History Report");
            System.out.println("6. Monthly Pay by Job Title");
            System.out.println("7. Monthly Pay by Division");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");

            String choice = inputDevice.nextLine();

            switch (choice) {

                case "1":
                    insertEmployee(inputDevice, employeeService);
                    break;

                case "2":
                    searchEmployee(inputDevice, employeeService);
                    break;

                case "3":
                    updateEmployee(inputDevice, employeeService);
                    break;

                case "4":
                    updateSalaryRange(inputDevice, employeeService);
                    break;

                case "5":
                    employeePayHistory(inputDevice, reportService);
                    break;

                case "6":
                    monthlyPayByJobTitle(inputDevice, reportService);
                    break;

                case "7":
                    monthlyPayByDivision(inputDevice, reportService);
                    break;

                case "8":
                    System.out.println("Exiting program...");
                    inputDevice.close();
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // ============================
    // INSERT EMPLOYEE (TASK 1)
    // ============================
    private static void insertEmployee(Scanner inputDevice, IEmployeeService employeeService) {
        try {
            System.out.print("Enter first name: ");
            String firstName = inputDevice.nextLine();

            System.out.print("Enter last name: ");
            String lastName = inputDevice.nextLine();

            System.out.print("Enter SSN (9 digits): ");
            String ssn = inputDevice.nextLine();

            System.out.print("Enter job title: ");
            String jobTitle = inputDevice.nextLine();

            System.out.print("Enter division: ");
            String division = inputDevice.nextLine();

            System.out.print("Enter salary: ");
            double salary = Double.parseDouble(inputDevice.nextLine());

            System.out.print("Enter address: ");
            String address = inputDevice.nextLine();

            System.out.print("Enter hire date (YYYY-MM-DD): ");
            LocalDate hireDate = LocalDate.parse(inputDevice.nextLine());

            Employee employee = new Employee(
                    firstName, lastName, ssn,
                    jobTitle, division, salary,
                    address, hireDate
            );

            boolean success = employeeService.insertEmployee(employee);

            if (success) {
                System.out.println("Employee inserted successfully.");
            } else {
                System.out.println("Failed to insert employee.");
            }

        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }

    // ============================
    // SEARCH EMPLOYEE (TASK 2)
    // ============================
    private static void searchEmployee(Scanner inputDevice, IEmployeeService employeeService) {
        try {
            System.out.print("Enter search term (name, SSN, or employee ID): ");
            String searchInput = inputDevice.nextLine();

            List<Employee> employees = employeeService.searchEmployee(searchInput);

            if (employees.isEmpty()) {
                System.out.println("No employees found.");
            } else {
                for (Employee emp : employees) {
                    System.out.println("---------------------------");
                    System.out.println("ID: " + emp.getEmpId());
                    System.out.println("Name: " + emp.getFirstName() + " " + emp.getLastName());
                    System.out.println("SSN: " + emp.getSsn());
                    System.out.println("Job Title: " + emp.getJobTitle());
                    System.out.println("Division: " + emp.getDivision());
                    System.out.println("Salary: " + emp.getSalary());
                    System.out.println("Address: " + emp.getAddress());
                    System.out.println("Hire Date: " + emp.getHireDate());
                }
            }

        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }

    // ============================
    // UPDATE EMPLOYEE (TASK 3)
    // ============================
    private static void updateEmployee(Scanner inputDevice, IEmployeeService employeeService) {
        try {
            System.out.print("Enter employee ID to update: ");
            String idInput = inputDevice.nextLine();

            List<Employee> found = employeeService.searchEmployee(idInput);

            if (found.isEmpty()) {
                System.out.println("Employee not found.");
                return;
            }

            Employee emp = found.get(0);

            System.out.println("Leave a field blank to skip updating it.");

            System.out.print("New address: ");
            String address = inputDevice.nextLine();
            if (!address.isBlank()) emp.setAddress(address);

            System.out.print("New job title: ");
            String jobTitle = inputDevice.nextLine();
            if (!jobTitle.isBlank()) emp.setJobTitle(jobTitle);

            System.out.print("New division: ");
            String division = inputDevice.nextLine();
            if (!division.isBlank()) emp.setDivision(division);

            System.out.print("New salary: ");
            String salaryInput = inputDevice.nextLine();
            if (!salaryInput.isBlank()) emp.setSalary(Double.parseDouble(salaryInput));

            boolean updated = employeeService.updateEmployee(emp);

            if (updated) {
                System.out.println("Employee updated successfully.");
            } else {
                System.out.println("Update failed.");
            }

        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }

    // ============================
    // UPDATE SALARY RANGE (TASK 4)
    // ============================
    private static void updateSalaryRange(Scanner inputDevice, IEmployeeService employeeService) {
        try {
            System.out.print("Enter percentage increase (e.g., 3.2): ");
            double percentage = Double.parseDouble(inputDevice.nextLine());

            System.out.print("Enter minimum salary: ");
            double minSalary = Double.parseDouble(inputDevice.nextLine());

            System.out.print("Enter maximum salary: ");
            double maxSalary = Double.parseDouble(inputDevice.nextLine());

            int updatedCount = employeeService.updateSalaryRange(percentage, minSalary, maxSalary);

            System.out.println(updatedCount + " employee(s) had their salary updated.");

        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }

    // ============================
    // REPORT 1 — PAY HISTORY (TASK 5)
    // ============================
    private static void employeePayHistory(Scanner inputDevice, IReportService reportService) {
        try {
            System.out.print("Enter employee ID: ");
            int empId = Integer.parseInt(inputDevice.nextLine());

            List<String> results = reportService.generateEmployeePayHistory(empId);

            if (results.isEmpty()) {
                System.out.println("No pay history found.");
            } else {
                System.out.println("\n=== PAY HISTORY ===");
                results.forEach(System.out::println);
            }

        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }

    // ============================
    // REPORT 2 — PAY BY JOB TITLE
    // ============================
    private static void monthlyPayByJobTitle(Scanner inputDevice, IReportService reportService) {
        try {
            System.out.print("Enter month (1-12): ");
            int month = Integer.parseInt(inputDevice.nextLine());

            System.out.print("Enter year: ");
            int year = Integer.parseInt(inputDevice.nextLine());

            List<String> results = reportService.generateMonthlyPayByJobTitle(month, year);

            if (results.isEmpty()) {
                System.out.println("No data for this month.");
            } else {
                System.out.println("\n=== MONTHLY PAY BY JOB TITLE ===");
                results.forEach(System.out::println);
            }

        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }

    // ============================
    // REPORT 3 — PAY BY DIVISION
    // ============================
    private static void monthlyPayByDivision(Scanner inputDevice, IReportService reportService) {
        try {
            System.out.print("Enter month (1-12): ");
            int month = Integer.parseInt(inputDevice.nextLine());

            System.out.print("Enter year: ");
            int year = Integer.parseInt(inputDevice.nextLine());

            List<String> results = reportService.generateMonthlyPayByDivision(month, year);

            if (results.isEmpty()) {
                System.out.println("No data for this month.");
            } else {
                System.out.println("\n=== MONTHLY PAY BY DIVISION ===");
                results.forEach(System.out::println);
            }

        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }
}
