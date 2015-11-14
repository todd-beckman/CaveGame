package esof322.a4;

public interface Factory
{
    public Player createAdventure();    
    
    public String saveAdventure(String filename, State gameState);
    public Player loadAdventure(String filename);
    
    public Room createRoom(String description);
    public Door createDoor(Room from, Room into, Key key);

    public Key createKey();
    public Treasure createTreasure(String description);
    
    
}
