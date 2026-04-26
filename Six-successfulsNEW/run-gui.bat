@echo off
REM Employee Management System - JavaFX GUI Runner
setlocal EnableDelayedExpansion
cd /d "%~dp0"

if "%JAVAFX_HOME%"=="" (
    echo Error: JAVAFX_HOME environment variable is not set.
    pause
    exit /b 1
)
REM === Include all jars from lib folder (e.g., SQLite connector) ===
set "APP_CP=.;lib/*"

set "SRC_FILES="
for %%F in (src\*.java) do (
    set "SRC_FILES=!SRC_FILES! "%%F""
)

if "!SRC_FILES!"=="" (
    echo Error: No Java source files found in src.
    pause
    exit /b 1
)
REM SQLite database file will be created automatically

echo Compiling Java files...
javac --module-path "%JAVAFX_HOME%\lib" --add-modules javafx.controls,javafx.fxml ^
     -cp "%APP_CP%" !SRC_FILES!

if %errorlevel% neq 0 (
    echo Compilation failed.
    pause
    exit /b 1
)

echo Running Employee Management GUI...
java --module-path "%JAVAFX_HOME%\lib" --add-modules javafx.controls,javafx.fxml ^
     -cp "%APP_CP%" src.EmployeeManagementGUI

pause
