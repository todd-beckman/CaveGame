package esof322.a4;

/**
 * A class wrapping the enumerations of directions. Because Java enums are for
 * some reason not integers and are thus completely useless
 * 
 * @author Todd Beckman
 *
 */
public class Direction
{
    public static int reverse(int direction)
    {
        switch (direction)
        {
        case NORTH:
            return SOUTH;
        case EAST:
            return WEST;
        case SOUTH:
            return NORTH;
        case WEST:
            return EAST;
        case UP:
            return DOWN;
        case DOWN:
            return UP;
        }
        return -1;
    }

    /**
     * The enumeration of direction facing north
     */
    public static final int NORTH = 0;

    /**
     * The enumeration of direction facing east
     */
    public static final int EAST = 1;

    /**
     * The enumeration of direction facing south
     */
    public static final int SOUTH = 2;

    /**
     * The enumeration of direction facing west
     */
    public static final int WEST = 3;

    /**
     * The enumeration of direction facing up
     */
    public static final int UP = 4;

    /**
     * The enumeration of direction facing down
     */
    public static final int DOWN = 5;

}
