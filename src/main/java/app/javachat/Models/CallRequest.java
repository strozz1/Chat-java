package app.javachat.Models;

import java.io.Serializable;

public class CallRequest implements Serializable {
    private boolean accept;
    private User user;

    public CallRequest(User user) {
        this.user= user;
    }

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }
}
