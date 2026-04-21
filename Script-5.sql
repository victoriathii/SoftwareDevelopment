drop database if exists employeeData;

create database employeeData;
use employeeData;


CREATE TABLE employees (
  empId INT AUTO_INCREMENT,
  firstName VARCHAR(65) NOT NULL,
  lastName VARCHAR(65) NOT NULL,
  ssn VARCHAR(9),
  jobTitle VARCHAR(65) NOT NULL,
  division VARCHAR(65) NOT NULL,
  salary DECIMAL(10,2) NOT NULL,
  address VARCHAR(65) NOT NULL,
  hireDate DATE,
  PRIMARY KEY (empid) 
);
