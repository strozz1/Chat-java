package app.javachat.Controllers;

import app.javachat.Models.Mensaje;
import app.javachat.Models.PackageInfo;
import app.javachat.Models.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ChatFileManager {

    private PackageInfo paquete;
    private final String serverIp;

    private final String APP_NAME = "MyMessage";
    private final String FILE_NAME = "mensajes.xd";
    private final String PATH_TO_USER_FOLDER = System.getProperty("user.home");

    private Path userFolder;
    private Path serverFolder;
    private Path serverFile;

    /**
     * Constructor el cual recibe la ip del server y una lista de mensajes, guardandolos en los atributos.
     *
     * @param serverIp
     * @param paquete
     */
    ChatFileManager(String serverIp, PackageInfo paquete) {
        this.paquete = paquete;
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
            writer.writeObject(paquete);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void readFile() {
        try {
            ObjectInputStream reader = new ObjectInputStream(Files.newInputStream(serverFile));
            paquete =(PackageInfo) reader.readObject();
            reader.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public PackageInfo getPaquete() {
        return paquete;
    }

    public void setPaquete(PackageInfo paquete) {
        this.paquete = paquete;
    }

    public static void main(String[] args) {
        ArrayList<Mensaje> a = new ArrayList();
        ArrayList<User> b = new ArrayList();
        b.add(new User("Juan", "192.168.1.2"));
        b.add(new User("Pepe", "192.168.1.3"));
        b.add(new User("Alberto", "192.168.1.34"));

        a.add(new Mensaje("hola", b.get(0), LocalDateTime.now()));
        a.add(new Mensaje("adios", b.get(1), LocalDateTime.now()));
        a.add(new Mensaje("q tal", b.get(0), LocalDateTime.now()));

        PackageInfo g = new PackageInfo();
        g.setListMensajes(a);
        g.setListUsuarios(b);
        ChatFileManager n = new ChatFileManager("192.167.1.12", g);
        System.out.println(n.getPaquete());

    }
}
