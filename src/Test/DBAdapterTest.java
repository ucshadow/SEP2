package Test;


import static org.junit.Assert.*;

import common.Department;
import common.FakeUser;
import common.User;
import common.WorkingSchedule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.DBAdapter;
import server.IDBAdapter;
import setup.Database;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class DBAdapterTest {

    private IDBAdapter idbAdapter;
    private User user;
    private User fakeNull;
    private Database database;
    private FakeUser fakeUser;
    private ArrayList<User> fakeUsers;
    private ArrayList<User> fromDB;
    private ArrayList<Department> fakeDepartments;
    private ArrayList<Department> dFromDB;
    private ArrayList<WorkingSchedule> fakeWorkingSchedule;
    private ArrayList<WorkingSchedule> wsFromDB;

    @Before
    public void Start() {

        idbAdapter = new DBAdapter();
        user = new User();
        user.setUsername("ForTesting");
        user.setPassword("123456789A");
        user.setUserRole("Admin");
        user.setCpr("1234567890");
        user.setWage("1234.2");

        fakeNull = new User();
        fakeNull.setUsername(null);
        fakeNull.setPassword(null);
        fakeNull.setUserRole(null);
        fakeNull.setCpr(null);
        fakeNull.setWage(null);
        database = new Database("postgres", "1q2w3e");
        database.deleteFromtables();
        fakeUser = new FakeUser();
    }

    @After
    public void tearDown() {

        database.deleteFromtables();
    }

    @Test
    public void createUser() {
        idbAdapter.createAccount(user);
        fromDB = idbAdapter.getAllUsers();
        assertEquals(1, fromDB.size());
        assertEquals(user.getCpr(), fromDB.get(0).getCpr());
        assertEquals(user.getUsername(), fromDB.get(0).getUsername());
        assertEquals(user.getUserRole(), fromDB.get(0).getUserRole());
        assertEquals(user.getPassword(), fromDB.get(0).getPassword());

        //Trying null
        try {
            idbAdapter.createAccount(fakeNull);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        database.deleteFromtables();

        fakeUser.setEverythingUp(10, 1);
        fakeUsers = fakeUser.getWorkers();
        ArrayList<User> fromDB;
        for (int i = 0; i < fakeUsers.size(); i++) {
            idbAdapter.createAccount(fakeUsers.get(i));
            fromDB = idbAdapter.getAllUsers();
            assertTrue(fromDB.size() == i + 1);
        }
        fromDB = idbAdapter.getAllUsers();
        assertEquals(fakeUsers.size(), fromDB.size());
        for (int i = 0; i < fromDB.size(); i++) {
            assertEquals(fakeUsers.get(i).getCpr(), fromDB.get(i).getCpr());
            assertEquals(fakeUsers.get(i).getUsername(), fromDB.get(i).getUsername());
            assertEquals(fakeUsers.get(i).getUserRole(), fromDB.get(i).getUserRole());
            assertEquals(fakeUsers.get(i).getPassword(), fromDB.get(i).getPassword());
        }

        database.deleteFromtables();

    }

    @Test
    public void editAccount() {
        fakeUser.setEverythingUp(10, 1);
        fakeUsers = fakeUser.getWorkers();
        for (int i = 0; i < fakeUsers.size(); i++) {
            idbAdapter.createAccount(fakeUsers.get(i));
            fromDB = idbAdapter.getAllUsers();
            assertTrue(fromDB.size() == i + 1);
        }
        for (User item : fakeUsers
                ) {
            item.setPassword("Testing123");
            idbAdapter.editAccount(item);
        }
        fromDB = idbAdapter.getAllUsers();
        for (int i = 0; i < fromDB.size(); i++) {
            assertEquals(fromDB.get(i).getPassword(), fromDB.get(i).getPassword());
        }
        fakeUsers.get(1).setPassword("NoPassword1");
        idbAdapter.editAccount(fakeUsers.get(1));
        fromDB = idbAdapter.getAllUsers();
        User userforTest = null;
        for (User item : fromDB
                ) {
            if (item.getCpr().equalsIgnoreCase(fakeUsers.get(1).getCpr())) {
                userforTest = item;
            }
        }
        assertEquals(userforTest.getPassword(), fakeUsers.get(1).getPassword());

        database.deleteFromtables();
    }

    @Test
    public void deleteAccount() {
        fakeUser.setEverythingUp(5, 1);
        fakeUsers = fakeUser.getWorkers();
        for (User item : fakeUsers
                ) {
            idbAdapter.createAccount(item);
        }
        fromDB = idbAdapter.getAllUsers();
        assertEquals(fakeUsers.size(), fromDB.size());
        for (User item : fromDB
                ) {
            idbAdapter.removeAccount(item);
        }
        fromDB = idbAdapter.getAllUsers();
        assertEquals(0, fromDB.size());

        database.deleteFromtables();


    }

    @Test
    public void createDepartment() {
        fakeUser.setEverythingUp(10, 10);
        fakeDepartments = fakeUser.getDepartments();
        fakeUsers = fakeUser.getWorkers();
        for (User item : fakeUsers) {
            idbAdapter.createAccount(item);
        }
        for (Department item : fakeDepartments
                ) {
            idbAdapter.createDepartment(item);
        }
        dFromDB = idbAdapter.getAllDepartments();
        assertEquals(fakeDepartments.size(), dFromDB.size());
        assertEquals(fakeDepartments.get(0).getdNumber(), dFromDB.get(0).getdNumber());
        assertEquals(fakeDepartments.get(fakeDepartments.size() - 1).getdLocation(), dFromDB.get(dFromDB.size() - 1).getdLocation());


        database.deleteFromtables();
    }

    @Test
    public void editDepartments() {
        fakeUser.setEverythingUp(15, 5);
        fakeDepartments = fakeUser.getDepartments();
        fakeUsers = fakeUser.getWorkers();
        for (User item : fakeUsers) {
            idbAdapter.createAccount(item);
        }
        for (Department item : fakeDepartments
                ) {
            idbAdapter.createDepartment(item);
        }
        dFromDB = idbAdapter.getAllDepartments();
        assertEquals(dFromDB.size(), fakeDepartments.size());
        Department temp = new Department(fakeDepartments.get(0).getdNumber(), "Ice", "1234", fakeUsers.get(0).getCpr());

        idbAdapter.editDepartment(temp, fakeDepartments.get(0));
        ArrayList<Department> temp3 = idbAdapter.getAllDepartments();
        Department temp2 = null;
        for (Department item : temp3) {
            if (item.getdNumber().equals(fakeDepartments.get(0).getdNumber())) {
                temp2 = new Department(item.getdNumber(), item.getdName(), item.getdLocation(), item.getdManager());
            }
        }
        assertEquals(temp.getdNumber(), temp2.getdNumber());
        assertEquals(temp.getdName(), temp2.getdName());
        assertEquals(temp.getdLocation(), temp2.getdLocation());

        database.deleteFromtables();
    }


    @Test
    public void deleteDepartments() {
        fakeUser.setEverythingUp(15, 5);
        fakeDepartments = fakeUser.getDepartments();
        fakeUsers = fakeUser.getWorkers();
        for (User item : fakeUsers) {
            idbAdapter.createAccount(item);
        }
        for (Department item : fakeDepartments
                ) {
            idbAdapter.createDepartment(item);
        }
        dFromDB = idbAdapter.getAllDepartments();

        assertEquals(fakeDepartments.size(), dFromDB.size());

        for (int i = 0; i < dFromDB.size(); i++) {
            assertEquals(fakeDepartments.get(i).getdNumber(), dFromDB.get(i).getdNumber());
            idbAdapter.deleteDepartment(dFromDB.get(i));
        }
        dFromDB = idbAdapter.getAllDepartments();
        assertEquals(0, dFromDB.size());

        database.deleteFromtables();
    }

    @Test
    public void getAllDepartments() {
        fakeUser.setEverythingUp(15, 5);
        fakeDepartments = fakeUser.getDepartments();
        fakeUsers = fakeUser.getWorkers();
        for (User item : fakeUsers) {
            idbAdapter.createAccount(item);
        }
        for (Department item : fakeDepartments
                ) {
            idbAdapter.createDepartment(item);
        }
        dFromDB = idbAdapter.getAllDepartments();

        assertEquals(fakeDepartments.size(), dFromDB.size());


        for (int i = 0; i < dFromDB.size(); i++) {
            assertEquals(fakeDepartments.get(i).getdNumber(), dFromDB.get(i).getdNumber());
            assertEquals(fakeDepartments.get(i).getdName(), dFromDB.get(i).getdName());
            assertEquals(fakeDepartments.get(i).getdManager(), dFromDB.get(i).getdManager());
            assertEquals(fakeDepartments.get(i).getdLocation(), dFromDB.get(i).getdLocation());

        }

        database.deleteFromtables();
    }


    @Test
    public void changeWage() {
        fakeUser.setEverythingUp(3, 1);
        fakeUsers = fakeUser.getWorkers();
        for (User item : fakeUsers) {
            idbAdapter.createAccount(item);
        }
        User test = idbAdapter.getUserInfOForAdmin(fakeUsers.get(0));
        User test2 = idbAdapter.getUserInfOForAdmin(fakeUsers.get(1));
        User test3 = idbAdapter.getUserInfOForAdmin(fakeUsers.get(2));
        assertEquals(fakeUsers.get(0).getWage(), test.getWage().trim());
        assertEquals(fakeUsers.get(1).getWage(), test2.getWage().trim());
        assertEquals(fakeUsers.get(2).getWage(), test3.getWage().trim());
        fakeUsers.get(0).setWage("12345");
        fakeUsers.get(1).setWage("2345");
        fakeUsers.get(2).setWage("345");
        idbAdapter.changeWagePerHours(fakeUsers.get(0));
        idbAdapter.changeWagePerHours(fakeUsers.get(1));
        idbAdapter.changeWagePerHours(fakeUsers.get(2));
        User tes = idbAdapter.getUserInfOForAdmin(fakeUsers.get(0));
        User tes2 = idbAdapter.getUserInfOForAdmin(fakeUsers.get(1));
        User tes3 = idbAdapter.getUserInfOForAdmin(fakeUsers.get(2));
        assertEquals(fakeUsers.get(0).getWage(), tes.getWage().trim());
        assertEquals(fakeUsers.get(1).getWage(), tes2.getWage().trim());
        assertEquals(fakeUsers.get(2).getWage(), tes3.getWage().trim());

        database.deleteFromtables();
    }

    @Test
    public void changeUserInfo() {
        fakeUser.setEverythingUp(4, 1);
        fakeUsers = fakeUser.getWorkers();
        for (User item : fakeUsers) {
            idbAdapter.createAccount(item);
        }
        String[] forTest = {"Firsttest", "SecondTest", "ThirdTest", "FortTest"};
        fakeUsers.get(0).setFirstName(forTest[0]);
        fakeUsers.get(1).setFirstName(forTest[1]);
        fakeUsers.get(2).setFirstName(forTest[2]);
        fakeUsers.get(3).setFirstName(forTest[3]);
        for (User item : fakeUsers) {
            idbAdapter.changeUserInformation(item);
        }
        fromDB = idbAdapter.getAllColleagues();
        assertEquals(fakeUsers.get(0).getFirstName(), fromDB.get(0).getFirstName());
        assertEquals(fakeUsers.get(1).getFirstName(), fromDB.get(1).getFirstName());
        assertEquals(fakeUsers.get(2).getFirstName(), fromDB.get(2).getFirstName());
        assertEquals(fakeUsers.get(3).getFirstName(), fromDB.get(3).getFirstName());

        fakeUsers.get(0).setLastName(forTest[3]);
        fakeUsers.get(1).setLastName(forTest[2]);
        fakeUsers.get(2).setLastName(forTest[1]);
        fakeUsers.get(3).setLastName(forTest[0]);
        for (User item : fakeUsers) {
            idbAdapter.changeUserInformation(item);
        }
        fromDB = idbAdapter.getAllColleagues();
        assertEquals(fakeUsers.get(0).getLastName(), fromDB.get(0).getLastName());
        assertEquals(fakeUsers.get(1).getLastName(), fromDB.get(1).getLastName());
        assertEquals(fakeUsers.get(2).getLastName(), fromDB.get(2).getLastName());
        assertEquals(fakeUsers.get(3).getLastName(), fromDB.get(3).getLastName());


        fakeUsers.get(0).setSecondName("TryAndCatch");
        idbAdapter.changeUserInformation(fakeUsers.get(0));
        User user = idbAdapter.logIn(fakeUsers.get(0));
        assertEquals(fakeUsers.get(0).getSecondName(), user.getSecondName());

        fakeUsers.get(0).setPassword("TryAndCatch123");
        idbAdapter.changeUserInformation(fakeUsers.get(0));
        user = idbAdapter.logIn(fakeUsers.get(0));
        assertEquals(fakeUsers.get(0).getPassword(), user.getPassword());

        fakeUsers.get(0).setEmail("TryAndCatch@via.dk");
        idbAdapter.changeUserInformation(fakeUsers.get(0));
        user = idbAdapter.logIn(fakeUsers.get(0));
        assertEquals(fakeUsers.get(0).getEmail(), user.getEmail());

        fakeUsers.get(0).setMobile("12345678");
        idbAdapter.changeUserInformation(fakeUsers.get(0));
        user = idbAdapter.logIn(fakeUsers.get(0));
        assertEquals(fakeUsers.get(0).getMobile(), user.getMobile());

        fakeUsers.get(0).setKonto("3214");
        idbAdapter.changeUserInformation(fakeUsers.get(0));
        user = idbAdapter.logIn(fakeUsers.get(0));
        assertEquals(fakeUsers.get(0).getKonto(), user.getKonto());

        fakeUsers.get(0).setUserRole("User");
        idbAdapter.changeUserInformation(fakeUsers.get(0));
        user = idbAdapter.logIn(fakeUsers.get(0));
        assertEquals(fakeUsers.get(0).getUserRole(), user.getUserRole());


        database.deleteFromtables();
    }

    @Test
    public void getAllCol() {
        fakeUser.setEverythingUp(4, 1);
        fakeUsers = fakeUser.getWorkers();
        for (User item : fakeUsers) {
            idbAdapter.createAccount(item);
        }
        fromDB = idbAdapter.getAllColleagues();
        assertEquals(fakeUsers.size(), fromDB.size());

        database.deleteFromtables();
    }

    @Test
    public void getAllUsers() {
        fakeUser.setEverythingUp(4, 1);
        fakeUsers = fakeUser.getWorkers();
        for (User item : fakeUsers) {
            idbAdapter.createAccount(item);
        }
        fromDB = idbAdapter.getAllUsers();
        assertEquals(fakeUsers.size(), fromDB.size());

        database.deleteFromtables();
    }

    @Test
    public void logIn() {
        database.deleteFromtables();
        fakeUser.setEverythingUp(2, 1);
        fakeUsers = fakeUser.getWorkers();
        for (User item : fakeUsers) {
            idbAdapter.createAccount(item);
        }
        fromDB = idbAdapter.getAllUsers();
        assertEquals(fakeUsers.size(), fromDB.size());
        User user = idbAdapter.logIn(fakeUsers.get(0));
        assertEquals(fakeUsers.get(0).getUserRole(), user.getUserRole());
        assertEquals(fakeUsers.get(0).getUsername(), user.getUsername());
        assertEquals(fakeUsers.get(0).getPassword(), user.getPassword());
        assertEquals(fakeUsers.get(0).getWage().trim(), user.getWage().trim());


        database.deleteFromtables();
    }

    @Test
    public void addWorkingSchedule() {
        fakeUser.setEverythingUp(100, 12);
        fakeUsers = fakeUser.getWorkers();
        fakeDepartments = fakeUser.getDepartments();
        fakeWorkingSchedule = fakeUser.getWorkingHours();
        for (User item : fakeUsers) {
            idbAdapter.createAccount(item);
        }
        for (Department item : fakeDepartments) {
            idbAdapter.createDepartment(item);
        }

        int day = LocalDate.now().getDayOfMonth();
        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();
        String date = day + "/" + month + "/" + year;
        ArrayList<WorkingSchedule> wsArray = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            WorkingSchedule ws = new WorkingSchedule(fakeDepartments.get(i).getdNumber(), fakeUsers.get(i).getCpr(), date, "08:00", "10:00");
            idbAdapter.addToWorkingSchedule(ws);
            wsArray.add(ws);
        }
        int s = 0;
        for (int i = 0; i < 10; i++) {
            wsFromDB = idbAdapter.workingSchedulePerWeek(fakeUsers.get(i));
            assertEquals(1, wsFromDB.size());
            s++;
            assertEquals(wsFromDB.get(0).getDepartmentNumber(), fakeDepartments.get(i).getdNumber());
            assertEquals(wsFromDB.get(0).getEmployeeCPR(), fakeUsers.get(i).getCpr());
        }
        assertEquals(s, wsArray.size());

        database.deleteFromtables();
    }


    @Test
    public void getWorkingSchedule() {
        fakeUser.setEverythingUp(100, 3);
        fakeUsers = fakeUser.getWorkers();
        fakeDepartments = fakeUser.getDepartments();
        fakeWorkingSchedule = fakeUser.getWorkingHours();
        for (User item : fakeUsers) {
            idbAdapter.createAccount(item);
        }
        for (Department item : fakeDepartments) {
            idbAdapter.createDepartment(item);
        }
        for (WorkingSchedule item : fakeWorkingSchedule) {
            idbAdapter.addToWorkingSchedule(item);
        }
        wsFromDB = idbAdapter.workingSchedulePerWeek(fakeUsers.get(0));
        ArrayList<WorkingSchedule> db = new ArrayList<>();
        int currentDayInWeek = LocalDate.now().getDayOfWeek().getValue();
        int day = LocalDate.now().getDayOfMonth();
        int monday = day - currentDayInWeek;
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        int sunday = day + (7 - currentDayInWeek);
        for (WorkingSchedule item : fakeWorkingSchedule) {
            String[] b = item.getWorkingDate().split("/");
            int valueDay = Integer.parseInt(b[0]);
            int valueMonth = Integer.parseInt(b[1]);
            int valueYear = Integer.parseInt(b[2]);
            if (item.getEmployeeCPR().equalsIgnoreCase(fakeUsers.get(0).getCpr()) && valueDay >= monday && valueMonth == month && valueYear == year && valueDay <= sunday) {
                db.add(item);
            }
        }
        assertEquals(db.size(), wsFromDB.size());
        assertEquals(db.get(0).getDepartmentNumber(), wsFromDB.get(0).getDepartmentNumber());
        assertEquals(db.get(0).getEmployeeCPR(), fakeUsers.get(0).getCpr());


        database.deleteFromtables();
    }


    @Test
    public void getWorkingDepartments() {
        fakeUser.setEverythingUp(100, 3);
        fakeUsers = fakeUser.getWorkers();
        fakeDepartments = fakeUser.getDepartments();
        fakeWorkingSchedule = fakeUser.getWorkingHours();
        for (User item : fakeUsers) {
            idbAdapter.createAccount(item);
        }
        for (Department item : fakeDepartments) {
            idbAdapter.createDepartment(item);
        }
        for (WorkingSchedule item : fakeWorkingSchedule) {
            idbAdapter.addToWorkingSchedule(item);
        }
        wsFromDB = idbAdapter.workingSchedulePerWeek(fakeUsers.get(0));
        ArrayList<WorkingSchedule> db = new ArrayList<>();
        for (WorkingSchedule item : fakeWorkingSchedule) {
            if (item.getEmployeeCPR().equalsIgnoreCase(fakeUsers.get(0).getCpr())) {
                db.add(item);
            }
        }
        ArrayList<String> allD = new ArrayList<>();
        for (int i = 0; i < db.size(); i++) {

            if (!allD.contains(db.get(i).getDepartmentNumber())) {
                allD.add(db.get(i).getDepartmentNumber());
            }
        }
        ArrayList<String> temp = idbAdapter.getWorkingDepartments(fakeUsers.get(0));
        assertEquals(allD.size(), temp.size());
        long first = 0;
        long second = 0;

        for (int i = 0; i < allD.size(); i++) {
            first += Integer.valueOf(allD.get(i));
            second += Integer.valueOf(temp.get(i));
        }

        assertEquals(first, second);

        database.deleteFromtables();
    }

    @Test
    public void getWorkingCol() {
        fakeUser.setEverythingUp(20, 1);
        fakeUsers = fakeUser.getWorkers();
        fakeDepartments = fakeUser.getDepartments();
        fakeWorkingSchedule = fakeUser.getWorkingHours();
        for (User item : fakeUsers) {
            idbAdapter.createAccount(item);
        }
        for (Department item : fakeDepartments) {
            idbAdapter.createDepartment(item);
        }
        for (WorkingSchedule item : fakeWorkingSchedule) {
            idbAdapter.addToWorkingSchedule(item);
        }
        wsFromDB = idbAdapter.workingSchedulePerWeek(fakeUsers.get(0));
        ArrayList<WorkingSchedule> db = new ArrayList<>();
        for (WorkingSchedule item : fakeWorkingSchedule) {
            if (item.getEmployeeCPR().equalsIgnoreCase(fakeUsers.get(0).getCpr())) {
                db.add(item);
            }
        }
        ArrayList<String> allD = new ArrayList<>();
        for (int i = 0; i < db.size(); i++) {

            if (!allD.contains(db.get(i).getDepartmentNumber())) {
                allD.add(db.get(i).getDepartmentNumber());
            }
        }
        ArrayList<String> temp = idbAdapter.getWorkingDepartments(fakeUsers.get(0));
        assertEquals(allD.size(), temp.size());
        for (int i = 0; i < temp.size(); i++) {
            assertEquals(temp.get(i), allD.get(i));
        }

        idbAdapter.createAccount(user);
        int day = LocalDate.now().getDayOfMonth();
        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();
        String date = day + "/" + month + "/" + year;
        WorkingSchedule ws = new WorkingSchedule(fakeDepartments.get(0).getdNumber(), user.getCpr(), date, "08:00", "10:00");

        idbAdapter.addToWorkingSchedule(ws);
        ArrayList<User> workingCol = idbAdapter.getWorkingColleagues(user);
        assertEquals(workingCol.size(), fakeUsers.size() + 1);

        database.deleteFromtables();
    }


    @Test
    public void getUsersByDepartment() {
        fakeUser.setEverythingUp(15, 5);
        fakeUsers = fakeUser.getWorkers();
        fakeDepartments = fakeUser.getDepartments();
        fakeWorkingSchedule = fakeUser.getWorkingHours();
        for (User item : fakeUsers) {
            idbAdapter.createAccount(item);
        }
        for (Department item : fakeDepartments) {
            idbAdapter.createDepartment(item);
        }
        for (WorkingSchedule item : fakeWorkingSchedule) {
            idbAdapter.addToWorkingSchedule(item);
        }
        ArrayList<String> allDdepartments = new ArrayList<>();
        ArrayList<String> allDEmployees = new ArrayList<>();
        for (WorkingSchedule itemForAllD :
                fakeWorkingSchedule) {
            if (!allDdepartments.contains(itemForAllD.getDepartmentNumber())) {
                allDdepartments.add(itemForAllD.getDepartmentNumber());
            }
        }
        for (Department item : fakeDepartments
                ) {
            ArrayList<User> dbResult = idbAdapter.getUsersByDepartment(item);
            for (WorkingSchedule items : fakeWorkingSchedule) {
                if (item.getdNumber().equals(items.getDepartmentNumber())) {
                    if (!allDEmployees.contains(items.getEmployeeCPR())) {
                        allDEmployees.add(items.getEmployeeCPR());
                    }
                }
            }
            assertEquals(dbResult.size(), allDEmployees.size());
            allDEmployees.clear();
            for (String item2 : allDdepartments
                    ) {
                if (item2.equalsIgnoreCase(item.getdNumber())) {
                    assertEquals(item2, item.getdNumber());
                }
            }

        }

    }

    @Test
    public void usersWithoutWOrkingSchedule() {
        fakeUser.setEverythingUp(15, 5);
        fakeUsers = fakeUser.getWorkers();
        fakeDepartments = fakeUser.getDepartments();
        fakeWorkingSchedule = fakeUser.getWorkingHours();
        for (User item : fakeUsers) {
            idbAdapter.createAccount(item);
        }
        idbAdapter.createAccount(user);
        for (Department item : fakeDepartments) {
            idbAdapter.createDepartment(item);
        }
        for (WorkingSchedule item : fakeWorkingSchedule) {
            idbAdapter.addToWorkingSchedule(item);
        }
        fromDB = idbAdapter.getAllUsersWithoutWorkingSchedule();
        assertEquals(fromDB.size(), 1);
        fakeUser.setEverythingUp(20, 5);
        fakeUsers = fakeUser.getWorkers();

        for (User item : fakeUsers
                ) {
            idbAdapter.createAccount(item);
        }
        fromDB = idbAdapter.getAllUsersWithoutWorkingSchedule();
        assertEquals(fromDB.size(), 21);

    }

    @Test
    public void getUserForAdmin() {
        fakeUser.setEverythingUp(15, 5);
        fakeUsers = fakeUser.getWorkers();
        fakeDepartments = fakeUser.getDepartments();
        fakeWorkingSchedule = fakeUser.getWorkingHours();
        for (User item : fakeUsers) {
            idbAdapter.createAccount(item);
        }
        for (User item : fakeUsers) {
            idbAdapter.changeUserInformation(item);
        }
        for (User item : fakeUsers) {
            User u = idbAdapter.getUserInfOForAdmin(item);
            assertEquals(item.getCpr(), u.getCpr());
            assertEquals(item.getUsername(), u.getUsername());
            assertEquals(item.getUserRole(), u.getUserRole());
            assertEquals(item.getPassword(), u.getPassword());
            assertEquals(item.getFirstName(), u.getFirstName());
            assertEquals(item.getLastName(), u.getLastName());
            assertEquals(item.getMoreInfo(), u.getMoreInfo());
        }
    }

    @Test
    public void getHistoryWorkingSchedule() {
        fakeUser.setEverythingUp(10, 1);
        fakeUsers = fakeUser.getWorkers();
        fakeDepartments = fakeUser.getDepartments();
        fakeWorkingSchedule = fakeUser.getWorkingHours();
        for (User item : fakeUsers) {
            idbAdapter.createAccount(item);
        }
        idbAdapter.createAccount(user);
        for (Department item : fakeDepartments) {
            idbAdapter.createDepartment(item);
        }
        for (WorkingSchedule item : fakeWorkingSchedule) {
            idbAdapter.addToWorkingSchedule(item);
        }
        for (User item : fakeUsers) {
            idbAdapter.changeUserInformation(item);
        }
        ArrayList<WorkingSchedule> wsTEST = new ArrayList<>();
        for (User item : fakeUsers) {
            wsFromDB = idbAdapter.getHistoryWorkingSchedule(item);
            for (WorkingSchedule item2 : fakeWorkingSchedule) {
                if (item.getCpr().equals(item2.getEmployeeCPR())) {
                    wsTEST.add(item2);
                }
            }
            assertEquals(wsFromDB.size(), wsTEST.size());
            for (int i = 0; i < wsTEST.size(); i++) {
                assertEquals(wsFromDB.get(i).getDepartmentNumber(), wsTEST.get(i).getDepartmentNumber());
                assertEquals(wsFromDB.get(i).getEmployeeCPR(), wsTEST.get(i).getEmployeeCPR());
                String[] b = wsFromDB.get(i).getWorkingDate().split("-");
                int valueYear = Integer.parseInt(b[0]);
                int valueMonth = Integer.parseInt(b[1]);
                int valueDay = Integer.parseInt(b[2]);
                String forTest;
                if (valueDay < 10) {
                    forTest = "0" + valueDay + "/" + valueMonth + "/" + valueYear;
                } else {
                    forTest = valueDay + "/" + valueMonth + "/" + valueYear;
                }
                assertEquals(forTest, wsTEST.get(i).getWorkingDate());
                assertEquals(wsFromDB.get(i).getStartHours(), wsTEST.get(i).getStartHours() + ":00");
                assertEquals(wsFromDB.get(i).getEndHours(), wsTEST.get(i).getEndHours() + ":00");
            }

            wsTEST.clear();
        }
    }

}
