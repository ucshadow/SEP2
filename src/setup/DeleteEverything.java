package setup;

import java.util.Scanner;

public class DeleteEverything {
    public static void main(String[] args) {
        String username;
        String pass;
        System.out.println("Enter username for postgres");
        username = new Scanner(System.in).nextLine();
        System.out.println("Enter password for postgres");
        pass = new Scanner(System.in).nextLine();
        System.out.println("Database will be deleted in just a minute");
        Database database = new Database(username, pass);
        database.dropAll();
    }
}
