package client;


public class MainClient {


    private Client client;


    public MainClient() {
        client = new Client("10.152.208.103", 6789);
    }

    public static void main(String[] args) {
        new MainClient();
    }


}
