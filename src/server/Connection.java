package server;


import common.Department;
import common.Request;
import common.Response;
import common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection implements Runnable, OurObserver {
    private Server server;
    private ObjectInputStream inFromClient;
    private ObjectOutputStream outToClient;
    private IDBAdapter adapter;

    /**
     * Constructor.
     * Instantiates streams.
     *
     * @param socket
     * @param server
     * @param adapter
     */
    public Connection(Socket socket, Server server, DBAdapter adapter) {
        this.server = server;
        this.adapter = adapter;
        try {
            inFromClient = new ObjectInputStream(socket.getInputStream());
            outToClient = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
                if (requestText.equalsIgnoreCase("createAccount")) {
                    System.out.println(s.getRequestObject());
                    adapter.createAccount((User) s.getRequestObject());
                } else if (requestText.equalsIgnoreCase("removeAccount")) {
                    System.out.println(s.getRequestObject());
                    adapter.removeAccount((User) s.getRequestObject());
                } else if (requestText.equalsIgnoreCase("editAccount")) {
                    System.out.println(s.getRequestObject());
                    adapter.editAccount((User) s.getRequestObject());
                } else if (requestText.equalsIgnoreCase("createDepartment")) {
                    adapter.createDepartment((Department) s.getRequestObject());
                } else if (requestText.equalsIgnoreCase("editDepartment")) {
                    adapter.editDepartment((Department) s.getRequestObjects()[0], (Department) s.getRequestObjects()[1]);
                }
            } catch (Exception e) {
                server.removeObserver(this);
            }
        }
    }

    public ObjectOutputStream getOutputStream() {
        return outToClient;
    }


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
