package client;

import common.Request;
import common.Response;

import java.io.IOException;
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

    public void sendRequest(String request) {
        while (true) {
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
            System.out.println(get());
        }


    }

    public String get() {
        String b = "Receiving                    =       ";
        if (in == null) {
            try {
                in = new ObjectInputStream(clientSocket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            b += (String) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return b;
    }
}