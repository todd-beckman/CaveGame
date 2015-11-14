package esof322.a4.level0;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import esof322.a4.CaveSite;
import esof322.a4.Door;
import esof322.a4.Factory;
import esof322.a4.Item;
import esof322.a4.Key;
import esof322.a4.Player;
import esof322.a4.Room;
import esof322.a4.Treasure;
import esof322.a4.util.SaveParser;

public class Level0Factory implements Factory
{
    private final String NEW_FILE_LOCATION = "level0.dat";
    private final String SAVE_LOCATION = "save0.dat";
    public Player player;
    public ArrayList<Key> keys;
    public ArrayList<Room> rooms;
    public ArrayList<Door> doors;
    public Treasure treasure;
    
    
    public Level0Factory ()
    {
        keys = new ArrayList<Key>();
        rooms = new ArrayList<Room>();
        doors = new ArrayList<Door>();
        player = new Player();
        treasure = createTreasure("A bag filled with shiny gold bars");
    }
    
    @Override
    public Player createAdventure()
    {
        return loadAdventure(NEW_FILE_LOCATION);
    }
    
    @Override
    public String saveAdventure()
    {
        try
        {
            FileWriter writer = new FileWriter(SAVE_LOCATION);
            writer.write(adventureToString());
            writer.close();
            return "Saved game to " + SAVE_LOCATION;
        }
        catch (IOException e)
        {
            return "Could not save game to " + SAVE_LOCATION + " -- Maybe it is open elsewhere?";
        }
    }
    
    @Override
    public Player loadAdventure(String filename)
    {
        SaveParser.parse(this, filename);
        return player;
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
        
    public Room createRoom(String description)
    {
        Room newRoom = new Room(description);
        rooms.add(newRoom);
        return newRoom;
    }
    
    public Door createDoor(Room from, Room into, Key key)
    {
        Door door = new Door(from, into, key);
        doors.add(door);
        return door;
    }
    

    public Key createKey(String desc)
    {
        Key key = new Key(desc);
        keys.add(key);
        return key;
    }
    
    @Override
    public Treasure createTreasure(String description)
    {
        treasure = new Treasure(description);
        return treasure;
    }
    

    /**
     * Wraps Integer.parseInt to make things more readable
     * @param name String to parse
     * @return the integer representation
     */
    private int integer(String name)
    {
        return Integer.parseInt(name);
    }
    
    @Override
    public void readCommand(String command, String... args)
    {try{
        //  Why Java is a terrible language:
        
        
        switch (command)
        {
        //  addroom:DESCRIPTION
        case "addroom":
            createRoom(args[1]);
            break;
        //  addkey:DESCRIPTION
        case "addkey":
            keys.add(createKey(args[1]));
            break;
        //  adddoor:ROOM 1:ROOM 2:KEY ID
        case "adddoor":
            createDoor(rooms.get(integer(args[1])),
                    rooms.get(integer(args[2])),
                    keys.get(integer(args[3])));
            break;
        //  setside:CAVESITE 1:CAVESITE 2
        case "setside":
            int toIndex = integer(args[3]);
            CaveSite toSite;
            if (toIndex < rooms.size())
            {
                toSite = rooms.get(toIndex);
            }
            else
            {
                toSite = doors.get(toIndex - rooms.size());
            }
            rooms.get(integer(args[1])).setSide(
                    integer(args[2]),
                    toSite);
            break;
        //  putkey:KEY ID:ROOM ID
        case "putkey":
            rooms.get(integer(args[2])).addItem(keys.get(integer(args[1])));
            break;
        //  puttreasure:ROOM ID
        case "puttreasure":
            rooms.get(integer(args[1])).addItem(treasure);
            break;
        //  putplayer:ROOM ID
        case "putplayer":
            player.setLocation(rooms.get(integer(args[1])));
            break;
        //  givekey:KEY ID (requires player location is set)
        case "givekey":
            if (player.getLocation() == null)
            {
                throw new RuntimeException("Cannot give player items if it is not in the cave");
            }
            Key key = keys.get(integer(args[1]));
            player.getLocation().addItem(key);
            player.pickUp(key);
            break;
        //  givetreasure (requires player location is set)
        case "givetreasure":
            if (player.getLocation() == null)
            {
                throw new RuntimeException("Cannot give player items if it is not in the cave");
            }
            player.getLocation().addItem(treasure);
            player.pickUp(treasure);
            break;
        default:
            throw new RuntimeException("Invalid save structure instruction:" + command);
        }
    } catch(Exception e) {
        e.printStackTrace();
        System.out.println(Arrays.toString(args));
    }}
    
    private String adventureToString()
    {
        StringBuilder sb = new StringBuilder();
        
        for (Room room: rooms)
        {
            sb.append("addroom:");
            sb.append(room.getDesc().replaceAll("\n", "~"));
            sb.append("\n");
        }
        
        for (Key key: keys)
        {
            sb.append("addkey:");
            sb.append(key.getDesc());
            sb.append("\n");
        }
        
        for (int r = 0; r < rooms.size(); r++)
        {
            for (Item item: rooms.get(r).getRoomContents())
            {
                if (item instanceof Key)
                {
                    sb.append("putkey:");
                    sb.append(keys.indexOf((Key)item));
                    sb.append(":");
                    sb.append(r);
                    sb.append("\n");
                }
                else if (item instanceof Treasure)
                {
                    sb.append("puttreasure:");
                    sb.append(r);
                    sb.append("\n");
                }
            }
        }
        
        for (Door door: doors)
        {
            sb.append("adddoor:");
            sb.append(rooms.indexOf((Room)door.getOrigin()));
            sb.append(":");
            sb.append(rooms.indexOf((Room)door.getDestination()));
            sb.append(":");
            sb.append(keys.indexOf(door.getKey()));
            sb.append("\n");
        }

        
        for (int r = 0; r < rooms.size(); r++)
        {
            CaveSite[] sides = rooms.get(r).getSides();
            for (int s = 0; s < sides.length; s++)
            {
                if (sides[s] instanceof Room)
                {
                    sb.append("setside:");
                    sb.append(r);
                    sb.append(":");
                    sb.append(s);
                    sb.append(":");
                    sb.append(rooms.indexOf((Room)sides[s]));
                    sb.append("\n");
                }
                else if (sides[s] instanceof Door)
                {
                    sb.append("setside:");
                    sb.append(r);
                    sb.append(":");
                    sb.append(s);
                    sb.append(":");
                    sb.append(rooms.size() + doors.indexOf(sides[s]));
                    sb.append("\n");
                }
            }
        }
        
        sb.append("putplayer:");
        sb.append(rooms.indexOf(player.getLocation()));
        sb.append("\n");
        
        for (Item item: player.getItems())
        {
            if (item instanceof Key)
            {
                sb.append("givekey:");
                sb.append(keys.indexOf((Key)item));
                sb.append("\n");
            }
            else if (item instanceof Treasure)
            {
                sb.append("givetreasure\n");
            }
        }
        

        
        return sb.toString();
    }
}
