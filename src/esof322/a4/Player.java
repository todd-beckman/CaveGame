package esof322.a4;

import java.util.ArrayDeque;

/**
 * Adventure Game Program Code Copyright (c) 1999 James M. Bieman
 *
 * To compile: javac AdventureGame.java To run: java AdventureGame
 *
 * The main routine is AdventureGame.main
 **/

public class Player
{
    private Room location;
    
    //  Used to store a dynamic inventory without gaps with constant insert, search, and delete
    private ArrayDeque<Item> inventory = new ArrayDeque<Item>(MAXIMUM_ITEM_COUNT);

    /**
     * The maximum number of items a player is allowed to hold
     */
    public static int MAXIMUM_ITEM_COUNT = 2;
    
    /**
     * Force the player to move to a new location. Should not be called outside of
     * level creation as it violates the state machine
     * @param room The current room
     */
    public void setLocation(Room room)
    {
        location = room;
    }

    /**
     * Gets the description of the room the player is currently in
     * @return The current room's description
     */
    public String look()
    {
        return location.getDesc() + "\n" + location.showContents();
    }

    /**
     * Attempt to move in a certain direction
     * @param direction The direction to move
     * @return The description of the success or failure
     */
    public String go(int direction)
    {
        return location.exit(direction, this);
    }
    
    /**
     * Gets the list of items the player currently has
     * @return The array containing the items
     */
    public Item[] getItems()
    {
        return inventory.toArray(new Item[0]);
    }
    
    /**
     * Determines whether a player is holding a certain item
     * @param item The item to search for
     * @return Whether the player has it
     */
    public boolean hasItem(Item item)
    {
        return inventory.contains(item);
    }
    
    public boolean hasKey()
    {
        for (Item i: inventory)
        {
            if (i instanceof Key)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Pick up an item from the room
     * @param item The item to be picked up
     */
    public void pickUp(Item item)
    {
        if (location.hasItem(item) && inventory.size() < MAXIMUM_ITEM_COUNT)
        {
            inventory.add(item);
            location.removeItem(item);
        }
    }

    /**
     * Drops an item into the room
     * @param item The item to be dropped
     * @return The status of the success or failure of dropping the item
     */
    public String drop(Item item)
    {
        if (inventory.contains(item))
        {
            location.addItem(item);
            inventory.remove(item);
            return item.getDesc();
        }
        return "";
    }

    /**
     * Gets the player's current location
     * @return The location
     */
    public Room getLocation()
    {
        return location;
    }

    /**
     * Formats the player's inventory into a string with each item on a new line
     * @return The player's inventory
     */
    public String showInventory()
    {
        StringBuilder sb = new StringBuilder();
        for (Item item : inventory)
        {
            sb.append(item.getName());
            sb.append(": ");
            sb.append(item.getDesc());
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Determines if the player's hands are full
     * @return Whether the player can pick up an item
     */
    public boolean areHandsFull()
    {
        return inventory.size() == MAXIMUM_ITEM_COUNT;
    }
    
    /**
     * Determines if the player's hands are empty
     * @return Whether the player has an item to be dropped
     */
    public boolean areHandsEmpty()
    {
        return inventory.size() == 0;
    }
    
    /**
     * Checks the number of items the player has
     * @return The number
     */
    public int numItemsCarried()
    {
        return inventory.size();
    }
}
