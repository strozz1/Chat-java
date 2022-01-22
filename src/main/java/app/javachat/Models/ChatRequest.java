package app.javachat.Models;

import app.javachat.Info;

import java.io.Serializable;

public class ChatRequest implements Serializable {
    private final User sender;
    private final int chatPort;
    private final int chatListenerPort;
    private final boolean accept;
    private int indexOfPort;

    public ChatRequest(User sender) {
        this.sender = sender;
        this.chatPort = getAvailablePort();
        this.accept= false;
        this.chatListenerPort= Info.NEW_CHAT_LISTENER_PORT;


    }
    public ChatRequest(User sender,boolean accept) {
        this.sender = sender;
        this.chatPort = getAvailablePort();
        this.accept=accept;
        this.chatListenerPort= Info.NEW_CHAT_LISTENER_PORT;

    }

    private int getAvailablePort() {
        //default port 100
        int port = 100;
        for (int i = 768; i < 800; i++) {
            if (Info.isPortFree(i)) {
                port = i;
                break;
            }
        }
        this.indexOfPort=Info.usePort(port);
        return port;
    }


    public User getSender() {
        return sender;
    }

    public int getChatPort() {
        return chatPort;
    }

    public boolean isAccept() {
        return accept;
    }

    public int getChatListenerPort() {
        return chatListenerPort;
    }

    public int getIndexOfPort() {
        return indexOfPort;
    }

    public void setIndexOfPort(int indexOfPort) {
        this.indexOfPort = indexOfPort;
    }
}
