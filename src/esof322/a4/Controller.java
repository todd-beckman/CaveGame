package esof322.a4;

public interface Controller
{
    /**
     * Gets a single character. This will likely be one the Command enumerations
     * @return The character to send back
     */
    public char receiveChar();

    /**
     * Opens a dialogue that allows the player to choose between options through the console
     * @return 
     */
    public int chooseBetween(String[] options);
    
    /**
     * Displays a message, particularly one relating to actions taken
     * @param message The message to display
     */
    public void showActionMessage(String message);
    
    /**
     * Displays a message, particularly one relating to the game state
     * @param message The message to display
     */
    public void showStatusMessage(String message);
}
