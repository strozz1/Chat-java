package app.javachat.Llamadas;

public interface CallState {

    /**
     * This method is called when we try to establish a connection.
     * After calling this method, state will go from Disconnected to Waiting
     */
    public void startCall();

    /**
     * This method is called when we try to close a connection.
     * After calling this method, state will go from Connected to Disconnected
     */
    public void endCall();

    /**
     * This method is called when we try to establish a connection, but fails.
     * After calling this method, state will go from Waiting to Disconnected
     */
    public void callFailed();

    /**
     * This method is called when the connection was established successfully.
     * After calling this method, state will go from waiting to Connected
     */
    public void connect();

    /**
     * This method is called when Call changes state, caused by another method of this interface.
     */
    public void changeState();
}
