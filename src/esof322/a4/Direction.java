package esof322.a4;

/**
 * A class wrapping the enumerations of directions.
 * Because Java enums are for some reason not integers and are thus completely useless
	
 * @author Todd Beckman
 *
 */
public class Direction {
	public static int turnRight (int from)
	{
		if (from < 4)
		{
			return (from + 5) % 4;
		}
		else
		{
			return from;
		}
	}

	public static int turnLeft (int from)
	{
		if (from < 4)
		{
			return (from + 3) % 4;
		}
		else
		{
			return from;
		}
	}
	
	/**
	 * The enumeration of direction facing north
	 */
	public static int NORTH = 0;
	
	/**
	 * The enumeration of direction facing east
	 */
	public static int EAST = 1;
	
	/**
	 * The enumeration of direction facing south
	 */
	public static int SOUTH = 2;
	
	/**
	 * The enumeration of direction facing west
	 */
	public static int WEST = 3;
	
	/**
	 * The enumeration of direction facing up
	 */
	public static int UP = 4;

	/**
	 * The enumeration of direction facing down
	 */
	public static int DOWN = 5;
}
