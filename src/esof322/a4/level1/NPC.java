package esof322.a4.level1;

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
    @Override
    public String getName()
    {
        return "NPC";
    }
    @Override
    public String getDesc()
    {
        return phrase;
    }
}
