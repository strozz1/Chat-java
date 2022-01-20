package app.javachat.Llamadas;

import app.javachat.Llamadas.States.DisconnectedCallState;

public class Call {
    private CallState state;
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
}
