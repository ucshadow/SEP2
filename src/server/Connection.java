package server;


import common.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Connection implements Runnable, OurObserver {
    private Server server;
    private ObjectInputStream inFromClient;
    private ObjectOutputStream outToClient;
    private IDBAdapter adapter;

    //TODO Server Connection reset
    @Override


    public void run() {
        while (true) {
            Request s;
            try {
                Object temp = inFromClient.readObject();
                s = (Request) temp;
                String requestText = s.getType();
                switch (requestText.toLowerCase()) {
                    case "addschedule":
                        adapter.addToWorkingSchedule((WorkingSchedule) s.getRequestObject());
                        break;
                    case "createaccount":
                        adapter.createAccount((User) s.getRequestObject());
                        break;
                    case "removeaccount":
                        adapter.removeAccount((User) s.getRequestObject());
                        break;
                    case "editaccount":
                        adapter.editAccount((User) s.getRequestObject());
                        break;
                    case "changeuserinfo":
                        adapter.changeUserInformation((User) s.getRequestObject());
                        break;
                    case "createdepartment":
                        adapter.createDepartment((Department) s.getRequestObject());
                        break;
                    case "editdepartment":
                        adapter.editDepartment((Department) s.getRequestObjects()[0], (Department) s.getRequestObjects()[1]);
                        break;
                    case "deletedepartment":
                        adapter.deleteDepartment((Department) s.getRequestObject());
                        break;
                    case "getalldepartments":
                        ArrayList<Department> department1 = adapter.getAllDepartments();
                        getOutputStream().writeObject(new Response<>("getAllDepartments", department1));
                        break;
                    case "getworkingschedule":
                        ArrayList<WorkingSchedule> workingSchedules = adapter.workingSchedulePerWeek((User) s.getRequestObject());
                        getOutputStream().writeObject(new Response<>("getWorkingSchedule", workingSchedules));
                        break;
                    case "getallworkingschedule":
                        ArrayList<WorkingSchedule> allWorkingSchedules = adapter.getAllWorkingSchedules();
                        getOutputStream().writeObject(new Response<>("getAllWorkingSchedule", allWorkingSchedules));
                        break;
                    case "getuserswithoutschedule":
                        ArrayList<User> usersWithoutSchedules = adapter.getAllUsersWithoutWorkingSchedule();
                        getOutputStream().writeObject(new Response<>("getUsersWithoutSchedule", usersWithoutSchedules));
                        break;
                    case "getworkingcolleagues":
                        ArrayList<User> workingCollegues = adapter.getWorkingColleagues((User) s.getRequestObject());
                        getOutputStream().writeObject(new Response<>("getWorkingColleagues", workingCollegues));
                        break;
                    case "getmyworkingdepartments":
                        ArrayList<String> workingDepartments = adapter.getWorkingDepartments((User) s.getRequestObject());
                        getOutputStream().writeObject(new Response<>("getMyWorkingDepartments", workingDepartments));
                        break;
                    case "login":
                        getOutputStream().writeObject(new Response<>("login",
                                adapter.logIn((User) s.getRequestObject())));
                        break;
                    case "getallcolleagues":
                        ArrayList<User> allColleagues = adapter.getAllColleagues();
                        getOutputStream().writeObject(new Response<>("getAllColleagues", allColleagues));
                        break;
                    case "changewageperhour":
                        adapter.changeWagePerHours((User) s.getRequestObject());
                        break;
                    case "getallusers":
                        ArrayList<User> users = adapter.getAllUsers();
                        getOutputStream().writeObject(new Response<>("getallusers", users));
                        break;
                    case "getuserbydepartment":
                        ArrayList<User> user2 = adapter.getUsersByDepartment((Department) s.getRequestObject());
                        getOutputStream().writeObject(new Response<>("getuserbydepartment", user2));
                        break;
                    case "getuserinfoforadmin":
                        User userForAdmin = adapter.getUserInfOForAdmin((User) s.getRequestObject());
                        getOutputStream().writeObject(new Response<>("getuserinfoforadmin", userForAdmin));
                    case "workinghistory":
                        ArrayList<WorkingSchedule> workingSchedules1 = adapter.getHistoryWorkingSchedule((User) s.getRequestObject());
                        getOutputStream().writeObject(new Response<>("workinghistory", workingSchedules1));
                }

            } catch (Exception e) {

                e.printStackTrace();
                try {
                    outToClient.close();
                    inFromClient.close();
                } catch (IOException e1) {
                    e.printStackTrace();

                }
                server.removeObserver(this);
                break;
            }
        }
    }


    public Connection(Socket socket, Server server, IDBAdapter adapter) {
        this.server = server;
        this.adapter = adapter;
        try {
            inFromClient = new ObjectInputStream(socket.getInputStream());
            outToClient = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public ObjectOutputStream getOutputStream() {
        return outToClient;
    }


    private OurObserver getMe() {
        return this;
    }


    public void writeObject(Response response) {
        new Thread(() -> {
            try {
                getOutputStream().writeObject(response);
            } catch (IOException e) {
                server.removeObserver(getMe());
            }

        }).start();
    }

    @Override
    public void update(Response asd) {
        writeObject(asd);
    }
}
