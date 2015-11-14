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
    public int chooseBetween(String[] options)
    {
        System.out.println("Please choose between one of:");
        for (int i = 0; i < options.length; i++)
        {
            System.out.print(i + ": ");
            System.out.println(options[i]);
        }
        int choice = -1;
        do{
            System.out.println("What is your selection?");
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
            return chooseBetween(options);
        }
        return choice;
    }

    @Override
    public void showActionMessage(String message)
    {
        System.out.println(message);
    }

    @Override
    public void showStatusMessage(String message)
    {
        System.out.println(message);
    }
}
