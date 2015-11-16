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
        return contents[controller.chooseBetween("Pick up which item?", items)];
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
        return inventory[controller.chooseBetween("Pick up which item?", items)];
    }

    /**
     * Initializes the quest and thus begins the game
     */
    public void startQuest()
    {
        int choice = -1;
        String[] loadOptions = {"Start Level 0", "Start Level 1"};
        choice = controller.chooseBetween(
                "Which adventure will you be playing today?", loadOptions);
        
        switch (choice)
        {
        case 0:
            factory = new Level0Factory();
            break;
        case 1:
            factory = new Level1Factory();
            break;
            
        //  this should not happen
        default:
            System.out.println("Oops! Something broke! That level does not exist!");
            System.exit(1);
        }
        
        Adventure adventure = new Adventure(factory);
        
        if (new File(factory.getSaveLocation()).exists())
        {
            choice = -1;
            String[] resumeOptions = {"Resume", "New Game"};
            choice = controller.chooseBetween(
                    "Would you like to resume your adventure?", resumeOptions);
            switch (choice)
            {
            case 0:
                player = adventure.loadAdventure();
                break;
            case 1:
                player = adventure.newAdventure();
                break;
            default:
                System.out.println("Oops! Something broke! Must pick Resume or New Game!");
                System.exit(1);
            }
        }
        else
        {
            player = adventure.newAdventure();
        }
        
        //  In case something broke, don't let the game go on.
        if (player == null)
        {
            System.out.println("Oops! Loading didn't seem to go right.");
            System.exit(1);
        }
        
        controller.showStatusMessage(factory.getStartMessage());
        
        boolean gameon = true;

        //  Game loop
        while (gameon)
        {
            Room location = player.getLocation();
            controller.showRoomDescription(location.getDesc());
            controller.showRoomContents(location.showContents());
            controller.showRoomInteractables(location.showInteractables());
            controller.showPlayerInventory(player.showInventory());

            // Display the controls
            controller.showControls();
            
            switch (controller.receiveChar())
            {
            case Command.NORTH:
                controller.showStatusMessage(player.go(Direction.NORTH));
                break;
            case Command.EAST:
                controller.showStatusMessage(player.go(Direction.EAST));
                break;
            case Command.SOUTH:
                controller.showStatusMessage(player.go(Direction.SOUTH));
                break;
            case Command.WEST:
                controller.showStatusMessage(player.go(Direction.WEST));
                break;
            case Command.UP:
                controller.showStatusMessage(player.go(Direction.UP));
                break;
            case Command.DOWN:
                controller.showStatusMessage(player.go(Direction.DOWN));
                break;
            case Command.QUIT:
                gameon = false;
                break;
            case Command.GRAB:
                if (player.areHandsFull())
                {
                    controller.showStatusMessage("Your hands are full. Try getting rid of something.");
                }
                else
                {
                    if ((player.getLocation()).roomEmpty())
                    {
                        controller.showStatusMessage("The room is empty.");
                    }
                    else
                    {
                        Item grabItem = choosePickupItem();
                        player.pickUp(grabItem);
                        (player.getLocation()).removeItem(grabItem);
                        controller.showStatusMessage("Grabbed the item " + grabItem.getDesc());
                    }
                }
                break;
            case Command.DROP:
                if (player.areHandsEmpty())
                {
                    controller.showStatusMessage("You have nothing to drop.");
                }
                else
                {
                    Item dropItem = chooseDropItem();
                    controller.showStatusMessage("Dropped " + player.drop(dropItem));
                }
                break;
            case Command.INTERACT:
                controller.showStatusMessage(location.interact());
                break;
            case Command.SAVE:
                controller.showStatusMessage(adventure.saveAdventure());
                break;
            default:
                controller.showStatusMessage("Sorry. Did you mean 'q'?");
                break;
            }
        }
        controller.showStatusMessage("Bye! Have a very good time!");
    }

    /**
     * Prepares the game for playing. startQuest() is still needed in order to begin the
     * quest.
     * @param view The user interface to be used
     */
    public AdventureGame(Controller view)
    {
        this.controller = view;
    }

    public static void main(String args[]) throws IOException
    {
        AdventureGame theGame = new AdventureGame(new ConsoleController());
        theGame.startQuest();
    }

}
