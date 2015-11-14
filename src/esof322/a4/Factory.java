package esof322.a4;

public interface Factory
{
    public String getNewAdventureLocation();
    public String getSaveLocation();
    
    public Player createAdventure();    

    public Player loadAdventure(String filename);
    public String saveAdventure();
    
    public Room createRoom(String description);
    public Door createDoor(Room from, Room into, Key key);

    public Key createKey(String description);
    public Treasure createTreasure(String description);
    
    public void readCommand(String command, String ... args);
}
