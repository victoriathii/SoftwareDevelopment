@echo off
REM Employee Management System - Run Script
REM Compiles and launches the JavaFX GUI

cd /d "%~dp0"

echo Compiling Java files...
javac --module-path "C:\javafx\javafx-sdk-22.0.1\lib" --add-modules javafx.controls,javafx.fxml -cp ".;lib/*" src\*.java -d .

if %errorlevel% neq 0 (
    echo Compilation failed.
    pause
    exit /b 1
)

echo Starting Employee Management GUI...
java --module-path "C:\javafx\javafx-sdk-22.0.1\lib" --add-modules javafx.controls,javafx.fxml -cp ".;lib/*" src.EmployeeManagementGUI

pause