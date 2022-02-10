package app.javachat.Models;

import java.io.Serializable;

public class ChatInfo implements Serializable {
    private User user;
    private int otherChatPort, callListenerPort;
    private int localChatPort;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getOtherChatPort() {
        return otherChatPort;
    }

    public void setOtherChatPort(int otherChatPort) {
        this.otherChatPort = otherChatPort;
    }

    public int getCallListenerPort() {
        return callListenerPort;
    }

    public void setCallListenerPort(int callListenerPort) {
        this.callListenerPort = callListenerPort;
    }

    public int getLocalChatPort() {
        return localChatPort;
    }

    public void setLocalChatPort(int localChatPort) {
        this.localChatPort = localChatPort;
    }
    @Override
    public String toString() {
        return "ChatInfo{" +
                "user=" + user +
                ", otherChatPort=" + otherChatPort +
                ", otherCallPort=" + callListenerPort +
                ", localChatPort=" + localChatPort +
                '}';
    }
}
