package Test;

import server.DBHandler;

public class TranscationTest {

    public static void main(String[] args) {
        DBHandler dbHandler = new DBHandler();
        for (int i = 0; i < 2000000; i++) {
            new Thread(() -> {
                dbHandler.executeStatements("update testing set number = number+1 where that='1';");
            }).start();
        }

    }
}
