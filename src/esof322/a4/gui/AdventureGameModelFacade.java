package esof322.a4.gui;

import esof322.a4.Controller;
import esof322.a4.util.Command;

public class AdventureGameModelFacade implements Controller
{

    // some private fields to reference current location,
    // its description, what I'm carrying, etc.
    //
    // These methods and fields are left as exercises.

    /**
     * The view with which the player will see the game
     */
    private AdventureGameView gui;

    /**
     * Prevent button control for events such as input
     */
    private boolean lockCommands = false;

    /**
     * The object used to lock synchronized messages
     **/
    private Object messager;

    public AdventureGameModelFacade(AdventureGameView gui)
    {
        this.gui = gui;
    }
    
    public AdventureGameView getView()
    {
        return gui;
    }

    private char command = ' ';
    private void sendCommand(char c)
    {
        if (!lockCommands)
        {
            synchronized (messager)
            {
                command = c;
                messager.notifyAll();
            }
        }
    }

    public void goUp()
    {
        sendCommand(Command.UP);
    }

    public void goDown()
    {
        sendCommand(Command.DOWN);
    }

    public void goNorth()
    {
        sendCommand(Command.NORTH);
    }

    public void goSouth()
    {
        sendCommand(Command.SOUTH);
    }

    public void goEast()
    {
        sendCommand(Command.EAST);
    }

    public void goWest()
    {
        sendCommand(Command.WEST);
    }

    public void grab()
    {
        sendCommand(Command.GRAB);
    }

    public void drop()
    {
        sendCommand(Command.DROP);
    }

    @Override
    public char receiveChar()
    {
        synchronized (messager)
        {
            try
            {
                while (command == ' ')
                {
                    messager.wait();
                }
            }
            catch (InterruptedException e){}
        }
        char received = command;
        command = ' ';
        return received;
    }



    @Override
    public int chooseBetween(String subject, String[] options)
    {
        return gui.getChoice(subject, options);
    }

    @Override
    public void showControls(){ /* lol */}

    @Override
    public void showStatusMessage(String message)
    {
        gui.setStatusMessage(message);
    }

    @Override
    public void showRoomDescription(String description)
    {
        gui.setRoomDescription(description);
    }

    @Override
    public void showRoomContents(String[] contents)
    {
        gui.setRoomContents(contents);
    }

    @Override
    public void showRoomInteractables(String[] interactables)
    {
        gui.setInteractables(interactables);
    }

    @Override
    public void showPlayerInventory(String[] inventory)
    {
        gui.setInventory(inventory);
    }
}
