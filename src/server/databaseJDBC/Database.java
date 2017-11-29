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
                "\n" +
                "CREATE TABLE UserLogIn (\n" +
                "  Username VARCHAR(25) UNIQUE CONSTRAINT username_minvalue CHECK (LENGTH(Username) > 4),\n" +
                "  cpr      CPR_DOMAIN,\n" +
                "  Password VARCHAR(100) CONSTRAINT password_minValue CHECK (LENGTH(Password) >= 8) CONSTRAINT password_check CHECK (\n" +
                "    Password LIKE '%A%' OR PASSWORD LIKE '%B%' OR PASSWORD LIKE '%C%' OR PASSWORD LIKE '%D%' OR PASSWORD LIKE '%E%' OR\n" +
                "    PASSWORD LIKE '%F%' OR PASSWORD LIKE '%G%' OR PASSWORD LIKE '%H%' OR PASSWORD LIKE '%I%' OR PASSWORD LIKE '%J%'\n" +
                "    OR PASSWORD LIKE '%K%' OR PASSWORD LIKE '%L%' OR PASSWORD LIKE '%M%' OR PASSWORD LIKE '%N%' OR\n" +
                "    PASSWORD LIKE '%O%' OR PASSWORD LIKE '%P%' OR PASSWORD LIKE '%Q%' OR PASSWORD LIKE '%R%' OR PASSWORD LIKE '%S%'\n" +
                "    OR PASSWORD LIKE '%T%' OR PASSWORD LIKE '%U%' OR PASSWORD LIKE '%V%' OR PASSWORD LIKE '%W%' OR PASSWORD LIKE '%X%'\n" +
                "    OR PASSWORD LIKE '%Y%' OR PASSWORD LIKE '%Z%'),\n" +
                "  userRole VARCHAR(7) DEFAULT 'Admin' CHECK (userRole IN ('Admin', 'User', 'Manager'))\n" +
                ");\n" +
                "\n" +
                "ALTER TABLE UserLogIn\n" +
                "  RENAME COLUMN Password TO pass;\n" +
                "ALTER TABLE UserLogIn\n" +
                "  ADD PRIMARY KEY (Username, cpr);\n" +
                "\n" +
                "CREATE TABLE Employee (\n" +
                "  picture                VARCHAR,\n" +
                "  username               VARCHAR,\n" +
                "  password               VARCHAR,\n" +
                "  firstName              VARCHAR(25),\n" +
                "  secondName             VARCHAR(25),\n" +
                "  familyName             VARCHAR(25),\n" +
                "  cpr                    CPR_DOMAIN PRIMARY KEY,\n" +
                "  dateOfBirth            DATE,\n" +
                "  address                VARCHAR(25),\n" +
                "  postcode               VARCHAR(10),\n" +
                "  city                   VARCHAR(25),\n" +
                "  mobile                 CHAR(8),\n" +
                "  landline               CHAR(8),\n" +
                "  email                  VARCHAR,\n" +
                "  konto                  CHAR(4),\n" +
                "  regNumber              CHAR(10),\n" +
                "  licencePlate           VARCHAR,\n" +
                "  preferredCommunication VARCHAR DEFAULT 'Mobile' CHECK (preferredCommunication IN ('Mobile', 'Home', 'Email')),\n" +
                "  moreInfo               VARCHAR,\n" +
                "  wage                   VARCHAR,\n" +
                "  userRole               VARCHAR\n" +
                "\n" +
                "\n" +
                ");\n" +
                "ALTER TABLE Employee\n" +
                "  RENAME COLUMN password TO passEmp;\n" +
                "CREATE TABLE department (\n" +
                "  dno       DNO_DOMAIN,\n" +
                "  dname     VARCHAR,\n" +
                "  dlocation VARCHAR,\n" +
                "  dManager  CHAR(10)\n" +
                "\n" +
                ");\n" +
                "\n" +
                "ALTER TABLE department\n" +
                "  ADD PRIMARY KEY (dno);\n" +
                "\n" +
                "\n" +
                "CREATE TABLE workingSchedule (\n" +
                "  id         SERIAL PRIMARY KEY,\n" +
                "  dno        DNO_DOMAIN,\n" +
                "  employecpr CPR_DOMAIN,\n" +
                "  workingDay DATE,\n" +
                "  startHours TIME,\n" +
                "  endHours   TIME\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE wagePerHour (\n" +
                "  employeeCPR CPR_DOMAIN PRIMARY KEY ,\n" +
                "  wage        NUMERIC(6, 2)\n" +
                ");\n" +
                "--Functions\n" +
                "-- Trigger function create to automatically insert cpr,username and pass once employee is created as a USER\n" +
                "-- Trigger function that deletes employee data one employee is deleted\n" +
                "CREATE OR REPLACE FUNCTION newEmployee()\n" +
                "  RETURNS TRIGGER AS $$\n" +
                "BEGIN\n" +
                "  IF (tg_op = 'INSERT')\n" +
                "  THEN\n" +
                "    INSERT INTO Employee\n" +
                "    VALUES\n" +
                "      ('', new.username, new.pass, '', '', '', new.cpr, NULL, '', '', '', '', '', '', '', '', '', NULL, '',\n" +
                "                                                                          '',\n" +
                "                                                                          new.userRole);\n" +
                "    RETURN new;\n" +
                "  ELSIF (tg_op = 'DELETE')\n" +
                "    THEN\n" +
                "      DELETE FROM employee\n" +
                "      WHERE old.cpr = cpr;\n" +
                "      DELETE FROM wagePerHour\n" +
                "      WHERE old.cpr = employee;\n" +
                "  END IF;\n" +
                "  RETURN old;\n" +
                "END;\n" +
                "$$ LANGUAGE plpgsql;\n" +
                "\n" +
                "CREATE OR REPLACE FUNCTION addWage()\n" +
                "  RETURNS TRIGGER AS $$\n" +
                "BEGIN\n" +
                "  IF (tg_op = 'INSERT')\n" +
                "  THEN\n" +
                "    UPDATE Employee\n" +
                "    SET wage = new.wage\n" +
                "    WHERE cpr = new.employeeCPR;\n" +
                "    RETURN new;\n" +
                "  END IF;\n" +
                "  RETURN old;\n" +
                "END;\n" +
                "$$ LANGUAGE plpgsql;\n" +
                "\n" +
                "--Trigger function created to update password upon change from employee table to userlogin table\n" +
                "CREATE OR REPLACE FUNCTION empPassword()\n" +
                "  RETURNS TRIGGER AS $$\n" +
                "BEGIN\n" +
                "  IF (tg_op = 'UPDATE')\n" +
                "  THEN\n" +
                "    UPDATE userLogIn\n" +
                "    SET pass = passEmp\n" +
                "    FROM employee\n" +
                "    WHERE userlogin.cpr = employee.cpr;\n" +
                "  END IF;\n" +
                "  RETURN new;\n" +
                "END;\n" +
                "$$ LANGUAGE plpgsql;\n" +
                "--FUNCTION END\n" +
                "-- TRIGGERS\n" +
                "\n" +
                "\n" +
                "CREATE TRIGGER passChange\n" +
                "AFTER UPDATE OF passEmp\n" +
                "  ON Employee\n" +
                "EXECUTE PROCEDURE empPassword();\n" +
                "\n" +
                "\n" +
                "CREATE TRIGGER newEmp\n" +
                "BEFORE INSERT OR DELETE ON userlogin\n" +
                "FOR EACH ROW\n" +
                "EXECUTE PROCEDURE newEmployee();\n" +
                "\n" +
                "\n" +
                "CREATE TRIGGER newEmpWage\n" +
                "BEFORE INSERT ON wagePerHour\n" +
                "FOR EACH ROW\n" +
                "EXECUTE PROCEDURE addWage();\n" +
                "\n" +
                "--TRIGGERS END";
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
