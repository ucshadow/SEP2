package server;


import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;

public class DBHandler {
    private Connection connection = null;
    private Statement statement = null;
    private String driver = "org.postgresql.Driver";
    private String url = "jdbc:postgresql://localhost/postgres?currentSchema=sep2";
    private String username = "postgres";
    private String pass = "postgres";

    public DBHandler() {
        try {
            connection = DriverManager.getConnection(url, username, pass);

            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        } catch (SQLException e) {
            System.out.println("Errorrr");
            System.out.println("=========================================================");
            e.printStackTrace();
        }
    }

    public void executeStatements(String sql) {

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
                System.out.println("Roll back catch");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }
            if (e.getSQLState().toString().equals("23505")) {
                System.out.println("CPR exists");
            } else {
                System.out.println(e.getSQLState());
                System.out.println(e.getMessage());
            }
        }


    }

    public ArrayList getResultSet(String sql) {
        ArrayList<String> temp = new ArrayList<>();

        try {
            Class.forName(driver);

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
        return temp;

    }

    public ArrayList getSingleRow(String sql) {
        ArrayList<String> temp = new ArrayList<>();

        try {
            Class.forName(driver);

            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    temp.add(resultSet.getString(i));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return temp;

    }

    public ArrayList<String[]> getAllRows(String sql) {
        ArrayList<String[]> temp = new ArrayList<>();
        String[] temp2 = null;

        try {
            Class.forName(driver);

            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                temp2 = new String[resultSet.getMetaData().getColumnCount()];
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    temp2[i - 1] = resultSet.getString(i);
                }
                temp.add(temp2);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return temp;

    }


}