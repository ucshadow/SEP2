package main.userlogin;

public class LogIn {
    private String username;
    private String password;
    private User user;

    public LogIn(String username, String password) {
        user = new User();
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
