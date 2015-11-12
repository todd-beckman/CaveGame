package esof322.a4;

public interface View
{
    public char receiveChar();

    public int chooseBetween(String[] options);
    
    public void showActionMessage(String message);
    
    public void showStatusMessage(String message);
}
