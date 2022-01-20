package app.javachat;

import app.javachat.Models.User;

import java.util.ArrayList;
import java.util.List;

public class Info {
    public static User localUser;
    public static final int NEW_CHAT_LISTENER_PORT= 867;
    private static List<Integer> occupatedPorts= new ArrayList<>(NEW_CHAT_LISTENER_PORT);

    public static void usePort(int PORT){
        occupatedPorts.add(PORT);
    }
    public static void UnUsePort(int PORT){
        occupatedPorts.remove(PORT);
    }

    public static List<Integer> getOccupatedPorts() {
        return occupatedPorts;
    }

    public static boolean isPortFree(int port){
        return !occupatedPorts.contains(port);
    }

}
