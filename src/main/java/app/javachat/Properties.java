package app.javachat;

import app.javachat.Logger.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * Contains all necessary properties for de application services.
 */
public class Properties {
    private static File propertiesFile = new File("src/main/java/app/javachat/application.properties");
    private static HashMap<String, String> propertiesMap = new HashMap<>();

    static {
            String line;
        try(BufferedReader reader = new BufferedReader(new FileReader(propertiesFile))) {
            while ((line = reader.readLine()) != null) {
                String[] property = line.split("->");
                propertiesMap.put(property[0], property[1].trim());

            }
        } catch (IOException e) {
            Log.error(e.getMessage(),"Properties");
        }
    }

    /**
     *
     * @param property The property to obtain
     * @return the value of the property asked
     */
    public static String getProperty(String property) {
        return propertiesMap.get(property);
    }
}
