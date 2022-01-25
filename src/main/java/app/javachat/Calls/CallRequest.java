package app.javachat.Calls;

import app.javachat.Models.User;

import java.io.Serializable;

public class CallRequest implements Serializable {
    private boolean accept;
    private User user;
    private boolean isResponse;


    public CallRequest(User user) {
        this.user= user;
    }

    public CallRequest(User user,boolean accept,boolean isResponse) {
        this.user= user;
        this.accept=accept;
        this.isResponse= isResponse;
    }

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    public boolean isResponse() {
        return isResponse;
    }
}
