package server;

import common.Department;
import common.User;
import common.WorkingSchedule;

import java.time.LocalDate;
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

    private boolean checkForCity(String sql) {
        String string = "select * from city where postcode = '" + sql + "';";
        ArrayList arrayList = dbHandler.getResultSet(string);
        return arrayList.size() > 0;
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

    @Override
    public void changeUserInformation(User user) {
        String sql;
        sql = "REFRESH MATERIALIZED VIEW employeeinformation;";
        dbHandler.executeStatements(sql);
        sql = "SELECT * from employeeinformation WHERE cpr = '" + user.getCpr() + "';";
        ArrayList<String> arrayList = dbHandler.getSingleRow(sql);

        if (!arrayList.get(2).equals(user.getPassword())) {
            sql = "Update userlogin set pass ='" + user.getPassword() + "' where cpr ='" + user.getCpr() + "';";
            dbHandler.executeStatements(sql);
        }
        if ((!arrayList.get(11).equals(user.getMobile()) || (!arrayList.get(12).equals(user.getLandline())) || (!arrayList.get(13).equals(user.getEmail())) || (!(
                arrayList.get(17).equals(user.getPrefferedCommunication())
        )))) {
            sql = "Update communication set mobile ='" + user.getMobile() + "',landline='" + user.getLandline() + "',email='" + user.getEmail() + "', preferredCommunication ='" + user.getPrefferedCommunication() + "' where cpr ='" + user.getCpr() + "';";
            dbHandler.executeStatements(sql);
        }
        if ((!arrayList.get(14).equals(user.getKonto())) || (!arrayList.get(15).equals(user.getRecnumber()))) {
            sql = "update bankinfodk set konto ='" + user.getKonto() + "', regnumber ='" + user.getRecnumber() + "' where cpr ='" + user.getCpr() + "';";
            dbHandler.executeStatements(sql);
        }
        if (!checkForCity(user.getPostcode())) {
            sql = "Insert into city values ('" + user.getPostcode() + "','" + user.getCity() + "');";
            dbHandler.executeStatements(sql);
        }
        if ((!arrayList.get(0).equals(user.getPicture())) || (!arrayList.get(7).equals(user.getDob())) || (!arrayList.get(8).equals(user.getAddress())) || (!arrayList.get(9).equals(user.getPostcode())) || (!arrayList.get(16).equals(user.getLicencePlate())) ||
                (!arrayList.get(18).equals(user.getMoreInfo())) || (!arrayList.get(3).equals(user.getFirstName())) || (!arrayList.get(4).equals(user.getSecondName())) || (!arrayList.get(5).equals(user.getLastName())))
            sql = "Update Employee set picture ='" + user.getPicture() + "',dateofbirth= to_date('" + user.getDob() + "', 'dd/mm/yyyy') ,address='" + user.getAddress() + "',postcode='" + user.getPostcode() + "',licenceplate='" +
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

        return createUser(arrayList);
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
    public void createDepartment(Department department) {
        String sql;
        if (!checkForCity(department.getdLocation())) {
            sql = "Insert into city values ('" + department.getdLocation() + "','" + department.getdCity() + "');";
            dbHandler.executeStatements(sql);
        }
        sql = "INSERT INTO department VALUES ('" + department.getdNumber().toLowerCase() + "','" + department.getdName() + "','" + department.getdManager() + "','" + department.getdLocation() + "','now()');";
        dbHandler.executeStatements(sql);
    }

    @Override
    public void editDepartment(Department department, Department oldDepartment) {
        String sql;
        if (!checkForCity(department.getdLocation())) {
            sql = "Insert into city values ('" + department.getdLocation() + "','" + department.getdCity() + "');";
            dbHandler.executeStatements(sql);
        }
        sql = "Update department set dno ='" + department.getdNumber().toLowerCase() + "', dname ='" + department.getdName() + "',dpostcode = '" + department.getdLocation() + "',dmanager ='" + department.getdManager() +
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

        int currentDayInWeek = LocalDate.now().getDayOfWeek().getValue();
        int day = LocalDate.now().getDayOfMonth();
        int monday = day - currentDayInWeek;
        int sunday = day + (7 - currentDayInWeek);
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();

        String firstDayOfWeek = monday + "/" + month + "/" + year;
        String lastDayOfWeek = sunday + "/" + month + "/" + year;
        String sql = "SELECT * FROM workingschedule WHERE employecpr = '" + user.getCpr() +
                "' AND workingday >=  to_date('" + firstDayOfWeek + "', 'dd/mm/yyyy')  AND workingday <=  to_date('" + lastDayOfWeek + "', 'dd/mm/yyyy');";
        System.out.println(sql);
        ArrayList<String[]> temp = dbHandler.getAllRows(sql);
//        System.out.println("arrayList is empty + " + temp.size());
        ArrayList<WorkingSchedule> workingSchedules = new ArrayList<>();
        for (String[] item : temp) {
//            System.out.println("each row in all rows");
//            System.out.println(Arrays.toString(item));
            WorkingSchedule workingSchedule = new WorkingSchedule(item[1], item[2], item[3], item[4], item[5]);
            workingSchedules.add(workingSchedule);
        }
//        System.out.println(firstDayOfWeek + "    " + lastDayOfWeek);
//        System.out.println(workingSchedules);
        return workingSchedules;
    }

    @Override
    public ArrayList<WorkingSchedule> allWorkingSchedulesPerWeek() {

        int currentDayInWeek = LocalDate.now().getDayOfWeek().getValue();
        int day = LocalDate.now().getDayOfMonth();
        int monday = day - currentDayInWeek;
        int sunday = day + (7 - currentDayInWeek);
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();

        String firstDayOfWeek = monday + "/" + month + "/" + year;
        String lastDayOfWeek = sunday + "/" + month + "/" + year;
        String sql = "SELECT * FROM workingschedule WHERE " +
                "workingday >=  to_date('" + firstDayOfWeek + "', 'dd/mm/yyyy')  AND workingday <=  to_date('" + lastDayOfWeek + "', 'dd/mm/yyyy');";
        System.out.println(sql);
        ArrayList<String[]> temp = dbHandler.getAllRows(sql);
//        System.out.println("arrayList is empty + " + temp.size());
        ArrayList<WorkingSchedule> workingSchedules = new ArrayList<>();
        for (String[] item : temp) {
            WorkingSchedule workingSchedule = new WorkingSchedule(item[1], item[2], item[3], item[4], item[5]);
            workingSchedules.add(workingSchedule);
        }
//        System.out.println(firstDayOfWeek + "    " + lastDayOfWeek);
//        System.out.println(workingSchedules);
        return workingSchedules;
    }

    @Override
    public ArrayList<WorkingSchedule> getAllWorkingSchedules() {

//        int currentDayInWeek = LocalDate.now().getDayOfWeek().getValue();
//        int day = LocalDate.now().getDayOfMonth();
//        int monday = day - currentDayInWeek;
//        int sunday = day + (7 - currentDayInWeek);
//        int year = LocalDate.now().getYear();
//        int month = LocalDate.now().getMonthValue();
//
//        String firstDayOfWeek = monday + "/" + month + "/" + year;
//        String lastDayOfWeek = sunday + "/" + month + "/" + year;
        String sql = "SELECT * FROM workingschedule";
        ArrayList<String[]> temp = dbHandler.getAllRows(sql);
//        System.out.println("arrayList is empty + " + temp.size());
        ArrayList<WorkingSchedule> workingSchedules = new ArrayList<>();
        for (String[] item : temp) {
            WorkingSchedule workingSchedule = new WorkingSchedule(item[1], item[2], item[3], item[4], item[5]);
            workingSchedules.add(workingSchedule);
        }
//        System.out.println(firstDayOfWeek + "    " + lastDayOfWeek);
//        System.out.println(workingSchedules);
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
    public ArrayList<User> getUsersByDepartment(Department department) {
        String sql = "REFRESH MATERIALIZED VIEW usersbydepartment;";
        dbHandler.executeStatements(sql);
        sql = "SELECT firstname,familyname, employecpr, dno from usersbydepartment where dno = '" + department.getdNumber() + "';";
        ArrayList<User> forReturn = new ArrayList<>();
        ArrayList<String[]> users = dbHandler.getAllRows(sql);
        for (String[] item : users) {
            User user = new User();
            user.setFirstName(item[0]);
            user.setLastName(item[1]);
            user.setCpr(item[2]);
            forReturn.add(user);
        }
        return forReturn;
    }


    @Override
    public ArrayList<User> getAllUsersWithoutWorkingSchedule() {
       String sql = "SELECT employee.cpr, employee.firstname, employee.familyname FROM employee WHERE NOT EXISTS(SELECT cpr  FROM workingSchedule  WHERE Employee.cpr = workingSchedule.employecpr);";
        ArrayList<User> forReturn = new ArrayList<>();
        ArrayList<String[]> users = dbHandler.getAllRows(sql);
        for (String[] item : users) {
            User user = new User();
            user.setCpr(item[0]);
            user.setFirstName(item[1]);
            user.setLastName(item[2]);
            forReturn.add(user);
        }
        return forReturn;


    }

    @Override
    public User getUserInfOForAdmin(User user) {
        String sql2 = "REFRESH MATERIALIZED VIEW employeeinformation;";
        dbHandler.executeStatements(sql2);
        sql2 = "SELECT * from employeeinformation WHERE cpr = '" + user.getCpr() + "';";
        ArrayList<String> arrayList = dbHandler.getSingleRow(sql2);

        return createUser(arrayList);
    }

    private User createUser(ArrayList<String> arrayList) {
        int i = 0;
//        User user1 = new User(arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++), arrayList.get(i++));
        User user1 = new User();
        user1.setPicture(arrayList.get(i++));
        user1.setUsername(arrayList.get(i++));
        user1.setPassword(arrayList.get(i++));
        if (arrayList.get(i).equals("firstname")) {
            user1.setFirstName("");
            i++;
        } else {
            user1.setFirstName(arrayList.get(i++));
        }
        if (arrayList.get(i).equals("secondname")) {
            user1.setSecondName("");
            i++;
        } else {
            user1.setSecondName(arrayList.get(i++));
        }
        if (arrayList.get(i).equals("lastname")) {
            user1.setLastName("");
            i++;
        } else {
            user1.setLastName(arrayList.get(i++));
        }
        user1.setCpr(arrayList.get(i++));
        int day = LocalDate.now().getDayOfMonth();
        String currentDay = String.valueOf(day);
        if (day < 10) {
            currentDay = "0" + LocalDate.now().getDayOfMonth();
        }
        String date = LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue() + "-" + currentDay;
        if (arrayList.get(i).equals(date)) {
            user1.setDob("");
            i++;
        } else {
            user1.setDob(arrayList.get(i++));
        }
        if (arrayList.get(i).equals("address")) {
            user1.setAddress("");
            i++;
        } else {
            user1.setAddress(arrayList.get(i++));
        }
        if (arrayList.get(i).equals("postcode")) {
            user1.setPostcode("");
            i++;
        } else {
            user1.setPostcode(arrayList.get(i++));
        }
        if (arrayList.get(i).equals("City")) {
            user1.setCity("");
            i++;
        } else {
            user1.setCity(arrayList.get(i++));
        }
        if (arrayList.get(i).equals("00000000")) {
            user1.setMobile("");
            i++;
        } else {
            user1.setMobile(arrayList.get(i++));
        }
        if (arrayList.get(i).equals("00000000")) {
            user1.setLandline("");
            i++;
        } else {
            user1.setLandline(arrayList.get(i++));
        }
        if (arrayList.get(i).equals("email@email.com")) {
            user1.setEmail("");
            i++;
        } else {
            user1.setEmail(arrayList.get(i++));
        }
        if (arrayList.get(i).equals("0000")) {
            user1.setKonto("");
            i++;
        } else {
            user1.setKonto(arrayList.get(i++));
        }
        if (arrayList.get(i).equals("0000000000")) {
            user1.setRecnumber("");
            i++;
        } else {
            user1.setRecnumber(arrayList.get(i++));
        }
        if (arrayList.get(i).equals("licenceplate")) {
            user1.setLicencePlate("");
            i++;
        } else {
            user1.setLicencePlate(arrayList.get(i++));
        }
        user1.setPrefferedCommunication(arrayList.get(i++));
        if (arrayList.get(i).equals("more info")) {
            user1.setMoreInfo("");
            i++;
        } else {
            user1.setMoreInfo(arrayList.get(i++));
        }
        user1.setWage(arrayList.get(i++));
        user1.setUserRole(arrayList.get(i++));
        return user1;
    }

}

