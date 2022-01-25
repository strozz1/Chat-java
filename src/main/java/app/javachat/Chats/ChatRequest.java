package app.javachat.Chats;

import app.javachat.Utilities.Info;
import app.javachat.Models.User;

import java.io.Serializable;

public class ChatRequest implements Serializable {
    private final User sender;
    private final int chatPort;
    private final int chatListenerPort;
    private final boolean accept;
    private final int callPort;

    //This will be use to see where is my port in my local list.
    private int indexOfChatPort,indexOfCallPort;

    public ChatRequest(User sender) {
        this.sender = sender;
        this.chatPort = getAvailableChatPort();
        this.accept= false;
        this.chatListenerPort= Info.NEW_CHAT_LISTENER_PORT;
        this.callPort = getAvailableCallPort();


    }
    public ChatRequest(User sender,boolean accept) {
        this.sender = sender;
        this.chatPort = getAvailableChatPort();
        this.accept=accept;
        this.chatListenerPort= Info.NEW_CHAT_LISTENER_PORT;
        this.callPort = getAvailableCallPort();


    }

    public int getIndexOfCallPort() {
        return indexOfCallPort;
    }

    public int getCallPort() {
        return callPort;
    }

    public void setIndexOfCallPort(int indexOfCallPort) {
        this.indexOfCallPort = indexOfCallPort;
    }

    /**
     * Searches for a port and returns it, saving his index on indexOfChatPort
     * @return
     */
    private int getAvailableChatPort() {
        //default port 100
        int port = 100;
        for (int i = 768; i < 800; i++) {
            if (Info.isPortFree(i)) {
                port = i;
                break;
            }
        }
        this.indexOfChatPort =Info.usePort(port);
        return port;
    }

    /**
     * Searches for a port and returns it, saving his index on indexOfCallPort
     * @return
     */
    private int getAvailableCallPort() {
        //default port 100
        int port = 100;
        for (int i = 768; i < 800; i++) {
            if (Info.isPortFree(i)) {
                port = i;
                break;
            }
        }
        this.indexOfCallPort =Info.usePort(port);
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

    public int getIndexOfChatPort() {
        return indexOfChatPort;
    }

    public void setIndexOfChatPort(int indexOfChatPort) {
        this.indexOfChatPort = indexOfChatPort;
    }
}
