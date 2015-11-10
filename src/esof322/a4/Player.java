package esof322.a4;

import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * Adventure Game Program Code Copyright (c) 1999 James M. Bieman
 *
 * To compile: javac AdventureGame.java To run: java AdventureGame
 *
 * The main routine is AdventureGame.main
 **/

/*
 * Todd Beckman
 * Dylan Hills
 * Kalvyn Lu
 * Luke O'Neill
 * Luke Welna
 */
/*
 * Dylan Hills: drop() and go() now return strings of the item and the room respectively.
 */
public class Player {
    private Room myLoc;
    private ArrayDeque<Item> inventory = new ArrayDeque<Item>(MAXIMUM_ITEM_COUNT);
    
    /**
     * The maximum number of items a player is allowed to hold
     */
    public static int MAXIMUM_ITEM_COUNT = 2;

    public void setRoom(Room r)
    {
        myLoc = r;
    }

    public String look()
    {
        return myLoc.getDesc();
    }

    public String go(int direction)
    {
        return myLoc.exit(direction, this);
    }

    public void pickUp(Item item)
    {
    	if (myLoc.hasItem(item) && inventory.size() < MAXIMUM_ITEM_COUNT)
        {
        	inventory.add(item);
            myLoc.removeItem(item);
        }
    }

    public Item[] getItems() {
    	return (Item[])inventory.toArray();
    }
    
    public boolean hasItem(Item item)
    {
    	return inventory.contains(item);
    }
    
    public String drop(Item item)
    {
    	if (inventory.contains(item))
    	{
    		myLoc.addItem(item);
    		inventory.remove(item);
    		return item.getDesc();
        }
        return "";
    }

    public void setLoc(Room r)
    {
        myLoc = r;
    }

    public Room getLoc()
    {
        return myLoc;
    }

    public String showInventory()
    {
    	StringBuilder sb = new StringBuilder();
    	for (Item item: inventory)
    	{
    		sb.append(item.getName());
    		sb.append(": ");
    		sb.append(item.getDesc());
    		sb.append("\n");
    	}
        return sb.toString();
    }

    public boolean areHandsFull()
    {
        return inventory.size() == MAXIMUM_ITEM_COUNT;
    }

    public boolean areHandsEmpty()
    {
        return inventory.size() == 0;
    }

    public int numItemsCarried()
    {
        return inventory.size();
    }

}
