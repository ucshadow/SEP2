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

    /**
     * Run method from runnable interface.
     *
     * Reads object and returns username  //ToDO -- unfinished.
     */
    public void run() {
        while (true) {
            Request s;
            try {
                Object temp = inFromClient.readObject();
                s = (Request) temp;
                String requestText = s.getType();
//
//                System.out.println(s.getRequestObjects().toString());
//                if (!wordsCheck(s.getRequestObject().toString())) {
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
                    case "getdepartment":
                        Department department = (Department) adapter.viewDepartment((Department) s.getRequestObject());
                        getOutputStream().writeObject(new Response<>("viewDepartment", department));
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
                    case "getwageperhours":
                        String wage = adapter.getWagePerHour((User) s.getRequestObject());
                        getOutputStream().writeObject(new Response<>("getWagePerHours", wage));
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
                }
//                } else {
//                    adapter.wordCheck(s.getRequestObject().toString());
//                    getOutputStream().writeObject(new Response<>("Word check was triggered", null));
//                }
            } catch (Exception e) {
                try {
                    outToClient.close();
                    inFromClient.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                server.removeObserver(this);
                break;


            }
        }
    }

    /**
     * Constructor.
     * Instantiates streams.
     *
     * @param socket
     * @param server
     */
    public Connection(Socket socket, Server server, IDBAdapter adapter) {
        this.server = server;
        this.adapter = adapter;
        try {
            inFromClient = new ObjectInputStream(socket.getInputStream());
            outToClient = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObjectOutputStream getOutputStream() {
        return outToClient;
    }

//    private boolean wordsCheck(String string) {
//        String[] keyWords = {"select", "update", "insert", "drop", "delete", "create"};
//        for (String item : keyWords) {
//            if (string.toLowerCase().contains(item)) {
//                return true;
//            }
//        }
//        return false;
//    }

    private OurObserver getMe() {
        return this;
    }

    /**
     * Abstract method for writing response to observer.
     *
     * @param response
     */
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
