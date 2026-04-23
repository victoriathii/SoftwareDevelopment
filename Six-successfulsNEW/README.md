# Employee Management System - JavaFX GUI

A JavaFX-based graphical user interface for the Employee Management System that provides an intuitive way to manage employee records and generate reports.

## Features

### Employee Management
- **Add Employee**: Create new employee records with all required information
- **Search Employee**: Find employees by name, SSN, or job title
- **Update Employee**: Modify existing employee information
- **Update Salary Range**: Apply percentage-based salary increases to employees within a salary range
- **View Employees**: Display all employees in a sortable table

### Reports
- **Employee Pay History**: View salary history for a specific employee
- **Monthly Pay by Job Title**: Generate reports showing total pay by job title for a specific month/year
- **Monthly Pay by Division**: Generate reports showing total pay by division for a specific month/year

## Prerequisites

- Java 17 or higher
- JavaFX 17+ (separate download required for Java 11+)
- MySQL database (configured in DBConnection.java)

## JavaFX Setup

Since Java 11, JavaFX is no longer included in the JDK. You have several options:

### Option 1: Use Maven (Recommended)
1. Install Maven from https://maven.apache.org/download.cgi
2. Run: `mvn clean javafx:run`

### Option 2: Download JavaFX SDK
1. Download JavaFX SDK from https://gluonhq.com/products/javafx/
2. Extract to a folder (e.g., `C:\javafx-sdk-17`)
3. Set environment variable: `set JAVAFX_HOME=C:\javafx-sdk-17`
4. Compile with: `javac --module-path %JAVAFX_HOME%\lib --add-modules javafx.controls,javafx.fxml -cp . src\*.java`
5. Run with: `java --module-path %JAVAFX_HOME%\lib --add-modules javafx.controls,javafx.fxml -cp . src.EmployeeManagementGUI`

### Option 3: Use an IDE
- Most IDEs (Eclipse, IntelliJ, VS Code) can be configured to include JavaFX libraries
- Add the JavaFX SDK to your project classpath

## Quick Start

### Run Console Version (No additional setup required)
Double-click `run-console.bat` or run:
```bash
run-console.bat
```

### Run GUI Version (Requires JavaFX setup)
1. Download JavaFX SDK from https://gluonhq.com/products/javafx/
2. Extract to a folder and set `JAVAFX_HOME` environment variable
3. Double-click `run-gui.bat` or run:
```bash
run-gui.bat
```

### Using Maven (Advanced)
If you have Maven installed:
```bash
mvn clean javafx:run
```

## GUI Overview

The application uses a tabbed interface:

### Employee Management Tab
- **Employee Table**: Displays employee data in a sortable table
- **Action Buttons**:
  - Add Employee: Opens a form dialog to create new employees
  - Search Employee: Opens a search dialog to find employees
  - Update Employee: Opens an update form for the selected employee
  - Update Salary Range: Opens a dialog to apply percentage increases to salary ranges
  - Refresh Table: Clears the current table view

### Reports Tab
- **Report Controls**: Select month and year for reports
- **Report Types**:
  - Employee Pay History: Enter employee ID to view their pay history
  - Monthly Pay by Job Title: Generate report for selected month/year
  - Monthly Pay by Division: Generate report for selected month/year
- **Report Display**: Text area showing the generated report

## Usage Tips

- Select an employee in the table before clicking "Update Employee"
- Use the search function to filter employees before updating
- Reports are displayed in the text area on the Reports tab
- All dialogs include input validation and error handling

## Architecture

The GUI application maintains the same service layer architecture as the console version:
- `EmployeeManagementGUI`: Main JavaFX application class
- `IEmployeeService` / `EmployeeService`: Business logic for employee operations
- `IReportService` / `ReportService`: Business logic for report generation
- `IEmployeeDAO` / `EmployeeDAO`: Data access layer for employees
- `IReportDAO` / `ReportDAO`: Data access layer for reports
- `DBConnection`: Database connection management

## Project Structure

```
├── src/
│   ├── EmployeeManagementGUI.java    # Main JavaFX GUI application
│   ├── Main.java                      # Console version main class
│   ├── Employee.java                  # Employee data model
│   ├── DBConnection.java              # Database connection utility
│   ├── EmployeeDAO.java               # Employee data access object
│   ├── EmployeeService.java           # Employee business logic
│   ├── ReportDAO.java                 # Report data access object
│   ├── ReportService.java             # Report business logic
│   ├── IEmployeeDAO.java              # Employee DAO interface
│   ├── IEmployeeService.java          # Employee service interface
│   ├── IReportDAO.java                # Report DAO interface
│   ├── IReportService.java            # Report service interface
│   └── InputReader.java               # Input utility for console version
├── pom.xml                            # Maven configuration
├── README.md                          # This file
├── run-console.bat                    # Batch file to run console version
├── run-gui.bat                        # Batch file to run GUI version
└── Script-5.sql                       # Database schema
```

## GUI Features Overview

The JavaFX GUI provides:

- **Tabbed Interface**: Separate tabs for employee management and reports
- **Employee Table**: Sortable table displaying all employee information
- **Dialog-Based Forms**: User-friendly forms for adding/updating employees
- **Search Functionality**: Quick search with results displayed in the table
- **Report Generation**: Generate various reports with date selection
- **Error Handling**: Comprehensive error messages and validation
- **Responsive Design**: Resizable window with proper layout management

## Notes

- The GUI version maintains the same business logic as the console version
- All database operations are identical between GUI and console versions
- The GUI provides a more user-friendly interface while preserving all functionality
- Console version is available as a fallback if JavaFX setup is not desired