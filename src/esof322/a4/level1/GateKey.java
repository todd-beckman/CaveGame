package esof322.a4.level1;

import esof322.a4.Key;

public class GateKey extends Key
{
    public Switch[] switches;
    public Boolean[] desired;
    public GateKey(Switch[] switches, Boolean[] desired)
    {
        super("Someone set us up the bomb.");
        this.switches = switches;
        this.desired = desired;
    }
    
    @Override
    public String getName()
    {
        return "Teru Sama";
    }
}
