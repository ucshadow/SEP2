package common;

import java.io.Serializable;

public class Request implements Serializable {
    private String request;


    public Request(String request) {
        this.request = request;

    }

    public Request(String request, boolean isArray) {
        this.request = request;

    }

    public String getType() {
        return request;
    }

//    public Reservation getParameter() {
//        return parameter;
//    }
//
//    public Reservation[] getReservations() {
//        return reservations;
//    }



    @Override
    public String toString() {
        return "common.Request{" +
                "request='" + request + '\'' +
                ", parameter=" +"placeholder text"+
                '}';
    }
}
