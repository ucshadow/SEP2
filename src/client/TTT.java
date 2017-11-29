package client;

import common.Department;
import common.User;

public class TTT {
    public static void main(String[] args) {
        Controller c = new Controller();
        User u = new User("MomoLandaasd", "Pass123456", "1234567890", "Admin", "1234");
        User n = new User("James", "Asd12345678", "1212121213", "Admin", "2121.00");
        User j = new User("Jess", "12345678A", "1029384756", "Admin", "3222.00");
        User k = new User("Robert", "12345678A", "0987654322", "Admin", "4222.32");
        User t = new User("Check", "123456789A", "1213141517", "Manager", "5222.99");
        User u1 = new User("MomoLansd", "Pass123s456", "1213421213", "Admin", "4321");
        User u3 = new User("1234567890", "999.90");
        User tt = new User("Check12", "123456789A", "1213141517", "Admin");
        User user = new User("Yusuf", "Lasdasd1234", "1234567890", "Admin");
        User username1 = new User("usernameTest", "passwordTest", "0123456969", "Admin");
//        User usernameChanged = new User("usernameTest", "passwordChange", "abcdef", "secondChange", "familyChange", "0123456969", "12/12/2012", "addressChange", "1000", "city1", "1234567", "1235", "mailChange", "1674", "123455", "12352463356", "Email", "morenfo");

        Department d1 = new Department("d001", "Ice", "London", "1212121213");
        Department d = new Department("D001", null, null, null);
        Department dnew = new Department("D001", "Freez", "Tokyo", "1313131313");
        Department d2 = new Department("D002", "Freezer0", "London", "1123456789");
        Department d3 = new Department("D003", "Jelly", "Paris", "1123456789");
        Department d4 = new Department("D003", null, null, null);

    }
}
