package app.javachat.Calls;

import app.javachat.Models.User;

import java.io.Serializable;

public class CallRequest implements Serializable {
    private boolean accept;
    private User user;


    public CallRequest(User user) {
        this.user= user;
    }
    public CallRequest(User user,boolean accept) {
        this.user= user;
        this.accept=accept;
    }

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }
}
