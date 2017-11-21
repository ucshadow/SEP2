package main.databasehandlers;


public class CreateUser {


    public static void createNewUser(String username, String cpr, String password, String role) {
//        TODO do we need Role and User class
        String sql = "INSERT INTO UserLogIn VALUES ('" + username + "'" + cpr + "'" + password + "'" + role + "');";
        MainHandler.executeStatements(sql);
    }

}
