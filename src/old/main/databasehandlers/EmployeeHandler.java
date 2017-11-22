package old.main.databasehandlers;

import old.main.employee.Employee;

import java.util.ArrayList;

public class EmployeeHandler {


    public EmployeeHandler() {
    }

    public static void addNewEmployee(Employee a) {

        String sql = "INSERT INTO Employee VALUES (" + "'" + a.getFirstName() + "'," + "'" + a.getSecondName() + "'," + "'" + a.getFamilyName() + "'," + "'" + a.getCpr() + "'," + "'" + a.getDateOfBirth() + "'," + "'" + a.getAddress() + "'," + "'" + a.getPostCode() + "'," + "'" + a.getCity() + "'," + "'" + a.getMobilePhone() + "'," + "'" + a.getHomePhone() + "'," + "'" + a.getEmail() + "'," + "'" + a.getKonto() + "'," + "'" + a.getRegNumber() + "'," + "'" + a.getLicensePlate() + "'," + "'" + a.getPrefferd() + "'," + "'" + a.getMoreInformation() + "');";
        MainHandler.executeStatements(sql);
    }

    public static void updateEmployee(Employee a) {
        String sql = "Select firstname from employee where cpr = '" + a.getCpr() + "';";
        ArrayList<String> temp = MainHandler.getResultSet(sql);
        if (temp.size() > 0) {
            System.out.println("Has that cpr");
        } else addNewEmployee(a);
    }


}
