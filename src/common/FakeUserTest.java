package common;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class FakeUserTest {

    public static void main(String[] args) {
        FakeUser f = new FakeUser();
        f.setEverythingUp(10    , 3);
        System.out.println(f);
        f.dumpToPostgreslocaly();
//        System.out.println(f);

    }
}
