package server.databaseJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private Connection connection = null;
    private Statement statement = null;
    private String driver = "org.postgresql.Driver";
    private String url = "jdbc:postgresql://localhost/postgres";
    private String username;
    private String password;

    public Database(String username, String password) {
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

    private void everything() {
        String sql = "\n" +
                "CREATE DOMAIN cpr_Domain CHAR(10);\n" +
                "CREATE DOMAIN dno_Domain CHAR(4);\n" +
                "CREATE DOMAIN postcode_Domain VARCHAR(10);\n" +
                "\n" +
                "\n" +
                "CREATE TABLE UserLogIn (\n" +
                "  cpr      CPR_DOMAIN PRIMARY KEY,\n" +
                "  Username VARCHAR(25) UNIQUE CONSTRAINT username_minvalue CHECK (LENGTH(Username) > 4),\n" +
                "  pass     VARCHAR(100) CONSTRAINT password_minValue CHECK (LENGTH(pass) >= 8) CONSTRAINT password_check CHECK (\n" +
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
                "  city     VARCHAR(25)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE Employee (\n" +
                "  ID           SERIAL PRIMARY KEY,\n" +
                "  picture      VARCHAR,\n" +
                "  firstName    VARCHAR(25),\n" +
                "  secondName   VARCHAR(25),\n" +
                "  familyName   VARCHAR(25),\n" +
                "  cpr          CPR_DOMAIN REFERENCES UserLogIn (cpr),\n" +
                "  dateOfBirth  DATE,\n" +
                "  address      VARCHAR(25),\n" +
                "  postcode     VARCHAR(10) REFERENCES city (postcode),\n" +
                "  licencePlate VARCHAR,\n" +
                "  moreInfo     VARCHAR\n" +
                ");\n" +
                "\n" +
                "\n" +
                "CREATE TABLE communication (\n" +
                "  id                     SERIAL PRIMARY KEY,\n" +
                "  cpr                    CPR_DOMAIN REFERENCES UserLogIn (cpr),\n" +
                "  mobile                 CHAR(8),\n" +
                "  landline               CHAR(8),\n" +
                "  email                  VARCHAR,\n" +
                "  preferredCommunication VARCHAR DEFAULT 'Mobile' CHECK (preferredCommunication IN ('Mobile', 'Home', 'Email'))\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE bankInfoDK (\n" +
                "  id        SERIAL PRIMARY KEY,\n" +
                "  cpr       CPR_DOMAIN REFERENCES UserLogIn (cpr),\n" +
                "  konto     CHAR(4),\n" +
                "  regNumber CHAR(10)\n" +
                ");\n" +
                "\n" +
                "\n" +
                "CREATE TABLE department (\n" +
                "  dno        DNO_DOMAIN PRIMARY KEY,\n" +
                "  dname      VARCHAR,\n" +
                "  dManager   CHAR(10) REFERENCES UserLogIn (cpr),\n" +
                "  dPostcode  VARCHAR(10) REFERENCES city (postcode),\n" +
                "  dStartdate TIMESTAMP\n" +
                ");\n" +
                "\n" +
                "\n" +
                "CREATE TABLE workingSchedule (\n" +
                "  id         SERIAL PRIMARY KEY,\n" +
                "  dno        DNO_DOMAIN REFERENCES department (dno),\n" +
                "  employecpr CPR_DOMAIN REFERENCES UserLogIn (cpr),\n" +
                "  workingDay DATE,\n" +
                "  startHours TIME,\n" +
                "  endHours   TIME\n" +
                ");\n" +
                "\n" +
                "\n" +
                "CREATE TABLE wagePerHour (\n" +
                "  id          SERIAL PRIMARY KEY,\n" +
                "  employeeCPR CPR_DOMAIN REFERENCES UserLogIn (cpr),\n" +
                "  wage        NUMERIC(6, 2)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE history (\n" +
                "  id        SERIAL PRIMARY KEY,\n" +
                "  tablename VARCHAR,\n" +
                "  operation VARCHAR,\n" +
                "  details   VARCHAR,\n" +
                "  TIMESTAMP TIMESTAMP\n" +
                ");\n" +
                "--Functions\n" +
                "-- Trigger function create to automatically insert cpr,username and pass once employee is created as a USER\n" +
                "-- Trigger function that deletes employee data one employee is deleted\n" +
                "CREATE OR REPLACE FUNCTION newUserCreatedOrRemoved()\n" +
                "  RETURNS TRIGGER AS $$\n" +
                "BEGIN\n" +
                "  IF (tg_op = 'INSERT')\n" +
                "  THEN\n" +
                "    INSERT INTO Employee (cpr, postcode) VALUES (new.cpr, '1234');\n" +
                "    INSERT INTO communication (cpr) VALUES (new.cpr);\n" +
                "    INSERT INTO bankInfoDK (cpr) VALUES (new.cpr);\n" +
                "    INSERT INTO wagePerHour (employeeCPR) VALUES (new.cpr);\n" +
                "    RETURN new;\n" +
                "  ELSIF (tg_op = 'DELETE')\n" +
                "    THEN\n" +
                "      DELETE FROM employee\n" +
                "      WHERE old.cpr = cpr;\n" +
                "      DELETE FROM wagePerHour\n" +
                "      WHERE old.cpr = employeecpr;\n" +
                "      DELETE FROM bankInfoDK\n" +
                "      WHERE old.cpr = cpr;\n" +
                "      DELETE FROM communication\n" +
                "      WHERE old.cpr = cpr;\n" +
                "  END IF;\n" +
                "  RETURN old;\n" +
                "END;\n" +
                "$$ LANGUAGE plpgsql;\n" +
                "\n" +
                "CREATE TRIGGER newUserAdded\n" +
                "AFTER INSERT ON userlogin\n" +
                "FOR EACH ROW\n" +
                "EXECUTE PROCEDURE newUserCreatedOrRemoved();\n" +
                "\n" +
                "CREATE TRIGGER oldUserRemoved\n" +
                "BEFORE DELETE ON UserLogIn\n" +
                "FOR EACH ROW\n" +
                "EXECUTE PROCEDURE newUserCreatedOrRemoved();\n" +
                "\n" +
                "\n" +
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
                "    INNER JOIN Employee ON communication.cpr = Employee.cpr;\n";
        executeStatements(sql);
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

}
