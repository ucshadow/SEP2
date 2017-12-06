import java.util.ArrayList;
import java.util.Calendar;

public class Switchss {
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String firstDayOfWeek = calendar.get(calendar.DATE) + "/" + (calendar.get(calendar.MONTH) + 1) + "/" + calendar.get(calendar.YEAR);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        String lastDayOfWeek = calendar.get(calendar.DATE) + "/" + (calendar.get(calendar.MONTH) + 1) + "/" + calendar.get(calendar.YEAR);

        System.out.println(firstDayOfWeek + "   " + lastDayOfWeek);
    }
}


//    public static void main(String[] args) {
//        String a = "A";
//        String a0 = "C";
//        String a1 = "B";
//        ArrayList<String> asdasd = new ArrayList<>();
//        for (int i = 0; i < 2; i++) {
//            asdasd.add(a + i);
//        }
//        System.out.println(asdasd);
//    }
//
///* while (true) {
//            Request s;
//            try {
//                Object temp = inFromClient.readObject();
//                s = (Request) temp;
//                String requestText = s.getType();
//                if (requestText.equalsIgnoreCase("createAccount")) {
//                    System.out.println(s.getRequestObject());
//                    adapter.createAccount((User) s.getRequestObject());
//                } else if (requestText.equalsIgnoreCase("removeAccount")) {
//                    System.out.println(s.getRequestObject());
//                    adapter.removeAccount((User) s.getRequestObject());
//                } else if (requestText.equalsIgnoreCase("editAccount")) {
//                    System.out.println(s.getRequestObject());
//                    adapter.editAccount((User) s.getRequestObject());
//                } else if (requestText.equalsIgnoreCase("changeUserInfo")) {
//                    adapter.changeUserInformation((User) s.getRequestObject());
//                } else if (requestText.equalsIgnoreCase("createDepartment")) {
//                    adapter.createDepartment((Department) s.getRequestObject());
//                } else if (requestText.equalsIgnoreCase("editDepartment")) {
//                    adapter.editDepartment((Department) s.getRequestObjects()[0], (Department) s.getRequestObjects()[1]);
//                } else if (requestText.equalsIgnoreCase("getDepartment")) {
//                    Department department = (Department) adapter.viewDepartment((Department) s.getRequestObject());
//                    getOutputStream().writeObject(new Response<>("viewDepartment", department));
//                } else if (requestText.equalsIgnoreCase("deleteDepartment")) {
//                    adapter.deleteDepartment((Department) s.getRequestObject());
//                } else if (requestText.equalsIgnoreCase("getAllDepartments")) {
//                    ArrayList<Department> department = adapter.getAllDepartments();
//                    getOutputStream().writeObject(new Response<>("getAllDepartments", department));
//                } else if (requestText.equalsIgnoreCase("getWorkingSchedule")) {
//                    ArrayList<WorkingSchedule> workingSchedules = adapter.workingSchedulePerWeek((User) s.getRequestObject());
//                    getOutputStream().writeObject(new Response<>("getWorkingSchedule", workingSchedules));
//                } else if (requestText.equalsIgnoreCase("getWagePerHours")) {
//                    String wage = adapter.getWagePerHour((User) s.getRequestObject());
//                    getOutputStream().writeObject(new Response<>("getWagePerHours", wage));
//                } else if (requestText.equalsIgnoreCase("changeWagePerHour")) {
//                    adapter.changeWagePerHours((User) s.getRequestObject());
//                } else if (requestText.equalsIgnoreCase("getWorkingColleagues")) {
//                    ArrayList<User> workingCollegues = adapter.getWorkingColleagues((User) s.getRequestObject());
//                    getOutputStream().writeObject(new Response<>("getWorkingColleagues", workingCollegues));
//                } else if (requestText.equalsIgnoreCase("getMyWorkingDepartments")) {
//                    ArrayList<String> workingDepartments = adapter.getWorkingDepartments((User) s.getRequestObject());
//                    getOutputStream().writeObject(new Response<>("getMyWorkingDepartments", workingDepartments));
//                } else if (requestText.equalsIgnoreCase("logIn")) {
//                    getOutputStream().writeObject(new Response<>("login",
//                            adapter.logIn((User) s.getRequestObject())));
//                } else if (requestText.equalsIgnoreCase("getAllColleagues")) {
//                    ArrayList<User> allColleagues = adapter.getWorkingColleagues((User) s.getRequestObject());
//                    getOutputStream().writeObject(new Response<>("getAllColleagues", allColleagues));
//                }
//            } catch (Exception e) {
//                server.removeObserver(this);
//            }
//        }*/

//
