package esof322.a4;

public interface Subject
{
    public void addObserver(Observer observer);
    public void notifyObservers();
}
