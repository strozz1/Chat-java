package app.javachat.Utilities;

import app.javachat.Logger.Log;
import app.javachat.Models.AppState;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static app.javachat.Utilities.Info.APP_NAME;

public class LocalDataManager {


    private static final String FILE_NAME = "userData.conf";
    private static final String APP_FOLDER = System.getProperty("user.home");

    public static final Path userFolder = Paths.get(APP_FOLDER).resolve(APP_NAME);
    private static final Path userDataFile = userFolder.resolve(FILE_NAME);

    public static void saveState() {
        Log.show("Saving user data to local store");
        createFiles();
        AppState appState = Info.saveState();
        try (ObjectOutputStream writer = new ObjectOutputStream(Files.newOutputStream(userDataFile))) {
            writer.writeObject(appState);
        } catch (IOException e) {
            Log.error(e.getMessage(), "ChatFileManager");
        }
    }


    public static AppState loadState() {
        Log.show("Loading user data from local store");
        createFiles();

        AppState appState = new AppState();
        try (ObjectInputStream reader = new ObjectInputStream(Files.newInputStream(userDataFile))) {
            appState = (AppState) reader.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Log.error(e.getMessage(), "ChatFileManager");
        }
        return appState;

    }

    private static void createFiles() {
        try {
            if (!Files.exists(userDataFile)) {
                Files.createDirectories(userFolder);
                Files.createFile(userDataFile);
            }

        } catch (IOException e) {
            Log.error(e.getMessage());
        }
    }

    public static void savePhoto(File file){

        try {
            BufferedWriter bufferedWriter= new BufferedWriter(new FileWriter(new File("a.png")));
            Path resolve = userFolder.resolve("profile.png");
            if(Files.exists(resolve))
            Files.delete(resolve);
            Files.copy(Paths.get(file.getPath()),userFolder.resolve("profile.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
