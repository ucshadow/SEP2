package server;

import common.Department;
import common.User;
import common.WorkingSchedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class DBAdapter implements IDBAdapter {
    private Calendar calendar;
    private DBHandler dbHandler;

    public DBAdapter() {
        calendar = Calendar.getInstance();
        dbHandler = new DBHandler();
    }


    @Override
    public void createAccount(User user) {
        String sql = "INSERT INTO UserLogIn VALUES (" + "'" + user.getCpr() + "'," + "'" + user.getUsername() + "'," + "'" + user.getPassword() + "'," + "'" + user.getUserRole() + "'" + ")";
        dbHandler.executeStatements(sql);
        sql = " update wageperhour set wage ='" + user.getWage() + "' where employeecpr='" + user.getCpr() + "' ;";
        dbHandler.executeStatements(sql);
    }

    @Override
    public void editAccount(User user) {
        String sql = "update userlogin set username = '" + user.getUsername() + "', pass = '" + user.getPassword() + "', userRole = '" + user.getUserRole() + "' where cpr = '" + user.getCpr() + "';";
        dbHandler.executeStatements(sql);
    }

    @Override
    public void removeAccount(User user) {
        String sql = "DELETE FROM userlogin WHERE cpr = '" + user.getCpr() + "';";
        dbHandler.executeStatements(sql);
    }

    //    @Override
    //    public boolean checkUsername(String username) {
    //        ArrayList temp = dbHandler.getResultSet("SELECT username from UserLogIn where username='" + username + "'; ");
    //        return temp.size() >= 1;
    //    }

    //    @Override
    //    public String getUserPassword(String username) {
    //        if (checkUsername(username)) {
    //            String sql = "SELECT password from UserLogIn WHERE username = '" + username + "';";
    //            ArrayList temp = dbHandler.getResultSet(sql);
    //            return (String) temp.get(0);
    //        }
    //        return null;
    //    }

    @Override
    public void changeUserInformation(User user) {
        String sql = "Update userlogin set pass ='" + user.getPassword() + "' where cpr ='" + user.getCpr() + "';";
        dbHandler.executeStatements(sql);
        sql = "Update communication set mobile ='" + user.getMobile() + "',landline='" + user.getLandline() + "',email='" + user.getEmail() + "', preferredCommunication ='" + user.getPrefferedCommunication() + "' where cpr ='" + user.getCpr() + "';";
        dbHandler.executeStatements(sql);
        sql = "update bankinfodk set konto ='" + user.getKonto() + "', regnumber ='" + user.getRecnumber() + "' where cpr ='" + user.getCpr() + "';";
        dbHandler.executeStatements(sql);
        sql = "Insert into city values ('" + user.getPostcode() + "','" + user.getCity() + "');";
        dbHandler.executeStatements(sql);
        sql = "Update Employee set picture ='" + user.getPicture() + "',dateofbirth='" + user.getDob() + "',address='" + user.getAddress() + "',postcode='" + user.getPostcode() + "',licenceplate='" +
                user.getLicencePlate() + "',moreinfo ='" + user.getMoreInfo() + "',firstname ='" + user.getFirstName() + "',secondName ='" + user.getSecondName() + "',familyName='" + user.getLastName() + "' where cpr ='" + user.getCpr() + "';";
        dbHandler.executeStatements(sql);

    }

    @Override
    public void changeWagePerHours(User user) {
        String sql = "Update wagePerHour set wage ='" + user.getWage() + "' where employeecpr ='" + user.getCpr() + "';";
        dbHandler.executeStatements(sql);
    }

    @Override
    public String getWagePerHour(User user) {
        String sql = "Select wage from wagePerHour where employeecpr = '" + user.getCpr() + "';";
        ArrayList<String> temp = dbHandler.getResultSet(sql);
        String forReturn = null;
        try {
            forReturn = temp.get(0).trim();
        } catch (Exception e) {
            //Do nothing
        }
        return forReturn;
    }

    @Override
    public User logIn(User user) {
        String sql = "SELECT * from UserLogIn WHERE Username = '" + user.getUsername() + "' and pass = '" +
                user.getPassword() + "';";
        ArrayList temp = dbHandler.getSingleRow(sql);
        if (temp.isEmpty()) {
            return new User();
        }
        String sql2 = "REFRESH MATERIALIZED VIEW employeeinformation;";
        dbHandler.executeStatements(sql2);
        sql2 = "SELECT * from employeeinformation WHERE cpr = '" + temp.get(0) + "';";
        ArrayList<String> arrayList = dbHandler.getSingleRow(sql2);
        int i = 0;
        User user1 = new User(arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++));
//        user1.setPicture(arrayList.get(i++));
//        user1.setUsername(arrayList.get(i++));
//        user1.setPassword(arrayList.get(i++));
//        user1.setFirstName(arrayList.get(i++));
//        user1.setSecondName(arrayList.get(i++));
//        user1.setLastName(arrayList.get(i++));
//        user1.setCpr(arrayList.get(i++));
//        user1.setDob(arrayList.get(i++));
//        user1.setAddress(arrayList.get(i++));
//        user1.setPostcode(arrayList.get(i++));
//        user1.setCity(arrayList.get(i++));
//        user1.setMobile(arrayList.get(i++));
//        user1.setLandline(arrayList.get(i++));
//        user1.setEmail(arrayList.get(i++));
//        user1.setKonto(arrayList.get(i++));
//        user1.setRecnumber(arrayList.get(i++));
//        user1.setLicencePlate(arrayList.get(i++));
//        user1.setPrefferedCommunication(arrayList.get(i++));
//        user1.setMoreInfo(arrayList.get(i++));
//        user1.setWage(arrayList.get(i++));
//        user1.setUserRole(arrayList.get(i++));
        return user1;
    }

    @Override
    public void addToWorkingSchedule(WorkingSchedule workingSchedule) {
        String sql =
                "INSERT INTO workingschedule (dno, employecpr, workingday, starthours, endhours) VALUES ('"
                        + workingSchedule.getDepartmentNumber() + "','" + workingSchedule.getEmployeeCPR() +
                        "', to_date('" + workingSchedule.getWorkingDate() + "', 'dd/mm/yyyy'),'" + workingSchedule.getStartHours() +
                        "','" + workingSchedule.getEndHours() + "');";

        dbHandler.executeStatements(sql);
    }

    @Override
    public ArrayList<User> getAllUsers(User user) {
        return null;
    }


    //TODO d city
    @Override
    public void createDepartment(Department department) {
        String sql = "Insert into city values ('" + department.getdLocation() + "','" + null + "');";
        dbHandler.executeStatements(sql);
        sql = "INSERT INTO department VALUES ('" + department.getdNumber().toLowerCase() + "','" + department.getdName() + "','" + department.getdManager() + "','" + department.getdLocation() + "','now()');";
        dbHandler.executeStatements(sql);
    }

    @Override
    public void editDepartment(Department department, Department oldDepartment) {
        String sql = "Update department set dno ='" + department.getdNumber().toLowerCase() + "', dname ='" + department.getdName() + "',dpostcode = '" + department.getdLocation() + "',dmanager ='" + department.getdManager() +
                "' where dno = '" + oldDepartment.getdNumber().toLowerCase() + "';";
        dbHandler.executeStatements(sql);
    }

    @Override
    public Department viewDepartment(Department department) {
        String sql = "Select * from department where dno = '" + department.getdNumber() + "';";
        ArrayList temp = dbHandler.getSingleRow(sql);
        System.out.println(temp);
        Department d = new Department((String) temp.get(0), (String) temp.get(1), (String) temp.get(3), (String) temp.get(2));
        System.out.println(d);
        return d;
    }

    @Override
    public void deleteDepartment(Department department) {

        String sql = " Delete from department where dno='" + department.getdNumber().toLowerCase() + "';";
        dbHandler.executeStatements(sql);
    }

