package server;

import main.databasehandlers.MainHandler;

import java.util.ArrayList;

public class DBAdapter implements IDBAdapter {
        //TODO if set is not found return empty array

    public DBAdapter() {

    }

    /**
     *
     * @param username
     * @return true if username exists.
     */
    @Override
    public boolean checkUsername(String username) {
        ArrayList temp = MainHandler.getResultSet("SELECT username from UserLogIn where username='" + username + "'; ");
        return temp.size() >= 1;
    }

    /**
     *
     * @param username
     * @return user after checkUsername returns true to user.
     *
     */
    @Override
    public String getUser(String username) {
        if (checkUsername(username)) {
            String sql = "SELECT password from UserLogIn WHERE username = '" + username + "';";
            ArrayList temp = MainHandler.getResultSet(sql);
            return (String) temp.get(0);
        }
        return null;
    }
}
