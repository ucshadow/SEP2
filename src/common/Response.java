package common;

import java.io.Serializable;
import java.util.Arrays;

public class Response implements Serializable {
    private String response;

    public Response(String response) {
        this.response = response;
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
                ", parameter=" + "temp param" +
                ", allParameters=" +  "temp array"+
                '}';
    }
}
