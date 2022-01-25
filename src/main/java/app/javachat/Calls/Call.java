package app.javachat.Calls;

import app.javachat.Calls.States.DisconnectedCallState;
import app.javachat.Models.User;
import app.javachat.Utilities.Info;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Call {
    private final int otherPort;
    private int localPort;
    private CallState state;
    private User localUser, otherUser;
    private ServerSocket serverListener;
    private OutcomeSoundCall outcomeSoundCall;
    private IncomeSoundCall incomeSoundCall;

    public Call(int localPort, int otherPort) {
        this.localPort = localPort;
        this.otherPort = otherPort;
        state = new DisconnectedCallState(this);
        try {
            serverListener = new ServerSocket(localPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CallRequest listenForIncomingCalls() {
        CallRequest callRequest = null;
        try {
            //Recivimos el call request del otro usuario
            Socket socketAccept = serverListener.accept();
            ObjectInputStream inputStream = new ObjectInputStream(socketAccept.getInputStream());
            callRequest = (CallRequest) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return callRequest;
    }


    /**
     * Initialize call. Only call if state is disconnected.
     */
    public void startCall() {

        try {
            incomeSoundCall = new IncomeSoundCall(localPort);
            outcomeSoundCall = new OutcomeSoundCall(otherUser,otherPort);

            incomeSoundCall.start();
            //Wait in case lag for listening
            Thread.sleep(500);
            outcomeSoundCall.start();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stop call. Only call if state is connected.
     */
    public void endCall() {
        incomeSoundCall.stopCall();
        outcomeSoundCall.stopCall();
    }

    /**
     * Cancel call. Only call if state is waiting.
     */
    public void callFailed() {


    }

    /**
     * Connect  call. Only call if state is waiting.
     */
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

    /**
     * tell the other user we accepted his call. We send him a CallRequest with accept true
     */
    public void acceptCall() {
        Socket socket = null;
        ObjectOutputStream outputStream = null;
        try {
            CallRequest callRequest = new CallRequest(Info.localUser, true);

            socket = new Socket(otherUser.getIP(), otherPort);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(callRequest);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null)
                    outputStream.close();
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
