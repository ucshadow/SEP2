package server;

public interface IDBAdapter {

    boolean checkUsername(String username);
    String getUser(String username);
}
