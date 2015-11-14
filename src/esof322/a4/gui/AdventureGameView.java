package esof322.a4.gui;

import BreezySwing.GBFrame;
import esof322.a4.AdventureGame;
import esof322.a4.Item;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class AdventureGameView extends GBFrame
{
    private static final long serialVersionUID = 1L;

    // Window objects --------------------------------------
    JLabel welcomeLabel = addLabel(
            "Welcome to the Adventure Game " + "(inspired by an old game called the Colossal Cave Adventure)."
                    + " Java implementation Copyright (c) 1999-2012 by James M. Bieman",
            1, 1, 5, 1);

    JLabel viewLabel = addLabel("Your View: ", 2, 1, 1, 1);
    JTextArea viewArea = addTextArea("Start", 3, 1, 3, 7);

    JLabel carryingLabel = addLabel("You are carrying: ", 6, 4, 1, 1);
    JTextArea carryingArea = addTextArea("Nothing", 7, 4, 3, 3);

    JLabel actionLabel = addLabel("Action:", 2, 4, 1, 1);
    JTextArea actionArea = addTextArea("Action", 3, 4, 3, 3);
    JLabel separator1 = addLabel("-----------------------------------------------------------------", 10, 1, 4, 1);

    JLabel choiceLabel = addLabel("Choose a direction, pick-up, or drop an item", 11, 1, 5, 1);

    JButton grabButton = addButton("Grab an item", 12, 5, 1, 1);
    JButton dropButton = addButton("Drop an item", 13, 5, 1, 1);

    JButton northButton = addButton("North", 12, 2, 1, 1);
    JButton southButton = addButton("South", 14, 2, 1, 1);
    JButton eastButton = addButton("East", 13, 3, 1, 1);
    JButton westButton = addButton("West", 13, 1, 1, 1);
    JButton upButton = addButton("Up", 12, 3, 1, 1);
    JButton downButton = addButton("Down", 14, 3, 1, 1);

    JTextArea textInput = addTextArea("1", 15, 3, 1, 1);
    JButton textInputButton = addButton("Submit", 15, 4, 1, 1);

    // Used for item dialogue
    JFrame popup;
    JComboBox<String> itemBox;
    JButton itemSelectButton;
    Item item;

    AdventureGameModelFacade facade;
    
    // Constructor-----------------------------------------------

    public AdventureGameView(AdventureGameModelFacade facade)
    {
        this.facade = facade;
        
        
        setTitle("Adventure Game");

        viewArea.setEditable(false);
        carryingArea.setEditable(false);

        addKeyListener(new KeyListener()
        {
            public void keyPressed(KeyEvent e)
            {
            }

            public void keyReleased(KeyEvent e)
            {
            }

            // Only care about keys typed for now
            public void keyTyped(KeyEvent e)
            {
                switch (e.getKeyCode())
                {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    facade.goNorth();
                    break;

                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    facade.goSouth();
                    break;

                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    facade.goEast();
                    break;

                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    facade.goWest();
                    break;

                case KeyEvent.VK_R:
                    facade.goUp();
                    break;

                case KeyEvent.VK_F:
                    facade.goDown();
                    break;

                case KeyEvent.VK_G:
                    facade.grab();
                    break;

                case KeyEvent.VK_T:
                    facade.drop();
                    break;
                }
            }

        });

        // default:
        // if (buttonObj == textInputButton)
        // {
        // model.takeInput(textInput.getText());
        // textInput.replaceRange("", 0, textInput.getText().length());
        // }
        // break;
        // }
    }

    /**
     * Allows the player to pick between a list of things
     * @param inventory
     * @return
     */
    public int getChoice(String[] options)
    {
        popup.setSize(300, 200);

        itemBox = new JComboBox<String>(options);

        popup = new JFrame("Choose one");

        popup.add(itemBox);
        item = null;

        itemSelectButton = new JButton("This one!");
        itemSelectButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                popup.notifyAll();
            }
        });

        popup.add(itemSelectButton);
        popup.setVisible(true);

        synchronized (popup)
        {
            try
            {
                while (item == null)
                {
                    this.wait();
                }
            }
            catch (InterruptedException e)
            {
            }
        }

        popup.setVisible(false);
        popup = null;
        return itemBox.getSelectedIndex();
    }

    /**
     * Updates the user interface
     */
    public void displayCurrentInfo()
    {
        viewArea.setText(facade.getStatus());
        carryingArea.setText(facade.getItems());
        actionArea.setText(facade.getAction());
    }

    public static void main(String[] args)
    {
        AdventureGameModelFacade facade = new AdventureGameModelFacade();
        AdventureGame game = new AdventureGame(facade);
        
        AdventureGameView view = facade.getView();
        view.setSize(800, 600); /* was 400, 250 */
        view.setVisible(true);
        
        game.startQuest();
    }
}
