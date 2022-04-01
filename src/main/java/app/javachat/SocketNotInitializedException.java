package app.javachat;

public class SocketNotInitializedException extends Exception {
    public SocketNotInitializedException() {
        super("Socket is null. Try connecting to the server first");
    }
}
