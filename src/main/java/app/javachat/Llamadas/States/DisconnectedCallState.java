package app.javachat.Llamadas.States;

import app.javachat.Llamadas.Call;
import app.javachat.Llamadas.CallState;
import app.javachat.Logger.Log;

public class DisconnectedCallState implements CallState {
    private Call call;

    public DisconnectedCallState(Call call){
        this.call=call;
    }
    @Override
    public void startCall() {


    }

    @Override
    public void endCall() {
        Log.show("Im trying to end call, but im in State Disconnected, so i cant do anything");
    }

    @Override
    public void callFailed() {

    }

    @Override
    public void connect() {
        Log.show("Im trying to connect, but im in State Disconnected, so i cant do anything");

    }

    @Override
    public void changeState() {
        call.changeState(new WaitingCallState(call));
    }
}
