package esof322.a4;

/*
 * Todd Beckman
 * Dylan Hills
 * Kalvyn Lu
 * Luke O'Neill
 * Luke Welna
 */
/**
 * Adventure Game Program Code Copyright (c) 1999 James M. Bieman
 *
 * To compile: javac AdventureGame.java To run: java AdventureGame
 *
 * The main routine is AdventureGame.main
 **/

/**
 * Adventure Game Program Code Copyright (c) 1999-2012 James M. Bieman The Adventure game is based on the "Colossal Cave Adventure"
 * originally designed by Will Crowther and implemented by Will Crowther and Don Wood in Fortran in 1975 and 1976.
 *
 * This micro-version is a variant of the original cave system and is implemented in Java with just a few rooms and with a much more
 * limited vocabulary.
 *
 * Updated August 2010, January 2012 - Code is put into package cs314.a2 to match current CS314 coding standards. Updated January
 * 2012 - Renamed as the "Adventure Game"
 *
 * To compile: javac cs314.a2.AdventureGame.java To run: java cs314.a2.AdventureGame
 *
 * The main routine is AdventureGame.main
 **/

/**
 * class Adventure: Primary method, createCave, creates the cave system. It
 * eventually be replaced with a more flexible mechanism to support input and
 * output from devices other than an ASCII terminal.
 *
 * Room descriptions are followed by a room identifier, to ease debugging and
 * testing. These would be removed to help confuse the user, which is our
 * ultimate aim.
 *
 * I haven't added all of the room descriptions. They will be done later.
 *
 * In this version all I/O is through standard I/O; I/O is to and from the
 * console.
 */

public class Adventure
{

    private Room entrance;

    public Room createAdventure()
    {
        // The outside:
        Room outside = new Room("You are standing outside, on the edge of a cliff;\n"
                + " A creek runs alongside the cliff.\n" + "a cave opens straight down (outside).");

        // Room 1:
        Room r1 = new Room("The darkness is pierced by a bright light overhead.\n"
                + "There is a narrow, dark passage to the east (r1).");

        // Connect the outside to Room 1:
        outside.setSide(Direction.DOWN, r1);
        r1.setSide(Direction.UP, outside);
        entrance = outside;

        // Room 2:
        Room r2 = new Room("You are in a gloomy oval shaped room with grey walls.\n"
                + "There is a dim light to the west, and a narrow\n"
                + "dark hole to the east only about 18 inches high (r2).");

        // Room 3:
        Room r3 = new Room("You really need your flashlight here. \n" + "There is a wide passage that quickly narrows\n"
                + "to the west, a bright opening to the east,\n" + "and a deep hole that appears to have no bottom\n"
                + "in the middle of the room (r3).");

        // Connect Rooms 1, 2, & 3:
        r1.setSide(Direction.EAST, r2);
        r2.setSide(Direction.WEST, r1);
        r2.setSide(Direction.EAST, r3);
        r3.setSide(Direction.WEST, r2);

        // Room 4:
        Room r4 = new Room(
                "There is what looks like a giant grizzly bear\n" + "skull in a corner.  A passage leads to the west,\n"
                        + "another one to the north, and a slippery route\n"
                        + "goes down steeply. You can hear the shrieks of bats (r4).");

        // Room 5:
        Room r5 = new Room("There is a dim light from above and the shrieks\n"
                + "are clearly coming from a passageway to the east (r5).");

        // Room 6:
        Room r6 = new Room("The ceiling is full of bats.\n" + "You should put your hat on your head (r6).");

        // Room 7:
        Room r7 = new Room(
                "This room is very damp. There are puddles on the floor\n" + "and a steady dripping from above (r7).");

        // Connect rooms 3, 4, 5, 6, & 7.
        r3.setSide(Direction.EAST, r4);
        r3.setSide(Direction.DOWN, r5);
        r4.setSide(Direction.WEST, r3);
        r4.setSide(Direction.DOWN, r7);
        r5.setSide(Direction.UP, r3);
        r5.setSide(Direction.EAST, r6);
        r6.setSide(Direction.WEST, r5);
        r7.setSide(Direction.UP, r4);

        // Room 8:
        Room r8 = new Room("A lizard scampers past you, or is it a snake?\n"
                + "a narrow passage runs to the east and an evin narrower one\n" + "runs to the west (r8).");

        // Room 9:
        Room r9 = new Room("Room r9.");

        // Room 10:
        Room r10 = new Room("It looks like someone has been here.\n"
                + "There is a pile of candy wrappers on the floor,\n" + "and maybe something else. \n"
                + "Wait, there is a trap door on the floor,\n" + "but it is locked (r10).");

        // Room 11:
        Room r11 = new Room("This room is very dark. You can just barely see (r11).");
        Treasure theTreasure = new Treasure("A bag filled with gold bars.");
        r11.addItem(theTreasure);

        // Lets connect them:
        r4.setSide(Direction.NORTH, r8);
        r8.setSide(Direction.SOUTH, r4);
        r8.setSide(Direction.WEST, r9);
        r8.setSide(Direction.EAST, r10);
        r9.setSide(Direction.EAST, r8);
        r10.setSide(Direction.WEST, r8);

        // Create a key and put it in r6:
        Key theKey = new Key("A shiny gold key.");
        r6.addItem(theKey);

        // We add a door between r10 and r11:
        Door theDoor = new Door(r10, r11, theKey);
        r10.setSide(Direction.DOWN, theDoor);
        r11.setSide(Direction.UP, theDoor);

        // Now return the entrance:
        entrance = outside;
        return entrance;

    }
}
