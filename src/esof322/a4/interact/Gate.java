package esof322.a4.interact;

import esof322.a4.CaveSite;
import esof322.a4.Player;
import esof322.a4.Room;

public class Gate implements CaveSite, Observer
{    
    public final int id;
    private Switch daSwitch;
    private boolean isOpen;
    
    private Room from;
    private Room into;
    
    public Gate (Room from, Room into, Switch lock)
    {
        this.from = from;
        this.into = into;
        this.daSwitch = lock;
        isOpen = daSwitch.getState();
        id = lock.getID();
    }
    
    public boolean isGateOpen()
    {
        return isOpen;
    }
    
    @Override
    public void update()
    {
        isOpen = daSwitch.getState();
    }

    @Override
    public String enter(Player p)
    {
        if (isOpen)
        {
            if (p.getLocation().equals(from))
            {
                return from.enter(p);
            }
            else if (p.getLocation().equals(into))
            {
                return into.enter(p);
            }
            else
            {
                return "You cannot enter this gate from that location!";
            }
        }
        else
        {
            return "This gate is locked! Find the switch labeled " + id;
        }
    }

    @Override
    public String getName()
    {
        return "Gate";
    }

}
