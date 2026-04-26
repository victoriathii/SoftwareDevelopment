-- SQLite Database Setup Script
-- This script creates the employeeData.db SQLite database

-- Remove existing database file if it exists
-- Note: In SQLite, you delete the .db file directly, not via SQL

-- Create employees table
CREATE TABLE IF NOT EXISTS employees (
  empId INTEGER PRIMARY KEY AUTOINCREMENT,
  firstName VARCHAR(65) NOT NULL,
  lastName VARCHAR(65) NOT NULL,
  ssn VARCHAR(9),
  jobTitle VARCHAR(65) NOT NULL,
  division VARCHAR(65) NOT NULL,
  salary DECIMAL(10,2) NOT NULL,
  address VARCHAR(65) NOT NULL,
  hireDate DATE
);

-- Create payhistory table for salary change tracking
CREATE TABLE IF NOT EXISTS payhistory (
  historyId INTEGER PRIMARY KEY AUTOINCREMENT,
  empId INTEGER NOT NULL,
  oldSalary DECIMAL(10,2) NOT NULL,
  newSalary DECIMAL(10,2) NOT NULL,
  effectiveDate DATE NOT NULL,
  changeReason VARCHAR(255),
  FOREIGN KEY (empId) REFERENCES employees(empId)
);