package common;

import client.Controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * The FakeUser class generates a new user on invocation.
 *
 * @author Yusuf AJ Farah
 * @version 1.0
 * @since 2017-05-17
 */


public class FakeUser {


    private String[] girlNames = {"Twyla", "Dorathy", "Missy", "Alishia", "Brenda", "Blanch", "Thea", "Alfreda",
            "Mireille", "Dione", "Agnus", "Felicia", "Avelina", "Leonia", "Rosie", "Jaimee", "Rosella", "Annamarie",
            "Alexa", "Crista", "France", "Zenia", "Seema", "Rosa", "Jackqueline", "Elaina", "Latonia", "Eboni",
            "Evalyn", "Hannah", "Kristeen", "Tula", "Kathryne", "Elisa", "Zina", "Wendolyn", "Joanne", "Anika",
            "Loise", "Fabiola", "Cathryn", "Jerlene", "Kari", "Erlene", "Doretha", "Marcy", "Shawanna", "Marica",
            "Vina", "Manda"};

    private String[] boyNames = {"Brooks", "Columbus", "Lee", "Everette", "Raymundo", "Dexter", "Joesph", "Eli",
            "Ramon", "Javier", "Deon", "Donte", "Art", "Michel", "Perry", "Dean", "Aaron", "Jamie", "Clifton",
            "Terence", "Kennith", "Britt", "Harold", "Vincenzo", "Jacob", "Jules", "Harley", "Evan", "Alex", "Eddy",
            "Anton", "Teodoro", "Rolando", "Keneth", "Hosea", "Rodrigo", "Pablo", "Derick", "Kenneth", "Lino",
            "Demetrius", "Del", "Burt", "Nicolas", "Adan", "Russell", "Jospeh", "Troy", "Don", "Caleb"};

    private String[] famNames = {"Luther", "Hershel", "Brandon", "Reed", "Cliff", "Darell", "Lupe", "Johnnie", "Jorge",
            "Felton", "Aldo", "Kraig", "Calvin", "Elwood", "Sam", "Andre", "Kelly", "Amado", "Gerard", "Moises", "Raptor",
            "Tracey", "Alvaro", "Quentin", "Mel", "Jared", "Horacio", "Fredrick", "Moshe", "Buford", "Sonny", "Jackie",
            "Branden", "Kristofer", "Dylan", "Kris", "Claudio", "Wilfred", "Alexis", "Daniel", "Darrick", "Kim",
            "Benton", "Shawn", "Lyndon", "Garret", "Josef", "Barton", "Cedrick", "Tory"};

    private String[] streets = {"Creek Road", "Summit Street", "River Road", "Hudson Street", "Lafayette Street",
            "Ridge Street", "Cardinal Drive", "Heritage Drive", "Oak Street", "Main Street South", "4th Street",
            "Myrtle Avenue", "Strawberry Lane", "Monroe Drive", "Arlington Avenue", "Hanover Court", "Pin Oak Drive",
            "Windsor Drive", "Essex Court", "4th Street North", "Spruce Street", "Roberts Road", "High Street",
            "Hawthorne Lane", "Creekside Drive", "4th Street West", "Bay Street", "9th Street", "Hillside Drive",
            "Garfield Avenue", "Hill Street", "Meadow Street", "Overlook Circle", "Bridle Lane", "Division Street",
            "Holly Court", "Route 44", "Olive Street", "Adams Street", "2nd Avenue"};

    private String[] city = {"Copenhagen", "Aarhus", "Horsens", "Randers",
            "Vejle", "Odense", "Berlin", "Hamburg", "Dublin", "Honk Kong",
            "Bucurest", "Budapest", "Bratislava", "Sofia", "Cork", "Maimi",
            "Las Vegas", "Los Angelis", "Porto", "Milano", "Rome", "Viena",
            "Hanover", "Malta", "Oaho", "Dubai", "Zurich", "Barcelona",
            "Madrid", "Paris", "Sidney", "Moscow", "Istanbul", "London",
            "Prague", "Amsterdam", "Stockholm", "Riga", "Oslo", "Zagreb",
            "Reykjavik", "Edinburgh", "Florence", "Manchester"};
    private Random random = new Random();

    private String[] getAName() {
        String[] name = new String[4];
        int gender = random.nextInt(10);
        if (gender <= 5) {
            name[0] = boyNames[random.nextInt(50)];
            name[1] = boyNames[random.nextInt(50)];
            name[2] = famNames[random.nextInt(50)];
            name[3] = famNames[random.nextInt(50)];
        } else {
            name[0] = girlNames[random.nextInt(50)];
            name[1] = girlNames[random.nextInt(50)];
            name[2] = famNames[random.nextInt(50)];
            name[3] = famNames[random.nextInt(50)];
        }
        return name;
    }

