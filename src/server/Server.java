package server;


import java.io.IOException;
import java.net.ServerSocket;


public class Server extends OurObservable {
    private ServerSocket serverSocket;
    private final int PORT = 6789;
    private IDBAdapter adapter;

    public Server() {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        adapter = new DBAdapter();
    }


    public void startServer() {
        System.out.println("Server starting ...");
        while (true) {
            try {
                Connection newClient = new Connection(serverSocket.accept(), this, adapter);
                addObserver(newClient);
                new Thread(newClient).start();
                System.out.println("client connected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
