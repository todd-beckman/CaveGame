package esof322.a4.level1;

import esof322.a4.Door;
import esof322.a4.Factory;
import esof322.a4.Key;
import esof322.a4.Player;
import esof322.a4.Room;
import esof322.a4.Treasure;

public class Level1Factory implements Factory
{
    private final String NEW_FILE_LOCATION = "level1.dat";
    private final String SAVE_LOCATION = "save1.dat";

    @Override
    public String getStartMessage()
    {
        return "Welcome to Level 1! Now that you understand the basics of the game, it's\n"
                + "time to step it up a notch. There is an arbitrarily valueable item\n"
                + "locked away in the shed. Your job is to fetch the key hidden in the\n"
                + "depths of a secret Rocket hideout so you can steal their treasure.\n"
                + "Can you do it?";
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
        if (key instanceof GateKey)
        {
            return new Gate(from, into, (GateKey)key);
        }
        return new Door(from, into, key);
    }
    

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
        switch (description)
        {
        case "true":
            return new Switch(true);
        case "false":
            return new Switch(false);
        default:
            return new NPC(description);
        }
    }
}
