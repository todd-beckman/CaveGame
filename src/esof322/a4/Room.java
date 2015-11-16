package esof322.a4;

import java.util.ArrayList;

import esof322.a4.level1.Interactable;

/**
 * Adventure Game Program Code Copyright (c) 1999 James M. Bieman
 *
 * To compile: javac AdventureGame.java To run: java AdventureGame
 *
 * The main routine is AdventureGame.main
 *
 * Update August 2010: refactored Vector contents into ArrayList<Item> contents.
 * This gets rid of the use of obsolete Vector and makes it type safe.
 **/

/*
 * Todd Beckman Dylan Hills Kalvyn Lu Luke O'Neill Luke Welna
 */

public class Room implements CaveSite
{
    protected final String description;
    protected CaveSite[] side = new CaveSite[6];
    protected ArrayList<Item> contents = new ArrayList<Item>();
    protected ArrayList<Interactable> interactables = new ArrayList<Interactable>();

    /**
     * Constructs a Room. This default constructor is not recommended due to
     * excessive memory allocation.
     * 
     * @param description
     *            The description the player sees upon enterring this room
     */
    public Room(String description)
    {
        this.description = description;
        side[0] = new Wall();
        side[1] = new Wall();
        side[2] = new Wall();
        side[3] = new Wall();
        side[4] = new Wall();
        side[5] = new Wall();
    }
    
    public String getName()
    {
        return "Room";
    }

    public CaveSite[] getSides()
    {
        return side;
    }
    
    public void setSide(int direction, CaveSite m)
    {
        side[direction] = m;
    }

    public void addItem(Item item)
    {
        contents.add(item);
    }

    public void removeItem(Item item)
    {
        contents.remove(item);
    }
    
    public void addInteractable(Interactable interactable)
    {
        interactables.add(interactable);
    }
    
    public String interact()
    {
        if (interactables.size() == 0)
        {
            return "There is nothing to interact with in here!";
        }
        StringBuilder sb = new StringBuilder();
        for (Interactable i: interactables)
        {
            sb.append(i.interact());
            sb.append("\n");
        }
        return sb.toString();
    }

    public boolean roomEmpty()
    {
        return contents.isEmpty();
    }

    public boolean hasItem(Item item)
    {
        return contents.contains(item);
    }

    public Item[] getRoomContents()
    {
        return contents.toArray(new Item[0]);
    }

    public String enter(Player player)
    {
        player.setLocation(this);
        return "Entered a new room";
    }

    public String exit(int direction, Player p)
    {
        return side[direction].enter(p);
    }

    public String getDesc()
    {
        StringBuilder sb = new StringBuilder();
        String[] desc = description.split("~");
        for (String str: desc)
        {
            sb.append(str);
            sb.append("\n");
        }
        return sb.toString();
    }
    
    public String[] showContents()
    {
        String[] items = new String[contents.size()];
        for (int i = 0; i < items.length; i++)
        {
            Item item = contents.get(i);
            items[i] = item.getName() + ": " + item.getDesc();
        }
        return items;
    }

    public String[] showInteractables()
    {
        String[] ints = new String[interactables.size()];
        for (int i = 0; i < ints.length; i++)
        {
            Interactable interactable = interactables.get(i);
            ints[i] = interactable.getName() + ": " + interactable.getDesc();
        }
        return ints;
    }

}
