package setup;


import common.FakeUser;

import java.util.Scanner;

public class SetUp {
    public static void main(String[] args) {
        String username;
        String pass;
        System.out.println("Enter username for postgres");
        username = new Scanner(System.in).nextLine();
        System.out.println("Enter password for postgres");
        pass = new Scanner(System.in).nextLine();
        System.out.println("Database will be ready in just a minute");
        Database database = new Database(username, pass);
        database.mainMethod();
        System.out.println("Database is ready");
        System.out.println("Creating fake data");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        FakeUser fake = new FakeUser();
        fake.setEverythingUp(150, 3);
        fake.dumpToPostgreslocaly();
        System.out.println("Done");
        database.fixDate();
        System.out.println("Thank you for your time");
        System.out.println("Now you can log in respectively :\n\n\n");
        System.out.println("                    Troels Mortensen");
        System.out.println("             Admin username : AdminTroels");
        System.out.println("             User username : UserTroels");
        System.out.println("             Password : Password123\n\n\n");
        System.out.println("                    Jens Cramer Alkjærsig");
        System.out.println("                  Admin username : AdminJens");
        System.out.println("                  User username : UserJens");
        System.out.println("                  Password : Password123\n\n\n");
        System.out.println("Auto closing");
    }

}
