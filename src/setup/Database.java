package setup;


import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private Connection connection = null;
    private Statement statement = null;
    private String driver = "org.postgresql.Driver";
    private String url = "jdbc:postgresql://localhost/postgres";
    private String urlForTestingjUnit = "jdbc:postgresql://localhost/postgres?currentSchema=sep2";

    private String username;
    private String password;
    private String filename = "postgresUsernameAndPass.txt";

    public Database(String username, String password) {
        deleteFile(filename);
        writeToFile(username, password, filename);
        this.username = username;
        this.password = password;
    }

    public void mainMethod() {
        System.out.println("Creating database");
        openDataBase();
        System.out.println("Creating database.");
        createSchema();
        System.out.println("Creating database..");
        this.url = "jdbc:postgresql://localhost/postgres?currentSchema=sep2";
        System.out.println("Creating database...");
        openDataBase();
        System.out.println("Creating database....");
        everything();
        System.out.println("Creating database.....");
        closeDataBase();
        System.out.println("Creating database.");
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Database ready for use");
    }

    private void createSchema() {
        String sql = "CREATE SCHEMA sep2;";
        executeStatements(sql);
    }

    public void deleteFromtables() {
        try {
            connection = DriverManager.getConnection(urlForTestingjUnit, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String[] tables = {"bankinfodk", "communication", "workingschedule", "employee", "history", "wageperhour", "userlogin", "department"};
        for (String table : tables) {
            String sql = "delete from " + table + ";";
            executeStatements(sql);
        }
        String sql = "delete from city where postcode is distinct from 'postcode'";
        executeStatements(sql);
    }

    private void everything() {
        String sql = "\n" +
                "CREATE DOMAIN cpr_Domain CHAR(10) NOT NULL\n" +
                "  CONSTRAINT charLenght CHECK (length(value) = 10) CONSTRAINT emptyString CHECK (VALUE <> '' );\n" +
                "CREATE DOMAIN dno_Domain CHAR(7) NOT NULL CONSTRAINT emptyString CHECK (VALUE <> '' );\n" +
                "CREATE DOMAIN postcode_Domain VARCHAR(10) NOT NULL CONSTRAINT emptyString CHECK (VALUE <> '' );\n" +
                "CREATE DOMAIN varcharDomain VARCHAR(100) NOT NULL CONSTRAINT emptyString CHECK (VALUE <> '' );\n" +
                "CREATE DOMAIN numberDomain CHAR(8) NOT NULL CONSTRAINT emptyString CHECK (VALUE <> '' );\n" +
                "\n" +
                "CREATE TABLE UserLogIn (\n" +
                "  cpr      CPR_DOMAIN PRIMARY KEY,\n" +
                "  Username VARCHARDOMAIN UNIQUE CONSTRAINT username_minvalue CHECK (LENGTH(Username) > 4),\n" +
                "  pass     VARCHARDOMAIN CONSTRAINT password_minValue CHECK (LENGTH(pass) >= 8) CONSTRAINT password_check CHECK (\n" +
                "    pass LIKE '%A%' OR pass LIKE '%B%' OR pass LIKE '%C%' OR pass LIKE '%D%' OR pass LIKE '%E%' OR\n" +
                "    pass LIKE '%F%' OR pass LIKE '%G%' OR pass LIKE '%H%' OR pass LIKE '%I%' OR pass LIKE '%J%'\n" +
                "    OR pass LIKE '%K%' OR pass LIKE '%L%' OR pass LIKE '%M%' OR pass LIKE '%N%' OR\n" +
                "    pass LIKE '%O%' OR pass LIKE '%P%' OR pass LIKE '%Q%' OR pass LIKE '%R%' OR pass LIKE '%S%'\n" +
                "    OR pass LIKE '%T%' OR pass LIKE '%U%' OR pass LIKE '%V%' OR pass LIKE '%W%' OR pass LIKE '%X%'\n" +
                "    OR pass LIKE '%Y%' OR pass LIKE '%Z%'),\n" +
                "  userRole VARCHAR(7) DEFAULT 'Admin' CHECK (userRole IN ('Admin', 'User', 'Manager'))\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE city (\n" +
                "  postcode POSTCODE_DOMAIN PRIMARY KEY,\n" +
                "  city     VARCHARDOMAIN\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE Employee (\n" +
                "  ID           SERIAL PRIMARY KEY,\n" +
                "  picture      VARCHAR,\n" +
                "  firstName    VARCHARDOMAIN,\n" +
                "  secondName   VARCHARDOMAIN,\n" +
                "  familyName   VARCHARDOMAIN,\n" +
                "  cpr          CPR_DOMAIN REFERENCES UserLogIn (cpr) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "  dateOfBirth  DATE,\n" +
                "  address      VARCHARDOMAIN,\n" +
                "  postcode     POSTCODE_DOMAIN REFERENCES city (postcode),\n" +
                "  licencePlate VARCHARDOMAIN,\n" +
                "  moreInfo     VARCHARDOMAIN\n" +
                ");\n" +
                "\n" +
                "\n" +
                "CREATE TABLE communication (\n" +
                "  id                     SERIAL PRIMARY KEY,\n" +
                "  cpr                    CPR_DOMAIN REFERENCES UserLogIn (cpr) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "  mobile                 NUMBERDOMAIN,\n" +
                "  landline               NUMBERDOMAIN,\n" +
                "  email                  VARCHARDOMAIN,\n" +
                "  preferredCommunication VARCHAR DEFAULT 'Mobile' CHECK (preferredCommunication IN ('Mobile', 'Landline', 'Email'))\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE bankInfoDK (\n" +
                "  id        SERIAL PRIMARY KEY,\n" +
                "  cpr       CPR_DOMAIN REFERENCES UserLogIn (cpr) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "  konto     CHAR(4) CONSTRAINT kontoLenght CHECK (konto <> '' ),\n" +
                "  regNumber CHAR(10) CONSTRAINT regLenght CHECK (regnumber <> '' )\n" +
                ");\n" +
                "\n" +
                "\n" +
                "CREATE TABLE department (\n" +
                "  dno        DNO_DOMAIN PRIMARY KEY,\n" +
                "  dname      VARCHARDOMAIN,\n" +
                "  dManager   CPR_DOMAIN REFERENCES UserLogIn (cpr) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "  dPostcode  POSTCODE_DOMAIN REFERENCES city (postcode) ON UPDATE CASCADE,\n" +
                "  dStartdate TIMESTAMP\n" +
                ");\n" +
                "\n" +
                "\n" +
                "CREATE TABLE workingSchedule (\n" +
                "  id         SERIAL PRIMARY KEY,\n" +
                "  dno        DNO_DOMAIN REFERENCES department (dno) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "  employecpr CPR_DOMAIN REFERENCES UserLogIn (cpr) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "  workingDay DATE,\n" +
                "  startHours TIME,\n" +
                "  endHours   TIME\n" +
                ");\n" +
                "-- ALTER TABLE workingSchedule\n" +
                "--   ADD CONSTRAINT check_date check(workingDay>=now());\n" +
                "\n" +
                "CREATE TABLE wagePerHour (\n" +
                "  id          SERIAL PRIMARY KEY,\n" +
                "  employeeCPR CPR_DOMAIN REFERENCES UserLogIn (cpr) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "  wage        NUMBERDOMAIN CONSTRAINT check_Size CHECK (length(wage)\n" +
                "                                                        < 7)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE history (\n" +
                "  id        SERIAL PRIMARY KEY,\n" +
                "  tablename VARCHAR,\n" +
                "  operation VARCHAR,\n" +
                "  details   VARCHAR,\n" +
                "  TIMESTAMP TIMESTAMP\n" +
                ");\n" +
                "\n" +
                "\n" +
                "--Functions\n" +
                "-- Trigger function create to automatically insert cpr,username and pass once employee is created as a USER\n" +
                "-- Trigger function that deletes employee data one employee is deleted\n" +
                "CREATE OR REPLACE FUNCTION newUserCreated()\n" +
                "  RETURNS TRIGGER AS $$\n" +
                "BEGIN\n" +
                "  IF (tg_op = 'INSERT')\n" +
                "  THEN\n" +
                "    INSERT INTO Employee (picture, firstName, secondName, familyName, cpr, dateOfBirth, address, postcode, licencePlate, moreInfo)\n" +
                "    VALUES\n" +
                "      ('null', 'firstname', 'secondname', 'lastname', new.cpr, current_date, 'address', 'postcode',\n" +
                "       'licenceplate',\n" +
                "       'more info');\n" +
                "    INSERT INTO communication (cpr, mobile, landline, email)\n" +
                "    VALUES (new.cpr, '00000000', '00000000', 'email@email.com');\n" +
                "    INSERT INTO bankInfoDK (cpr, konto, regNumber) VALUES (new.cpr, '0000', '0000000000');\n" +
                "    INSERT INTO wagePerHour (employeeCPR, wage) VALUES (new.cpr, '0');\n" +
                "    RETURN new;\n" +
                "  END IF;\n" +
                "END;\n" +
                "$$ LANGUAGE plpgsql;\n" +
                "\n" +
                "CREATE TRIGGER newUserAdded\n" +
                "AFTER INSERT ON userlogin\n" +
                "FOR EACH ROW\n" +
                "EXECUTE PROCEDURE newUserCreated();\n" +
                "\n" +
                "\n" +
                "CREATE OR REPLACE FUNCTION historyAdd()\n" +
                "  RETURNS TRIGGER AS $$\n" +
                "DECLARE\n" +
                "  details VARCHAR;\n" +
                "BEGIN\n" +
                "  IF (tg_table_name = 'bankinfodk')\n" +
                "  THEN\n" +
                "    IF (tg_op = 'INSERT')\n" +
                "    THEN\n" +
                "      details = ('Employee with ', new.cpr, ' added bank account with konto ', new.konto, ' and reg number ', new.regnumber);\n" +
                "      INSERT INTO history (tablename, operation, details, TIMESTAMP) VALUES ('BankingInfo', ' INSERT ', details, now());\n" +
                "      RETURN new;\n" +
                "    ELSIF (tg_op = 'UPDATE')\n" +
                "      THEN\n" +
                "        details = ('Employee with ', new.cpr, ' updated bank account with konto ', new.konto, ' and reg number ', new.regnumber);\n" +
                "        INSERT INTO history (tablename, operation, details, TIMESTAMP)\n" +
                "        VALUES ('BankingInfo', ' Update ', details, now());\n" +
                "        RETURN new;\n" +
                "\n" +
                "    ELSEIF (tg_op = 'DELETE')\n" +
                "      THEN\n" +
                "        details = (\n" +
                "          'Employee with ', old.cpr, ' was deleted with bank account with konto ', old.konto, ' and reg number ',\n" +
                "          old.regnumber);\n" +
                "        INSERT INTO history (tablename, operation, details, TIMESTAMP)\n" +
                "        VALUES ('BankingInfo', ' Delete ', details, now());\n" +
                "        RETURN old;\n" +
                "    END IF;\n" +
                "  END IF;\n" +
                "\n" +
                "  IF (tg_table_name = 'city')\n" +
                "  THEN\n" +
                "    IF (tg_op = 'INSERT')\n" +
                "    THEN\n" +
                "      details = (\n" +
                "        'City with name ', new.city, ' with post code', new.postcode);\n" +
                "      INSERT INTO history (tablename, operation, details, TIMESTAMP)\n" +
                "      VALUES ('City', ' Insert ', details, now());\n" +
                "      RETURN new;\n" +
                "\n" +
                "    ELSIF (tg_op = 'UPDATE')\n" +
                "      THEN\n" +
                "        details = (\n" +
                "          'City with name ', new.city, ' with post code', new.postcode);\n" +
                "        INSERT INTO history (tablename, operation, details, TIMESTAMP)\n" +
                "        VALUES ('City', ' Update ', details, now());\n" +
                "        RETURN new;\n" +
                "\n" +
                "    ELSEIF (tg_op = 'DELETE')\n" +
                "      THEN\n" +
                "        details = (\n" +
                "          'City with name ', old.city, ' with post code', old.postcode);\n" +
                "        INSERT INTO history (tablename, operation, details, TIMESTAMP)\n" +
                "        VALUES ('City', ' Delete ', details, now());\n" +
                "        RETURN old;\n" +
                "    END IF;\n" +
                "  END IF;\n" +
                "\n" +
                "  IF (tg_table_name = 'communication')\n" +
                "  THEN\n" +
                "    IF (tg_op = 'INSERT')\n" +
                "    THEN\n" +
                "      details = (' communications for  employee with cpr ', new.cpr, ' with mobile ', new.mobile);\n" +
                "      INSERT INTO history (tablename, operation, details, TIMESTAMP)\n" +
                "      VALUES ('Communication', ' Insert ', details, now());\n" +
                "      RETURN new;\n" +
                "    ELSIF (tg_op = 'UPDATE')\n" +
                "      THEN\n" +
                "        details = ('communications for  employee with cpr ', old.cpr, ' with mobile ', new.mobile);\n" +
                "        INSERT INTO history (tablename, operation, details, TIMESTAMP)\n" +
                "        VALUES ('Communication', ' Update ', details, now());\n" +
                "        RETURN new;\n" +
                "\n" +
                "    ELSEIF (tg_op = 'DELETE')\n" +
                "      THEN\n" +
                "        details = ('communications for  employee with cpr ', old.cpr, ' with mobile ', old.mobile);\n" +
                "        INSERT INTO history (tablename, operation, details, TIMESTAMP)\n" +
                "        VALUES ('Communication', ' Delete ', details, now());\n" +
                "        RETURN old;\n" +
                "    END IF;\n" +
                "  END IF;\n" +
                "\n" +
                "  IF (tg_table_name = 'department')\n" +
                "  THEN\n" +
                "    IF (tg_op = 'INSERT')\n" +
                "    THEN\n" +
                "      details = ('department created with  ', new.dno, ' with name ', new.dname);\n" +
                "      INSERT INTO history (tablename, operation, details, TIMESTAMP) VALUES ('department', 'INSERT', details, now());\n" +
                "      RETURN new;\n" +
                "    ELSIF (tg_op = 'UPDATE')\n" +
                "      THEN\n" +
                "        details = ('department update with  ', new.dno, ' with name ', new.dname);\n" +
                "        INSERT INTO history (tablename, operation, details, TIMESTAMP)\n" +
                "        VALUES ('department', 'update', details, now());\n" +
                "        RETURN new;\n" +
                "\n" +
                "    ELSEIF (tg_op = 'DELETE')\n" +
                "      THEN\n" +
                "        details = ('department deleted with  ', old.dno, ' with name ', old.dname);\n" +
                "        INSERT INTO history (tablename, operation, details, TIMESTAMP)\n" +
                "        VALUES ('department', 'delete', details, now());\n" +
                "        RETURN old;\n" +
                "\n" +
                "    END IF;\n" +
                "  END IF;\n" +
                "\n" +
                "  IF (tg_table_name = 'employee')\n" +
                "  THEN\n" +
                "    IF (tg_op = 'INSERT')\n" +
                "    THEN\n" +
                "      details = ('Employee insert with  ', new.cpr, ' with name ', new.firstname);\n" +
                "      INSERT INTO history (tablename, operation, details, TIMESTAMP) VALUES ('Employee', 'INSERT', details, now());\n" +
                "      RETURN new;\n" +
                "\n" +
                "    ELSIF (tg_op = 'UPDATE')\n" +
                "      THEN\n" +
                "        details = ('Employee update with  ', new.cpr, ' with name ', new.firstname);\n" +
                "\n" +
                "        INSERT INTO history (tablename, operation, details, TIMESTAMP) VALUES ('Employee', 'update', details, now());\n" +
                "        RETURN new;\n" +
                "\n" +
                "    ELSEIF (tg_op = 'DELETE')\n" +
                "      THEN\n" +
                "        details = ('Delete employee with  ', old.cpr, ' with name ', old.firstname);\n" +
                "\n" +
                "        INSERT INTO history (tablename, operation, details, TIMESTAMP) VALUES ('Employee', 'delete', details, now());\n" +
                "        RETURN old;\n" +
                "\n" +
                "    END IF;\n" +
                "  END IF;\n" +
                "\n" +
                "  IF (tg_table_name = 'userlogin')\n" +
                "  THEN\n" +
                "    IF (tg_op = 'INSERT')\n" +
                "    THEN\n" +
                "      details = ('Inserted user with  ', new.cpr, ' with password ', new.pass, ' and role ', new.userrole);\n" +
                "\n" +
                "      INSERT INTO history (tablename, operation, details, TIMESTAMP) VALUES ('userlogin', 'INSERT', details, now());\n" +
                "      RETURN new;\n" +
                "\n" +
                "    ELSIF (tg_op = 'UPDATE')\n" +
                "      THEN\n" +
                "        details = ('updated user with  ', new.cpr, ' with password ', new.pass, ' and role ', new.userrole);\n" +
                "\n" +
                "        INSERT INTO history (tablename, operation, details, TIMESTAMP) VALUES ('userlogin', 'UPDATE', details, now());\n" +
                "        RETURN new;\n" +
                "\n" +
                "    ELSEIF (tg_op = 'DELETE')\n" +
                "      THEN\n" +
                "        details = ('deleted user with  ', old.cpr, ' with password ', old.pass, ' and role ', old.userrole);\n" +
                "\n" +
                "        INSERT INTO history (tablename, operation, details, TIMESTAMP) VALUES ('userlogin', 'DELETE', details, now());\n" +
                "        RETURN old;\n" +
                "\n" +
                "    END IF;\n" +
                "  END IF;\n" +
                "\n" +
                "  IF (tg_table_name = 'wageperhour')\n" +
                "  THEN\n" +
                "    IF (tg_op = 'INSERT')\n" +
                "    THEN\n" +
                "      details = ('Inserted wage with  ', new.employeecpr, ' with wage ', new.wage);\n" +
                "\n" +
                "      INSERT INTO history (tablename, operation, details, TIMESTAMP) VALUES ('wageperhour', 'INSERT', details, now());\n" +
                "      RETURN new;\n" +
                "\n" +
                "    ELSIF (tg_op = 'UPDATE')\n" +
                "      THEN\n" +
                "        details = ('Updated wage with  ', new.employeecpr, ' with wage ', new.wage);\n" +
                "\n" +
                "        INSERT INTO history (tablename, operation, details, TIMESTAMP)\n" +
                "        VALUES ('wageperhour', 'UPDATE', details, now());\n" +
                "        RETURN new;\n" +
                "\n" +
                "    ELSEIF (tg_op = 'DELETE')\n" +
                "      THEN\n" +
                "        details = ('delete wage with  ', old.employeecpr, ' with wage ', old.wage);\n" +
                "\n" +
                "        INSERT INTO history (tablename, operation, details, TIMESTAMP)\n" +
                "        VALUES ('wageperhour', 'DELETE', details, now());\n" +
                "        RETURN old;\n" +
                "\n" +
                "    END IF;\n" +
                "  END IF;\n" +
                "  IF (tg_table_name = 'workingschedule')\n" +
                "  THEN\n" +
                "    IF (tg_op = 'INSERT')\n" +
                "    THEN\n" +
                "      details = ('Inserted workingschedule with  cpr ', new.employecpr, ' with working date ', new.workingday, ' in department', new.dno);\n" +
                "\n" +
                "      INSERT INTO history (tablename, operation, details, TIMESTAMP)\n" +
                "      VALUES ('workingschedule', 'INSERT', details, now());\n" +
                "      RETURN new;\n" +
                "\n" +
                "    ELSIF (tg_op = 'UPDATE')\n" +
                "      THEN\n" +
                "        details = ('Updated workingschedule with  cpr ', new.employecpr, ' with working date ', new.workingday, ' in department', new.dno);\n" +
                "\n" +
                "        INSERT INTO history (tablename, operation, details, TIMESTAMP)\n" +
                "        VALUES ('workingschedule', 'UPDATE', details, now());\n" +
                "        RETURN new;\n" +
                "\n" +
                "    ELSEIF (tg_op = 'DELETE')\n" +
                "      THEN\n" +
                "        details = ('Deleted workingschedule with  cpr ', old.employecpr, ' with working date ', old.workingday, ' in department', old.dno);\n" +
                "\n" +
                "        INSERT INTO history (tablename, operation, details, TIMESTAMP)\n" +
                "        VALUES ('workingschedule', 'DELETE', details, now());\n" +
                "        RETURN old;\n" +
                "\n" +
                "    END IF;\n" +
                "  END IF;\n" +
                "  RETURN new;\n" +
                "END;\n" +
                "$$ LANGUAGE plpgsql;\n" +
                "\n" +
                "\n" +
                "CREATE TRIGGER logOnInsert\n" +
                "AFTER INSERT OR UPDATE OR DELETE ON bankInfoDK\n" +
                "FOR EACH ROW\n" +
                "EXECUTE PROCEDURE historyAdd();\n" +
                "\n" +
                "CREATE TRIGGER logOnInsert\n" +
                "AFTER INSERT OR UPDATE OR DELETE ON city\n" +
                "FOR EACH ROW\n" +
                "EXECUTE PROCEDURE historyAdd();\n" +
                "\n" +
                "\n" +
                "CREATE TRIGGER logOnInsert\n" +
                "AFTER INSERT OR UPDATE OR DELETE ON communication\n" +
                "FOR EACH ROW\n" +
                "EXECUTE PROCEDURE historyAdd();\n" +
                "\n" +
                "\n" +
                "CREATE TRIGGER logOnInsert\n" +
                "AFTER INSERT OR UPDATE OR DELETE ON department\n" +
                "FOR EACH ROW\n" +
                "EXECUTE PROCEDURE historyAdd();\n" +
                "\n" +
                "\n" +
                "CREATE TRIGGER logOnInsert\n" +
                "AFTER INSERT OR UPDATE OR DELETE ON Employee\n" +
                "FOR EACH ROW\n" +
                "EXECUTE PROCEDURE historyAdd();\n" +
                "\n" +
                "\n" +
                "CREATE TRIGGER logOnInsert\n" +
                "AFTER INSERT OR UPDATE OR DELETE ON UserLogIn\n" +
                "FOR EACH ROW\n" +
                "EXECUTE PROCEDURE historyAdd();\n" +
                "\n" +
                "\n" +
                "CREATE TRIGGER logOnInsert\n" +
                "AFTER INSERT OR UPDATE OR DELETE ON wagePerHour\n" +
                "FOR EACH ROW\n" +
                "EXECUTE PROCEDURE historyAdd();\n" +
                "\n" +
                "\n" +
                "CREATE TRIGGER logOnInsert\n" +
                "AFTER INSERT OR UPDATE OR DELETE ON workingSchedule\n" +
                "FOR EACH ROW\n" +
                "EXECUTE PROCEDURE historyAdd();\n" +
                "\n" +
                "\n" +
                "INSERT INTO city (postcode, city) VALUES ('postcode', 'City');\n" +
                "INSERT INTO userlogin (cpr, username, pass, userrole) VALUES ('2345678901', 'MomoLina', 'Password123', 'Admin');\n" +
                "INSERT INTO userlogin (cpr, username, pass, userrole) VALUES ('3456789012', 'Radu1234', 'Password123', 'Admin');\n" +
                "INSERT INTO userlogin (cpr, username, pass, userrole) VALUES ('4567890123', 'ChocolateHercules', 'Password123', 'Admin');\n" +
                "INSERT INTO userlogin (cpr, username, pass, userrole) VALUES ('5678901234', 'Nikolay', 'Password123', 'Admin');";
        executeStatements(sql);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sql = "\n" +
                "CREATE MATERIALIZED VIEW EmployeeInformation AS\n" +
                "  SELECT\n" +
                "    employee.picture,\n" +
                "    UserLogIn.Username,\n" +
                "    UserLogIn.pass,\n" +
                "    employee.firstname,\n" +
                "    employee.secondName,\n" +
                "    employee.familyName,\n" +
                "    UserLogIn.cpr,\n" +
                "    employee.dateOfBirth,\n" +
                "    employee.address,\n" +
                "    employee.postcode,\n" +
                "    city.city,\n" +
                "    communication.mobile,\n" +
                "    communication.landline,\n" +
                "    communication.email,\n" +
                "    bankInfoDK.konto,\n" +
                "    bankInfoDK.regNumber,\n" +
                "    employee.licencePlate,\n" +
                "    communication.preferredCommunication,\n" +
                "    employee.moreInfo,\n" +
                "    wagePerHour.wage,\n" +
                "    UserLogIn.userRole\n" +
                "  FROM\n" +
                "    City\n" +
                "    INNER JOIN Employee ON city.postcode = Employee.postcode\n" +
                "    INNER JOIN Communication ON Employee.cpr = communication.cpr\n" +
                "    INNER JOIN bankInfoDK ON communication.cpr = bankInfoDK.cpr\n" +
                "    INNER JOIN wagePerHour ON bankInfoDK.cpr = wagePerHour.employeeCPR\n" +
                "    INNER JOIN userlogin ON wagePerHour.employeeCPR = UserLogIn.cpr;\n" +
                "\n" +
                "CREATE MATERIALIZED VIEW allUsersWithWage AS\n" +
                "  SELECT\n" +
                "    UserLogIn.cpr,\n" +
                "    UserLogIn.username,\n" +
                "    UserLogIn.pass,\n" +
                "    UserLogIn.userrole,\n" +
                "    wagePerHour.wage\n" +
                "  FROM UserLogIn\n" +
                "    INNER JOIN wagePerHour ON UserLogIn.cpr = wagePerHour.employeeCPR;\n" +
                "\n" +
                "CREATE MATERIALIZED VIEW workingColleagues AS\n" +
                "  SELECT\n" +
                "    picture,\n" +
                "    firstName,\n" +
                "    familyName,\n" +
                "    mobile,\n" +
                "    email,\n" +
                "    Employee.cpr,\n" +
                "    workingSchedule.dno\n" +
                "  FROM communication\n" +
                "    INNER JOIN employee ON communication.cpr = Employee.cpr\n" +
                "    LEFT OUTER JOIN workingSchedule ON Employee.cpr = workingSchedule.employecpr;\n" +
                "\n" +
                "CREATE MATERIALIZED VIEW allColleagues AS\n" +
                "  SELECT\n" +
                "    picture,\n" +
                "    firstName,\n" +
                "    familyName,\n" +
                "    mobile,\n" +
                "    email,\n" +
                "    Employee.cpr\n" +
                "  FROM communication\n" +
                "    INNER JOIN Employee ON communication.cpr = Employee.cpr;\n" +
                "\n" +
                "\n" +
                "CREATE MATERIALIZED VIEW usersByDepartment AS\n" +
                "  SELECT DISTINCT\n" +
                "    (workingSchedule.employecpr),\n" +
                "    employee.firstname,\n" +
                "    employee.familyName,\n" +
                "    workingSchedule.dno\n" +
                "  FROM employee\n" +
                "    INNER JOIN workingschedule ON Employee.cpr = workingSchedule.employecpr;\n";
        executeStatements(sql);
    }


    public void dropAll() {
        this.url = "jdbc:postgresql://localhost/postgres?currentSchema=sep2";
        openDataBase();
        String sql = "DROP SCHEMA sep2 CASCADE;";
        executeStatements(sql);
    }

    public void fixDate() {
        executeStatements("ALTER TABLE workingSchedule\n" +
                "  ADD CONSTRAINT check_date check(workingDay>=now());");
    }

    private void executeStatements(String sql) {

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            if (e.getSQLState().toString().equals("23505")) {
                System.out.println("CPR exists");
            } else {
                System.out.println(e.getSQLState());
                System.out.println(e.getMessage());
            }
        }


    }

    private void closeDataBase() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void openDataBase() {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.out.println("Wrong password or username");
        }
    }

    private void writeToFile(String username, String pass, String fileName) {

        ObjectOutputStream writeToFile = null;
        try {
            FileOutputStream fileOutStream = new FileOutputStream(fileName);
            writeToFile = new ObjectOutputStream(fileOutStream);

            writeToFile.writeObject(username);
            writeToFile.writeObject(pass);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writeToFile != null) {
                try {
                    writeToFile.close();
                } catch (IOException e) {
                    System.out.println("IO Error closing file " + fileName);
                }
            }
        }

    }

    public void deleteFile(String filename) {
        File f = new File(filename);
        if (f.exists()) {
            f.delete();
        }
        System.gc();
    }
}
