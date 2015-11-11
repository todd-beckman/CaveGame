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
public class AdventureGameModelFacade
{

    // some private fields to reference current location,
    // its description, what I'm carrying, etc.
    //
    // These methods and fields are left as exercises.
    /**
     * The game from which data will be requested
     **/
    private AdventureGame game;

    /**
     * The view with which the player will see the game
     */
    private AdventureGameView view;

    /**
     * The current room's description
     */
    private String roomView = "";

    /**
     * The most recent action taken
     */
    private String action = "";

    private boolean lockCommands = false;

    /**
     * The input mechanism to interface with the game
     **/
    private SynchronizedMessageHandler messager;

    AdventureGameModelFacade()
    {
        this.messager = new SynchronizedMessageHandler();
        this.game = new AdventureGame(this, messager);
    }

    public void startQuest()
    {
        game.startQuest();
    }

    private void sendCommand(char c)
    {
        if (!lockCommands)
        {
            messager.send(c);
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

    public void takeInput(String input)
    {
        messager.send(input);
    }

    public void setGUI(AdventureGameView gui)
    {
        this.view = gui;
    }

    public void setView(String views)
    {
        this.roomView = views;
        view.displayCurrentInfo();
    }

    public String getView()
    {
        return roomView;
    }

    public String getItems()
    {
        return game.getPlayer().showInventory();
    }

    public String getAction()
    {
        return action;
    }

    public void setAction(String a)
    {
        action = a;
        view.displayCurrentInfo();
    }

    public Item getItemChoice(Player player)
    {
        return view.getItemChoice(player.getItems());
    }

}
