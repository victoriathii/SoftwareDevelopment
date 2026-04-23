@echo off
REM Employee Management System - Console Version Runner

echo Compiling Java files...
cd src
javac *.java

if %errorlevel% neq 0 (
    echo Compilation failed.
    cd ..
    pause
    exit /b 1
)

echo Running Employee Management Console...
java Main

cd ..
pause