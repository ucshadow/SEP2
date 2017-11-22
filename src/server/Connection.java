package server;


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
    private DBAdapter adapter;

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
                if (requestText.equalsIgnoreCase("createuser")) {
                    adapter.createUser((User) s.getRequestObject());
                }
//                String a = adapter.getUserPassword(s.getType());
//                outToClient.writeObject("From db " + a);
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
