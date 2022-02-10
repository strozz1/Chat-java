package app.javachat.Calls;

import app.javachat.Chats.ChatRequest;
import app.javachat.Logger.Log;
import app.javachat.Models.User;
import app.javachat.Utilities.Info;
import javafx.application.Platform;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static app.javachat.Utilities.Info.Call.*;

public class CallListener {

    private ServerSocket localServer;
    private ServerSocket callServer;

    public CallListener() {
        try {
            localServer = new ServerSocket(Info.Call.CALL_LISTENER_PORT);
        } catch (IOException e) {
            Log.error(e.getMessage(), "Call Listener");
        }

    }

    public void listenCallsRequests() {
        try {
            Log.show("Listening for new call requests  at port "+ CALL_LISTENER_PORT, "Call Listener");
            Socket inputServer = localServer.accept();
            Log.show("New income call request.", "Call Listener");
            ObjectInputStream inputStream = new ObjectInputStream(inputServer.getInputStream());
            Object objectRead = inputStream.readObject();
            inputStream.close();

            if (objectRead instanceof CallRequest callRequest) {
                CallRequest selfCallRequest = new CallRequest();

                if (callRequest.isResponse()) {
                    Log.show("The call is a response.", "Call Listener");

                    // If its a response, we verify if user is free
                    if (callRequest.isCallFree()) {
                        Log.show("The call is free.", "Call Listener");

                        User otherUser = callRequest.getUser();
                        Call call = new Call(CALL_PORT, otherUser, callRequest.getCallPort());
                        Info.Call.setCall(call);
                        Info.Call.getCall().sendCallRequest(false, false);
                        Log.show("Sent a call request.", "Call Listener");
                    }
                } else sendCallResponse(callRequest);

            }
        } catch (IOException | ClassNotFoundException e) {
            Log.error(e.getMessage());
        }
    }

    /**
     * Send back a response for a call request.
     *
     * @param callRequest - the call request received by the other user.
     * @throws IOException
     */
    private void sendCallResponse(CallRequest callRequest) throws IOException {
        boolean isInCall = Info.Call.isInCall();
        CallRequest selfCallRequest = new CallRequest(isInCall, true, false);

        String ip = callRequest.getUser().getIP();
        int port = callRequest.getCallListenerPort();

        Socket socket = new Socket(ip, port);
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(selfCallRequest);
        outputStream.close();
        Log.show("Sending Call Response", "CallListener");
    }

    /**
     * This method listen for incoming calls, accepted by the call listener
     */
    public void listenForIncomingCalls() {
        try {
            callServer = new ServerSocket(CALL_PORT);
            Log.show("Listening for new incoming calls at port "+ CALL_PORT,"Call Listener");

            Socket socketAccept = callServer.accept();
            ObjectInputStream inputStream = new ObjectInputStream(socketAccept.getInputStream());
            CallRequest callRequest = (CallRequest) inputStream.readObject();

            // If accept, create call window, otherwise create new call incoming window.
            if (callRequest.isAccept()) {
                Info.Call.setInCall(true);
                Platform.runLater(Info.Call::startCallWindow);
            } else {
                Platform.runLater(Info.Call::createIncomingCallWindow);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
