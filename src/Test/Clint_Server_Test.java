/*
    Since JUnit does not support running multiple threads at once
    this test was written to test the server capacity.
    Works fine with 400 threads. At around 500, the server will not respond
    in the allowed time of 2 seconds. At around 2000 threads
    the connections are starting to be refused by the server, also the JVM
    runs out of memory for a new thread.
 */

package Test;

import client.Controller;
import common.Response;
import server.Server;

import java.util.stream.IntStream;

public class Clint_Server_Test {

    private Server s = new Server();
    private int atOnceConnections = 1000;
    private int allowedTime = 2000; // milliseconds

    public Clint_Server_Test() {
        startServer();
    }

    private void startServer() {
        new Thread(() -> s.startServer()).start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        IntStream.range(0, atOnceConnections).parallel().forEach(e -> beginTest());
    }

    private void beginTest() {
        new Thread(() -> {
            Controller c = new Controller();
            c.getAllDepartments();

            int count = 0;

            while (true) {
                Response r = c.getLastResponse();
                if (r != null) {
                    System.out.println(r);
                    break;
                }
                try {
                    Thread.sleep(allowedTime / 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
                if (count >= 10) {
                    break;
                }
            }

        }).start();
    }

    public static void main(String[] args) {
        new Clint_Server_Test();
    }

}
