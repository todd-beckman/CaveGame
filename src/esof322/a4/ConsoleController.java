package esof322.a4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A game view which uses the console I/O in order to play the Cave Game
 * @author Todd Beckman
 *
 */
public class ConsoleController implements Controller
{
    private BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Gets the first character of the next line of input
     */
    @Override
    public char receiveChar()
    {
        try
        {
            return keyboard.readLine().toLowerCase().charAt(0);
        }
        // Empty String
        catch (StringIndexOutOfBoundsException e1)
        {
            return ' ';
        }
        // Buffered Reader fails
        catch (IOException e)
        {
            return ' ';
        }
    }
    
    @Override
    public int chooseBetween(String subject, String[] options)
    {
        System.out.println(subject);
        for (int i = 0; i < options.length; i++)
        {
            System.out.print(i + ": ");
            System.out.println(options[i]);
        }
        int choice = -1;
        do{
            //  because it's not a Java feature
            switch (receiveChar())
            {
            case '0': choice = 0; break;
            case '1': choice = 1; break;
            case '2': choice = 2; break;
            case '3': choice = 3; break;
            case '4': choice = 4; break;
            case '5': choice = 5; break;
            case '6': choice = 6; break;
            case '7': choice = 7; break;
            case '8': choice = 8; break;
            case '9': choice = 9; break;
            default: choice = -1; break;
            }
        }
        while (choice < 0 && options.length < choice + 1);
        //  For some reason this can still happen?
        if (choice < 0)
        {
            return chooseBetween(subject, options);
        }
        return choice;
    }
    
    @Override
    public void showControls()
    {
        System.out.println("What will you do? Go (n,s,e,w,u,d)? Grab (g)? Toss(t), Save (p)? Quit (q)?");
    }
    
    @Override
    public void showStatusMessage(String message)
    {
        System.out.println(message);
    }

    @Override
    public void showRoomDescription(String message)
    {
        System.out.println(message);
    }

    @Override
    public void showRoomContents(String[] contents)
    {
        System.out.println("Room contains:");
        for (String item: contents)
        {
            System.out.println("  " + item);
        }
    }

    @Override
    public void showRoomInteractables(String[] interactables)
    {
        System.out.println("Interactions:");
        for (String object: interactables)
        {
            System.out.println("  " + object);
        }
    }
    
    @Override
    public void showPlayerInventory(String[] contents)
    {
        System.out.println("You are holding:");
        for (String item: contents)
        {
            System.out.println("  " + item);
        }
    }
}
