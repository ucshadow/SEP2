package client;

import common.User;

public class TTT {
    public static void main(String[] args) {
        Controller c = new Controller();
        User u = new User("Yusufs", "Asd12345678", "1212121213", "Admin");
        User n = new User("James", "Asd12345678", "1212121213", "Admin");


        User j = new User("Jess", "12345678A", "1029384756", "Admin");
        User k = new User("Robert", "12345678A", "0987654322", "Admin");

        User t = new User("Check","123456789A", "1213141517","Manager");
        User tt = new User("Check12","123456789A", "1213141517","Admin");

//         c.createUser(t);

//        c.createUser(u);
//        c.editUser(j);

         c.submitEdit(tt);
    }
}
