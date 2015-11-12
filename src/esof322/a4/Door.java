package esof322.a4;

/**
 * Adventure Game Program Code Copyright (c) 1999 James M. Bieman
 *
 * To compile: javac AdventureGame.java To run: java AdventureGame
 *
 * The main routine is AdventureGame.main
 **/

// class Door
/*
 * Todd Beckman Dylan Hills Kalvyn Lu Luke O'Neill Luke Welna
 */

public class Door implements CaveSite
{
    /**
     * In this implementation doors are always locked. A player must have the
     * correct key to get through a door. Doors automatically lock after a
     * player passes through.
     */
    private Key key;

    /** The door's location. */
    private CaveSite outSite;
    private CaveSite inSite;
    
    private int id;

    /** We can construct a door at the site. */
    public Door(CaveSite out, CaveSite in, Key k)
    {
        outSite = out;
        inSite = in;
        key = k;
        key.getID();
    }
    
    public int getID()
    {
        return id;
    }
    
    public CaveSite getOrigin()
    {
        return inSite;
    }
    
    public Key getKey()
    {
        return key;
    }
    
    public CaveSite getDestination()
    {
        return outSite;
    }
    
    public String getName()
    {
        return "Door";
    }

    public String enter(Player player)
    {
        if (player.hasItem(key))
        {
            String output = "Your key works! The door creaks open,\nand slams behind you after you pass through.";
            if (player.getLocation() == outSite)
            {
                inSite.enter(player);
                return output;
            }
            if (player.getLocation() == inSite)
            {
                outSite.enter(player);
                return output;
            }
            return "You cannot use this door from there!";
        }
        else
        {
            return "You don't have the key for this door! \n Sorry.";
        }
    }

}
