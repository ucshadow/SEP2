CREATE SCHEMA sep2;
SET SEARCH_PATH = sep2;


CREATE DOMAIN cpr_Domain CHAR(10);
CREATE DOMAIN dno_Domain CHAR(4);
CREATE DOMAIN postcode_Domain VARCHAR(10);


CREATE TABLE UserLogIn (
  cpr      CPR_DOMAIN PRIMARY KEY,
  Username VARCHAR(25) UNIQUE CONSTRAINT username_minvalue CHECK (LENGTH(Username) > 4),
  pass     VARCHAR(100) CONSTRAINT password_minValue CHECK (LENGTH(pass) >= 8) CONSTRAINT password_check CHECK (
    pass LIKE '%A%' OR pass LIKE '%B%' OR pass LIKE '%C%' OR pass LIKE '%D%' OR pass LIKE '%E%' OR
    pass LIKE '%F%' OR pass LIKE '%G%' OR pass LIKE '%H%' OR pass LIKE '%I%' OR pass LIKE '%J%'
    OR pass LIKE '%K%' OR pass LIKE '%L%' OR pass LIKE '%M%' OR pass LIKE '%N%' OR
    pass LIKE '%O%' OR pass LIKE '%P%' OR pass LIKE '%Q%' OR pass LIKE '%R%' OR pass LIKE '%S%'
    OR pass LIKE '%T%' OR pass LIKE '%U%' OR pass LIKE '%V%' OR pass LIKE '%W%' OR pass LIKE '%X%'
    OR pass LIKE '%Y%' OR pass LIKE '%Z%'),
  userRole VARCHAR(7) DEFAULT 'Admin' CHECK (userRole IN ('Admin', 'User', 'Manager'))
);

CREATE TABLE city (
  postcode POSTCODE_DOMAIN PRIMARY KEY,
  city     VARCHAR(25)
);

CREATE TABLE Employee (
  ID           SERIAL PRIMARY KEY,
  picture      VARCHAR,
  firstName    VARCHAR(25),
  secondName   VARCHAR(25),
  familyName   VARCHAR(25),
  cpr          CPR_DOMAIN REFERENCES UserLogIn (cpr),
  dateOfBirth  DATE,
  address      VARCHAR(25),
  postcode     VARCHAR(10) REFERENCES city (postcode),
  licencePlate VARCHAR,
  moreInfo     VARCHAR
);


CREATE TABLE communication (
  id                     SERIAL PRIMARY KEY,
  cpr                    CPR_DOMAIN REFERENCES UserLogIn (cpr),
  mobile                 CHAR(8),
  landline               CHAR(8),
  email                  VARCHAR,
  preferredCommunication VARCHAR DEFAULT 'Mobile' CHECK (preferredCommunication IN ('Mobile', 'Home', 'Email'))
);

CREATE TABLE bankInfoDK (
  id        SERIAL PRIMARY KEY,
  cpr       CPR_DOMAIN REFERENCES UserLogIn (cpr),
  konto     CHAR(4),
  regNumber CHAR(10)
);


CREATE TABLE department (
  dno        DNO_DOMAIN PRIMARY KEY,
  dname      VARCHAR,
  dManager   CHAR(10) REFERENCES UserLogIn (cpr),
  dPostcode  VARCHAR(10) REFERENCES city (postcode),
  dStartdate TIMESTAMP
);


CREATE TABLE workingSchedule (
  id         SERIAL PRIMARY KEY,
  dno        DNO_DOMAIN REFERENCES department (dno),
  employecpr CPR_DOMAIN REFERENCES UserLogIn (cpr),
  workingDay DATE,
  startHours TIME,
  endHours   TIME
);


CREATE TABLE wagePerHour (
  id          SERIAL PRIMARY KEY,
  employeeCPR CPR_DOMAIN REFERENCES UserLogIn (cpr),
  wage        NUMERIC(6, 2)
);

CREATE TABLE history (
  id        SERIAL PRIMARY KEY,
  tablename VARCHAR,
  operation VARCHAR,
  details   VARCHAR,
  TIMESTAMP TIMESTAMP
);
--Functions
-- Trigger function create to automatically insert cpr,username and pass once employee is created as a USER
-- Trigger function that deletes employee data one employee is deleted
CREATE OR REPLACE FUNCTION newUserCreatedOrRemoved()
  RETURNS TRIGGER AS $$
