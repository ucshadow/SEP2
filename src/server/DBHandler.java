package server;

import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;

public class DBHandler {

    private static Connection connection = null;
    private static Statement statement = null;
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost/postgres?currentSchema=sep2";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "aev123";


    public static void executeStatements(String sql) {
        openDataBase();
        if (!check(sql)) {
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

            closeDataBase();
        } else System.out.println("KeyWords ");
    }

    public static ArrayList getResultSet(String statment) {
        ArrayList<String> temp = new ArrayList<>();
        if (!check(statment)) {
            try {
                Class.forName(DRIVER);
                openDataBase();

                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(statment);
                while (resultSet.next()) {
                    if (resultSet.getString(1).length() > 1) {
                        temp.add(resultSet.getString(1));
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
//        System.out.println("DBHandler" + temp);
            closeDataBase();
            return temp;
        } else System.out.println("Keywords");
        return null;
    }

    public static ArrayList getSingleRow(String statement) {
        ArrayList<String> temp = new ArrayList<>();
        if (!check(statement)) {
            try {
                Class.forName(DRIVER);
                String sql = statement;
                openDataBase();

                DBHandler.statement = connection.createStatement();
                ResultSet resultSet = DBHandler.statement.executeQuery(sql);
                while (resultSet.next()) {
                    for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                        temp.add(resultSet.getString(i));
                    }
                }
//            while (resultSet.next()) {
//                if (resultSet.getString(1).length() > 1) {
//                    temp.add(resultSet.getString(1));
//                }
//            }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
//        System.out.println("DBHandler" + temp);
            closeDataBase();
            return temp;
        } else System.out.println("Keywords");
        return null;
    }

    public static ArrayList<String[]> getAllRows(String statement) {
        ArrayList<String[]> temp = new ArrayList<>();
        String[] temp2 = null;
        if (!check(statement)) {
            try {
                Class.forName(DRIVER);
                openDataBase();
                DBHandler.statement = connection.createStatement();
                ResultSet resultSet = DBHandler.statement.executeQuery(statement);
                while (resultSet.next()) {
                    temp2 = new String[resultSet.getMetaData().getColumnCount()];
                    for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                        temp2[i - 1] = resultSet.getString(i);
                    }
                    temp.add(temp2);
                }
//            while (resultSet.next()) {
//                if (resultSet.getString(1).length() > 1) {
//                    temp.add(resultSet.getString(1));
//                }
//            }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
//        System.out.println("DBHandler" + temp);
            closeDataBase();
            return temp;
        } else System.out.println("Keywords");
        return null;
    }

    private static boolean check(String sql) {
        String[] keyWords = {"DROP", "DELETE", "create"};
        for (String item : keyWords) {
            if (sql.matches(item)) {
                return true;
            }
        }
        return false;
    }

    private static void closeDataBase() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void openDataBase() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}