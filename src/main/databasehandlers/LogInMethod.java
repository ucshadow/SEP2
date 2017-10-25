package main.databasehandlers;


import java.util.ArrayList;

public class LogInMethod {

    public static boolean authentication(String username, String password) {
        ArrayList<String> temp = checkUserNames();
        for (String item : temp) {
            if (item.equals(username)) {
                ArrayList<String> pass = checkPassword(username);
                for (String items : pass) {
                    if (items.equals(password)) {
                        return true;
                        //ToDO gui part for opening :)
                    }
                }
            }
        }
        return false;
    }

    private static ArrayList checkUserNames() {
        ArrayList temp = MainHandler.getResultSet("SELECT Username from UserLogIn;");
        return temp;
    }

    private static ArrayList checkPassword(String username) {
        String sql = "SELECT Password from UserLogIn WHERE Username = '" + username + "';";
        ArrayList temp = MainHandler.getResultSet(sql);
        return temp;
    }

    public static void main(String[] args) {
        LogInMethod.authentication("sdfgfffffff", "asdasdsAdasd");
    }
}
