package common;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class FakeUserTest {

    public static void main(String[] args) {
        FakeUser f = new FakeUser();
        f.setEverythingUp(300, 60);
        System.out.println(f);
        f.dumpToPostgress();
//        System.out.println(f);

    }
}
