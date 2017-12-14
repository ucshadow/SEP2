package Test;

import common.FakeUser;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class FakeUserTest {

    public static void main(String[] args) {
        FakeUser f = new FakeUser();
        f.setEverythingUp(20    , 2);
        f.dumpToPostgreslocaly();
    }
}
