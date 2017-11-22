package server;
import common.Response;

import java.io.IOException;
import java.util.ArrayList;


public class OurObservable {
    private ArrayList<OurObserver> observers;

    public OurObservable(){
        observers = new ArrayList<>();
    }

    /**
     * Simply adds client if client is not null.
     * @param addClient
     */
   public synchronized void addObserver(OurObserver addClient){
        if (addClient == null)
            throw new NullPointerException("No Null Clients please!");
       observers.add(addClient);
   }

    /**
     * Simply removes observer if oldClient is not a null.
     * @param oldClient
     */
    public synchronized void removeObserver(OurObserver oldClient){
       if (oldClient == null)
           throw new NullPointerException("Bitch .. i told you once , im not gon tell you again MOTHERFUCKER!");
       else
        observers.remove(oldClient);
    }


    public synchronized void notifyAll(Response resp) throws IOException{
        for (OurObserver item : observers) {
            item.update(resp);
        }
    }


}
