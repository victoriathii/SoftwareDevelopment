@echo off
echo Launching JavaFX GUI...

java ^
  --module-path "C:\javafx\javafx-sdk-22.0.1\lib" ^
  --add-modules javafx.controls,javafx.fxml ^
  -cp ".;lib/*" ^
  EmployeeManagementGUI

pause
