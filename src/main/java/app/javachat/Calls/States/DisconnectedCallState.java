package app.javachat.Calls.States;

import app.javachat.Calls.Call;
import app.javachat.Calls.CallState;
import app.javachat.Logger.Log;
import app.javachat.Calls.CallRequest;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class DisconnectedCallState implements CallState {
    private Call call;
    private boolean isResponding;

    public DisconnectedCallState(Call call) {
        this.call = call;
    }

    @Override
    public void startCall() {
        sendCallToOtherUser();
        if (isResponding) {
            //Al terminar, cambiamos el estado.
            changeState();
        } else{
            Log.error("Error en el startCall(), no responde el server");
        }


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

    @Override
    public void waitResponse() {

    }


    private void sendCallToOtherUser() {
        Socket socketEnviarSolicitud = null;
        ObjectOutputStream outputStream = null;
        String ipOtherUser = call.getOtherUser().getIP();
        try{
            socketEnviarSolicitud = new Socket(ipOtherUser, 234);
            outputStream = (ObjectOutputStream) socketEnviarSolicitud.getOutputStream();

            CallRequest callRequest = new CallRequest(call.getLocalUser());
            outputStream.writeObject(callRequest);

            outputStream.close();
            socketEnviarSolicitud.close();
            isResponding=true;
        } catch (IOException e) {
            Log.error(e.getMessage());
            isResponding=false;
        }


    }
}

