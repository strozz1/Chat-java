package app.javachat.Llamadas.States;

import app.javachat.Llamadas.Call;
import app.javachat.Llamadas.CallState;
import app.javachat.Logger.Log;
import app.javachat.Models.CallRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class WaitingCallState implements CallState {
    private Call call;
    private boolean callAccepted;

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
        changeState();
    }

    @Override
    public void connect() {
    }

    @Override
    public void changeState() {
        if (callAccepted)
            call.changeState(new ConnectedCallState(call));
        else
            call.changeState(new DisconnectedCallState(call));
    }

    @Override
    public void waitResponse() {
        //wait for response of other user
        boolean accepted = getFeedBackofCall();
        if (accepted) {
            callAccepted = true;
            changeState();
        } else{
            callAccepted=false;
        }


    }

    private boolean getFeedBackofCall() {
        CallRequest callRequest = null;
        ServerSocket socketGetFeedBack = null;
        ObjectInputStream inputStream = null;

        try {
            socketGetFeedBack = new ServerSocket(234);
            socketGetFeedBack.setSoTimeout(20*1000);
            Socket acceptSocket = socketGetFeedBack.accept();
            inputStream = (ObjectInputStream) acceptSocket.getInputStream();
            callRequest = (CallRequest) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Log.error(e.getMessage());
        } finally {
            try {
                inputStream.close();
                socketGetFeedBack.close();
            } catch (IOException e) {
                Log.error(e.getMessage());
            }
        }
        if (callRequest != null)
            return callRequest.isAccept();
        return false;
    }
}
