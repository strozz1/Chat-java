package app.javachat.Utilities;

import app.javachat.Chats.SimpleChat;
import app.javachat.Controllers.CustomControllers.LeftChatItem;
import app.javachat.Models.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

public class Info {
    public static User localUser;
    public static StringProperty username=new SimpleStringProperty("");

    public static final int NEW_CHAT_LISTENER_PORT = 867;
    private static List<Integer> occupatedPorts = new ArrayList<>(NEW_CHAT_LISTENER_PORT);
    private static List<LeftChatItem> chats = new ArrayList<>();

    public static int usePort(int PORT) {
        occupatedPorts.add(PORT);
        return occupatedPorts.indexOf(PORT);
    }
    public void setUsername(String username){
        this.username.setValue(username);
        localUser.setUsername(username);
    }

    public static void unUsePort(int PORT) {
        occupatedPorts.remove(PORT);
    }

    public static void addChat(LeftChatItem chat) {
        chats.add(chat);
    }

    public static int getChatPos(LeftChatItem chat) {
        return chats.indexOf(chat);
    }

    public static void removeChat(LeftChatItem chat) {
        chats.remove(chat);
    }

    public static List<Integer> getOccupatedPorts() {
        return occupatedPorts;
    }

    public static int getPort(int index) {
        return occupatedPorts.get(index);
    }

    public static boolean isPortFree(int port) {
        return !occupatedPorts.contains(port);
    }

    public static boolean checkIfChatExist(LeftChatItem chatItem) {
        for (LeftChatItem local : chats) {
            String newIp = ((SimpleChat) chatItem.getChatItem().getChat()).otherUser.getIP();
            String localIp = ((SimpleChat) local.getChatItem().getChat()).otherUser.getIP();
            if (newIp.equals(localIp))
                return true;
        }
        return false;
    }

    public static Node getChat(int pos) {
        return chats.get(pos);
    }
}
