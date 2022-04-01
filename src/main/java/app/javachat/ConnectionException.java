package app.javachat;

public class ConnectionException extends Exception{
    public ConnectionException() {
        super("Failed to connect with the server");
    }
}
