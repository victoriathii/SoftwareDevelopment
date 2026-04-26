drop database if exists employeeData;

create database employeeData;
use employeeData;


CREATE TABLE employee (
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

CREATE TABLE payhistory (
  historyId     INT AUTO_INCREMENT,
  empId         INT            NOT NULL,
  effectiveDate DATE           NOT NULL,
  oldSalary     DECIMAL(10,2)  NOT NULL,
  newSalary     DECIMAL(10,2)  NOT NULL,
  changeReason  VARCHAR(255),
  PRIMARY KEY (historyId),
  FOREIGN KEY (empId) REFERENCES employee(empId)
);
