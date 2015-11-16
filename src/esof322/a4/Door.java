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

    protected CaveSite from;
    protected CaveSite into;
    private Key key;

    /**
     * Constructs a door 
     * @param out
     * @param in
     */
    public Door(CaveSite from, CaveSite into, Key key)
    {
        this.from = from;
        this.into = into;
        this.key = key;
    }
    
    public CaveSite getOrigin()
    {
        return into;
    }

    public CaveSite getDestination()
    {
        return from;
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
            if (player.getLocation() == from)
            {
                into.enter(player);
                return output;
            }
            if (player.getLocation() == into)
            {
                from.enter(player);
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
