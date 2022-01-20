package app.javachat.Llamadas.States;

import app.javachat.Llamadas.Call;
import app.javachat.Llamadas.CallState;
import app.javachat.Logger.Log;

public class WaitingCallState implements CallState {
    private Call call;
    private boolean callConnectionSuccess;

    public WaitingCallState(Call call) {
        this.call = call;
    }


    @Override
    public void startCall() {
        Log.show("Im trying to start a call, but im in State Waiting, so i cant do anything");

    }

    @Override
    public void endCall() {
        Log.show("Im trying to end a call, but im in State Waiting, so i cant do anything");

    }

    @Override
    public void callFailed() {

        this.callConnectionSuccess = false;
    }

    @Override
    public void connect() {
        this.callConnectionSuccess = true;

    }

    @Override
    public void changeState() {
        if (callConnectionSuccess)
            call.changeState(new ConnectedCallState(call));
        else
            call.changeState(new DisconnectedCallState(call));
    }
}
