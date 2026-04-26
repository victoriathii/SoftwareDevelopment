@echo off
echo Compiling JavaFX project...

javac ^
  --module-path "C:\javafx\javafx-sdk-22.0.1\lib" ^
  --add-modules javafx.controls,javafx.fxml ^
  -cp ".;lib/*" ^
  src\*.java ^
  -d .

echo Done! Compiled successfully.
pause
