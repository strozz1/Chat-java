package app.javachat.Utilities;

import app.javachat.Controllers.ViewControllers.CallWindowController;
import app.javachat.Controllers.ViewControllers.IncomingCallViewController;
import app.javachat.MainApplication;
import app.javachat.Models.AppState;
import app.javachat.Models.ChatInfo;
import app.javachat.Models.User;
import app.javachat.SimpleRoom;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.sound.sampled.AudioFormat;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Info {
    public static User localUser;
    public static Path profilePictureFile=LocalDataManager.userFolder.resolve("profile.png");
    public static StringProperty username = new SimpleStringProperty("");
    public static final int NEW_CHAT_LISTENER_PORT = 867;
    public static final String APP_NAME = "MensajeriaApp";
    public static List<ChatInfo> chatInfoList = new ArrayList<>();
    public static HashMap<String,SimpleRoom> rooms = new HashMap<>();
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
        chatInfoList = appState.getChatInfoList();
    }
    public static HashMap<String, Object> getMapFromJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<HashMap<String, Object>> typeRef = new TypeReference<>() {
            };
            return mapper.readValue(json, typeRef);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
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
        public static final int CALL_PORT = getAvailableCallPort();
        private static boolean inCall = false;
        static app.javachat.Calls.Call localCall;
        public static final int CALL_LISTENER_PORT = 8867;
        public static final int BUFFER_SIZE = 512;
        public static final int SAMPLE_RATE = 44000;
        public static final int SAMPLE_SIZE_BITS = 16;

        /**
         * Searches for a port and returns it, saving his index on indexOfCallPort
         *
         * @return port
         */
        public static int getAvailableCallPort() {
            int port = 55000;
            for (int i = 55000; i < 55300; i++) {
                if (Info.isPortFree(i)) {
                    port = i;
                    break;
                }
            }
            Info.usePort(port);
            return port;
        }

        public static AudioFormat getAudioFormat() {
            return new AudioFormat(Info.Call.SAMPLE_RATE, Info.Call.SAMPLE_SIZE_BITS, 1, true, true);
        }

        public synchronized static boolean isInCall() {
            return inCall;
        }

        public synchronized static void setInCall(boolean inCall) {
            Call.inCall = inCall;
        }

        public static app.javachat.Calls.Call getLocalCall() {
            return localCall;
        }

        public static void setLocalCall(app.javachat.Calls.Call call) {
            localCall = call;
        }

        public static void startCallWindow() {
            try {
                FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("call-window.fxml"));

                Stage callWindow = new Stage();
                callWindow.initModality(Modality.WINDOW_MODAL);
                callWindow.setResizable(false);
                callWindow.setTitle("Llamada con " + localCall.getOtherUser().getUsername());

                CallWindowController callWindowController = new CallWindowController();
                loader.setController(callWindowController);
                Parent root = loader.load();


                Scene scene = new Scene(root);
                callWindow.setScene(scene);
                callWindow.setAlwaysOnTop(true);
                callWindow.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public static void createIncomingCallWindow() {
            try {
                FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("incoming-call-view.fxml"));
                IncomingCallViewController controller = new IncomingCallViewController();
                loader.setController(controller);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.setResizable(false);

                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
