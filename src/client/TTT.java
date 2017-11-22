package client;

import common.User;

public class TTT {
    public static void main(String[] args) {
        Controller c = new Controller();
        User u = new User("Catalin", "Asd12345678", "1234567890", "Admin");
        c.createUser(u);
    }
}
