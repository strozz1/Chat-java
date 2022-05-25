package app.javachat.Utilities;

import app.javachat.Controllers.CustomControllers.ChatItem;
import app.javachat.Controllers.CustomControllers.LeftChatItem;
import app.javachat.Logger.Log;
import app.javachat.Models.Container;
import app.javachat.Models.Room;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static app.javachat.Utilities.Info.APP_NAME;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class LocalDataManager {


    private static final String CRED_NAME = "credentials.conf";
    private static final String APP_FOLDER = System.getProperty("user.home");
    public static final Path userFolder = Paths.get(APP_FOLDER).resolve(APP_NAME);
    private static final Path userCredentialsFile = userFolder.resolve(CRED_NAME);

    public static void saveState() {
        Log.show("Saving user data to local store", "LocalDataManager");
        saveUserCredentials(Info.username.getValue(), Info.getPassword(), new Container(Info.rooms));
    }


    public static void loadState() {
        Log.show("Loading user data from local store");
        loadUserCredentials();
    }

    private static void loadUserCredentials() {
        Properties properties = new Properties();
        try {
            properties.load(new ObjectInputStream(Files.newInputStream(userCredentialsFile)));
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");
            String image = properties.getProperty("image");
            String chats = properties.getProperty("chats");
            if (username != null) Info.username.setValue(username);
            if (password != null) Info.setPassword(password);
            if (image != null) Info.setImage(image);
            if(chats!=null) {
                HashMap<String, Room> rooms = (HashMap<String, Room>) Utils.base64ToObject(chats);
                if (rooms != null) loadChats(rooms);
            }

            else Info.rooms = new HashMap<>();

        } catch (IOException e) {
            Log.show("No user data guardada, esperando login", LocalDataManager.class.getName());
            Info.rooms = new HashMap<>();
        }

    }

    private static void getRooms(HashMap<String, LeftChatItem> rooms) {

    }

    public static void saveUserCredentials(String username, String password, Object o) {
        try {
            Properties properties = new Properties();
            properties.setProperty("username", username);
            properties.setProperty("password", password);
            if (Info.image != null)
                properties.setProperty("image", Info.image);
            if (o != null) properties.setProperty("chats", Utils.objectToBase64(parseToList()));
            OutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(userCredentialsFile, CREATE, TRUNCATE_EXISTING));
            properties.store(outputStream, "comment");

        } catch (IOException e) {
            e.printStackTrace();
        }
        Info.setUsername(username);
        Info.setPassword(password);

    }

    public static void clearCredentials() {
        try {
            Files.delete(userCredentialsFile);
        } catch (IOException e) {
            Log.error("No eixste el fichero, pero no pasa nada",LocalDataManager.class.getName());
        }
    }

    public static void loadChats(HashMap<String, Room> rooms){
        for(Map.Entry<String, Room> entry : rooms.entrySet()) {
            String key = entry.getKey();
            Room value = entry.getValue();
            ChatItem chatItem= new ChatItem(value);
            LeftChatItem leftChatItem= new LeftChatItem(chatItem);
            Info.rooms.put(key,leftChatItem);
            // do what you have to do here
            // In your case, another loop.
        }

    }
    public static HashMap<String, Room> parseToList(){
        HashMap<String, Room> rooms= new HashMap<>();
        Info.rooms.forEach((a,b)->{
            rooms.put(a,b.getChatItem().getController().getRoom());
        });
        return rooms;
    }


}
