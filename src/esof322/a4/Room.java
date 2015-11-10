package esof322.a4;

/**
 * Adventure Game Program Code Copyright (c) 1999 James M. Bieman
 *
 * To compile: javac AdventureGame.java To run: java AdventureGame
 *
 * The main routine is AdventureGame.main
 *
 * Update August 2010: refactored Vector contents into ArrayList<Item> contents. This gets rid of the use of obsolete Vector and
 * makes it type safe.
 **/

// class Room
/*
 * Todd Beckman
 * Dylan Hills
 * Kalvyn Lu
 * Luke O'Neill
 * Luke Welna
 */

import java.util.ArrayList;

public class Room implements CaveSite
{
    private final String description;
    private CaveSite[] side = new CaveSite[6];
    private ArrayList<Item> contents = new ArrayList<Item>();

    /**
     * Constructs a Room. This default constructor is not recommended due to excessive memory allocation.
     * @param description The description the player sees upon enterring this room
     */
    Room(String description)
    {
    	this.description = description;
        side[Direction.NORTH] = new Wall();
        side[Direction.EAST] = new Wall();
        side[Direction.SOUTH] = new Wall();
        side[Direction.WEST] = new Wall();
        side[Direction.UP] = new Wall();
        side[Direction.DOWN] = new Wall();
    }
    
    /**
     * Alias constructor which allows the six sides to be set by the constructor, preventing
     * any need for unnecessary memory allocation.
     * 
     * @param north The CaveSite found to the north of this room
     * @param east The CaveSite found to the east of this room
     * @param south The CaveSite found to the south of this room
     * @param west The CaveSite found to the west of this room
     * @param up The CaveSite found above this room
     * @param down The CaveSite found below this room
     */
    Room(String description, CaveSite north, CaveSite east, CaveSite south, CaveSite west, CaveSite up, CaveSite down)
    {
        side[Direction.NORTH] = north;
        side[Direction.EAST] = east;
        side[Direction.SOUTH] = south;
        side[Direction.WEST] = west;
        side[Direction.UP] = up;
        side[Direction.DOWN] = down;
        this.description = description;
    }

    public void setSide(int direction, CaveSite m)
    {
        side[direction] = m;
    }

    public void addItem(Item theItem)
    {
        contents.add(theItem);
    }

    public void removeItem(Item theItem)
    {
        contents.remove(theItem);
    }

    public boolean roomEmpty()
    {
        return contents.isEmpty();
    }

    public Item[] getRoomContents()
    {
        Item[] contentsArray = new Item[contents.size()];
        contentsArray = contents.toArray(contentsArray);
        return (Item[])contents.toArray();
    }

    public String enter(Player p)
    {
        p.setLoc(this);
        return "Entered a new room";
    }

    public String exit(int direction, Player p)
    {
        return side[direction].enter(p);
    }

    public String getDesc()
    {
    	StringBuilder sb = new StringBuilder();
    	sb.append(description);
    	sb.append("\n\nRoom Contents: ");
        for (Item i: contents)
        {
        	sb.append(i.getDesc());
        	sb.append(" ");
        }
        sb.append("\n");
        return sb.toString();
    }

}
