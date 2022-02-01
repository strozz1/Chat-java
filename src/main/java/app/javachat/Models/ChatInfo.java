package app.javachat.Models;

import app.javachat.Calls.Call;
import app.javachat.Chats.Chat;

import java.io.Serializable;

public class ChatInfo implements Serializable {
    private User user;
    private int otherChatPort,otherCallPort;
    private int localChatPort,localCallPort;

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

    public int getOtherCallPort() {
        return otherCallPort;
    }

    public void setOtherCallPort(int otherCallPort) {
        this.otherCallPort = otherCallPort;
    }

    public int getLocalChatPort() {
        return localChatPort;
    }

    public void setLocalChatPort(int localChatPort) {
        this.localChatPort = localChatPort;
    }

    public int getLocalCallPort() {
        return localCallPort;
    }

    public void setLocalCallPort(int localCallPort) {
        this.localCallPort = localCallPort;
    }

    @Override
    public String toString() {
        return "ChatInfo{" +
                "user=" + user +
                ", otherChatPort=" + otherChatPort +
                ", otherCallPort=" + otherCallPort +
                ", localChatPort=" + localChatPort +
                ", localCallPort=" + localCallPort +
                '}';
    }
}
