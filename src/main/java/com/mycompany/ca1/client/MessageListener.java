/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ca1.client;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TimmosQuadros
 */
public class MessageListener extends Thread {
    
    EchoClient client;
    boolean isLoggedIn=true;

    public MessageListener(EchoClient client) {
        this.client=client;
    }

    @Override
    public void run() {
        while(isLoggedIn){
            String msg = client.receive();
            notifyAllObservers(msg);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                //DO SOMETHING!
            }
        }
        super.run(); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    List<Observer> observers = new ArrayList<>();

    public void notifyAllObservers(String msg) {
        for (Observer observer : observers) {
            observer.responseReceived(msg);
        }
    }

    public void registerObserver(Observer o) {
        observers.add(o);
    }
    
    public void setIsloggedIn(boolean isLoggedIn){
        this.isLoggedIn=isLoggedIn;
    }

}
