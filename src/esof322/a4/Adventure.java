package esof322.a4;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import esof322.a4.level1.Gate;
import esof322.a4.level1.GateKey;
import esof322.a4.level1.Switch;
import esof322.a4.level1.NPC;

public class Adventure
{
    /**
     * Wraps Integer.parseInt to make things more readable
     * @param name String to parse
     * @return the integer representation
     */
    private static int integer(String name)
    {
        return Integer.parseInt(name);
    }
    

    private Player player;
    private Factory factory;
    private Treasure treasure;
    private ArrayList<Room> rooms = new ArrayList<Room>();
    private ArrayList<Door> doors = new ArrayList<Door>();
    private ArrayList<Key> keys = new ArrayList<Key>();
    private ArrayList<NPC> npcs = new ArrayList<NPC>();
    private ArrayList<Switch> switches = new ArrayList<Switch>();
    private ArrayList<Gate> gates = new ArrayList<Gate>();
    
    public Adventure(Factory factory)
    {
        this.factory = factory;
        player = factory.createPlayer();
    }
    
    public Player newAdventure()
    {
        return createAdventure(factory.getNewAdventureLocation());
    }
    
    public Player loadAdventure()
    {
        return createAdventure(factory.getSaveLocation());
    }
    
    public String saveAdventure()
    {
        return "The save feature of this game does not function quite yet. Sorry!";
//        try
//        {
//            FileWriter writer = new FileWriter(factory.getSaveLocation());
//            writer.write(adventureToString());
//            writer.close();
//            return "Saved game to " + factory.getSaveLocation();
//        }
//        catch (IOException e)
//        {
//            return "Could not save game to " + factory.getSaveLocation()
//                + " -- Maybe it is open elsewhere?";
//        }
    }

    /**
     * Creates an adventure from file given a factory.
     * @param filename
     * @param factory
     * @return
     */
    public Player createAdventure(String filename)
    {
        ArrayList<String[]> commands = new ArrayList<String[]>();
        try
        {
            Scanner s = new Scanner(new FileReader(filename));
            while (s.hasNextLine())
            {
                commands.add(s.nextLine().split(":"));
            }
            s.close();
        }
        catch(IOException e)
        {
            System.out.println("Could not read save file " + filename + " -- Maybe it does not exist?");
            System.exit(0);
        }
        
        for (String[] args: commands)
        {try{
            //  Why Java is a terrible language:
            switch (args[0])
            {
            //  addroom:DESCRIPTION
            case "addroom":
                rooms.add(factory.createRoom(args[1]));
                break;
            //  addkey:DESCRIPTION
            case "addkey":
                keys.add(factory.createKey(args[1]));
                break;
            //  addtreasure:DESCRIPTION
            case "addtreasure":
                treasure = factory.createTreasure(args[1]);
                break;
            //  adddoor:ROOM 1:ROOM 2:KEY ID
            case "adddoor":
                doors.add(factory.createDoor(rooms.get(integer(args[1])),
                        rooms.get(integer(args[2])),
                        keys.get(integer(args[3]))));
                break;
            //  addnpc:ROOM ID:Message
            case "addnpc":
                NPC person = (NPC)factory.createInteractable(args[2]);
                npcs.add(person);
                rooms.get(integer(args[1])).addInteractable(person);
                break;
            //  addswitch:DEFAULT VALUE:ROOM ID
            case "addswitch":
                Switch s = (Switch)factory.createInteractable(
                        (integer(args[1]) == 1) ? "true" : "false");
                switches.add(s);
                rooms.get(integer(args[2])).addInteractable(s);
                break;
            //  addgate:ROOM 1:ROOM 2:(SWITCH:DESIRED)+
            case "addgate":
                ArrayList<Switch> watchedSwitches = new ArrayList<Switch>();
                ArrayList<Boolean> desired = new ArrayList<Boolean>();
                for (int i = 3; i < args.length; i+= 2)
                {
                    watchedSwitches.add(switches.get(integer(args[i])));
                    desired.add(integer(args[i + 1]) == 1);
                }
                GateKey gateKey = new GateKey(
                        watchedSwitches.toArray(new Switch[0]),
                        desired.toArray(new Boolean[0]));
                Gate gate = (Gate)factory.createDoor(
                        rooms.get(integer(args[1])),
                        rooms.get(integer(args[2])),
                        gateKey);
                gates.add(gate);
                break;
            //  setside:(GATE:|DOOR:)CAVESITE 1:CAVESITE 2
            case "setside":
                if (args[1].equals("gate"))
                {
                    rooms.get(integer(args[3]))
                        .setSide(integer(args[4]),
                            gates.get(integer(args[2])));
                }
                else if (args[1].equals("door"))
                {
                    rooms.get(integer(args[3]))
                        .setSide(integer(args[4]),
                            doors.get(integer(args[2])));
                }
                else
                {
                    rooms.get(integer(args[1]))
                        .setSide(integer(args[2]),
                            rooms.get(integer(args[3])));
                }
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
                throw new RuntimeException("Invalid save structure instruction:" + args[0]);
            }
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println(Arrays.toString(args));
            System.exit(1);
            return null;
        }}
        return player;
    }
    
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
                    sb.append("setside:door:");
                    sb.append(doors.indexOf(sides[s]));
                    sb.append(":");
                    sb.append(r);
                    sb.append(":");
                    sb.append(s);
                    sb.append("\n");
                }
                else if (sides[s] instanceof Gate)
                {
                    sb.append("setside:gate:");
                    sb.append(gates.indexOf(sides[s]));
                    sb.append(":");
                    sb.append(r);
                    sb.append(":");
                    sb.append(s);
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
