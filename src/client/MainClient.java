package client;


public class MainClient {


    private Client client;


    public MainClient() {
        client = new Client("localhost", 6789);
    }

    public static void main(String[] args) {
        new MainClient();
    }


}
