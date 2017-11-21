package client;


public class MainClient {


    public static void main(String[] args) {

        Client client = new Client("localhost", 6789);
        client.sendRequest("asdasd");


    }


}
