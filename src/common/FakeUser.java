package common;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * The FakeUser class generates a new user on invocation.
 *
 * @author Yusuf AJ Farah
 * @version 1.0
 * @since 2017-05-17
 */
public class FakeUser {

//    private String picture,password, cpr, address, postcode, mobile, landline,
//            email, konto, recnumber, licencePlate, prefferedCommunication, moreInfo, userRole, wage;

    private String[] firstNames = {"Luther", "Hershel", "Brandon", "Reed", "Cliff", "Darell", "Lupe", "Johnnie", "Jorge",
            "Felton", "Aldo", "Kraig", "Calvin", "Elwood", "Sam", "Andre", "Kelly", "Amado", "Gerard", "Moises", "Raptor",
            "Tracey", "Alvaro", "Quentin", "Mel", "Jared", "Horacio", "Fredrick", "Moshe", "Buford", "Sonny", "Jackie",
            "Branden", "Kristofer", "Dylan", "Kris", "Claudio", "Wilfred", "Alexis", "Daniel", "Darrick", "Kim",
            "Benton", "Shawn", "Lyndon", "Garret", "Josef", "Barton", "Cedrick", "Tory"};

    private String[] secondNames = {"Twyla", "Dorathy", "Missy", "Alishia", "Brenda", "Blanch", "Thea", "Alfreda",
            "Mireille", "Dione", "Agnus", "Felicia", "Avelina", "Leonia", "Rosie", "Jaimee", "Rosella", "Annamarie",
            "Alexa", "Crista", "France", "Zenia", "Seema", "Rosa", "Jackqueline", "Elaina", "Latonia", "Eboni",
            "Evalyn", "Hannah", "Kristeen", "Tula", "Kathryne", "Elisa", "Zina", "Wendolyn", "Joanne", "Anika",
            "Loise", "Fabiola", "Cathryn", "Jerlene", "Kari", "Erlene", "Doretha", "Marcy", "Shawanna", "Marica",
            "Vina", "Manda", "Brooks", "Columbus", "Lee", "Everette", "Raymundo", "Dexter", "Joesph", "Eli",
            "Ramon", "Javier", "Deon", "Donte", "Art", "Michel", "Perry", "Dean", "Aaron", "Jamie", "Clifton",
            "Terence", "Kennith", "Britt", "Harold", "Vincenzo", "Jacob", "Jules", "Harley", "Evan", "Alex", "Eddy",
            "Anton", "Teodoro", "Rolando", "Keneth", "Hosea", "Rodrigo", "Pablo", "Derick", "Kenneth", "Lino",
            "Demetrius", "Del", "Burt", "Nicolas", "Adan", "Russell", "Jospeh", "Troy", "Don", "Caleb"};

    private String[] lastNames = {"Twyla", "Dorathy", "Missy", "Alishia", "Brenda", "Blanch", "Thea", "Alfreda",
            "Mireille", "Dione", "Agnus", "Felicia", "Avelina", "Leonia", "Rosie", "Jaimee", "Rosella", "Annamarie",
            "Alexa", "Crista", "France", "Zenia", "Seema", "Rosa", "Jackqueline", "Elaina", "Latonia", "Eboni",
            "Evalyn", "Hannah", "Kristeen", "Tula", "Kathryne", "Elisa", "Zina", "Wendolyn", "Joanne", "Anika",
            "Loise", "Fabiola", "Cathryn", "Jerlene", "Kari", "Erlene", "Doretha", "Marcy", "Shawanna", "Marica",
            "Vina", "Manda", "Brooks", "Columbus", "Lee", "Everette", "Raymundo", "Dexter", "Joesph", "Eli",
            "Ramon", "Javier", "Deon", "Donte", "Art", "Michel", "Perry", "Dean", "Aaron", "Jamie", "Clifton",
            "Terence", "Kennith", "Britt", "Harold", "Vincenzo", "Jacob", "Jules", "Harley", "Evan", "Alex", "Eddy",
            "Anton", "Teodoro", "Rolando", "Keneth", "Hosea", "Rodrigo", "Pablo", "Derick", "Kenneth", "Lino",
            "Demetrius", "Del", "Burt", "Nicolas", "Adan", "Russell", "Jospeh", "Troy", "Don", "Caleb"};

    private String[] city = {"Copenhagen", "Aarhus", "Horsens", "Randers",
            "Vejle", "Odense", "Berlin", "Hamburg", "Dublin", "Honk Kong",
            "Bucurest", "Budapest", "Bratislava", "Sofia", "Cork", "Maimi",
            "Las Vegas", "Los Angelis", "Porto", "Milano", "Rome", "Viena",
            "Hanover", "Malta", "Oaho", "Dubai", "Zurich", "Barcelona",
            "Madrid", "Paris", "Sidney", "Moscow", "Istanbul", "London",
            "Prague", "Amsterdam", "Stockholm", "Riga", "Oslo", "Zagreb",
            "Reykjavik", "Edinburgh", "Florence", "Manchester"};

    Random rnd = new Random();

    protected String getUsername() {

        String usernamechars = "abcdefghijklmnopqrstuvwxyz1234567890";

        StringBuilder username = new StringBuilder();


        while (username.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * usernamechars.length());
            username.append(usernamechars.charAt(index));
        }

        String saltStr = username.toString();
        return saltStr;

    }

    private String getDateOfBirth() {
        long l = Math.abs(rnd.nextLong());
        Date date2 = new Date(Math.abs(l));

        GregorianCalendar g2 = new GregorianCalendar();
        g2.setTime(date2);

        return String.valueOf(g2.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(g2.get(Calendar.MONTH) + 1) +
                "/" + String.valueOf((1935 + rnd.nextInt(70)));
    }

    private String getCPR() {

        int answer = rnd.nextInt(10000) + 1;

        return Integer.toString(answer);
    }
}
