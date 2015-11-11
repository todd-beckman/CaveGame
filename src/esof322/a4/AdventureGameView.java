package esof322.a4;

import BreezySwing.GBFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/*
 * Todd Beckman
 * Dylan Hills
 * Kalvyn Lu
 * Luke O'Neill
 * Luke Welna
 */
/*
 * Todd Beckman: Added startQuest method to invoke the model's startQuest
 * Kalvyn Lu: Added a TextArea for String input. Hooked up grab and drop buttons to the model.
 * Dylan Hills: Added an action TextArea to display what the player just did.
 */
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

    AdventureGameModelFacade model;

    // Constructor-----------------------------------------------

    public AdventureGameView()
    {
        setTitle("Adventure Game");
        model = new AdventureGameModelFacade();

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
                    model.goNorth();
                    break;

                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    model.goSouth();
                    break;

                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    model.goEast();
                    break;

                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    model.goWest();
                    break;

                case KeyEvent.VK_R:
                    model.goUp();
                    break;

                case KeyEvent.VK_F:
                    model.goDown();
                    break;

                case KeyEvent.VK_G:
                    model.grab();
                    break;

                case KeyEvent.VK_T:
                    model.drop();
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

    public Item getItemChoice(Item[] inventory)
    {
        popup.setSize(300, 200);

        String[] names = new String[inventory.length];
        for (int i = 0; i < inventory.length; i++)
        {
            names[i] = inventory[i].getName() + ": " + inventory[i].getDesc();
        }
        itemBox = new JComboBox<String>(names);

        popup = new JFrame("Choose an item");

        popup.add(itemBox);
        item = null;

        itemSelectButton = new JButton("This one!");
        itemSelectButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                int index = itemBox.getSelectedIndex();
                item = inventory[index];
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
        return item;
    }

    public void displayCurrentInfo()
    {
        viewArea.setText(model.getView());
        carryingArea.setText(model.getItems());
        actionArea.setText(model.getAction());
    }

    public void startQuest()
    {
        model.setGUI(this);
        model.startQuest();
    }

    public static void main(String[] args)
    {
        AdventureGameView view = new AdventureGameView();
        view.setSize(800, 600); /* was 400, 250 */
        view.setVisible(true);
        view.startQuest();
    }
}
