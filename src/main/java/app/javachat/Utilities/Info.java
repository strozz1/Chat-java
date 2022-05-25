package app.javachat.Utilities;

import app.javachat.Controllers.CustomControllers.LeftChatItem;
import app.javachat.MainApplication;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class Info {
    public static String theme;
    public static ThemeTypes themeType;
    public static boolean userIsLogged;
    public static Path profilePictureFile=LocalDataManager.userFolder.resolve("profile.png");
    public static StringProperty username = new SimpleStringProperty();
    private static String password;
    public static final String APP_NAME = "MensajeriaApp";
    public static HashMap<String,LeftChatItem> rooms = new HashMap<>();
    public static MessageSenderService messageSender;
    public static String image;


    public static void setUsername(String username) {
        Info.username.setValue(username);
    }

    public static void saveChatItemToCOntainer(String username,LeftChatItem item){
        rooms.put(username,item);
    }


    public static HashMap<String, Object> getMapFromJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<HashMap<String, Object>> typeRef = new TypeReference<>() {};
            return mapper.readValue(json, typeRef);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setPassword(String password) {
        Info.password=password;
    }

    public static String getPassword() {
        return password;
    }

    public static void setImage(String image) {
        Info.image=image;
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

        public static void startCallWindow() {
            try {
                FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("call-window.fxml"));

                Stage callWindow = new Stage();
                callWindow.initModality(Modality.WINDOW_MODAL);
                callWindow.setResizable(false);
//                callWindow.setTitle("Llamada con " + localCall.getOtherUser().getUsername());

//                CallWindowController callWindowController = new CallWindowController();
//                loader.setController(callWindowController);
                Parent root = loader.load();


                Scene scene = new Scene(root);
                callWindow.setScene(scene);
                callWindow.setAlwaysOnTop(true);
                callWindow.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        public static void createIncomingCallWindow() {
//            try {
//                FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("incoming-call-view.fxml"));
//                IncomingCallViewController controller = new IncomingCallViewController();
//                loader.setController(controller);
//                Parent root = loader.load();
//                Scene scene = new Scene(root);
//                Stage stage = new Stage();
//                stage.setScene(scene);
//                stage.initModality(Modality.WINDOW_MODAL);
//                stage.setResizable(false);
//
//                stage.show();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
