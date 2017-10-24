package main;

import main.Employee;

import java.sql.*;

public class Handler {

    private Connection connection = null;
    private Statement statement = null;
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=sep2";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "1q2w3e";

    public Handler() {
    }

    public void addNewEmployee(Employee a) {
        try {
            Class.forName(DRIVER);
            String sql = "INSERT INTO Employee VALUES (" + "'" + a.getFirstName() + "'," + "'" + a.getSecondName() + "'," + "'" + a.getFamilyName() + "'," + "'" + a.getCpr() + "'," + "'" + a.getDateOfBirth() + "'," + "'" + a.getAddress() + "'," + "'" + a.getPostCode() + "'," + "'" + a.getCity() + "'," + "'" + a.getMobilePhone() + "'," + "'" + a.getHomePhone() + "'," + "'" + a.getEmail() + "'," + "'" + a.getKonto() + "'," + "'" + a.getRegNumber() + "'," + "'" + a.getLicensePlate() + "'," + "'" + a.getPrefferd() + "'," + "'" + a.getMoreInformation() + "');";
            executeStatements(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateEmployee(Employee a) {
        try {
            Class.forName(DRIVER);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        if (checkCPR(a.getCpr())) {
            System.out.println("employe is here");
        } else {
        String sql = "INSERT INTO Employee VALUES (" + "'" + a.getFirstName() + "'," + "'" + a.getSecondName() + "'," + "'" + a.getFamilyName() + "'," + "'" + a.getCpr() + "'," + "'" + a.getDateOfBirth() + "'," + "'" + a.getAddress() + "'," + "'" + a.getPostCode() + "'," + "'" + a.getCity() + "'," + "'" + a.getMobilePhone() + "'," + "'" + a.getHomePhone() + "'," + "'" + a.getEmail() + "'," + "'" + a.getKonto() + "'," + "'" + a.getRegNumber() + "'," + "'" + a.getLicensePlate() + "'," + "'" + a.getPrefferd() + "'," + "'" + a.getMoreInformation() + "');";
            executeStatements(sql);
        }
    }

    private boolean checkCPR(String cpr) {
        try {
            Class.forName(DRIVER);
            String sql = "SELECT firstname from Employee WHERE  cpr = '" + cpr + "';";
            openDataBase();

            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if (resultSet.getString(1).length() > 1) {
                    System.out.println(resultSet.getString(1));
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        closeDataBase();

        return false;
    }

    private void executeStatements(String sql) {
        openDataBase();
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            if (e.getSQLState().toString().equals("23505")) {
                System.out.println("CPR exists");
            } else {
                System.out.println(e.getSQLState());
            }
        }

        closeDataBase();

    }


    /**
     * private method used to close connection to data base
     */
    private void closeDataBase() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * private method used to open connection for data base
     */
    private void openDataBase() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
