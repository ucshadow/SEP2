package server;

import main.databasehandlers.MainHandler;

import java.util.ArrayList;

public class DBAdapter implements IDBAdapter{


    public DBAdapter(){

    }


    @Override
    public boolean checkUsername(String username) {

        ArrayList temp = MainHandler.getResultSet("SELECT " + username + " from UserLogIn;");
        return temp.size() >= 1 && (temp.get(0) == null || temp.get(0).equals(""));
    }

    @Override
    public String getUser(String username) {
        if(checkUsername(username)){
            String sql = "SELECT Password from UserLogIn WHERE Username = '" + username + "';";
            ArrayList temp = MainHandler.getResultSet(sql);
            return (String) temp.get(0);
        }
        return null;
    }
}
