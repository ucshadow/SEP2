package main.userlogin;

import java.util.ArrayList;

public class User {
    private String user;
    private String cpr;
    private String password;
    private Role role;

    public User() {
    }

    public User(String user, String cpr, String password, Role role) {
        this.user = user;
        this.cpr = cpr;
        this.password = password;
        this.role = role;
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

}
