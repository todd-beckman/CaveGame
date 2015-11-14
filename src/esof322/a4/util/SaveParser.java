package esof322.a4.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import esof322.a4.Factory;

public class SaveParser
{
    /**
     * Parses a save data file, sending formatted commands to the Factory as necessary
     * @param factory The Factory to process the commands
     * @param save The name of the save file
     */
    public static void parse(Factory factory, String filename)
    {
        try
        {
            Scanner s = new Scanner(new FileReader(filename));
            while (s.hasNextLine())
            {
                String[] lineData = s.nextLine().split(":");
                factory.readCommand(lineData[0], lineData);
            }
            s.close();
        }
        catch(IOException e)
        {
            System.out.println("Could not read save file at location " + filename + " -- Maybe it does not exist?");
            System.exit(0);
        }
    }
}

