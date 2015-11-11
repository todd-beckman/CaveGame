package esof322.a4;

/**
 * Adventure Game Program Code Copyright (c) 1999 James M. Bieman
 *
 * To compile: javac AdventureGame.java To run: java AdventureGame
 *
 * The main routine is AdventureGame.main
 **/

// class Treasure
/*
 * Todd Beckman Dylan Hills Kalvyn Lu Luke O'Neill Luke Welna
 */

public class Treasure extends Item
{
    public Treasure(String description)
    {
        super(description);
    }

    @Override
    public String getName()
    {
        return "Treasure";
    }
}
