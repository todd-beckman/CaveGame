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

    private CaveSite outSite;
    private CaveSite inSite;
    private Key key;

    /**
     * Constructs a door 
     * @param out
     * @param in
     */
    public Door(CaveSite out, CaveSite in, Key key)
    {
        outSite = out;
        inSite = in;
        this.key = key;
    }
    
    public CaveSite getOrigin()
    {
        return inSite;
    }

    public CaveSite getDestination()
    {
        return outSite;
    }
    
    public Key getKey()
    {
        return key;
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