//    private ArrayList getUserByCPR(String CPR) {
//        String sql = "SELECT * from UserLogIn WHERE CPR = '" + CPR + "';";
//        ArrayList temp = dbHandler.getSingleRow(sql);
//        return temp;
//    }

    //TODO DStartDate
    @Override
    public ArrayList<Department> getAllDepartments() {

        String sql = "Select * from department;";
        ArrayList<String[]> temp = dbHandler.getAllRows(sql);
        ArrayList<Department> departments = new ArrayList<>();
        for (String[] item : temp) {
            Department department = new Department(item[0], item[1], item[3], item[2]);
            departments.add(department);
        }
        return departments;
    }

    @Override
    public ArrayList<WorkingSchedule> workingSchedulePerWeek(User user) {

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String firstDayOfWeek = calendar.get(calendar.DATE) + "/" + (calendar.get(calendar.MONTH) + 1) + "/" + calendar.get(calendar.YEAR);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        String lastDayOfWeek = calendar.get(calendar.DATE) + "/" + (calendar.get(calendar.MONTH) + 1) + "/" + calendar.get(calendar.YEAR);
        String sql = "SELECT * FROM workingschedule WHERE employecpr = '" + user.getCpr() + "' AND workingday >='" + firstDayOfWeek + "' AND workingday <= '" + lastDayOfWeek + "';";
//        System.out.println(sql);
        ArrayList<String[]> temp = dbHandler.getAllRows(sql);
        ArrayList<WorkingSchedule> workingSchedules = new ArrayList<>();
        for (String[] item : temp) {
            WorkingSchedule workingSchedule = new WorkingSchedule(item[1], item[2], item[3], item[4], item[5]);
//            System.out.println(workingSchedule.toString());
            workingSchedules.add(workingSchedule);
        }
        System.out.println(firstDayOfWeek + "    " + lastDayOfWeek);
        System.out.println(workingSchedules);
        return workingSchedules;
    }


    @Override
    public ArrayList<User> getWorkingColleagues(User user) {

        String sql = "SELECT  DISTINCT dno FROM workingSchedule where employecpr ='" + user.getCpr() + "';";
        ArrayList<String> forReturn = dbHandler.getResultSet(sql);
        ArrayList<User> users = null;
        for (String item2 : forReturn) {
            sql = "REFRESH MATERIALIZED VIEW workingcolleagues;";
            dbHandler.executeStatements(sql);
            sql = "SELECT * from workingcolleagues " +
                    "WHERE cpr IS DISTINCT FROM'"
                    + user.getCpr() + "' AND dno = '" + item2 + "';";
            ArrayList<String[]> temp = dbHandler.getAllRows(sql);
            users = new ArrayList<>();
            for (String[] item : temp) {
                User user1 = new User();
                user1.setPicture(item[0]);
                user1.setFirstName(item[1]);
                user1.setLastName(item[2]);
                user1.setMobile(item[3]);
                user1.setEmail(item[4]);
                users.add(user1);
            }
        }
        return users;
    }

    @Override
    public ArrayList<String> getWorkingDepartments(User user) {

        String sql = "SELECT DISTINCT dno FROM workingSchedule where employecpr ='" + user.getCpr() + "';";
        return dbHandler.getResultSet(sql);
    }

    @Override
    public ArrayList<User> getAllColleagues() {

        String sql = "REFRESH MATERIALIZED VIEW allcolleagues;";
        dbHandler.executeStatements(sql);
        sql = "SELECT * from allcolleagues;";
        ArrayList<String[]> temp = dbHandler.getAllRows(sql);
        ArrayList<User> users = new ArrayList<>();
        for (String[] item : temp) {
            User user1 = new User();
            user1.setPicture(item[0]);
            user1.setFirstName(item[1]);
            user1.setLastName(item[2]);
            user1.setMobile(item[3]);
            user1.setEmail(item[4]);
            users.add(user1);
        }
        return users;
    }

    @Override
    public ArrayList<User> getAllUsers() {

        String sql = "select * from userlogin;";
        ArrayList<String[]> arrayList = dbHandler.getAllRows(sql);
        ArrayList<User> users = new ArrayList<>();
        for (String[] item : arrayList) {
            User newuser = new User();
            newuser.setCpr(item[0]);
            newuser.setUsername(item[1]);
            newuser.setPassword(item[2]);
            newuser.setUserRole(item[3]);
            users.add(newuser);
        }
        return users;
    }

    @Override
    public void wordCheck(String string) {
        String sql = "INSERT INTO history (tablename, operation, details, timestamp) VALUES ('WordCHECK', 'False words', '" + string + "', now());";
        dbHandler.executeStatements(sql);
    }

//    public static void main(String[] args) {
//        User user = new User("9087654321", "3333.20");
//        System.out.println(new DBAdapter().getWorkingColleagues(user));
//    }
    //TODO USER WAGE GUI
}

