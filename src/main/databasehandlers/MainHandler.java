package main.databasehandlers;

import java.sql.*;
import java.util.ArrayList;

public class MainHandler {

    private static Connection connection = null;
    private static Statement statement = null;
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=sep2";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "1q2w3e";


    public static void executeStatements(String sql) {
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

    public static ArrayList getResultSet(String statment) {
        ArrayList<String> temp = new ArrayList<>();
        try {
            Class.forName(DRIVER);
            String sql = statment;
            openDataBase();

            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if (resultSet.getString(1).length() > 1) {
                    temp.add(resultSet.getString(1));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        closeDataBase();
        return temp;

    }


    /**
     * private method used to close connection to data base
     */
    private static void closeDataBase() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * private method used to open connection for data base
     */
    private static void openDataBase() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}