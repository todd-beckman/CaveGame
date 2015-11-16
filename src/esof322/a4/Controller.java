package esof322.a4;

public interface Controller
{
    /**
     * Gets a single character. This will likely be one the Command enumerations
     * @return The character to send back
     */
    public char receiveChar();

    /**
     * Opens a dialogue that allows the player to choose between options
     * @param subject The question being asked
     * @param options The options the user has available
     * @return The index mapping to the decision
     */
    public int chooseBetween(String subject, String[] options);
    
    /**
     * Displays how to play the game
     */
    public void showControls();
    
    /**
     * Displays a message regarding the status of the game.
     * @param message The message to display
     */
    public void showStatusMessage(String message);
    
    /**
     * Displays the room's description.
     * @param message The message to display
     */
    public void showRoomDescription(String message);
    
    /**
     * Displays the items in this room.
     * @param contents
     */
    public void showRoomContents(String[] contents);
    
    /**
     * Displays the objects that can be interacted with in this room.
     * @param interactables
     */
    public void showRoomInteractables(String[] interactables);
    
    /**
     * Displays what the player is currently holding.
     * @param contents
     */
    public void showPlayerInventory(String[] contents);
}
