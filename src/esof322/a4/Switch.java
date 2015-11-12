package esof322.a4;

import java.util.ArrayList;

public class Switch implements Subject
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

}
