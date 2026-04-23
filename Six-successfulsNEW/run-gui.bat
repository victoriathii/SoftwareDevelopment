@echo off
REM Employee Management System - JavaFX GUI Runner
REM Make sure JAVAFX_HOME environment variable is set to your JavaFX SDK directory

if "%JAVAFX_HOME%"=="" (
    echo Error: JAVAFX_HOME environment variable is not set.
    echo Please download JavaFX SDK from https://gluonhq.com/products/javafx/
    echo and set JAVAFX_HOME to the extracted directory.
    pause
    exit /b 1
)

echo Compiling Java files...
javac --module-path "%JAVAFX_HOME%\lib" --add-modules javafx.controls,javafx.fxml -cp . src\*.java

if %errorlevel% neq 0 (
    echo Compilation failed.
    pause
    exit /b 1
)

echo Running Employee Management GUI...
java --module-path "%JAVAFX_HOME%\lib" --add-modules javafx.controls,javafx.fxml -cp . src.EmployeeManagementGUI

pause