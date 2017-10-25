package main.userlogin;

import java.util.ArrayList;

public class User {
    private String user;
    private String password;
    private Role role;
    private String cpr;
    private ArrayList<User> allUsers = new ArrayList<>();

    public User() {
    }

    public User(String user, String passworld, Role role, String cpr) {
        this.user = user;
        password = passworld;
        this.role = role;
        this.cpr = cpr;
        allUsers.add(this);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public ArrayList<main.userlogin.User> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(ArrayList<main.userlogin.User> allUsers) {
        this.allUsers = allUsers;
    }
}
