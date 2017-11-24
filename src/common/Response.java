package common;

import java.io.Serializable;
import java.util.Arrays;

public class Response<T> implements Serializable {

    private T respnoseObject;
    private T[] responseObjects;
    private String response;

    public Response(String response, T respnoseObject) {
        this.response = response;
        this.respnoseObject = respnoseObject;

    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

//    public Reservation getParameter() {
//        return parameter;
//    }
//
//    public void setParameter(Reservation parameter) {
//        this.parameter = parameter;
//    }
//
//    public Reservation[] getAllParameters() {
//        return allParameters;
//    }
//
//    public void setAllParameters(Reservation[] allParameters) {
//        this.allParameters = allParameters;
//    }

    @Override
    public String toString() {
        return "common.Response{" +
                "response='" + response + '\'' +
                ", parameter=" + respnoseObject +
                ", allParameters=" + "temp array" +
                '}';
    }
}
