package esof322.a4.gui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayDeque;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AdventureGameView extends JFrame
{
    private static final long serialVersionUID = 1L;
    
    private AdventureGameModelFacade facade;
    
    private ArrayDeque<Character> buffer = new ArrayDeque<Character>();
    public char getInput()
    {
        synchronized(facade)
        {
            try
            {
                while (buffer.isEmpty())
                {
                    facade.wait();
                }
            }
            catch (InterruptedException e) {}
        }
        return buffer.pollFirst();
    }
    private void queueInput(char c)
    {
        buffer.addLast(c);
    }
    
    private String statusMessage = "";
    public void setStatusMessage(String message)
    {
        this.statusMessage = message;
    }
    
    private String roomDescription = "";
    public void setRoomDescription(String description)
    {
        this.roomDescription = description;
    }
    
    private String[] roomContents = {};
    public void setRoomContents(String[] contents)
    {
        this.roomContents = contents;
    }
    
    private String[] playerInventory = {};
    public void setInventory(String[] inventory)
    {
        this.setInventory(inventory);
    }
    
    private String[] roomInteractables = {};
    public void setInteractables(String[] interactables)
    {
        this.roomInteractables = interactables;
    }
    
    Canvas canvas = new Canvas();
    class Canvas extends JPanel
    {
        private static final long serialVersionUID = 1L;
        public void paintComponent(Graphics g)
        {
            g.fillRect(20, 20, 100, 200);
        }
    }
    
    public AdventureGameView()
    {
        setTitle("Adventure Game");
        setContentPane(canvas);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);

        addKeyListener(new KeyListener()
        {
            public void keyPressed(KeyEvent e)
            {
                System.out.println("Typed " + e.getKeyCode());
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

            public void keyReleased(KeyEvent e){}

            public void keyTyped(KeyEvent e){}
        });
        
        setVisible(true);
    }
    
    public int getChoice(String subject, String[] options)
    {
        // TODO Auto-generated method stub
        return 0;
    }
    
    public static void main(String[] args)
    {
        new AdventureGameView();
        
//        AdventureGameModelFacade facade = new AdventureGameModelFacade();
//        AdventureGame game = new AdventureGame(facade);
//        
//        AdventureGameView view = facade.getView();
//        view.setSize(800, 600); /* was 400, 250 */
//        view.setVisible(true);
//        
//        game.startQuest();
    }
}
