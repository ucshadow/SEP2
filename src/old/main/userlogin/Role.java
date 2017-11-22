package old.main.userlogin;

public class Role {
    private String role;

    public Role(String role) {
        if (roleCheck(role)) {
            this.role = role;
        }

    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        if (roleCheck(role)) {
            this.role = role;
        }
    }

    private boolean roleCheck(String role) {
        if (role.equals("Admin") || role.equals("User") || role.equals("Manager")) {
            return true;
        }
        return false;
    }
}
