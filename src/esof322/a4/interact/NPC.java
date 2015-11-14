package esof322.a4.interact;

public class NPC implements Interactable
{
    private final String phrase;
    public String getPhrase() 
    {
        return phrase;
    }
    public NPC (String phrase)
    {
        this.phrase = phrase;
    }
    
    @Override
    public String interact()
    {
        return phrase;
    }

}
