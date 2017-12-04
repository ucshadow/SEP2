package Test;


import static org.junit.Assert.*;

import common.Department;
import common.FakeUser;
import common.User;
import common.WorkingSchedule;
import org.junit.Before;
import org.junit.Test;

import server.DBAdapter;
import server.IDBAdapter;
import server.databaseJDBC.Database;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DBAdapterTest {

    private IDBAdapter idbAdapter;
    private User user;
    private Department department;
    private WorkingSchedule workingSchedule;
    private User user2;
    private DateTimeFormatter dataFormat;
    private User fake;
    private Database database;
    private FakeUser fakeUser;

    @Before
    public void Start() {
        idbAdapter = new DBAdapter();
        user = new User();
        user.setUsername("ForTesting");
        user.setPassword("123456789A");
        user.setUserRole("Admin");
        user.setCpr("1234567890");
        user.setWage("1234.2");

        fake = new User();
        fake.setUsername("Fake");
        fake.setPassword("0000000000");
        fake.setUserRole("Admin");
        fake.setCpr("2345678901");
        fake.setWage("1234.2");


        user2 = new User();
        user2.setUsername("ForTesting2");
        user2.setPassword("123456789B");
        user2.setUserRole("Admin");
        user2.setCpr("2345678901");
        user2.setWage("1234.2");
        dataFormat = DateTimeFormatter.ofPattern("dd/LL/yyyy");
        department = new Department("12345", "DepartmentForTesting", "1234", "1234567890");
        workingSchedule = new WorkingSchedule("12345", "2345678901", LocalDate.now().format(dataFormat), "08:00", "16:00");
        database = new Database("postgres", "1q2w3e");
        fakeUser = new FakeUser();
    }


    @Test
    public void createOneUser() {
        idbAdapter.createAccount(user);
        ArrayList<User> all = idbAdapter.getAllUsers();
        String wage = idbAdapter.getWagePerHour(user);
        assertEquals(1, all.size());
        assertEquals(user.getWage(), wage);
        assertEquals(user.getCpr(), all.get(0).getCpr());
        assertEquals(user.getUsername(), all.get(0).getUsername());
        assertEquals(user.getUserRole(), all.get(0).getUserRole());
        assertEquals(user.getPassword(), all.get(0).getPassword());
        database.deleteFromtables();

    }

    @Test
    public void createTenUsers() {
        fakeUser.setEverythingUp(10, 1);
        ArrayList<User> users = fakeUser.getWorkers();
        ArrayList<User> allUsersFromDB;
        for (int i = 0; i < users.size(); i++) {
            idbAdapter.createAccount(users.get(i));
            allUsersFromDB = idbAdapter.getAllUsers();
            assertTrue(allUsersFromDB.size() == i + 1);
        }
        allUsersFromDB = idbAdapter.getAllUsers();
        assertEquals(users.size(), allUsersFromDB.size());
        for (int i = 0; i < allUsersFromDB.size(); i++) {
            assertEquals(users.get(i).getCpr(), allUsersFromDB.get(i).getCpr());
            assertEquals(users.get(i).getUsername(), allUsersFromDB.get(i).getUsername());
            assertEquals(users.get(i).getUserRole(), allUsersFromDB.get(i).getUserRole());
            assertEquals(users.get(i).getPassword(), allUsersFromDB.get(i).getPassword());
        }

        database.deleteFromtables();

    }

    @Test
    public void createHounderdUsers() {
        fakeUser.setEverythingUp(100, 1);
        ArrayList<User> users = fakeUser.getWorkers();
        ArrayList<User> allUsersFromDB;
        for (int i = 0; i < users.size(); i++) {
            idbAdapter.createAccount(users.get(i));
            allUsersFromDB = idbAdapter.getAllUsers();
            assertTrue(allUsersFromDB.size() == i + 1);
        }
        allUsersFromDB = idbAdapter.getAllUsers();
        assertEquals(users.size(), allUsersFromDB.size());
        for (int i = 0; i < allUsersFromDB.size(); i++) {
            assertEquals(users.get(i).getCpr(), allUsersFromDB.get(i).getCpr());
            assertEquals(users.get(i).getUsername(), allUsersFromDB.get(i).getUsername());
            assertEquals(users.get(i).getUserRole(), allUsersFromDB.get(i).getUserRole());
            assertEquals(users.get(i).getPassword(), allUsersFromDB.get(i).getPassword());
        }

        database.deleteFromtables();
    }

    @Test
    public void editAccount() {
        fakeUser.setEverythingUp(10, 1);
        ArrayList<User> users = fakeUser.getWorkers();
        ArrayList<User> allUsersFromDB;
        for (int i = 0; i < users.size(); i++) {
            idbAdapter.createAccount(users.get(i));
            allUsersFromDB = idbAdapter.getAllUsers();
            assertTrue(allUsersFromDB.size() == i + 1);
        }
        for (User item : users
                ) {
            item.setPassword("Testing123");
            idbAdapter.editAccount(item);
        }
        allUsersFromDB = idbAdapter.getAllUsers();
        for (int i = 0; i < allUsersFromDB.size(); i++) {
            assertEquals(allUsersFromDB.get(i).getPassword(), allUsersFromDB.get(i).getPassword());
        }
        database.deleteFromtables();
    }

}
