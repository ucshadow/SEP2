package common;

import java.io.Serializable;
import java.util.Arrays;

public class Request<T> implements Serializable {
    private T requestObject;
    private T[] requestObjects;
    private String requestText;

    public Request(String requestText, T requestObject) {
        this.requestText = requestText;
        this.requestObject = requestObject;
    }

    public Request(String requestText, T[] requestObjects) {
        this.requestText = requestText;
        this.requestObjects = requestObjects;
    }

    public String getType() {
        return requestText;
    }

    public T getRequestObject() {
        return requestObject;
    }

    public T[] getRequestObjects() {
        return requestObjects;
    }

    // ToDo: modify toString to print out based on whether the requestObject is a single element or an array
    @Override
    public String toString() {
        return "common.Request{" +
                "requestText='" + requestText + '\'' +
                "requestObject'" + requestObject.toString() + '\'' +
                ", parameter=" + "placeholder text" +
                '}';
    }

   
}
