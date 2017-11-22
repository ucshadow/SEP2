package client;

import common.Request;
import common.User;

public class Controller {

    public void createUser(User user) {
        Request<User> createUserRequest = new Request<>("createuser", user);
    }
}
