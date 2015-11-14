package esof322.a4;

import java.io.File;

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
import esof322.a4.level1.Level1Factory;
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

        //  TODO: dialogue to choose whether to load from file
        //  requires checking to see if save file exists
        //  TODO: dialogue to choose which level to load
        
        int choice = -1;
        controller.showActionMessage("Which adventure will you be playing today?");
        String[] loadOptions = {"Start Level 0", "Start Level 1"};
        choice = controller.chooseBetween(loadOptions);
        
        switch (choice)
        {
        case 0:
            factory = new Level0Factory();
            break;
        case 1:
            factory = new Level1Factory();
            break;
        default:
            System.out.println("Oops! Something broke! That factory does not exist!");
            System.exit(1);
        }
        
        if (new File(factory.getSaveLocation()).exists())
        {
            choice = -1;
            controller.showActionMessage("Would you like to resume your adventure?");
            String[] resumeOptions = {"Resume", "New Game"};
            choice = controller.chooseBetween(resumeOptions);
            switch (choice)
            {
            case 0:
                player = factory.loadAdventure(factory.getSaveLocation());
                break;
            case 1:
                player = factory.createAdventure();
                break;
            default:
                System.out.println("Oops! Something broke! Must pick Resume or New Game!");
                System.exit(1);
            }
        }
        else
        {
            player = factory.createAdventure();
        }
        
        if (player == null)
        {
            System.out.println("Oops!");
        }
                
        boolean gameon = true;

        //  Game loop
        while (gameon)
        {
            controller.showStatusMessage(player.look() +
                    "\n\nYou are carrying: " + player.showInventory());


            // Display the controls
            if (controller instanceof ConsoleController)
            {
                controller.showActionMessage("Which way (n,s,e,w,u,d),\n" + " or grab (g) or toss (t) an item,\n" + " or quit (q)?");
            }
            
            switch (controller.receiveChar())
            {
            case Command.NORTH:
                controller.showActionMessage(player.go(Direction.NORTH));
                break;
            case Command.EAST:
                controller.showActionMessage(player.go(Direction.EAST));
                break;
            case Command.SOUTH:
                controller.showActionMessage(player.go(Direction.SOUTH));
                break;
            case Command.WEST:
                controller.showActionMessage(player.go(Direction.WEST));
                break;
            case Command.UP:
                controller.showActionMessage(player.go(Direction.UP));
                break;
            case Command.DOWN:
                controller.showActionMessage(player.go(Direction.DOWN));
                break;
            case Command.QUIT:
                gameon = false;
                break;
            case Command.GRAB:
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
                break;
            case Command.DROP:
                if (player.areHandsEmpty())
                {
                    controller.showActionMessage("You have nothing to drop.");
                }
                else
                {
                    Item dropItem = chooseDropItem();
                    controller.showActionMessage("Dropped " + player.drop(dropItem));
                }
                break;
            case Command.SAVE:
                controller.showActionMessage(factory.saveAdventure());
                break;
            default:
                controller.showActionMessage("Sorry. Did you mean 'q'?");
                break;
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
