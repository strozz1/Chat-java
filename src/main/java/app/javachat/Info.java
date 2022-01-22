package app.javachat;

import app.javachat.Models.Chat;
import app.javachat.Models.User;

import java.util.ArrayList;
import java.util.List;

public class Info {
    public static User localUser;
    public static final int NEW_CHAT_LISTENER_PORT= 867;
    private static List<Integer> occupatedPorts= new ArrayList<>(NEW_CHAT_LISTENER_PORT);
    private static List<Chat> chats= new ArrayList<>();

    public static int usePort(int PORT){
        occupatedPorts.add(PORT);
        return occupatedPorts.indexOf(PORT);
    }
    public static void unUsePort(int PORT){
        occupatedPorts.remove(PORT);
    }

    public static void addChat(Chat chat){
        chats.add(chat);
    }
    public static int getChatPos(Chat chat){
        return chats.indexOf(chat);
    }
    public static void removeChat(Chat chat){
        chats.remove(chat);
    }

    public static List<Integer> getOccupatedPorts() {
        return occupatedPorts;
    }
    public static int getPort(int index){
        return occupatedPorts.get(index);
    }

    public static boolean isPortFree(int port){
        return !occupatedPorts.contains(port);
    }

}
