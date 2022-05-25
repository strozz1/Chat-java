package app.javachat.Utilities;

import app.javachat.Controllers.CustomControllers.LeftChatItem;
import app.javachat.Logger.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
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
        saveUserCredentials(Info.username.getValue(), Info.getPassword(), Info.rooms);
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

            if (chats != null) Info.rooms = (HashMap<String, LeftChatItem>) Utils.base64ToObject(chats);
            else Info.rooms = new HashMap<>();

        } catch (IOException e) {
            Log.error("No user data guardada", LocalDataManager.class.getName());
        }
    }

    public static void saveUserCredentials(String username, String password, Object o) {
        try {
            Properties properties = new Properties();
            properties.setProperty("username", username);
            properties.setProperty("password", password);
            if (Info.image != null)
                properties.setProperty("image", Info.image);
            if (o != null) properties.setProperty("chats", Utils.objectToBase64(o));
            OutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(userCredentialsFile, CREATE, TRUNCATE_EXISTING));
            properties.store(outputStream, "comment");

        } catch (IOException e) {
            e.printStackTrace();
        }
        Info.setUsername(username);
        Info.setPassword(password);

    }
}
