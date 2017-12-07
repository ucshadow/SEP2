package server;


import java.io.IOException;
import java.net.ServerSocket;


public class Server extends OurObservable {
    private final int PORT = 6789;
    private ServerSocket serverSocket;
    private IDBAdapter adapter;
    private Connection newClient;

    public Server() {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        adapter = new DBAdapter();
        new ScheduleUpdater(adapter).automaticUpdater();
    }


    public void startServer() {
        System.out.println("Server starting ...");
        while (true) {
            try {
                newClient = new Connection(serverSocket.accept(), this, adapter);
                addObserver(newClient);
                new Thread(newClient).start();
                System.out.println("client connected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
