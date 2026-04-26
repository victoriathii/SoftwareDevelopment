package src;

import java.time.LocalDate;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EmployeeManagementGUI extends Application {

    private IEmployeeService employeeService;
    private IReportService reportService;

    // UI Components
    private TabPane tabPane;
    private TableView<Employee> employeeTable;
    private TextArea reportArea;

    @Override
    public void start(Stage primaryStage) {
        // Initialize services
        IEmployeeDAO employeeDAO = new EmployeeDAO();
        employeeService = new EmployeeService(employeeDAO);

        IReportDAO reportDAO = new ReportDAO();
        reportService = new ReportService(reportDAO);

        // Create main layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(createMenuBar());
        mainLayout.setCenter(createTabPane());

        // Create scene
        Scene scene = new Scene(mainLayout, 1000, 700);
        primaryStage.setTitle("Employee Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> System.exit(0));
        fileMenu.getItems().add(exitItem);

        Menu helpMenu = new Menu("Help");
        MenuItem aboutItem = new MenuItem("About");
        aboutItem.setOnAction(e -> showAboutDialog());
        helpMenu.getItems().add(aboutItem);

        menuBar.getMenus().addAll(fileMenu, helpMenu);
        return menuBar;
    }

    private TabPane createTabPane() {
        tabPane = new TabPane();

        // Employee Management Tab
        Tab employeeTab = new Tab("Employee Management", createEmployeeManagementPane());
        employeeTab.setClosable(false);

        // Reports Tab
        Tab reportsTab = new Tab("Reports", createReportsPane());
        reportsTab.setClosable(false);

        tabPane.getTabs().addAll(employeeTab, reportsTab);
        return tabPane;
    }

    private VBox createEmployeeManagementPane() {
        VBox pane = new VBox(10);
        pane.setPadding(new Insets(20));

        // Employee Table
        employeeTable = new TableView<>();
        setupEmployeeTable();

        // Buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        Button addButton = new Button("Add Employee");
        addButton.setOnAction(e -> showAddEmployeeDialog());

        Button searchButton = new Button("Search Employee");
        searchButton.setOnAction(e -> showSearchEmployeeDialog());

        Button updateButton = new Button("Update Employee");
        updateButton.setOnAction(e -> showUpdateEmployeeDialog());

        Button salaryUpdateButton = new Button("Update Salary Range");
        salaryUpdateButton.setOnAction(e -> showSalaryUpdateDialog());

        Button deleteButton = new Button("Delete Employee");
        deleteButton.setOnAction(e -> showDeleteEmployeeDialog());

        buttonBox.getChildren().addAll(addButton, searchButton, updateButton, salaryUpdateButton, deleteButton);

        // Refresh Button
        Button refreshButton = new Button("Refresh Table");
        refreshButton.setOnAction(e -> refreshEmployeeTable());

        HBox refreshBox = new HBox(refreshButton);
        refreshBox.setAlignment(Pos.CENTER);

        pane.getChildren().addAll(employeeTable, buttonBox, refreshBox);
        VBox.setVgrow(employeeTable, Priority.ALWAYS);

        return pane;
    }

    private void setupEmployeeTable() {
        TableColumn<Employee, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("empId"));
        idCol.setPrefWidth(50);

        TableColumn<Employee, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameCol.setPrefWidth(100);

        TableColumn<Employee, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameCol.setPrefWidth(100);

        TableColumn<Employee, String> ssnCol = new TableColumn<>("SSN");
        ssnCol.setCellValueFactory(new PropertyValueFactory<>("ssn"));
        ssnCol.setPrefWidth(100);

        TableColumn<Employee, String> jobTitleCol = new TableColumn<>("Job Title");
        jobTitleCol.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));
        jobTitleCol.setPrefWidth(120);

        TableColumn<Employee, String> divisionCol = new TableColumn<>("Division");
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
        divisionCol.setPrefWidth(100);

        TableColumn<Employee, Double> salaryCol = new TableColumn<>("Salary");
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        salaryCol.setPrefWidth(100);

        TableColumn<Employee, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressCol.setPrefWidth(150);

        TableColumn<Employee, LocalDate> hireDateCol = new TableColumn<>("Hire Date");
        hireDateCol.setCellValueFactory(new PropertyValueFactory<>("hireDate"));
        hireDateCol.setPrefWidth(100);

        employeeTable.getColumns().addAll(idCol, firstNameCol, lastNameCol, ssnCol,
                jobTitleCol, divisionCol, salaryCol, addressCol, hireDateCol);
        employeeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private VBox createReportsPane() {
        VBox pane = new VBox(10);
        pane.setPadding(new Insets(20));

        // Report controls
        HBox controlsBox = new HBox(10);
        controlsBox.setAlignment(Pos.CENTER);

        Label monthLabel = new Label("Month:");
        Spinner<Integer> monthSpinner = new Spinner<>(1, 12, LocalDate.now().getMonthValue());
        monthSpinner.setPrefWidth(70);

        Label yearLabel = new Label("Year:");
        Spinner<Integer> yearSpinner = new Spinner<>(2000, 2030, LocalDate.now().getYear());
        yearSpinner.setPrefWidth(80);

        Button payHistoryButton = new Button("Employee Pay History");
        payHistoryButton.setOnAction(e -> showPayHistoryDialog());

        Button jobTitleReportButton = new Button("Monthly Pay by Job Title");
        jobTitleReportButton.setOnAction(e -> generateJobTitleReport(monthSpinner.getValue(), yearSpinner.getValue()));

        Button divisionReportButton = new Button("Monthly Pay by Division");
        divisionReportButton.setOnAction(e -> generateDivisionReport(monthSpinner.getValue(), yearSpinner.getValue()));

        controlsBox.getChildren().addAll(monthLabel, monthSpinner, yearLabel, yearSpinner,
                payHistoryButton, jobTitleReportButton, divisionReportButton);

        // Report display area
        reportArea = new TextArea();
        reportArea.setEditable(false);
        reportArea.setPrefRowCount(20);

        pane.getChildren().addAll(controlsBox, reportArea);
        VBox.setVgrow(reportArea, Priority.ALWAYS);

        return pane;
    }

    private void showAddEmployeeDialog() {
        Dialog<Employee> dialog = new Dialog<>();
        dialog.setTitle("Add New Employee");
        dialog.setHeaderText("Enter employee details:");

        // Create form fields
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");

        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");

        TextField ssnField = new TextField();
        ssnField.setPromptText("SSN");

        TextField jobTitleField = new TextField();
        jobTitleField.setPromptText("Job Title");

        TextField divisionField = new TextField();
        divisionField.setPromptText("Division");

        TextField salaryField = new TextField();
        salaryField.setPromptText("Salary");

        TextField addressField = new TextField();
        addressField.setPromptText("Address");

        DatePicker hireDatePicker = new DatePicker();
        hireDatePicker.setValue(LocalDate.now());

        // Layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label("First Name:"), 0, 0);
        grid.add(firstNameField, 1, 0);
        grid.add(new Label("Last Name:"), 0, 1);
        grid.add(lastNameField, 1, 1);
        grid.add(new Label("SSN:"), 0, 2);
        grid.add(ssnField, 1, 2);
        grid.add(new Label("Job Title:"), 0, 3);
        grid.add(jobTitleField, 1, 3);
        grid.add(new Label("Division:"), 0, 4);
        grid.add(divisionField, 1, 4);
        grid.add(new Label("Salary:"), 0, 5);
        grid.add(salaryField, 1, 5);
        grid.add(new Label("Address:"), 0, 6);
        grid.add(addressField, 1, 6);
        grid.add(new Label("Hire Date:"), 0, 7);
        grid.add(hireDatePicker, 1, 7);

        dialog.getDialogPane().setContent(grid);

        // Buttons
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                try {
                    String firstName = firstNameField.getText();
                    String lastName = lastNameField.getText();
                    String ssn = ssnField.getText();
                    String jobTitle = jobTitleField.getText();
                    String division = divisionField.getText();
                    double salary = Double.parseDouble(salaryField.getText());
                    String address = addressField.getText();
                    LocalDate hireDate = hireDatePicker.getValue();

                    Employee employee = new Employee(firstName, lastName, ssn, jobTitle,
                            division, salary, address, hireDate);

                    boolean success = employeeService.insertEmployee(employee);
                    if (success) {
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Employee added successfully!");
                        refreshEmployeeTable();
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Error", "Failed to add employee.");
                    }
                } catch (Exception ex) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Invalid input: " + ex.getMessage());
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void showSearchEmployeeDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Search Employee");
        dialog.setHeaderText("Enter search criteria:");
        dialog.setContentText("Name, SSN, or Job Title:");

        dialog.showAndWait().ifPresent(searchInput -> {
            try {
                List<Employee> results = employeeService.searchEmployee(searchInput);
                if (results.isEmpty()) {
                    showAlert(Alert.AlertType.INFORMATION, "No Results", "No employees found matching: " + searchInput);
                } else {
                    ObservableList<Employee> data = FXCollections.observableArrayList(results);
                    employeeTable.setItems(data);
                }
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Search failed: " + ex.getMessage());
            }
        });
    }

    private void showUpdateEmployeeDialog() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an employee to update.");
            return;
        }

        Dialog<Employee> dialog = new Dialog<>();
        dialog.setTitle("Update Employee");
        dialog.setHeaderText("Update employee details:");

        // Pre-fill fields with current data
        TextField firstNameField = new TextField(selectedEmployee.getFirstName());
        TextField lastNameField = new TextField(selectedEmployee.getLastName());
        TextField ssnField = new TextField(selectedEmployee.getSsn());
        TextField jobTitleField = new TextField(selectedEmployee.getJobTitle());
        TextField divisionField = new TextField(selectedEmployee.getDivision());
        TextField salaryField = new TextField(String.valueOf(selectedEmployee.getSalary()));
        TextField addressField = new TextField(selectedEmployee.getAddress());
        DatePicker hireDatePicker = new DatePicker(selectedEmployee.getHireDate());

        // Layout (same as add dialog)
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label("First Name:"), 0, 0);
        grid.add(firstNameField, 1, 0);
        grid.add(new Label("Last Name:"), 0, 1);
        grid.add(lastNameField, 1, 1);
        grid.add(new Label("SSN:"), 0, 2);
        grid.add(ssnField, 1, 2);
        grid.add(new Label("Job Title:"), 0, 3);
        grid.add(jobTitleField, 1, 3);
        grid.add(new Label("Division:"), 0, 4);
        grid.add(divisionField, 1, 4);
        grid.add(new Label("Salary:"), 0, 5);
        grid.add(salaryField, 1, 5);
        grid.add(new Label("Address:"), 0, 6);
        grid.add(addressField, 1, 6);
        grid.add(new Label("Hire Date:"), 0, 7);
        grid.add(hireDatePicker, 1, 7);

        dialog.getDialogPane().setContent(grid);

        ButtonType updateButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButtonType) {
                try {
                    selectedEmployee.setFirstName(firstNameField.getText());
                    selectedEmployee.setLastName(lastNameField.getText());
                    selectedEmployee.setSsn(ssnField.getText());
                    selectedEmployee.setJobTitle(jobTitleField.getText());
                    selectedEmployee.setDivision(divisionField.getText());
                    selectedEmployee.setSalary(Double.parseDouble(salaryField.getText()));
                    selectedEmployee.setAddress(addressField.getText());
                    selectedEmployee.setHireDate(hireDatePicker.getValue());

                    boolean success = employeeService.updateEmployee(selectedEmployee);
                    if (success) {
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Employee updated successfully!");
                        refreshEmployeeTable();
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Error", "Failed to update employee.");
                    }
                } catch (Exception ex) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Invalid input: " + ex.getMessage());
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void showSalaryUpdateDialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Update Salary Range");
        dialog.setHeaderText("Update salaries by percentage for a salary range:");

        TextField percentageField = new TextField();
        percentageField.setPromptText("Percentage (e.g., 10.5)");

        TextField minSalaryField = new TextField();
        minSalaryField.setPromptText("Minimum Salary");

        TextField maxSalaryField = new TextField();
        maxSalaryField.setPromptText("Maximum Salary");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label("Percentage:"), 0, 0);
        grid.add(percentageField, 1, 0);
        grid.add(new Label("Min Salary:"), 0, 1);
        grid.add(minSalaryField, 1, 1);
        grid.add(new Label("Max Salary:"), 0, 2);
        grid.add(maxSalaryField, 1, 2);

        dialog.getDialogPane().setContent(grid);

        ButtonType updateButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButtonType) {
                try {
                    double percentage = Double.parseDouble(percentageField.getText());
                    double minSalary = Double.parseDouble(minSalaryField.getText());
                    double maxSalary = Double.parseDouble(maxSalaryField.getText());

                    int updatedCount = employeeService.updateSalaryRange(percentage, minSalary, maxSalary);
                    showAlert(Alert.AlertType.INFORMATION, "Success",
                            "Updated salaries for " + updatedCount + " employees.");
                    refreshEmployeeTable();
                } catch (Exception ex) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Invalid input: " + ex.getMessage());
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void showDeleteEmployeeDialog() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
    
        if (selectedEmployee == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an employee to delete.");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Deletion");
        confirmAlert.setHeaderText("Delete Employee: " + selectedEmployee.getFirstName() + " " + selectedEmployee.getLastName());
        confirmAlert.setContentText("Are you sure? This action cannot be undone.");

        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    boolean success = employeeService.deleteEmployee(selectedEmployee.getEmpId());
                    if (success) {
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Employee deleted successfully!");
                        refreshEmployeeTable(); // Make sure this method fetches data now!
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete employee from database.");
                    }
                } catch (Exception ex) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Deletion failed: " + ex.getMessage());
                }
            }
        });
    }

    private void showPayHistoryDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Employee Pay History");
        dialog.setHeaderText("Enter Employee ID:");
        dialog.setContentText("Employee ID:");

        dialog.showAndWait().ifPresent(empIdStr -> {
            try {
                int empId = Integer.parseInt(empIdStr);
                List<String> history = reportService.generateEmployeePayHistory(empId);
                StringBuilder sb = new StringBuilder();
                sb.append("Pay History for Employee ID: ").append(empId).append("\n\n");
                for (String entry : history) {
                    sb.append(entry).append("\n");
                }
                reportArea.setText(sb.toString());
                tabPane.getSelectionModel().select(1); // Switch to reports tab
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to generate report: " + ex.getMessage());
            }
        });
    }

    private void generateJobTitleReport(int month, int year) {
        try {
            List<String> report = reportService.generateMonthlyPayByJobTitle(month, year);
            StringBuilder sb = new StringBuilder();
            sb.append("Monthly Pay by Job Title - ").append(month).append("/").append(year).append("\n\n");
            for (String entry : report) {
                sb.append(entry).append("\n");
            }
            reportArea.setText(sb.toString());
            tabPane.getSelectionModel().select(1); // Switch to reports tab
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to generate report: " + ex.getMessage());
        }
    }

    private void generateDivisionReport(int month, int year) {
        try {
            List<String> report = reportService.generateMonthlyPayByDivision(month, year);
            StringBuilder sb = new StringBuilder();
            sb.append("Monthly Pay by Division - ").append(month).append("/").append(year).append("\n\n");
            for (String entry : report) {
                sb.append(entry).append("\n");
            }
            reportArea.setText(sb.toString());
            tabPane.getSelectionModel().select(1); // Switch to reports tab
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to generate report: " + ex.getMessage());
        }
    }

    private void refreshEmployeeTable() {
        try {
            // Fetch all employees (using an empty search string)
            List<Employee> allEmployees = employeeService.searchEmployee(""); 
            employeeTable.setItems(FXCollections.observableArrayList(allEmployees));
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to refresh table: " + ex.getMessage());
        }
    }

    private void showAboutDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Employee Management System");
        alert.setContentText("A JavaFX application for managing employee records and generating reports.");
        alert.showAndWait();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}