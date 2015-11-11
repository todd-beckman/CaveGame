package esof322.a4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*
 * Todd Beckman
 * Dylan Hills
 * Kalvyn Lu
 * Luke O'Neill
 * Luke Welna
 */
/**
 * Adventure Game Program Code Copyright (c) 1999 James M. Bieman
 *
 * To compile: javac AdventureGame.java To run: java AdventureGame
 *
 * The main routine is AdventureGame.main
 **/

/**
 * Adventure Game Program Code Copyright (c) 1999-2012 James M. Bieman The Adventure game is based on the "Colossal Cave Adventure"
 * originally designed by Will Crowther and implemented by Will Crowther and Don Wood in Fortran in 1975 and 1976.
 *
 * This micro-version is a variant of the original cave system and is implemented in Java with just a few rooms and with a much more
 * limited vocabulary.
 *
 * Updated August 2010, January 2012 - Code is put into package cs314.a2 to match current CS314 coding standards. Updated January
 * 2012 - Renamed as the "Adventure Game"
 *
 * To compile: javac cs314.a2.AdventureGame.java To run: java cs314.a2.AdventureGame
 *
 * The main routine is AdventureGame.main
 **/

/**
 * class Adventure: Primary method, createCave, creates the cave system. It
 * eventually be replaced with a more flexible mechanism to support input and
 * output from devices other than an ASCII terminal.
 *
 * Room descriptions are followed by a room identifier, to ease debugging and
 * testing. These would be removed to help confuse the user, which is our
 * ultimate aim.
 *
 * I haven't added all of the room descriptions. They will be done later.
 *
 * In this version all I/O is through standard I/O; I/O is to and from the
 * console.
 */

public class Adventure
{
    public static final String LEVEL_0 = "level0.dat";
    public static Room createAdventure(String fileName)
    {
        String save = "";
        try
        {
            BufferedReader fr = new BufferedReader(new FileReader(fileName));
            save = fr.readLine();
            fr.close();
        }
        catch(IOException e)
        {
            System.exit(0);
            return null;
        }
        
        State state = State.generateAdventureFromSave(save);
        return (Room)(state.rooms[0]);
    }
}