    private String getAUsername() {

        readEverything();

        String name = usernames[random.nextInt(50)];
        return name;
    }

    private String getAPicture() {

        readEverything();

        String name;
        int gender = random.nextInt(10);
        if (gender <= 5) {
            name = malePics[random.nextInt(50)];
        } else {
            name = femalePics[random.nextInt(50)];

        }
        return name;
    }


    private String getCPR() {
        int cpr1 = random.nextInt(77777) + 11111;
        int cpr2 = random.nextInt(77777) + 11111;
        return String.valueOf(cpr1) + String.valueOf(cpr2);
    }

    private String getDateOfBirth() {
        long l = Math.abs(random.nextLong());
        Date date2 = new Date(Math.abs(l));

        GregorianCalendar g2 = new GregorianCalendar();
        g2.setTime(date2);

        return String.valueOf(g2.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(g2.get(Calendar.MONTH) + 1) +
                "/" + String.valueOf((1935 + random.nextInt(70)));
    }

    private String getAddress() {
        return streets[random.nextInt(streets.length)];
    }

    private String getPostCode() {
        return String.valueOf(random.nextInt(7777) + 1111);
    }

    private String getCity() {
        return String.valueOf(city[random.nextInt(city.length)]);
    }

    private String getPhone() {
        int a = random.nextInt(7777) + 111;
        int b = random.nextInt(7777) + 111;
        return String.valueOf(a) + String.valueOf(b);
    }

    private String getLicensePlate() {
        int a = random.nextInt(91);
        int b = random.nextInt(91);
        while (a < 65) a = random.nextInt(91);
        while (b < 65) b = random.nextInt(91);
        return String.valueOf((char) a) +
                String.valueOf((char) b) +
                String.valueOf(random.nextInt(77777) + 11111);

    }

    private String getMail() {
        return "test@test.test";
    }

    private String getKonto() {
        return String.valueOf(random.nextInt(7777) + 1111);
    }

    private String getComm() {
        return "Mobile";
    }

    private String getWage() {
        Random rand = new Random();
        int n = rand.nextInt(10000 + 1);
        return Integer.toString(n);
    }

    private String getMore() {
        return "No more ";
    }

    private String getPass() {
        return "A12345";
    }

    String[] malePics = new String[300];
    String[] usernames = new String[300];
    String[] femalePics = new String[300];

    private void readEverything() {

        BufferedReader bufferedReader1;
        BufferedReader bufferedReader2;
        try {
            bufferedReader1 = new BufferedReader(new FileReader("C:\\Users\\Admin\\Desktop\\Sep2\\src\\usernames.txt"));

            for (int i = 0; i < 300; i++) {
                usernames[i] = bufferedReader1.readLine();
            }
            bufferedReader1.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bufferedReader1 = new BufferedReader(new FileReader("C:\\Users\\Admin\\Desktop\\Sep2\\src\\maleProfilePics.txt"));

            for (int i = 0; i < 300; i++) {
                malePics[i] = bufferedReader1.readLine();
            }
            bufferedReader1.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            bufferedReader2 = new BufferedReader(new FileReader("C:\\Users\\Admin\\Desktop\\Sep2\\src\\femaleProfilePics.txt"));

            for (int i = 0; i < 300; i++) {
                femalePics[i] = bufferedReader2.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void createAFakeAdmin() {
        ArrayList<String> newUserInformation = new ArrayList<>();

//        newUserInformation.add(getAPicture());
//        newUserInformation.add(getAName()[0]);
//        newUserInformation.add(getAName()[1]);
//        newUserInformation.add(getAName()[2]);
//        newUserInformation.add(getDateOfBirth());
//        newUserInformation.add(getAddress());
//        newUserInformation.add(getPostCode());
//        newUserInformation.add(getCity());
//        newUserInformation.add(getPhone());
//        newUserInformation.add(getPhone());
//        newUserInformation.add(getMail());
//        newUserInformation.add(getKonto());
//        newUserInformation.add(getCPR());
//        newUserInformation.add(getLicensePlate());
//        newUserInformation.add(getComm());
//        newUserInformation.add(getMore());

        newUserInformation.add(getAUsername()); //
        newUserInformation.add(getPass()); //
        newUserInformation.add(getCPR()); //
        newUserInformation.add("Admin"); //
        newUserInformation.add(getWage()); //

        new Controller().createUser(getAUsername(), getPass(), getCPR(), "Admin", getWage());


    }

    private void createAFakeUser() {
        ArrayList<String> newUserInformation = new ArrayList<>();


        newUserInformation.add(getAUsername()); //
        newUserInformation.add(getPass()); //
        newUserInformation.add(getCPR()); //
        newUserInformation.add("Admin"); //
        newUserInformation.add(getWage()); //

        new Controller().createUser(getAUsername(), getPass(), getCPR(), "Admin", getWage());

    }
}
