package server.databaseJDBC;


import java.util.Scanner;

public class MainDataBase {
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
        System.out.println("Thank you for your time database is ready");
    }

}
