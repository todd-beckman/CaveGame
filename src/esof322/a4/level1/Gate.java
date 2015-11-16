package esof322.a4.level1;

import esof322.a4.Door;
import esof322.a4.Player;
import esof322.a4.Room;

public class Gate extends Door//implements Observer
{
    private static int NUM_SWITCHES = 0;
    public final int id = NUM_SWITCHES++;
    
    //  There is a better way to do this
    //  but this preserves the design pattern structure
    private Switch[] switches;
    private boolean[] desiredStates;
    private boolean[] actualStates;
    
    public Gate (Room from, Room into, GateKey key)
    {
        super(from, into, null);
        switches = key.switches;
        desiredStates = new boolean[switches.length];
        actualStates = new boolean[switches.length];
        for (int i = 0; i < switches.length; i++)
        {
            desiredStates[i] = key.desired[i];
            actualStates[i] = switches[i].getState();
        }
    }
    
    private int indexOf(Switch s)
    {
        for (int i = 0; i < switches.length; i++)
        {
            if (s == switches[i])
            {
                return i;
            }
        }
        return -1;
    }

    public void update(Switch swtch)
    {
        int i = indexOf(swtch);
        if (-1 < i)
        {
            actualStates[i] = switches[i].getState();
        }
    }
    
    /**
     * Determines if the gate is open
     * @return Whether the gate is open
     */
    public boolean isGateOpen()
    {
        for (int i = 0; i < switches.length; i++)
        {
            if (actualStates[i] != desiredStates[i])
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public String enter(Player p)
    {
        if (isGateOpen())
        {
            if (p.getLocation() == into)
            {
                return from.enter(p);
            }
            else if (p.getLocation() == from)
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
            return "This gate is locked! Try changing the states of the switches!";
        }
    }

    @Override
    public String getName()
    {
        return "Gate";
    }

}
