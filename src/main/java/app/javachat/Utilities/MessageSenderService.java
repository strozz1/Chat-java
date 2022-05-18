package app.javachat.Utilities;

import app.javachat.Logger.Log;
import io.socket.client.Socket;

import java.util.HashMap;

public class MessageSenderService {
    private static Socket socket;

    public static void setSocket(Socket socket) {
        MessageSenderService.socket =socket;
    }

    public static void sendMessage(String jsonMessage){
        socket.emit("message",jsonMessage);
        Log.show("Message sended.","MessageSenderService");
    }

    /**
     * Sends request to server for a room creation
     * @param groupName The new room name
     * @param userList List of users who will be part of the room
     */
    public static void sendRoomCreation(String groupName, String[] userList) {
        String roomString = JSONBuilder.parseRoomToJSON(groupName, userList);
        System.out.println(roomString);
        socket.emit("room-creation",roomString);
    }
}
