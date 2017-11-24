package client;

import common.Request;
import common.User;

public class Controller {
    private Client client;

    public Controller() {
        client = new Client("localhost", 6789);
    }

    public void createUser(User user) {
        Request<User> createUserRequest = new Request<>("createuser", user);
        client.sendRequest(createUserRequest);
    }
    public void editUser(User user) {
        Request<User> editUserRequest = new Request<>("edituser", user);
        client.sendRequest(editUserRequest);
    }

    public void submitEdit(User user) {
        Request<User> submitEditRequest = new Request<>("submitedit", user);
        client.sendRequest(submitEditRequest);
    }

}
