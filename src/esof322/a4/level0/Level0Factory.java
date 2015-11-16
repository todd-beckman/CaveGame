package esof322.a4.level0;

import esof322.a4.Door;
import esof322.a4.Factory;
import esof322.a4.Key;
import esof322.a4.Player;
import esof322.a4.Room;
import esof322.a4.Treasure;
import esof322.a4.level1.Interactable;
import esof322.a4.level1.NPC;

public class Level0Factory implements Factory
{
    private final String NEW_FILE_LOCATION = "level0.dat";
    private final String SAVE_LOCATION = "save0.dat";
    public String getStartMessage()
    {
        return "Welcome to the Adventure Game,\n"
                + "which is inspired by an old game called the Colossal Cave Adventure.\n"
                + "Java implementation Copyright (c) 1999 - 2012 by James M. Bieman\n";
    }
    
    @Override
    public String getNewAdventureLocation()
    {
        return NEW_FILE_LOCATION;
    }
    
    @Override
    public String getSaveLocation()
    {
        return SAVE_LOCATION;
    }
    
    @Override
    public Room createRoom(String description)
    {
        return new Room(description);
    }
    
    @Override
    public Door createDoor(Room from, Room into, Key key)
    {
        return new Door(from, into, key);
    }
    
    @Override
    public Key createKey(String desc)
    {
        return new Key(desc);
    }
    
    @Override
    public Treasure createTreasure(String description)
    {
        return new Treasure(description);
    }

    @Override
    public Player createPlayer()
    {
        return new Player();
    }

    @Override
    public Interactable createInteractable(String description)
    {
        return new NPC(description);
    }
}
