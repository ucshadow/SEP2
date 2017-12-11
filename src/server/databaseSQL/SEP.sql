CREATE SCHEMA sep2;
SET SEARCH_PATH = sep2;
CREATE DOMAIN cpr_Domain CHAR(10) NOT NULL
  CONSTRAINT charLenght CHECK (length(value) = 10) CONSTRAINT emptyString CHECK (VALUE <> '' );
CREATE DOMAIN dno_Domain CHAR(7) NOT NULL CONSTRAINT emptyString CHECK (VALUE <> '' );
CREATE DOMAIN postcode_Domain VARCHAR(10) NOT NULL CONSTRAINT emptyString CHECK (VALUE <> '' );
CREATE DOMAIN varcharDomain VARCHAR(100) NOT NULL CONSTRAINT emptyString CHECK (VALUE <> '' );
CREATE DOMAIN numberDomain CHAR(8) NOT NULL CONSTRAINT emptyString CHECK (VALUE <> '' );

CREATE TABLE UserLogIn (
  cpr      CPR_DOMAIN PRIMARY KEY,
  Username VARCHARDOMAIN UNIQUE CONSTRAINT username_minvalue CHECK (LENGTH(Username) > 4),
  pass     VARCHARDOMAIN CONSTRAINT password_minValue CHECK (LENGTH(pass) >= 8) CONSTRAINT password_check CHECK (
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
  city     VARCHARDOMAIN
);

CREATE TABLE Employee (
  ID           SERIAL PRIMARY KEY,
  picture      VARCHAR,
  firstName    VARCHARDOMAIN,
  secondName   VARCHARDOMAIN,
  familyName   VARCHARDOMAIN,
  cpr          CPR_DOMAIN REFERENCES UserLogIn (cpr) ON DELETE CASCADE ON UPDATE CASCADE,
  dateOfBirth  DATE,
  address      VARCHARDOMAIN,
  postcode     POSTCODE_DOMAIN REFERENCES city (postcode),
  licencePlate VARCHARDOMAIN,
  moreInfo     VARCHARDOMAIN
);


CREATE TABLE communication (
  id                     SERIAL PRIMARY KEY,
  cpr                    CPR_DOMAIN REFERENCES UserLogIn (cpr) ON DELETE CASCADE ON UPDATE CASCADE,
  mobile                 NUMBERDOMAIN,
  landline               NUMBERDOMAIN,
  email                  VARCHARDOMAIN,
  preferredCommunication VARCHAR DEFAULT 'Mobile' CHECK (preferredCommunication IN ('Mobile', 'Landline', 'Email'))
);

CREATE TABLE bankInfoDK (
  id        SERIAL PRIMARY KEY,
  cpr       CPR_DOMAIN REFERENCES UserLogIn (cpr) ON DELETE CASCADE ON UPDATE CASCADE,
  konto     CHAR(4) CONSTRAINT kontoLenght CHECK (konto <> '' ),
  regNumber CHAR(10) CONSTRAINT regLenght CHECK (regnumber <> '' )
);


CREATE TABLE department (
  dno        DNO_DOMAIN PRIMARY KEY,
  dname      VARCHARDOMAIN,
  dManager   CPR_DOMAIN REFERENCES UserLogIn (cpr) ON DELETE CASCADE ON UPDATE CASCADE,
  dPostcode  POSTCODE_DOMAIN REFERENCES city (postcode) ON UPDATE CASCADE,
  dStartdate TIMESTAMP
);


CREATE TABLE workingSchedule (
  id         SERIAL PRIMARY KEY,
  dno        DNO_DOMAIN REFERENCES department (dno) ON DELETE CASCADE ON UPDATE CASCADE,
  employecpr CPR_DOMAIN REFERENCES UserLogIn (cpr) ON DELETE CASCADE ON UPDATE CASCADE,
  workingDay DATE,
  startHours TIME,
  endHours   TIME
);
-- ALTER TABLE workingSchedule
--   ADD CONSTRAINT check_date check(workingDay>=now());

CREATE TABLE wagePerHour (
  id          SERIAL PRIMARY KEY,
  employeeCPR CPR_DOMAIN REFERENCES UserLogIn (cpr) ON DELETE CASCADE ON UPDATE CASCADE,
  wage        NUMBERDOMAIN CONSTRAINT check_Size CHECK (length(wage)
                                                        < 7)
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
    INSERT INTO Employee (picture, firstName, secondName, familyName, cpr, dateOfBirth, address, postcode, licencePlate, moreInfo)
    VALUES
      ('null', 'firstname', 'secondname', 'lastname', new.cpr, current_date, 'address', 'postcode',
       'licenceplate',
       'more info');
    INSERT INTO communication (cpr, mobile, landline, email)
    VALUES (new.cpr, '00000000', '00000000', 'email@email.com');
    INSERT INTO bankInfoDK (cpr, konto, regNumber) VALUES (new.cpr, '0000', '0000000000');
    INSERT INTO wagePerHour (employeeCPR, wage) VALUES (new.cpr, '0');
    RETURN new;
  END IF;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER newUserAdded
AFTER INSERT ON userlogin
FOR EACH ROW
EXECUTE PROCEDURE newUserCreatedOrRemoved();


CREATE OR REPLACE FUNCTION historyAdd()
  RETURNS TRIGGER AS $$
DECLARE
  details VARCHAR;
BEGIN
  IF (tg_table_name = 'bankinfodk')
  THEN
    IF (tg_op = 'INSERT')
    THEN
      details = ('Employee with ', new.cpr, ' added bank account with konto ', new.konto, ' and reg number ', new.regnumber);
      INSERT INTO history (tablename, operation, details, TIMESTAMP) VALUES ('BankingInfo', ' INSERT ', details, now());
      RETURN new;
    ELSIF (tg_op = 'UPDATE')
      THEN
        details = ('Employee with ', new.cpr, ' updated bank account with konto ', new.konto, ' and reg number ', new.regnumber);
        INSERT INTO history (tablename, operation, details, TIMESTAMP)
        VALUES ('BankingInfo', ' Update ', details, now());
        RETURN new;

    ELSEIF (tg_op = 'DELETE')
      THEN
        details = (
          'Employee with ', old.cpr, ' was deleted with bank account with konto ', old.konto, ' and reg number ',
          old.regnumber);
        INSERT INTO history (tablename, operation, details, TIMESTAMP)
        VALUES ('BankingInfo', ' Delete ', details, now());
        RETURN old;
    END IF;
  END IF;

  IF (tg_table_name = 'city')
  THEN
    IF (tg_op = 'INSERT')
    THEN
      details = (
        'City with name ', new.city, ' with post code', new.postcode);
      INSERT INTO history (tablename, operation, details, TIMESTAMP)
      VALUES ('City', ' Insert ', details, now());
      RETURN new;

    ELSIF (tg_op = 'UPDATE')
      THEN
        details = (
          'City with name ', new.city, ' with post code', new.postcode);
        INSERT INTO history (tablename, operation, details, TIMESTAMP)
        VALUES ('City', ' Update ', details, now());
        RETURN new;

    ELSEIF (tg_op = 'DELETE')
      THEN
        details = (
          'City with name ', old.city, ' with post code', old.postcode);
        INSERT INTO history (tablename, operation, details, TIMESTAMP)
        VALUES ('City', ' Delete ', details, now());
        RETURN old;
    END IF;
  END IF;

  IF (tg_table_name = 'communication')
  THEN
    IF (tg_op = 'INSERT')
    THEN
      details = (' communications for  employee with cpr ', new.cpr, ' with mobile ', new.mobile);
      INSERT INTO history (tablename, operation, details, TIMESTAMP)
      VALUES ('Communication', ' Insert ', details, now());
      RETURN new;
    ELSIF (tg_op = 'UPDATE')
      THEN
        details = ('communications for  employee with cpr ', old.cpr, ' with mobile ', new.mobile);
        INSERT INTO history (tablename, operation, details, TIMESTAMP)
        VALUES ('Communication', ' Update ', details, now());
        RETURN new;

    ELSEIF (tg_op = 'DELETE')
      THEN
        details = ('communications for  employee with cpr ', old.cpr, ' with mobile ', old.mobile);
        INSERT INTO history (tablename, operation, details, TIMESTAMP)
        VALUES ('Communication', ' Delete ', details, now());
        RETURN old;
    END IF;
  END IF;

  IF (tg_table_name = 'department')
  THEN
    IF (tg_op = 'INSERT')
    THEN
      details = ('department created with  ', new.dno, ' with name ', new.dname);
      INSERT INTO history (tablename, operation, details, TIMESTAMP) VALUES ('department', 'INSERT', details, now());
      RETURN new;
    ELSIF (tg_op = 'UPDATE')
      THEN
        details = ('department update with  ', new.dno, ' with name ', new.dname);
        INSERT INTO history (tablename, operation, details, TIMESTAMP)
        VALUES ('department', 'update', details, now());
        RETURN new;

    ELSEIF (tg_op = 'DELETE')
      THEN
        details = ('department deleted with  ', old.dno, ' with name ', old.dname);
        INSERT INTO history (tablename, operation, details, TIMESTAMP)
        VALUES ('department', 'delete', details, now());
        RETURN old;

    END IF;
  END IF;

  IF (tg_table_name = 'employee')
  THEN
    IF (tg_op = 'INSERT')
    THEN
      details = ('Employee insert with  ', new.cpr, ' with name ', new.firstname);
      INSERT INTO history (tablename, operation, details, TIMESTAMP) VALUES ('Employee', 'INSERT', details, now());
      RETURN new;

    ELSIF (tg_op = 'UPDATE')
      THEN
        details = ('Employee update with  ', new.cpr, ' with name ', new.firstname);

        INSERT INTO history (tablename, operation, details, TIMESTAMP) VALUES ('Employee', 'update', details, now());
        RETURN new;

    ELSEIF (tg_op = 'DELETE')
      THEN
        details = ('Delete employee with  ', old.cpr, ' with name ', old.firstname);

        INSERT INTO history (tablename, operation, details, TIMESTAMP) VALUES ('Employee', 'delete', details, now());
        RETURN old;

    END IF;
  END IF;

  IF (tg_table_name = 'userlogin')
  THEN
    IF (tg_op = 'INSERT')
    THEN
      details = ('Inserted user with  ', new.cpr, ' with password ', new.pass, ' and role ', new.userrole);

      INSERT INTO history (tablename, operation, details, TIMESTAMP) VALUES ('userlogin', 'INSERT', details, now());
      RETURN new;

    ELSIF (tg_op = 'UPDATE')
      THEN
        details = ('updated user with  ', new.cpr, ' with password ', new.pass, ' and role ', new.userrole);

        INSERT INTO history (tablename, operation, details, TIMESTAMP) VALUES ('userlogin', 'UPDATE', details, now());
        RETURN new;

    ELSEIF (tg_op = 'DELETE')
      THEN
        details = ('deleted user with  ', old.cpr, ' with password ', old.pass, ' and role ', old.userrole);

        INSERT INTO history (tablename, operation, details, TIMESTAMP) VALUES ('userlogin', 'DELETE', details, now());
        RETURN old;

    END IF;
  END IF;

  IF (tg_table_name = 'wageperhour')
  THEN
    IF (tg_op = 'INSERT')
    THEN
      details = ('Inserted wage with  ', new.employeecpr, ' with wage ', new.wage);

      INSERT INTO history (tablename, operation, details, TIMESTAMP) VALUES ('wageperhour', 'INSERT', details, now());
      RETURN new;

    ELSIF (tg_op = 'UPDATE')
      THEN
        details = ('Updated wage with  ', new.employeecpr, ' with wage ', new.wage);

        INSERT INTO history (tablename, operation, details, TIMESTAMP)
        VALUES ('wageperhour', 'UPDATE', details, now());
        RETURN new;

    ELSEIF (tg_op = 'DELETE')
      THEN
        details = ('delete wage with  ', old.employeecpr, ' with wage ', old.wage);

        INSERT INTO history (tablename, operation, details, TIMESTAMP)
        VALUES ('wageperhour', 'DELETE', details, now());
        RETURN old;

    END IF;
  END IF;
  IF (tg_table_name = 'workingschedule')
  THEN
    IF (tg_op = 'INSERT')
    THEN
      details = ('Inserted workingschedule with  cpr ', new.employecpr, ' with working date ', new.workingday, ' in department', new.dno);

      INSERT INTO history (tablename, operation, details, TIMESTAMP)
      VALUES ('workingschedule', 'INSERT', details, now());
      RETURN new;

    ELSIF (tg_op = 'UPDATE')
      THEN
        details = ('Updated workingschedule with  cpr ', new.employecpr, ' with working date ', new.workingday, ' in department', new.dno);

        INSERT INTO history (tablename, operation, details, TIMESTAMP)
        VALUES ('workingschedule', 'UPDATE', details, now());
        RETURN new;

    ELSEIF (tg_op = 'DELETE')
      THEN
        details = ('Deleted workingschedule with  cpr ', old.employecpr, ' with working date ', old.workingday, ' in department', old.dno);

        INSERT INTO history (tablename, operation, details, TIMESTAMP)
        VALUES ('workingschedule', 'DELETE', details, now());
        RETURN old;

    END IF;
  END IF;
  RETURN new;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER logOnInsert
AFTER INSERT OR UPDATE OR DELETE ON bankInfoDK
FOR EACH ROW
EXECUTE PROCEDURE historyAdd();

CREATE TRIGGER logOnInsert
AFTER INSERT OR UPDATE OR DELETE ON city
FOR EACH ROW
EXECUTE PROCEDURE historyAdd();


CREATE TRIGGER logOnInsert
AFTER INSERT OR UPDATE OR DELETE ON communication
FOR EACH ROW
EXECUTE PROCEDURE historyAdd();


CREATE TRIGGER logOnInsert
AFTER INSERT OR UPDATE OR DELETE ON department
FOR EACH ROW
EXECUTE PROCEDURE historyAdd();


CREATE TRIGGER logOnInsert
AFTER INSERT OR UPDATE OR DELETE ON Employee
FOR EACH ROW
EXECUTE PROCEDURE historyAdd();


CREATE TRIGGER logOnInsert
AFTER INSERT OR UPDATE OR DELETE ON UserLogIn
FOR EACH ROW
EXECUTE PROCEDURE historyAdd();


CREATE TRIGGER logOnInsert
AFTER INSERT OR UPDATE OR DELETE ON wagePerHour
FOR EACH ROW
EXECUTE PROCEDURE historyAdd();


CREATE TRIGGER logOnInsert
AFTER INSERT OR UPDATE OR DELETE ON workingSchedule
FOR EACH ROW
EXECUTE PROCEDURE historyAdd();


INSERT INTO city (postcode, city) VALUES ('postcode', 'City');
INSERT INTO userlogin (cpr, username, pass, userrole) VALUES ('2345678901', 'MomoLina', 'Password123', 'Admin');
INSERT INTO userlogin (cpr, username, pass, userrole) VALUES ('3456789012', 'Radu1234', 'Password123', 'Admin');
INSERT INTO userlogin (cpr, username, pass, userrole) VALUES ('4567890123', 'ChocolateHercules', 'Password123', 'Admin');
INSERT INTO userlogin (cpr, username, pass, userrole) VALUES ('5678901234', 'Nikolay', 'Password123', 'Admin');

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

CREATE MATERIALIZED VIEW allUsersWithWage AS
  SELECT
    UserLogIn.cpr,
    UserLogIn.username,
    UserLogIn.pass,
    UserLogIn.userrole,
    wagePerHour.wage
  FROM UserLogIn
    INNER JOIN wagePerHour ON UserLogIn.cpr = wagePerHour.employeeCPR;

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


CREATE MATERIALIZED VIEW usersByDepartment AS
  SELECT DISTINCT
    (workingSchedule.employecpr),
    employee.firstname,
    employee.familyName,
    workingSchedule.dno
  FROM employee
    INNER JOIN workingschedule ON Employee.cpr = workingSchedule.employecpr;

--
-- CREATE MATERIALIZED VIEW userswithoudschedule AS
--   SELECT
--     employee.cpr,
--     employee.firstname,
--     employee.familyname
--   FROM employee
--   WHERE NOT EXISTS(SELECT cpr
--                    FROM workingSchedule
--                    WHERE Employee.cpr = workingSchedule.employecpr);

select * from userlogin;