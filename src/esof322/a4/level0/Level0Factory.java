package esof322.a4.level0;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import esof322.a4.Door;
import esof322.a4.Factory;
import esof322.a4.Key;
import esof322.a4.Player;
import esof322.a4.Room;
import esof322.a4.State;
import esof322.a4.Treasure;

public class Level0Factory implements Factory
{
    private int keyID = 0;
    
    public Player createAdventure()
    {
        return loadAdventure("level0.dat");
    }
    
    public String saveAdventure(String filename, State gameState)
    {
        try
        {
            FileWriter writer = new FileWriter(filename);
            writer.write(gameState.toString());
            writer.close();
            return "Saved game to " + filename;
        }
        catch (IOException e)
        {
            return "Could not save game to " + filename + ". Maybe it is open elsewhere?";
        }
    }
    
    public Player loadAdventure(String filename)
    {
        String save = "";
        try
        {
            BufferedReader fr = new BufferedReader(new FileReader(filename));
            save = fr.readLine();
            fr.close();
        }
        catch(IOException e)
        {
            System.exit(0);
            return null;
        }
        
        State state = new State(this, save);
        return state.player;
    }
    
    public Room createRoom(String description)
    {
        return new Room(description);
    }
    
    public Door createDoor(Room from, Room into, Key key)
    {
        Door door = new Door(from, into, key);
                
        return door;
    }
    

    public Key createKey()
    {
        return new Key("A shiny gold key labeled " + keyID, keyID++);
    }
    
    public Treasure createTreasure(String description)
    {
        return new Treasure(description);
    }
}
