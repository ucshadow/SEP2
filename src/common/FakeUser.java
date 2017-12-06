package common;

import client.Controller;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * The FakeUser class generates a new user on invocation.
 *
 * @author Yusuf AJ Farah, Catalin Udrea
 * @version 1.1
 * @since 2017-05-17
 */


public class FakeUser {

    // toDo: some people work more than one day, so make it a week

    private ArrayList<String> citiesZip;
    private ArrayList<String> boyNames;
    private ArrayList<String> girlNames;
    private ArrayList<String> boyPics;
    private ArrayList<String> girlPics;
    private ArrayList<String> familyNames;
    private ArrayList<String> adjectives;
    private ArrayList<String> streetNames;
    private Random random;

    private int interval = 10;

    private ArrayList<User> workers = new ArrayList<>();
    private ArrayList<Department> departments = new ArrayList<>();
    private ArrayList<WorkingSchedule> workingHours = new ArrayList<>();

    public FakeUser() {
        citiesZip = readFile("city.txt");
        boyNames = readFile("boyNames.txt");
        girlNames = readFile("girlNames.txt");
        boyPics = readFile("maleProfilePics.txt");
        girlPics = readFile("femaleProfilePics.txt");
        familyNames = readFile("familyNames.txt");
        adjectives = readFile("adj.txt");
        streetNames = readFile("streetNames.txt");
        random = new Random();
    }


