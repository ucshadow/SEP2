package server;


import java.io.IOException;
import java.net.ServerSocket;


public class Server  extends OurObservable {
    private ServerSocket serverSocket;
    private final int PORT = 6789;


    public Server() {

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void startServer() {
        System.out.println("Server starting ...");
        while (true) {
            try {
                Connection newClient = new Connection(serverSocket.accept(), this);
                addObserver(newClient);
                new Thread(newClient).start();
                System.out.println("client connected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
