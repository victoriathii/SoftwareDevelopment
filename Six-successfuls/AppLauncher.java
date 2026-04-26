package src;

public class AppLauncher {
    public static void main(String[] args) {
        SetupDatabase.initialize();
        EmployeeManagementGUI.main(args);
    }
}