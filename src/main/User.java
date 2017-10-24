package main;

import java.util.ArrayList;

public class User {
    private String User;
    private String Passworld;
    private Role role;
    private ArrayList<User> allUsers;


    public User(String user, String passworld) {
        User = user;
        Passworld = passworld;
        allUsers = new ArrayList<>();
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getPassworld() {
        return Passworld;
    }

    public void setPassworld(String passworld) {
        Passworld = passworld;
    }

    public ArrayList<User> getAllUsers() {

        return allUsers;
    }

}
