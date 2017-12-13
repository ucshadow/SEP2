package server;
import common.Response;

import java.io.IOException;
import java.util.ArrayList;


public class OurObservable {
    private ArrayList<OurObserver> observers;

    public OurObservable(){
        observers = new ArrayList<>();
    }

   public synchronized void addObserver(OurObserver addClient){
        if (addClient == null)
            throw new NullPointerException("No Null");
       observers.add(addClient);
   }


    public synchronized void removeObserver(OurObserver oldClient){
       if (oldClient == null)
           throw new NullPointerException("Null point exception");
       else
        observers.remove(oldClient);
    }


    public synchronized void notifyAll(Response resp) throws IOException{
        for (OurObserver item : observers) {
            item.update(resp);
        }
    }


}
