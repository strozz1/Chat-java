package app.javachat.Utilities;

import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class ImageUtils {
    public static String convertToBase64(File file) {
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            return Base64.getEncoder().encodeToString(bytes);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Image convertFromBase64(String base64) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64);
        return new Image(new ByteArrayInputStream(decodedBytes));
    }
}
