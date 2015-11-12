package esof322.a4;

/**
 * Adventure Game Program Code Copyright (c) 1999 James M. Bieman
 *
 * To compile: javac cs314.a2.AdventureGame.java To run: java cs314.a2.AdventureGame The main routine is AdventureGame.main
 *
 * The AdventureGame is a Java implementation of the old text based adventure game from long ago. The design was adapted from one in
 * Gamma, Helm, Johnson, Vlissides (The Gang of Four), "Design Patterns: Elements of Reusable Object-Oriented Software",
 * Addison-Wesley, 1997.
 *
 * To really be consistent with the old game we would need a much larger cave system with a hundred or so rooms, and a more
 * "understanding" user interface.
 *
 * The old game just put you near the cave, displayed the "view" as text, and offered no instructions. If you gave a command that it
 * understood, you could proceed. If your command could not be interpreted, nothing would happen. Rooms were never identified
 * precisely; your only clues came from the descriptions. You would have to remember or create your own map of the cave system to
 * find your way around. Sometimes you could not return exactly the way you came. An exit to the east may not enter the west side of
 * the "adjacent room"; the passage might curve.
 *
 * Perhaps, this implementation can evolve to be closer to the original game, or even go beyond it.
 *
 * Jim Bieman September 1999.
 *
 *
 * /** Adventure Game Program Code Copyright (c) 1999 James M. Bieman Updated August 2010 - Code is put into package cs314.a2 to
 * match current CS314 coding standards. - Obsolete Vector is replaced with ArrayList with type parameters. - Deletion of some
 * unused variables.
 *
 * To compile: javac cs314.a2.AdventureGame.java To run: java cs314.a2.AdventureGame
 *
 * The main routine is AdventureGame.main
 **/

import java.io.IOException;

/*
 * Todd Beckman
 * Dylan Hills
 * Kalvyn Lu
 * Luke O'Neill
 * Luke Welna
 */
public class AdventureGame
{
    int counter = 0;
    
    private Factory factory;
    
    private View view;

    /** The current player **/
    private Player player = new Player();

    /** Gets the player of the game **/
    public Player getPlayer()
    {
        return player;
    }

    /**
     * Our system-wide internal representation of directions is integers. Here,
     * we convert input string directions into integers. Internally, we use
     * integers 0-9 as directions throughout the program. This is a bit of a
     * cludge, but is simpler for now than creating a Direction class. I use
     * this cludge because Java in 1999 did not have an enumerated data type.
     */
    private int convertDirection(char input)
    {
        switch (input)
        {
        case Command.NORTH:
            return Direction.NORTH;
        case Command.EAST:
            return Direction.EAST;
        case Command.SOUTH:
            return Direction.SOUTH;
        case Command.WEST:
            return Direction.WEST;
        case Command.DOWN:
            return Direction.DOWN;
        case Command.UP:
            return Direction.UP;
        }
        return -1;
    }

    /**
     * choosePickupItem determines the specific item that a player wants to pick
     * up.
     */
    private Item choosePickupItem(Player player)
    {
        Item[] contents = player.getLocation().getRoomContents();
        String[] items = new String[contents.length];
        for (int i = 0; i < items.length; i++)
        {
            items[i] = contents[i].getName() + contents[i].getDesc();
        }
        return contents[view.chooseBetween(items)];
    }

    /**
     * chooseDropItem determines the specific item that a player wants to drop
     */
    private Item chooseDropItem(Player p)
    {
        Item[] inventory = player.getItems();
        String[] items = new String[inventory.length];
        for (int i = 0; i < items.length; i++)
        {
            items[i] = inventory[i].getName() + inventory[i].getDesc();
        }
        return inventory[view.chooseBetween(items)];
    }

    public void startQuest()
    {
        
        //  TODO: dialogue to choose which level to load
        factory = new Level0Factory();
        
        //  TODO: dialogue to choose whether to load from file
        //  requires checking to see if save file exists
        
        player = factory.createAdventure();
        
        char key = 'p'; // p is completely arbitrary

        /* The main query user, get command, interpret, execute cycle. */
        while (key != Command.QUIT)
        {
            // model.setAction("hello" + counter++);
            view.showStatusMessage(player.look() + "\n\nYou are carrying: " + player.showInventory() + '\n');

            /* get next move */
            int direction = -1;

            // Display the controls
            if (view instanceof ConsoleView)
            {
                view.showActionMessage("Which way (n,s,e,w,u,d),\n" + " or grab (g) or toss (t) an item,\n" + " or quit (q)?" + '\n');
            }
            
            key = view.receiveChar();
            
            
            direction = convertDirection(key);
            if (direction != -1)
            {
                view.showActionMessage(player.go(direction));
            }
            // Grab item
            else
            {
                if (key == Command.GRAB)
                {
                    if (player.areHandsFull())
                    {
                        view.showActionMessage("Your hands are full. Try getting rid of something.");
                    }
                    else
                        if ((player.getLocation()).roomEmpty())
                        {
                            view.showActionMessage("The room is empty.");
                        }
                        else
                        {
                            Item grabItem = choosePickupItem(player);
                            player.pickUp(grabItem);
                            (player.getLocation()).removeItem(grabItem);
                            view.showActionMessage("Grabbed the item " + grabItem.getDesc());
                        }
                }
                // Toss Item
                else
                {
                    if (key == Command.DROP)
                    {
                        if (player.areHandsEmpty())
                        {
                            view.showActionMessage("You have nothing to drop.");
                        }
                        else
                        {
                            Item dropItem = chooseDropItem(player);
                            view.showActionMessage("Dropped " + player.drop(dropItem));
                        }
                    }
                }
            }
        }
        view.showActionMessage("Bye! Have a very good time!");
    }

    /**
     * Setup a game in which the keyboard will be used
     */
    public AdventureGame(View view)
    {
        this.view = view;
        view.showStatusMessage("Welcome to the Adventure Game,\n"
                + "which is inspired by an old game called the Colossal Cave Adventure.\n"
                + "Java implementation Copyright (c) 1999 - 2012 by James M. Bieman\n");
    }

    public static void main(String args[]) throws IOException
    {
        AdventureGame theGame = new AdventureGame(new ConsoleView());
        theGame.startQuest();
    }

}