    private ArrayList<String> readFile(String fileName) {
        ArrayList<String> res = new ArrayList<>();
        try {
            Files.lines(FileSystems.getDefault().getPath(System.getProperty("user.dir") + "/src/", fileName))
                    .forEach(res::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    private Department createDepartment(String managerCPR) {
        return new Department(randomNumber(7), randomAdjective(),
                randomCityZip().split(",")[0], managerCPR);
    }

    private User createFullDetailsUser() {
        boolean isGirl = tossCoin();
        String[] cityZip = randomCityZip().split(",");
        String username = isGirl ? randomGirlUsername() : randomBoyUsername();

        return new User(isGirl ? randomGirlPic() : randomBoyPic(),
                username,
                "Password123",
                isGirl ? randomGirlName() : randomBoyName(),
                isGirl ? randomGirlName() : randomBoyName(),
                randomFamilyName(),
                randomNumber(10),
                randomBirthday(),
                randomStreetName(),
                cityZip[0],
                cityZip[1],
                randomNumber(8),
                randomNumber(8),
                username + "@gmail.com",
                randomNumber(4),
                randomNumber(10),
                randomNumber(3) + "-" + username,
                "Mobile",
                "More info here",
                randomNumber(4),
                "User");
    }

    private String randomNumber(int numberLength) {
        long l = random.nextLong();
        String s = String.valueOf(l);
        return s.substring(1, numberLength + 1);
    }

    private String randomGirlName() {
        return girlNames.get(random.nextInt(girlNames.size()));
    }

    private String randomBoyName() {
        return boyNames.get(random.nextInt(boyNames.size()));
    }

    private String randomFamilyName() {
        return familyNames.get(random.nextInt(familyNames.size()));
    }

    private String randomCityZip() {
        return citiesZip.get(random.nextInt(citiesZip.size()));
    }

    private String randomGirlPic() {
        return girlPics.get(random.nextInt(girlPics.size()));
    }

    private String randomBoyPic() {
        return boyPics.get(random.nextInt(boyPics.size()));
    }

    private String randomStreetName() {
        return streetNames.get(random.nextInt(streetNames.size()));
    }

    private boolean tossCoin() {
        return random.nextBoolean();
    }

    private String randomAdjective() {
        return adjectives.get(random.nextInt(adjectives.size()));
    }

    private String randomGirlUsername() {
        String s = "";
        s += randomAdjective();
        s += randomGirlName();
        s += randomGirlName();
        return s;
    }

    private String randomBoyUsername() {
        String s = "";
        s += randomAdjective();
        s += randomBoyName();
        s += randomBoyName();
        return s;
    }

    private String randomBirthday() {
        long unixTime = System.currentTimeMillis() / 1000L;
        long dob = unixTime - Long.valueOf(randomNumber(9));

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return Instant.ofEpochSecond(dob)
                .atZone(ZoneId.of("GMT-1"))
                .format(formatter);
    }

    private String randomDayInTheFuture() {
        long unixTime = System.currentTimeMillis() / 1000L;
        long dob = unixTime + Long.valueOf(randomNumber(1)) * 86000;

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return Instant.ofEpochSecond(dob)
                .atZone(ZoneId.of("GMT-1"))
                .format(formatter);
    }

    private User promoteToManager() {
        User u = workers.remove(random.nextInt(workers.size()));
        u.setUserRole("Manager");
        return u;
    }

    private String getDepartmentNumberByManagerCPR(String cpr) {
        for (Department d : departments) {
            if (d.getdManager().equals(cpr)) {
                return d.getdNumber();
            }
        }
        return null;
    }

    private String getRandomDepartmentNumber() {
        int rnd = random.nextInt(departments.size());
        return departments.get(rnd).getdNumber();
    }

    public void setEverythingUp(int numberOfEmployees, int numberOfDepartments) {
        for (int i = 0; i < numberOfEmployees; i++) {
            workers.add(createFullDetailsUser());
        }
        promoteSomeToManager(numberOfDepartments);
    }

    private void promoteSomeToManager(int numberOfDepartments) {
        ArrayList<User> managers = new ArrayList<>();
        for (int i = 0; i < numberOfDepartments; i++) {
            managers.add(promoteToManager());
        }
        setUpDepartments(managers);
    }

    private void setUpDepartments(ArrayList<User> managers) {
        for (User u : managers) {
            departments.add(createDepartment(u.getCpr()));
            workers.add(u); // add manager back to workers
        }
        setupWorkingHours();
    }

    private String formatHour(String hour) {
        if (hour.length() == 1) {
            hour = "0" + hour;
        }
        return hour + ":00";
    }

    private void setupWorkingHours() {
        for (User u : workers) {
            if (u.getUserRole().equals("Manager") || u.getUserRole().equals("User")) {
                fixWorkingHoursForDateInterval(u.getCpr());
            }
        }
    }

    private void fixWorkingHoursForDateInterval(String CPR) {
        Random r = new Random();
        int start = random.nextInt(12);
        int dayOfYear = LocalDate.now().getDayOfYear();
        int workingTime = r.nextInt(60);
        int workingFrom = dayOfYear - workingTime;
        int workingDays = r.nextInt(4) + 1;
        int workedWeeks = (int) (Math.ceil(workingTime / 7.0));
        System.out.println("working weeks -> " + workedWeeks);
        System.out.println("working days per week -> " + workingDays);


        System.out.println("all worked days:");
        ArrayList<Integer> workingDates = new ArrayList<>();

//        System.out.println(Math.ceil(20 / 7.0));

        for (int i = 0; i < workedWeeks; i++) {
            for (int day = 0; day < workingDays; day++) {
                workingDates.add(workingFrom + day);
            }
            workingFrom += 7;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LL/yyyy");

        workingDates.forEach(e -> {
            LocalDate localDate = LocalDate.now().withDayOfYear(e);
            String formattedString = localDate.format(formatter);
            workingHours.add(new WorkingSchedule(
                    getDepartmentNumberByManagerCPR(CPR),
                    CPR,
                    formattedString,
                    formatHour(String.valueOf(start)),
                    formatHour(String.valueOf(start + 8)))
            );
        });
//        System.out.println(workingDates);
//
//        System.out.println();
    }

    @Override
    public String toString() {
        workers.forEach(System.out::println);
        System.out.println();
        departments.forEach(System.out::println);
        System.out.println();
        workingHours.forEach(System.out::println);
        return "";
    }

    public void dumpToPostgress() {
        Controller c = new Controller();

        // create users
        for (User u : workers) {
            c.createUser(u.getUsername(), u.getPassword(), u.getCpr(), u.getUserRole(), u.getWage());
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // update users
        for (User u : workers) {
            ArrayList<String> attributes = new ArrayList<>();
            attributes.add(u.getPicture());
            attributes.add(u.getUsername());
            attributes.add(u.getPassword());
            attributes.add(u.getFirstName());
            attributes.add(u.getSecondName());
            attributes.add(u.getLastName());
            attributes.add(u.getCpr());
            attributes.add(u.getDob());
            attributes.add(u.getAddress());
            attributes.add(u.getPostcode());
            attributes.add(u.getCity());
            attributes.add(u.getMobile());
            attributes.add(u.getLandline());
            attributes.add(u.getEmail());
            attributes.add(u.getKonto());
            attributes.add(u.getRecnumber());
            attributes.add(u.getLicencePlate());
            attributes.add(u.getPrefferedCommunication());
            attributes.add(u.getMoreInfo());
            attributes.add(u.getUserRole());
            attributes.add(u.getWage());
            c.changeUserInformation(attributes);
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // update Departments
        for (Department d : departments) {
            c.createDepartment(d.getdNumber(), d.getdName(), d.getdLocation(), d.getdManager());
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (WorkingSchedule w : workingHours) {
            c.addWorkingSchedule(w.getDepartmentNumber(), w.getEmployeeCPR(), w.getWorkingDate(),
                    w.getStartHours(), w.getEndHours());

            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
