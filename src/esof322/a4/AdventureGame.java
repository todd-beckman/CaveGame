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

import esof322.a4.level0.Level0Factory;
import esof322.a4.util.Command;
import esof322.a4.util.Direction;

public class AdventureGame
{
    int counter = 0;
    
    private Factory factory;
    
    private Controller controller;

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
    
    /**
     * Converts between Command and Direction, and returns
     * -1 for input not matching a direction.
     * @param input The character, preferably enumerated from the Command class
     * @return The direction matching the Direction class enumeration
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
     * Determines which item the player will be picking up
     * @return The item from the room
     */
    private Item choosePickupItem()
    {
        Item[] contents = player.getLocation().getRoomContents();
        String[] items = new String[contents.length];
        for (int i = 0; i < items.length; i++)
        {
            items[i] = contents[i].getName() + ": " + contents[i].getDesc();
        }
        return contents[controller.chooseBetween(items)];
    }

    /**
     * Determines which item the player will be dropping
     * @return The item from inventory
     */
    private Item chooseDropItem()
    {
        Item[] inventory = player.getItems();
        String[] items = new String[inventory.length];
        for (int i = 0; i < items.length; i++)
        {
            items[i] = inventory[i].getName() + ": " + inventory[i].getDesc();
        }
        return inventory[controller.chooseBetween(items)];
    }

    /**
     * Initializes the quest and thus begins the game
     */
    public void startQuest()
    {
        
        //  TODO: dialogue to choose which level to load
        factory = new Level0Factory();
        
        //  TODO: dialogue to choose whether to load from file
        //  requires checking to see if save file exists
        
        player = factory.createAdventure();
        
        char key = 'p'; // p is completely arbitrary
        
        boolean gameon = true;

        /* The main query user, get command, interpret, execute cycle. */
        while (gameon)
        {
            // model.setAction("hello" + counter++);
            controller.showStatusMessage(player.look() +
                    "\n\nYou are carrying: " + player.showInventory());

            /* get next move */
            int direction = -1;

            // Display the controls
            if (controller instanceof ConsoleController)
            {
                controller.showActionMessage("Which way (n,s,e,w,u,d),\n" + " or grab (g) or toss (t) an item,\n" + " or quit (q)?");
            }
            
            key = controller.receiveChar();
            
            
            direction = convertDirection(key);
            if (direction != -1)
            {
                controller.showActionMessage(player.go(direction));
            }
            // Grab item
            else
            {
                if (key == Command.QUIT)
                {
                    gameon = false;
                }
                if (key == Command.GRAB)
                {
                    if (player.areHandsFull())
                    {
                        controller.showActionMessage("Your hands are full. Try getting rid of something.");
                    }
                    else
                    {
                        if ((player.getLocation()).roomEmpty())
                        {
                            controller.showActionMessage("The room is empty.");
                        }
                        else
                        {
                            Item grabItem = choosePickupItem();
                            player.pickUp(grabItem);
                            (player.getLocation()).removeItem(grabItem);
                            controller.showActionMessage("Grabbed the item " + grabItem.getDesc());
                        }
                    }
                }
                // Toss Item
                else
                {
                    if (key == Command.DROP)
                    {
                        if (player.areHandsEmpty())
                        {
                            controller.showActionMessage("You have nothing to drop.");
                        }
                        else
                        {
                            Item dropItem = chooseDropItem();
                            controller.showActionMessage("Dropped " + player.drop(dropItem));
                        }
                    }
                }
            }
        }
        controller.showActionMessage("Bye! Have a very good time!");
    }

    /**
     * Prepares the game for playing. startQuest() is still needed in order to begin the
     * quest.
     * @param view The user interface to be used
     */
    public AdventureGame(Controller view)
    {
        this.controller = view;
        view.showStatusMessage("Welcome to the Adventure Game,\n"
                + "which is inspired by an old game called the Colossal Cave Adventure.\n"
                + "Java implementation Copyright (c) 1999 - 2012 by James M. Bieman\n");
    }

    public static void main(String args[]) throws IOException
    {
        AdventureGame theGame = new AdventureGame(new ConsoleController());
        theGame.startQuest();
    }

}
