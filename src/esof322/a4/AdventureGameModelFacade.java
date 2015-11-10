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
public class AdventureGameModelFacade {

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
    
    /**
     * The input mechanism to interface with the game
     **/
    private InputListener listener;
    
    AdventureGameModelFacade()
    {
        this.listener = new InputListener();
        this.game = new AdventureGame(this, listener);
    }
    
    public void startQuest()
    {
        game.startQuest();
    }

    public void goUp()
    {
        listener.sendInt(Direction.UP);
    }

    public void goDown()
    {
        listener.sendInt(Direction.DOWN);
    }

    public void goNorth()
    {
        listener.sendInt(Direction.NORTH);
    }

    public void goSouth()
    {
        listener.sendInt(Direction.SOUTH);
    }

    public void goEast()
    {
        listener.sendInt(Direction.EAST);
    }

    public void goWest()
    {
        listener.sendInt(Direction.WEST);
    }

    public void grab()
    {
        listener.send("g");
    }
    
    public void drop()
    {
        listener.send("t");
    }
    
    public void takeInput(String input)
    {
        listener.send(input);
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


}
