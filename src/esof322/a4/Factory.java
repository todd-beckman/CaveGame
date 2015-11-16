package esof322.a4;

import esof322.a4.level1.Interactable;

public interface Factory
{
    public String getNewAdventureLocation();
    public String getSaveLocation();
    public String getStartMessage();
    public Player createPlayer();
    public Room createRoom(String description);
    public Door createDoor(Room from, Room into, Key key);
    public Key createKey(String description);
    public Treasure createTreasure(String description);
    public Interactable createInteractable(String description);
}
