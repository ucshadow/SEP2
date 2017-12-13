package common;

import java.io.Serializable;

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

    public T getRespnoseObject() {
        return respnoseObject;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public T[] getAllParameters() {
        return responseObjects;
    }

    @Override
    public String toString() {
        return "common.Response{" +
                "response='" + response + '\'' +
                ", parameter=" + respnoseObject +
                ", allParameters=" + responseObjects +
                '}';
    }
}
