package esof322.a4;

import BreezySwing.GBFrame;

import javax.swing.JButton;
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

    /**
     *
     */
    private static final long serialVersionUID = 1L;

// Window objects --------------------------------------
    JLabel welcomeLabel =
        addLabel("Welcome to the Adventure Game " +
                 "(inspired by an old game called the Colossal Cave Adventure)." +
                 " Java implementation Copyright (c) 1999-2012 by James M. Bieman",
                 1, 1, 5, 1);

    JLabel viewLabel = addLabel("Your View: ", 2, 1, 1, 1);
    JTextArea viewArea = addTextArea("Start", 3, 1, 3, 7);

    JLabel carryingLabel = addLabel("You are carrying: ", 6, 4, 1, 1);
    JTextArea carryingArea = addTextArea("Nothing", 7, 4, 3, 3);

    JLabel actionLabel = addLabel("Action:",2,4,1,1);
    JTextArea actionArea = addTextArea("Action",3,4,3,3);
    JLabel separator1 = addLabel
        ("-----------------------------------------------------------------"
         , 10, 1, 4, 1);

    JLabel choiceLabel = addLabel
        ("Choose a direction, pick-up, or drop an item", 11, 1, 5, 1);

    JButton grabButton = addButton("Grab an item", 12, 5, 1, 1);
    JButton dropButton = addButton("Drop an item", 13, 5, 1, 1);

    JButton northButton = addButton("North", 12, 2, 1, 1);
    JButton southButton = addButton("South", 14, 2, 1, 1);
    JButton eastButton = addButton("East", 13, 3, 1, 1);
    JButton westButton = addButton("West", 13, 1, 1, 1);
    JButton upButton = addButton("Up", 12, 3, 1, 1);
    JButton downButton = addButton("Down", 14, 3, 1, 1);
    
    JTextArea textInput = addTextArea("1",15,3,1,1);
    JButton textInputButton = addButton("Submit",15,4,1,1);

    AdventureGameModelFacade model;

    // Constructor-----------------------------------------------

    public AdventureGameView()
    {
        setTitle("Adventure Game");
        model = new AdventureGameModelFacade();

        viewArea.setEditable(false);
        carryingArea.setEditable(false);
    }

    // buttonClicked method--------------------------------------

    public void buttonClicked (JButton buttonObj)
    {
    	//	Because whoever made those if/else blocks should be fired
    	switch (buttonObj.getName()){
    	case "Up":
        	model.goUp();
        	break;
    	case "Down":
    		model.goDown();
    		break;
    	case "North":
    		model.goNorth();
    		break;
    	case "East":
    		model.goEast();
    		break;
    	case "South":
    		model.goSouth();
    		break;
    	case "West":
    		model.goSouth();
    		break;
    	case "Grab an item":
    		model.grab();
    		break;
    	case "Drop an item":
    		model.drop();
    		break;
		default:
	        if (buttonObj == textInputButton)
	        {
	            model.takeInput(textInput.getText());
	            textInput.replaceRange("", 0, textInput.getText().length());
	        }
	        break;
    	}
    }

    // Private methods-------------------------------------------

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
