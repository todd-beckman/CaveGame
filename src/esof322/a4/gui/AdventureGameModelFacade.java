package esof322.a4.gui;

import esof322.a4.Controller;

public class AdventureGameModelFacade implements Controller
{

    // some private fields to reference current location,
    // its description, what I'm carrying, etc.
    //
    // These methods and fields are left as exercises.

    /**
     * The view with which the player will see the game
     */
    private AdventureGameView view;

    public AdventureGameModelFacade(AdventureGameView gui)
    {
        this.view = gui;
    }
    
    @Override
    public char receiveChar()
    {
        return view.receiveChar();
    }

    @Override
    public int chooseBetween(String subject, String[] options)
    {
        return view.getChoice(subject, options);
    }

    @Override
    public void showControls(){ /* lol */}

    @Override
    public void showStatusMessage(String message)
    {
        view.setStatusMessage(message);
    }

    @Override
    public void showRoomDescription(String description)
    {
        view.setRoomDescription(description);
    }

    @Override
    public void showRoomContents(String[] contents)
    {
        view.setRoomContents(contents);
    }

    @Override
    public void showRoomInteractables(String[] interactables)
    {
        view.setInteractables(interactables);
    }

    @Override
    public void showPlayerInventory(String[] inventory)
    {
        view.setInventory(inventory);
    }
}
