package app.javachat.Utilities;

import javafx.scene.image.Image;

import java.io.*;
import java.nio.file.Files;
import java.util.Base64;

public class Utils {
    public static String fileToBase64(File file) {
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            return Base64.getEncoder().encodeToString(bytes);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Image base64ToImage(String base64) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64);
        return new Image(new ByteArrayInputStream(decodedBytes));
    }

    public static Object base64ToObject(String base64) {
        byte[] data = Base64.getDecoder().decode(base64);
        Object o;
        try (ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(data))) {
            o = ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return o;
    }

    public static String objectToBase64(Object o) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(o);
            return new String(Base64.getEncoder().encode(baos.toByteArray()));
        }
    }
}
