package app.javachat.Utilities;

import app.javachat.Models.AppState;
import app.javachat.Models.ChatInfo;
import app.javachat.Models.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.sound.sampled.AudioFormat;
import java.util.ArrayList;
import java.util.List;

public class Info {
    public static User localUser;
    public static StringProperty username = new SimpleStringProperty("");
    public static final int NEW_CHAT_LISTENER_PORT = 867;
    public static final String APP_NAME = "MensajeriaApp";
    public static List<ChatInfo> chatInfoList = new ArrayList<>();
    private static List<Integer> occupatedPorts = new ArrayList<>(NEW_CHAT_LISTENER_PORT);


    /**
     * This method tells the program whichh port it wll use for a specific chat, returning the index of the list containing all che used ports.
     *
     * @param PORT
     * @return position index of port on usedPorts list.
     */
    public static int usePort(int PORT) {
        occupatedPorts.add(PORT);
        return occupatedPorts.indexOf(PORT);
    }

    public static void setUsername(String username) {
        Info.username.setValue(username);
        localUser.setUsername(username);
    }

    public static AppState saveState() {
        AppState appState = new AppState();
        appState.setUser(localUser);
        appState.setOccupatedPorts(occupatedPorts);
        appState.setChatInfoList(chatInfoList);

        return appState;
    }

    public static void loadState() {
        AppState appState = LocalDataManager.loadState();

        localUser = appState.getUser();
        setUsername(localUser.getUsername());

        occupatedPorts = appState.getOccupatedPorts();
        chatInfoList=appState.getChatInfoList();
    }

    public static void unUsePort(int PORT) {
        occupatedPorts.remove(PORT);
    }

    public static void addChat(ChatInfo chat) {
        chatInfoList.add(chat);
    }

    public static int getPort(int index) {
        return occupatedPorts.get(index);
    }

    public static boolean isPortFree(int port) {
        return !occupatedPorts.contains(port);
    }

    public static boolean checkIfChatExist(ChatInfo chatInfo) {
        for (ChatInfo local : chatInfoList) {
            String newIp = chatInfo.getUser().getIP();
            String localIp = local.getUser().getIP();
            if (newIp.equals(localIp))
                return true;
        }
        return false;
    }


    /**
     * Embeded class for Call Info and data.
     */
    public static class Call {
        public static final int BUFFER_SIZE = 512;
        public static final int SAMPLE_RATE = 44000;
        public static final int SAMPLE_SIZE_BITS = 16;

        public static AudioFormat getAudioFormat() {
            return new AudioFormat(Info.Call.SAMPLE_RATE, Info.Call.SAMPLE_SIZE_BITS, 1, true, true);
        }
    }
}
