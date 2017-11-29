CREATE SCHEMA sep2;
SET SEARCH_PATH = sep2;

CREATE DOMAIN cpr_Domain CHAR(10);
CREATE DOMAIN dno_Domain CHAR(4);

CREATE TABLE UserLogIn (
  Username VARCHAR(25) UNIQUE CONSTRAINT username_minvalue CHECK (LENGTH(Username) > 4),
  cpr      CPR_DOMAIN,
  Password VARCHAR(100) CONSTRAINT password_minValue CHECK (LENGTH(Password) >= 8) CONSTRAINT password_check CHECK (
    Password LIKE '%A%' OR PASSWORD LIKE '%B%' OR PASSWORD LIKE '%C%' OR PASSWORD LIKE '%D%' OR PASSWORD LIKE '%E%' OR
    PASSWORD LIKE '%F%' OR PASSWORD LIKE '%G%' OR PASSWORD LIKE '%H%' OR PASSWORD LIKE '%I%' OR PASSWORD LIKE '%J%'
    OR PASSWORD LIKE '%K%' OR PASSWORD LIKE '%L%' OR PASSWORD LIKE '%M%' OR PASSWORD LIKE '%N%' OR
    PASSWORD LIKE '%O%' OR PASSWORD LIKE '%P%' OR PASSWORD LIKE '%Q%' OR PASSWORD LIKE '%R%' OR PASSWORD LIKE '%S%'
    OR PASSWORD LIKE '%T%' OR PASSWORD LIKE '%U%' OR PASSWORD LIKE '%V%' OR PASSWORD LIKE '%W%' OR PASSWORD LIKE '%X%'
    OR PASSWORD LIKE '%Y%' OR PASSWORD LIKE '%Z%'),
  userRole VARCHAR(7) DEFAULT 'Admin' CHECK (userRole IN ('Admin', 'User', 'Manager'))
);

ALTER TABLE UserLogIn
  RENAME COLUMN Password TO pass;
ALTER TABLE UserLogIn
  ADD PRIMARY KEY (Username, cpr);

CREATE TABLE Employee (
  picture                VARCHAR,
  username               VARCHAR,
  password               VARCHAR,
  firstName              VARCHAR(25),
  secondName             VARCHAR(25),
  familyName             VARCHAR(25),
  cpr                    CPR_DOMAIN PRIMARY KEY,
  dateOfBirth            DATE,
  address                VARCHAR(25),
  postcode               VARCHAR(10),
  city                   VARCHAR(25),
  mobile                 CHAR(8),
  landline               CHAR(8),
  email                  VARCHAR,
  konto                  CHAR(4),
  regNumber              CHAR(10),
  licencePlate           VARCHAR,
  preferredCommunication VARCHAR DEFAULT 'Mobile' CHECK (preferredCommunication IN ('Mobile', 'Home', 'Email')),
  moreInfo               VARCHAR,
  wage                   VARCHAR,
  userRole               VARCHAR


);
ALTER TABLE Employee
  RENAME COLUMN password TO passEmp;
CREATE TABLE department (
  dno       DNO_DOMAIN,
  dname     VARCHAR,
  dlocation VARCHAR,
  dManager  CHAR(10)

);

ALTER TABLE department
  ADD PRIMARY KEY (dno);


CREATE TABLE workingSchedule (
  id         SERIAL PRIMARY KEY,
  dno        DNO_DOMAIN,
  employecpr CPR_DOMAIN,
  workingDay DATE,
  startHours TIME,
  endHours   TIME
);

CREATE TABLE wagePerHour (
  employeeCPR CPR_DOMAIN,
  wage        NUMERIC(6, 2)
);
--Functions
-- Trigger function create to automatically insert cpr,username and pass once employee is created as a USER
-- Trigger function that deletes employee data one employee is deleted
CREATE OR REPLACE FUNCTION newEmployee()
  RETURNS TRIGGER AS $$
BEGIN
  IF (tg_op = 'INSERT')
  THEN
    INSERT INTO Employee
    VALUES
      ('', new.username, new.pass, '', '', '', new.cpr, NULL, '', '', '', '', '', '', '', '', '', NULL, '',
                                                                          '',
                                                                          new.userRole);
    RETURN new;
  ELSIF (tg_op = 'DELETE')
    THEN
      DELETE FROM employee
      WHERE old.cpr = cpr;
      DELETE FROM wagePerHour
      WHERE old.cpr = employee;
  END IF;
  RETURN old;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION addWage()
  RETURNS TRIGGER AS $$
BEGIN
  IF (tg_op = 'INSERT')
  THEN
    UPDATE Employee
    SET wage = new.wage
    WHERE cpr = new.employeeCPR;
    RETURN new;
  END IF;
  RETURN old;
END;
$$ LANGUAGE plpgsql;

--Trigger function created to update password upon change from employee table to userlogin table
CREATE OR REPLACE FUNCTION empPassword()
  RETURNS TRIGGER AS $$
BEGIN
  IF (tg_op = 'UPDATE')
  THEN
    UPDATE userLogIn
    SET pass = passEmp
    FROM employee
    WHERE userlogin.cpr = employee.cpr;
  END IF;
  RETURN new;
END;
$$ LANGUAGE plpgsql;
--FUNCTION END
-- TRIGGERS


CREATE TRIGGER passChange
AFTER UPDATE OF passEmp
  ON Employee
EXECUTE PROCEDURE empPassword();


CREATE TRIGGER newEmp
BEFORE INSERT OR DELETE ON userlogin
FOR EACH ROW
EXECUTE PROCEDURE newEmployee();


CREATE TRIGGER newEmpWage
BEFORE INSERT ON wagePerHour
FOR EACH ROW
EXECUTE PROCEDURE addWage();

--TRIGGERS END