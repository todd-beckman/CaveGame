package esof322.a4;

import java.util.ArrayList;

/*
Save data structure: no spaces except in strings. Commands separated by pipes (|). Arguments separated by colons (:).
Neither pipes nor colons are allowed in strings.

action:String (the current action string)
addroom:Wall
addroom:Room:String (the description)

----The following cannot be used until all CaveSites in question are added
addroom:Door:int (into Room's ID):int (from Room's ID):int (Key's ID)
setside:int (direction, use Direction enum):int (destination CaveSite's ID)
putitem:Key:int (destination Room's ID)
putitem:Treasure:int (destination Room's ID):String (description)
putplayer:int (destination Room's ID)

----The following cannot be used until a player's location has been set
giveplayer:Key:int (Key's ID)
giveplayer:Treasure:String (description)

 */

public class State
{
    public Player player;
    public String actionMessage;
    public Item[] items;
    public CaveSite[] rooms;

    public State (Player player, String actionMessage, Item[] items, CaveSite[] rooms)
    {
        this.player = player;
        this.actionMessage = actionMessage;
        this.items = items;
        this.rooms = rooms;
    }
    
    public static State generateAdventureFromSave (String save)
    {
        String actionMessage = "";
        
        ArrayList<CaveSite> rooms = new ArrayList<CaveSite>();
        ArrayList<Key> keys = new ArrayList<Key>();
        ArrayList<Item> items = new ArrayList<Item>();
        
        Player player = new Player();
        
        String[] data = save.split("|");
        for (String line : data)
        {
            String[] lineData = line.split(":");
            switch (lineData[0])
            {
            case "action":
                actionMessage = "";
                break;
            
            case "addroom":
                CaveSite room;
                if (lineData[1].equals("Wall"))
                {
                    room = new Wall();
                }
                else if (lineData[1].equals("Door"))
                {
                    int to = Integer.parseInt(lineData[2]);
                    int from = Integer.parseInt(lineData[3]);
                    Key key = keys.get(Integer.parseInt(lineData[4]));
                    room = new Door(rooms.get(to), rooms.get(from), key);
                }
                else if (lineData[1].equals("Room"))
                {
                    room = new Room(lineData[2]);
                }
                else
                {
                    room = new Wall();
                }
                rooms.add(room);
                break;
                            
            case "setside":
                ((Room)rooms.get(Integer.parseInt(lineData[1])))
                    .setSide(Integer.parseInt(lineData[2]),
                            (Room)rooms.get(Integer.parseInt(lineData[3])));
                break;
            
            case "putitem":
                if (lineData[1].equals("Key"))
                {
                    Key key = new Key("A key labeled " + keys.size(), keys.size()); 
                    keys.add(key);
                    items.add(key);
                    ((Room)rooms.get(Integer.parseInt(lineData[2]))).addItem(key);
                }
                else if (lineData[1].equals("Treasure"))
                {
                    Treasure treasure = new Treasure(lineData[3]);
                    items.add(treasure);
                    ((Room)rooms.get(Integer.parseInt(lineData[2]))).addItem(treasure);
                }
                break;
                
            case "putplayer":
                player.setLoc(((Room)rooms.get(Integer.parseInt(lineData[1]))));
                break;
                
            case "giveplayer":
                if (lineData[1].equals("Key"))
                {
                    Key key = keys.get(Integer.parseInt(lineData[2]));
                    player.getLocation().addItem(key);
                    player.pickUp(key);
                }
                else if (lineData[1].equals("Treasure"))
                {
                    Treasure treasure = new Treasure(lineData[2]);
                    items.add(treasure);
                    player.getLocation().addItem(treasure);
                    player.pickUp(treasure);
                }
                
            
            default:
                throw new RuntimeException("Invalid save structure instruction:" + lineData[0]);
            }
        }

        return new State(player, actionMessage, items.toArray(new Item[0]), rooms.toArray(new CaveSite[0]));
    }
    
    /**
     * Creates a string that contains the state of the game. Save this to file to preserve the state of the game.
     */
    public String toString ()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("action:" + actionMessage);
        ArrayList<Room> rooms = new ArrayList<Room>();
        ArrayList<Door> doors = new ArrayList<Door>();
        ArrayList<Key> keys = new ArrayList<Key>();
        
        for (int i = 0; i < this.rooms.length; i++)
        {
            CaveSite site = this.rooms[i];
            switch (site.getName())
            {
            case "Wall":
                sb.append("|addroom:Wall");
                break;
                
            case "Room":
                Room room = (Room)site;
                sb.append("|addroom:Room:" + room.getDesc());
                rooms.add(room);
                Item[] items = room.getRoomContents();
                for (Item item: items)
                {
                    String name = item.getName();
                    sb.append("putitem:" + name + ":" + rooms.indexOf(room));
                    if (name.equals("Key"))
                    {
                        keys.add((Key)item);
                    }
                }
                break;
                
            case "Door":
                doors.add((Door)site);
                break;
            }
        }
        
        for (Door door: doors)
        {
            int into = rooms.indexOf(door.getDestination());
            int from = rooms.indexOf(door.getOrigin());
            int key = keys.indexOf(door.getKey().getID());
            sb.append("|addroom:" + into + ":" + from + ":" + key);
        }
        
        sb.append("|putplayer:" + rooms.indexOf(player.getLocation()));
        
        for (Item item: player.getItems())
        {
            String name = item.getName();
            sb.append("|giveplayer:" + name);
            if (name.equals("Key"))
            {
                sb.append(":" + ((Key)item).getID());
            }
            else if (name.equals("Treasure"))
            {
                sb.append(":" + item.getDesc());
            }
        }
        
        return sb.toString();
    }
}

