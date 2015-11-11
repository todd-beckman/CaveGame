package esof322.a4;

/**
 * Adventure Game Program Code Copyright (c) 1999 James M. Bieman
 *
 * To compile: javac AdventureGame.java To run: java AdventureGame
 *
 * The main routine is AdventureGame.main
 **/

// class Key.
/*
 * Todd Beckman Dylan Hills Kalvyn Lu Luke O'Neill Luke Welna
 */

public class Key extends Item
{
    private int id;
    
    public Key(String description, int id)
    {
        super(description);
        this.id = id;
    }
    
    public Key(String description)
    {
        super(description);
        this.id = 0;
    }

    @Override
    public String getName()
    {
        return "Key";
    }
    
    public int getID()
    {
        return id;
    }
}
