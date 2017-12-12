package server;


import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;

public class DBHandler {
    private Connection connection = null;
    private Statement statement = null;
    private String driver = "org.postgresql.Driver";
    private String url = "jdbc:postgresql://localhost/postgres?currentSchema=sep2";
    private String username;
    private String pass;

    public DBHandler() {
        try {
            read("postgresUsernameAndPass.txt");
            System.out.println(username);
            System.out.println(pass);
            connection = DriverManager.getConnection(url, username, pass);
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void read(String fileName) {
        ArrayList<Object> objs = new ArrayList<>();

        ObjectInputStream readFromFile = null;
        try {
            FileInputStream fileInStream = new FileInputStream(fileName);
            readFromFile = new ObjectInputStream(fileInStream);
            while (true) {
                try {
                    objs.add(readFromFile.readObject());
                } catch (EOFException eof) {
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (EOFException e) {
            System.out.println("I think the file " + fileName + " was empty");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (readFromFile != null) {
                try {
                    readFromFile.close();
                } catch (IOException e) {
                    System.out.println("IO Error closing file " + fileName);
                }
            }
        }
        username = (String) objs.get(0);
        pass = (String) objs.get(1);

    }

    public synchronized void executeStatements(String sql) {

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }
            if (e.getSQLState().toString().equals("23505")) {
                //Do nothing
            } else {
                System.out.println(e.getMessage());
            }
        }


    }

    public synchronized ArrayList getResultSet(String sql) {
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

    public synchronized ArrayList getSingleRow(String sql) {
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

    public synchronized ArrayList<String[]> getAllRows(String sql) {
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