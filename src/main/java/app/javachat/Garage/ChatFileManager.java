package app.javachat.Garage;

import app.javachat.Garage.SalaModel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ChatFileManager {

    private SalaModel salaModel;
    private final String serverIp;

    private final String APP_NAME = "MyMessage";
    private final String FILE_NAME = "mensajes.xd";
    private final String PATH_TO_USER_FOLDER = System.getProperty("user.home");

    private Path userFolder;
    private Path serverFolder;
    private Path serverFile;

    /**
     * Constructor el cual recibe la ip del server y una lista de mensajes, guardándolos en los atributos.
     *
     * @param serverIp ip del server
     * @param salaModel sala del server
     */
    ChatFileManager(String serverIp, SalaModel salaModel) {
        this.salaModel = salaModel;
        this.serverIp = serverIp;
        createServerFile();
        writeFile();
    }


    private void createServerFile() {
        userFolder = Paths.get(PATH_TO_USER_FOLDER).resolve(Paths.get(APP_NAME));
        serverFolder = userFolder.resolve(Paths.get(serverIp));
        serverFile = serverFolder.resolve(Paths.get(FILE_NAME));
        try {
            //Create Server folder
            Files.createDirectories(serverFolder);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFile() {
        try {
            ObjectOutputStream writer = new ObjectOutputStream(Files.newOutputStream(serverFile));
            writer.writeObject(salaModel);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void readFile() {
        try {
            ObjectInputStream reader = new ObjectInputStream(Files.newInputStream(serverFile));
            salaModel =(SalaModel) reader.readObject();
            reader.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
    }
}
