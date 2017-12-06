package Test;

import server.DBHandler;

public class TranscationTest {

    public static void main(String[] args) {
        DBHandler dbHandler = new DBHandler();
//        for (int i = 0; i < 10000; i++) {
//            new Thread(() -> {
//                dbHandler.executeStatements("update testing set number = number+1 where that='1';");
//            }).start();
//        }

        dbHandler.executeStatements("INSERT INTO workingschedule (dno, employecpr, workingday, starthours, endhours)\n" +
                "VALUES ('1234', '2345678901', to_date('" + " 20/1/2017" + "', 'dd/mm/yyyy'),'08:00','09:00');");

    }
}
