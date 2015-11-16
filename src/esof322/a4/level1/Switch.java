package esof322.a4.level1;

import java.util.ArrayList;

public class Switch implements Interactable//, Subject
{
    private ArrayList<Gate> gates;
    
    private boolean active = false;
    
    private static int SWITCH_COUNT = 0;
    
    private int id = SWITCH_COUNT++;
    
    public Switch (boolean startValue)
    {
        gates = new ArrayList<Gate>();
        active = startValue;
    }
    
    public int getID()
    {
        return id;
    }
    
    public boolean getState()
    {
        return active;
    }
    
    public void addObserver(Gate gate)
    {
        gates.add(gate);
    }
    
    public void notifyObservers()
    {
        for (Gate gate: gates)
        {
            System.out.println("Notified a gate.");
            gate.update(this);
        }
    }

    public String interact()
    {
        active = !active;
        notifyObservers();
        return "Switch " + id + " was turned " + (active? "on" : "off") + ".";
    }

    @Override
    public String getName()
    {
        return "Switch";
    }

    @Override
    public String getDesc()
    {
        return "Number " + id;
    }
}
