package esof322.a4.interact;

public interface Subject
{
    public void addObserver(Observer observer);
    public void notifyObservers();
}
