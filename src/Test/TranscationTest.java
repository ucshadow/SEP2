package Test;

import common.User;
import server.DBAdapter;
import server.DBHandler;

import java.util.ArrayList;

public class TranscationTest {

    public static void main(String[] args) {


        DBHandler dbHandler = new DBHandler();
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                dbHandler.executeStatements("update testing set number = number+1 where that='1';");
            }).start();
        }


        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            dbHandler.executeStatements("REFRESH MATERIALIZED VIEW employeeinformation;");

        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
