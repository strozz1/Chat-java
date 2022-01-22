package app.javachat.Calls;

import app.javachat.Calls.States.DisconnectedCallState;
import app.javachat.Models.User;

public class Call {
    private CallState state;
    private User localUser,otherUser;
    public Call(){
        state = new DisconnectedCallState(this);
    }


    public void startCall() {
        state.startCall();
    }

    public void endCall() {
        state.endCall();
    }

    public void callFailed() {
        state.callFailed();

    }

    public void connect() {
        state.connect();
    }


    public void changeState(CallState state) {
        this.state = state;
    }

    public void setLocalUser(User localUser) {
        this.localUser = localUser;
    }

    public void setOtherUser(User otherUser) {
        this.otherUser = otherUser;
    }

    public User getLocalUser() {
        return localUser;
    }

    public User getOtherUser() {
        return otherUser;
    }

    public void waitResponse() {
        state.waitResponse();
    }

    public CallState getState() {
        return state;
    }
}
