package client;

import common.Request;
import common.Response;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    private Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;


    public Client(String url, int port) {
        try {
            System.out.println("Connecting ...");
            clientSocket = new Socket(url, port);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("              Connected");
    }

    public void sendRequest(Request request) {
        try {
            System.out.println("Sending...." + request);
            if (out == null) {
                out = new ObjectOutputStream(clientSocket.getOutputStream());
            }

            out.writeObject(request);
            System.out.println("Sent");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}