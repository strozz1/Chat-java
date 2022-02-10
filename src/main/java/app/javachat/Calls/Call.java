package app.javachat.Calls;

import app.javachat.Controllers.ViewControllers.CallWindowController;
import app.javachat.Models.User;
import app.javachat.Utilities.Info;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

public class Call implements Serializable {
    private final int otherPort;
    private int localPort;
    private User localUser, otherUser;
    private ServerSocket serverListener;
    private OutcomeSoundCall outcomeSoundCall;
    private IncomeSoundCall incomeSoundCall;
    private CallWindowController controller;

    public Call(int localPort, User otherUser, int otherPort) {
        this.localPort = localPort;
        this.otherUser = otherUser;
        this.otherPort = otherPort;

        incomeSoundCall = new IncomeSoundCall(localPort + 1, this);
        outcomeSoundCall = new OutcomeSoundCall(otherUser, otherPort + 1, this);

    }


    public CallRequest listenForIncomingCalls() {
        CallRequest callRequest = null;
        try {
            serverListener = new ServerSocket(localPort);
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
     *
     */
    public void endCall() {
            incomeSoundCall.stopCall();
            outcomeSoundCall.stopCall();
            controller.cerrarVentana();

    }

    /**
     * tell the other user we declined his call. We send him a CallRequest with accept false
     */
    public void callCanceled() {
        sendCallRequest(false, true);
    }

    /**
     * tell the other user we accepted his call. We send him a CallRequest with accept true
     */
    public void acceptCall() {
        sendCallRequest(true, true);
    }

    public void sendCallRequest(boolean accept, boolean isResponse) {
        Socket socket = null;
        ObjectOutputStream outputStream = null;
        try {
            CallRequest callRequest = new CallRequest(accept, isResponse,false);

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

    public User getOtherUser() {
        return otherUser;
    }

    public int getOtherPort() {
        return otherPort;
    }

    public int getLocalPort() {
        return localPort;
    }

    public User getLocalUser() {
        return localUser;
    }

    public void closeServerListener() {
        try {
            serverListener.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverListener = null;
    }


    public void setController(CallWindowController callWindowController) {
        this.controller= callWindowController;
    }
}
