package esof322.a4.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayDeque;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import esof322.a4.AdventureGame;
import esof322.a4.util.Command;

public class AdventureGameView extends JFrame
{
    private static final long serialVersionUID = 1L;
    
    private Object lock = new Object();
    
    private char command = ' ';
    public char receiveChar()
    {
        synchronized (lock)
        {
            try
            {
                lock.wait();
            }
            catch (InterruptedException e) {}
        }
        char output = command;
        command = ' ';
        return output;
    }
    

    class CaveButton extends JButton
    {
        private static final long serialVersionUID = 1L;
        public CaveButton(String name, char instruction)
        {
            super(name);
            this.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent arg0)
                {
                    command = instruction;
                    synchronized (lock)
                    {
                        lock.notify();
                    }
                }
            });
        }
    }
    
    private JTextArea statusBox = new JTextArea("Status");
    private String statusMessage = "";
    public void setStatusMessage(String message)
    {
        this.statusMessage = message;
        update();
    }
    
    private String roomDescription = "";
    public void setRoomDescription(String description)
    {
        this.roomDescription = description;
        update();
    }
    
    private JTextArea contentsBox = new JTextArea("Contents");
    private String[] roomContents = {};
    public void setRoomContents(String[] contents)
    {
        this.roomContents = contents;
        update();
    }
    
    private String[] roomInteractables = {};
    public void setInteractables(String[] interactables)
    {
        this.roomInteractables = interactables;
        update();
    }
    
    private JTextArea inventoryBox = new JTextArea("Inventory");
    private String[] playerInventory = {};
    public void setInventory(String[] inventory)
    {
        this.playerInventory = inventory;
        update();
    }
    
    public AdventureGameView()
    {
        setLayout(new GridLayout(2, 1));
        setTitle("Adventure Game");
        
        JPanel panel = new JPanel(new GridLayout(5, 3));
        
        add(statusBox);
        statusBox.setEditable(false);
        
        //  First row: Textboxes: Save, Room Contents, Player Inventory
        panel.add(new CaveButton("Save", Command.SAVE));

        panel.add(contentsBox);
        contentsBox.setEditable(false);
        
        panel.add(inventoryBox);
        inventoryBox.setEditable(false);
        
        //  Second Row: Grab North Up
        panel.add(new CaveButton("Grab", Command.GRAB));
        panel.add(new CaveButton("North", Command.NORTH));
        panel.add(new CaveButton("Up", Command.UP));

        //  Third Row: West Interact East
        panel.add(new CaveButton("West", Command.WEST));
        panel.add(new CaveButton("Interact", Command.INTERACT));
        panel.add(new CaveButton("East", Command.EAST));
        
        //  Fourth Row: Toss South Down
        panel.add(new CaveButton("Toss", Command.DROP));
        panel.add(new CaveButton("South", Command.SOUTH));
        panel.add(new CaveButton("Down", Command.DOWN));
        
        addKeyListener(new KeyListener()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                switch (e.getKeyCode())
                {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    command = Command.NORTH;
                    lock.notify();
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    command = Command.EAST;
                    lock.notify();
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    command = Command.SOUTH;
                    lock.notify();
                    break;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    command = Command.WEST;
                    lock.notify();
                    break;
                case KeyEvent.VK_G:
                case KeyEvent.VK_Q:
                    command = Command.GRAB;
                    lock.notify();
                    break;
                case KeyEvent.VK_Z:
                    command = Command.DROP;
                    lock.notify();
                    break;
                case KeyEvent.VK_F:
                    command = Command.QUIT;
                    lock.notify();
                    break;
                case KeyEvent.VK_V:
                    command = Command.SAVE;
                    lock.notify();
                    break;
                case KeyEvent.VK_E:
                    command = Command.UP;
                    lock.notify();
                    break;
                case KeyEvent.VK_C:
                    command = Command.DOWN;
                    lock.notify();
                    break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}

            @Override
            public void keyTyped(KeyEvent e) {}
        });

        add(panel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);
    }
    
    public void update()
    {
        statusBox.setText(roomDescription + "\n" + statusMessage);
        
        StringBuilder sb = new StringBuilder();
        sb.append("Room Contains: ");
        for (String str: roomContents)
        {
            sb.append(str.split(":")[0]);
            sb.append("   ");
        }
        sb.append("\nInteractables: ");
        for (String str: roomInteractables)
        {
            sb.append(str.split(":")[0]);
            sb.append("  ");
        }
        contentsBox.setText(sb.toString());
        
        sb = new StringBuilder();
        sb.append("You are holding:\n  ");
        for (String str: playerInventory)
        {
            sb.append(str.split(":")[0]);
            sb.append("\n  ");
        }
        inventoryBox.setText(sb.toString());
    }
    
    public int getChoice(String subject, String[] options)
    {
        //  Set up the display
        JFrame popup = new JFrame(subject);
        JPanel panel = new JPanel(new GridLayout(1, 2));
        
        //  Make the list to pick from
        JComboBox<String> dropdown = new JComboBox<String>(options);
        panel.add(dropdown);
        
        //  Make the submit button
        JButton button = new JButton("This one!");
        button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                synchronized (lock)
                {
                    lock.notify();
                }
            }
        });
        panel.add(button);
        
        //  Add everything to the popup
        popup.setContentPane(panel);
        
        //  Prepare to display
        popup.setSize(450, 100);
        popup.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        //  Display
        popup.setVisible(true);
        
        synchronized (lock)
        {
            try
            {
                lock.wait();
            }
            catch (InterruptedException e) {}
        }
        
        //  Remove the display
        popup.setVisible(false);
        
        return dropdown.getSelectedIndex();
    }
    
    public static void main(String[] args)
    {
        //  Create the View
        AdventureGameView view = new AdventureGameView();
        
        //  Create the Controller
        AdventureGameModelFacade facade = new AdventureGameModelFacade(view);
        
        //  Create the model
        AdventureGame game = new AdventureGame(facade);
        
        //  Start the game
        game.startQuest();
    }
}