BEGIN
  IF (tg_op = 'INSERT')
  THEN
    INSERT INTO Employee (cpr, postcode) VALUES (new.cpr, '1234');
    INSERT INTO communication (cpr) VALUES (new.cpr);
    INSERT INTO bankInfoDK (cpr) VALUES (new.cpr);
    INSERT INTO wagePerHour (employeeCPR) VALUES (new.cpr);
    RETURN new;
  ELSIF (tg_op = 'DELETE')
    THEN
      DELETE FROM employee
      WHERE old.cpr = cpr;
      DELETE FROM wagePerHour
      WHERE old.cpr = employeecpr;
      DELETE FROM bankInfoDK
      WHERE old.cpr = cpr;
      DELETE FROM communication
      WHERE old.cpr = cpr;
  END IF;
  RETURN old;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER newUserAdded
AFTER INSERT ON userlogin
FOR EACH ROW
EXECUTE PROCEDURE newUserCreatedOrRemoved();

CREATE TRIGGER oldUserRemoved
BEFORE DELETE ON UserLogIn
FOR EACH ROW
EXECUTE PROCEDURE newUserCreatedOrRemoved();


CREATE MATERIALIZED VIEW EmployeeInformation AS
  SELECT
    employee.picture,
    UserLogIn.Username,
    UserLogIn.pass,
    employee.firstname,
    employee.secondName,
    employee.familyName,
    UserLogIn.cpr,
    employee.dateOfBirth,
    employee.address,
    employee.postcode,
    city.city,
    communication.mobile,
    communication.landline,
    communication.email,
    bankInfoDK.konto,
    bankInfoDK.regNumber,
    employee.licencePlate,
    communication.preferredCommunication,
    employee.moreInfo,
    wagePerHour.wage,
    UserLogIn.userRole
  FROM
    City
    INNER JOIN Employee ON city.postcode = Employee.postcode
    INNER JOIN Communication ON Employee.cpr = communication.cpr
    INNER JOIN bankInfoDK ON communication.cpr = bankInfoDK.cpr
    INNER JOIN wagePerHour ON bankInfoDK.cpr = wagePerHour.employeeCPR
    INNER JOIN userlogin ON wagePerHour.employeeCPR = UserLogIn.cpr;


CREATE MATERIALIZED VIEW workingColleagues AS
  SELECT
    picture,
    firstName,
    familyName,
    mobile,
    email,
    Employee.cpr,
    workingSchedule.dno
  FROM communication
    INNER JOIN employee ON communication.cpr = Employee.cpr
    LEFT OUTER JOIN workingSchedule ON Employee.cpr = workingSchedule.employecpr;

CREATE MATERIALIZED VIEW allColleagues AS
  SELECT
    picture,
    firstName,
    familyName,
    mobile,
    email,
    Employee.cpr
  FROM communication
    INNER JOIN Employee ON communication.cpr = Employee.cpr;


