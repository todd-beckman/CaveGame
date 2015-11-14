package esof322.a4.level1;

import esof322.a4.Door;
import esof322.a4.Factory;
import esof322.a4.Key;
import esof322.a4.Player;
import esof322.a4.Room;
import esof322.a4.Treasure;

public class Level1Factory implements Factory
{

    @Override
    public Player createAdventure()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String saveAdventure()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Player loadAdventure(String filename)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Room createRoom(String description)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Door createDoor(Room from, Room into, Key key)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Key createKey(String description)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Treasure createTreasure(String description)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getNewAdventureLocation()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getSaveLocation()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void readCommand(String command, String... args)
    {
        // TODO Auto-generated method stub
        
    }

}
