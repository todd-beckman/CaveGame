package esof322.a4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
        
        State state = State.generateAdventureFromSave(save);
        return state.player;
    }
    
    public Room createRoom(String description)
    {
        return new Room(description);
    }
   
    public Key createKey()
    {
        return new Key("A shiny gold key labeled " + keyID, keyID++);
    }
    
    public Door createDoor(Room from, int direction, Room into, Key key)
    {
        Door door = new Door(from, into, key);
        
        from.setSide(direction, door);
        into.setSide(Direction.reverse(direction), into);
        
        return door;
    }
    
    
}
