package app.javachat.Models;

import app.javachat.Info;

public class ChatRequest {
    private User sender;
    private int senderPort;

    public ChatRequest(User sender) {
        this.sender = sender;
        this.senderPort = getAvailablePort();

    }

    private int getAvailablePort() {
        //default port 100
        int port = 100;
        for (int i = 700; i < 800; i++) {
            if (Info.isPortFree(i)) {
                port = i;
                break;
            }
        }
        Info.usePort(port);
        return port;
    }


    public User getSender() {
        return sender;
    }

    public int getSenderPort() {
        return senderPort;
    }
}
