package esof322.a4.interact;

import java.util.ArrayList;

public class Switch implements Subject, Interactable
{
    private ArrayList<Observer> observers;
    
    private boolean active = false;
    
    private static int SWITCH_COUNT = 0;
    
    private int id = SWITCH_COUNT++;
    
    public Switch ()
    {
        observers = new ArrayList<Observer>();
    }
    
    public int getID()
    {
        return id;
    }
    
    public boolean getState()
    {
        return active;
    }
    
    @Override
    public void addObserver(Observer observer)
    {
        observers.add(observer);
    }
    
    @Override
    public void notifyObservers()
    {
        for (Observer o: observers)
        {
            o.update();
        }
    }

    public String interact()
    {
        active = !active;
        notifyObservers();
        return "Switch " + id + " was turned " + (active? "on" : "off") + ".";
    }
}
