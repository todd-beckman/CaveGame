package esof322.a4;

public interface Factory
{
    public Player createAdventure();    
    
    public String saveAdventure(String filename, State gameState);
    public Player loadAdventure(String filename);
    
    public Room createRoom(String description);
    public Key createKey();
    public Door createDoor(Room from, int direction, Room into, Key key);
    
    
}
