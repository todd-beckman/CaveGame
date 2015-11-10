package esof322.a4;

/*
 * Todd Beckman
 * Dylan Hills
 * Kalvyn Lu
 * Luke O'Neill
 * Luke Welna
 */
/**
 * Todd Beckman:
 * Input Listener
 * Provides a synchronized messaging system that allows for input to be sent and received in parallel
 */
public class InputListener {
    Object listenObject = new Object();
    
    /**
     * The message that is being sent
     */
    private String message;
    
    /**
     * Constructs a listener. Only one should be needed unless parallel input is expected.
     */
    InputListener() {}
    
    /**
     * Receive a string of input.
     * @param listener The object that is to be synchronized in waiting
     * @return The message that is finally received.
     */
    public String receive()
    {
        synchronized(listenObject)
        {
            try
            {
                message = null;
                while (message == null)
                {
                    listenObject.wait();
                }
            }
            catch (InterruptedException e) {}
        }
        return message;
    }
    
    /**
     * Send a message to the listener, if it is listening.
     * @param message The message to send
     */
    public void send(String message)
    {
        //  Alert the listener it is ready
        synchronized(listenObject)
        {
            this.message = message;
            listenObject.notifyAll();
        }
    }
}
