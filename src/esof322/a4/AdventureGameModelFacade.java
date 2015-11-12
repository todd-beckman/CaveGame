package esof322.a4;

/*
 * Todd Beckman
 * Dylan Hills
 * Kalvyn Lu
 * Luke O'Neill
 * Luke Welna
 */
/*
 * Todd Beckman: Provided private game field to be initialized at construction
 * Kalvyn Lu: Added game(), drop(), and takeInput() Methods. The game also shows the inventory.
 * Dylan Hills: Added roomView, action, getView(), setView(), getAction(),setAction(). Added AdventureGameView and setGUI().
 */
public class AdventureGameModelFacade implements View
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
     * The current room's description
     */
    private String roomView = "";

    /**
     * The most recent action taken
     */
    private String action = "";
    
    private String items = "";

    /**
     * Prevent button control for events such as input
     */
    private boolean lockCommands = false;

    /**
     * The input mechanism to interface with the game
     **/
    private Object messager;

    public AdventureGameModelFacade()
    {
        this.gui = new AdventureGameView(this);
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

    public void setItems(String items)
    {
        this.items = items;
    }
    
    public String getItems()
    {
        return items;
    }
    
    public String getAction()
    {
        return action;
    }
    
    @Override
    public void showActionMessage(String message)
    {
        action = message;
        gui.displayCurrentInfo();
    }

    @Override
    public void showStatusMessage(String message)
    {
        this.roomView = message;
        gui.displayCurrentInfo();
    }
    
    public String getStatus()
    {
        return roomView;
    }

    public Item getItemChoice(Player player)
    {
        return gui.getItemChoice(player.getItems());
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
    public int chooseBetween(String[] options)
    {
        // TODO Auto-generated method stub
        return 0;
    }

}
