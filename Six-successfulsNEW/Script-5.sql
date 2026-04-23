drop database if exists employeeData;

create database employeeData;
use employeeData;



CREATE TABLE employees (
  empid INT AUTO_INCREMENT,
  Fname VARCHAR(65) NOT NULL,
  Lname VARCHAR(65) NOT NULL,
  email VARCHAR(65) NOT NULL,
  HireDate DATE,
  Salary DECIMAL(10,2) NOT NULL,
  SSN VARCHAR(9),
  PRIMARY KEY (empid) 
);


CREATE TABLE division (
  ID int NOT null,
  Name varchar(100) DEFAULT NULL,
  city varchar(50) NOT NULL,
  addressLine1 varchar(50) NOT NULL,
  addressLine2 varchar(50) DEFAULT NULL,
  state varchar(50) DEFAULT NULL,
  country varchar(50) NOT NULL,
  postalCode varchar(15) NOT null,
  primary key (ID)
);


CREATE TABLE job_titles (
  job_title_id INT,
  job_title VARCHAR(125) NOT null,
  primary key (job_title_id)
);


/***********************************************************************/ 


CREATE TABLE payroll (
  payID INT AUTO_INCREMENT,
  pay_date DATE,
  earnings DECIMAL(8,2),
  fed_tax DECIMAL(7,2),
  fed_med DECIMAL(7,2),
  fed_SS DECIMAL(7,2),
  state_tax DECIMAL(7,2),
  retire_401k DECIMAL(7,2),
  health_care DECIMAL(7,2),
  empid INT,
  PRIMARY KEY (payID),
  constraint fk_payroll_employee foreign key (payID) references employees(empid)
);


CREATE TABLE employee_division (
  empid int NOT NULL,
  div_ID int NOT NULL,
  PRIMARY KEY (empid, div_ID),
  CONSTRAINT fk_ed_employee FOREIGN KEY (empid) REFERENCES employees(empid),
  CONSTRAINT fk_ed_division FOREIGN KEY (div_ID) REFERENCES division(ID)
);


CREATE TABLE employee_job_titles (
  empid INT NOT NULL,
  job_title_id INT NOT null,
  primary key (empid, job_title_id),
  CONSTRAINT fk_ejt_employee FOREIGN KEY (empid) REFERENCES employees(empid),
  CONSTRAINT fk_ejt_job FOREIGN KEY (job_title_id) REFERENCES job_titles(job_title_id)
);



/***********************************************************************/