/*
CREATE OR REPLACE FUNCTION historyAdd()
  RETURNS TRIGGER AS $$
DECLARE
  details VARCHAR;
BEGIN
  IF (tg_table_name = 'bankinfodk')
  THEN
    IF (tg_op = 'INSERT')
    THEN
      details = ('Employee with ' + new.cpr + ' added bank acount with konto ' + new.konto + ' and reg number ' +
                 new.regnumber);
      INSERT INTO history (tablename, operation, details, TIMESTAMP) VALUES ('BankingInfo', ' INSERT ', details, now());
      RETURN new;
    ELSIF (tg_op = ' UPDATE ')
      THEN
        details = ('Employee with ' + new.cpr + ' updated bank acount with konto ' + new.konto + ' and reg number ' +
                   new.regnumber);
        INSERT INTO history (tablename, operation, details, TIMESTAMP)
        VALUES ('BankingInfo', ' Update ', details, now());

        RETURN new;
    ELSEIF (tg_op = ' DELETE ')
      THEN
        details = (
          'Employee with ' + old.cpr + ' was deleted with bank acount with konto ' + old.konto + ' and reg number ' +
          old.regnumber);
        INSERT INTO history (tablename, operation, details, TIMESTAMP)
        VALUES ('BankingInfo', ' Delete ', details, now());

        RETURN new;
    END IF;
  ELSIF (tg_table_name = 'city')
    THEN
      IF (tg_op = ' INSERT ')
      THEN
        details = (
          'City with name ' + new.city + ' with post code' + new.postcode);
        INSERT INTO history (tablename, operation, details, TIMESTAMP)
        VALUES ('City', ' Insert ', details, now());
        RETURN new;
      ELSIF (tg_op = ' UPDATE ')
        THEN
          details = (
            'City with name ' + new.city + ' with post code' + new.postcode);
          INSERT INTO history (tablename, operation, details, TIMESTAMP)
          VALUES ('City', ' Update ', details, now());
          RETURN new;
      ELSEIF (tg_op = ' DELETE ')
        THEN
          details = (
            'City with name ' + old.city + ' with post code' + old.postcode);
          INSERT INTO history (tablename, operation, details, TIMESTAMP)
          VALUES ('City', ' Delete ', details, now());
          RETURN new;
      END IF;
  ELSIF (tg_table_name = 'communication')
    THEN
      IF (tg_op = ' INSERT ')
      THEN
        details = (' communications for  employee with cpr ' + new.cpr + ' with mobile ' + new.mobile);
        INSERT INTO history (tablename, operation, details, TIMESTAMP)
        VALUES ('Communication', ' Insert ', details, now());
        RETURN new;
      ELSIF (tg_op = ' UPDATE ')
        THEN
          details = ('communications for  employee with cpr ' + old.cpr + ' with mobile ' + new.mobile);
          INSERT INTO history (tablename, operation, details, TIMESTAMP)
          VALUES ('Communication', ' Update ', details, now());
          RETURN new;
      ELSEIF (tg_op = ' DELETE ')
        THEN
          details = ('communications for  employee with cpr ' + old.cpr + ' with mobile ' + old.mobile);
          INSERT INTO history (tablename, operation, details, TIMESTAMP)
          VALUES ('Communication', ' Delete ', details, now());
          RETURN new;
      END IF;
      --   ELSIF (tg_table_name = 'department')
      --     THEN
      --       IF (tg_op = ' INSERT ')
      --       THEN
      --         INSERT INTO history () VALUES ();
      --         RETURN new;
      --       ELSIF (tg_op = ' UPDATE ')
      --         THEN
      --           INSERT INTO history () VALUES ();
      --           RETURN new;
      --       ELSEIF (tg_op = ' DELETE ')
      --         THEN
      --           INSERT INTO history () VALUES ();
      --           RETURN new;
      --       END IF;
      --   ELSIF (tg_table_name = 'employee')
      --     THEN
      --       IF (tg_op = ' INSERT ')
      --       THEN
      --         INSERT INTO history () VALUES ();
      --         RETURN new;
      --       ELSIF (tg_op = ' UPDATE ')
      --         THEN
      --           INSERT INTO history () VALUES ();
      --           RETURN new;
      --       ELSEIF (tg_op = ' DELETE ')
      --         THEN
      --           INSERT INTO history () VALUES ();
      --           RETURN new;
      --       END IF;
      --   ELSIF (tg_table_name = 'userlogin')
      --     THEN
      --       IF (tg_op = ' INSERT ')
      --       THEN
      --         INSERT INTO history () VALUES ();
      --         RETURN new;
      --       ELSIF (tg_op = ' UPDATE ')
      --         THEN
      --           INSERT INTO history () VALUES ();
      --           RETURN new;
      --       ELSEIF (tg_op = ' DELETE ')
      --         THEN
      --           INSERT INTO history () VALUES ();
      --           RETURN new;
      --       END IF;
      --   ELSIF (tg_table_name = 'wageperhour')
      --     THEN
      --       IF (tg_op = ' INSERT ')
      --       THEN
      --         INSERT INTO history () VALUES ();
      --         RETURN new;
      --       ELSIF (tg_op = ' UPDATE ')
      --         THEN
      --           INSERT INTO history () VALUES ();
      --           RETURN new;
      --       ELSEIF (tg_op = ' DELETE ')
      --         THEN
      --           INSERT INTO history () VALUES ();
      --           RETURN new;
      --       END IF;
      --   ELSIF (tg_table_name = 'workingschedule')
      --     THEN
      --       IF (tg_op = ' INSERT ')
      --       THEN
      --         INSERT INTO history () VALUES ();
      --         RETURN new;
      --       ELSIF (tg_op = ' UPDATE ')
      --         THEN
      --           INSERT INTO history () VALUES ();
      --           RETURN new;
      --       ELSEIF (tg_op = ' DELETE ')
      --         THEN
      --           INSERT INTO history () VALUES ();
      --           RETURN new;
  END IF;
END;
$$ LANGUAGE plpgsql;
*/