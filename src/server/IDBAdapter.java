package server;

public interface IDBAdapter {

    boolean checkUsername(String username);
    String getUserPassword(String username);
}
