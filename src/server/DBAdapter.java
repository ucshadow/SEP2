package server;

import common.User;

import java.util.ArrayList;

public class DBAdapter implements IDBAdapter {
    //TODO if set is not found return empty array

    public DBAdapter() {

    }

    /**
     * @param username
     * @return true if username exists.
     */
    @Override
    public boolean checkUsername(String username) {
        ArrayList temp = DBHandler.getResultSet("SELECT username from UserLogIn where username='" + username + "'; ");
        return temp.size() >= 1;
    }

    /**
     * @param username
     * @return user after checkUsername returns true to user.
     */
    @Override
    public String getUserPassword(String username) {
        if (checkUsername(username)) {
            String sql = "SELECT password from UserLogIn WHERE username = '" + username + "';";
            ArrayList temp = DBHandler.getResultSet(sql);
            return (String) temp.get(0);
        }
        return null;
    }

    public ArrayList getUserByCPR(String CPR) {
        String sql = "SELECT * from UserLogIn WHERE CPR = '" + CPR + "';";
        ArrayList temp = DBHandler.getSingleRow(sql);
        return temp;
    }

    public void createUser(User user) {
        DBHandler.executeStatements("INSERT INTO UserLogIn VALUES (" +
                "'" + user.getUsername() + "'," +
                "'" + user.getCpr() + "'," +
                "'" + user.getPassword() + "'," +
                "'" + user.getUserRole() + "'" +
                ")");
    }

    public void removeUser(User user) {
        DBHandler.executeStatements( "DELETE FROM userlogin WHERE cpr = '" + user.getCpr() + "';");

    }

    public void submitEdit(User user) {

        DBHandler.executeStatements( "update userlogin set username = '"  + user.getUsername() + "', cpr = '" + user.getCpr() +  "', pass = '" + user.getPassword() + "', userRole = '" + user.getUserRole() + "' where cpr = '" + user.getCpr() + "';");



                /*"UPDATE userlogin set"
                        + " username = " +
                        "'" + user.getUsername() + "',"
                        + " cpr = " +
                "'" + user.getCpr() + "',"
                + " password = " +
                "'" + user.getPassword() + "',"
                + " user-role = " +
                "'" + user.getUserRole() + "'"
                + " where cpr = " +
                "'" + user.getCpr() + "'");

    */

    }
}

