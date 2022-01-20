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

    public static void usePort(int PORT){
        occupatedPorts.add(PORT);
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

    public static boolean isPortFree(int port){
        return !occupatedPorts.contains(port);
    }

}
