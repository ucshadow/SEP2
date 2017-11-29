package client;

import common.Request;
import common.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Stack;

public class Client {

    private Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private Stack<Response> responses = new Stack<>();


    public Client(String url, int port) {
        try {
            System.out.println("Connecting ...");
            clientSocket = new Socket(url, port);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("              Connected");
        get();

    }

    public void sendRequest(Request request) {
        try {
//            System.out.println("Sending...." + request);
            if (out == null) {
                out = new ObjectOutputStream(clientSocket.getOutputStream());
            }

            out.writeObject(request);
            System.out.println("Sent");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void get() {
        if (in == null) {
            new Thread(() -> {
                try {
                    in = new ObjectInputStream(clientSocket.getInputStream());
                    while (true) {
                        Response response;
                        response = (Response) in.readObject();

                        System.out.println("response from server: " + response.toString());
                        responses.push(response);

//                        if (response.getResponse().toLowerCase().equals("update reservation")) {
//                            model.updateReservation(response.getAllParameters());
//                        }

                        Thread.sleep(10);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.exit(1);
                }
            }).start();
        }
    }

    public Response getLastResponse() {
        return responses.pop();
    }

    public boolean isStackEmpty() {
        return responses.isEmpty();
    }
}