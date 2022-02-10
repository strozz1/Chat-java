package app.javachat.Calls;

import app.javachat.Models.User;
import app.javachat.Utilities.Info;

import java.io.Serializable;

import static app.javachat.Utilities.Info.Call.*;

public class CallRequest implements Serializable {
    private boolean callFree;
    private User user;
    private boolean isResponse,accept;
    private int callPort,callListenerPort;


    public CallRequest(boolean callFree,boolean isResponse,boolean accept) {
        this.accept=accept;
        this.user= Info.localUser;
        this.callFree =callFree;
        this.isResponse= isResponse;
        this.callPort =CALL_PORT;
        this.callListenerPort=CALL_LISTENER_PORT;
    }
    public CallRequest() {
        this.accept=false;
        this.user= Info.localUser;
        this.callFree =false;
        this.isResponse= false;
        this.callPort =CALL_PORT;
        this.callListenerPort=CALL_LISTENER_PORT;
    }

    public boolean isAccept() {
        return accept;
    }

    public boolean isCallFree() {
        return callFree;
    }

    public boolean isResponse() {
        return isResponse;
    }

    public User getUser() {
        return user;
    }

    public int getCallPort() {
        return callPort;
    }

    public int getCallListenerPort() {
        return callListenerPort;
    }
}
