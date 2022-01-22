package app.javachat.Calls.States;

import app.javachat.Calls.Call;
import app.javachat.Calls.CallState;
import app.javachat.Logger.Log;

public class ConnectedCallState implements CallState {
    private Call call;

    public ConnectedCallState(Call call) {
        this.call = call;
    }

    @Override
    public void startCall() {
        Log.show("Im trying to start a call, but im in State Connected, so i cant do anything");

    }

    @Override
    public void endCall() {

    }

    @Override
    public void callFailed() {
        //TODO implement when user get Fail while connected
        Log.show("TODO");

    }

    @Override
    public void connect() {
        Log.show("Im trying connect, but im in State Connected, so i cant do anything");

    }

    @Override
    public void changeState() {
        call.changeState(new DisconnectedCallState(call));

    }

    @Override
    public void waitResponse() {

    }
}
