package app.javachat.Utilities;

import io.socket.client.Socket;

public class MessageSenderService {
    private static Socket socket;

    public static void setSocket(Socket socket) {
        MessageSenderService.socket =socket;
    }

    public static void sendMessageToServer(String jsonMessage){
        socket.emit("message",jsonMessage);
    }
}
