package app.javachat.Calls;

import app.javachat.Models.User;
import app.javachat.Utilities.Info;

import static app.javachat.Utilities.Info.Call.getAvailableCallPort;

public class CallRequest {
    private User sender;
    private int callListenerPort;
    private boolean userFree, request;
    private int callPort;

    public CallRequest(boolean userFree) {
        this.sender = Info.localUser;
        if (userFree)
            callPort = getAvailableCallPort();
        this.request = false;
        callListenerPort = Info.CALL_LISTENER_PORT;
        this.userFree = userFree;
    }

    public CallRequest() {
        this.sender = Info.localUser;
        this.userFree = true;
        this.request = true;
        callListenerPort = Info.CALL_LISTENER_PORT;
        callPort = getAvailableCallPort();
    }

    public boolean isUserFree() {
        return userFree;
    }

    public boolean isRequest() {
        return request;
    }

    public int getCallPort() {
        return callPort;
    }

    public User getSender() {
        return sender;
    }

    public int getCallListenerPort() {
        return callListenerPort;
    }
}
