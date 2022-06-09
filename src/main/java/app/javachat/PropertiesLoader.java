package app.javachat;

import app.javachat.Logger.Log;
import io.socket.engineio.parser.Base64;

import java.io.*;
import java.util.HashMap;

/**
 *
 * Contains all necessary properties for de application services.
 */
public class PropertiesLoader {
//    private static File propertiesFile = new File("C:\\Users\\triss\\MensajeriaApp\\application.properties");
    private static HashMap<String, String> propertiesMap = new HashMap<>();


    static {
        propertiesMap.put("server-uri","http://81.34.143.209:1234/");
        propertiesMap.put("theme","dark");
        propertiesMap.put("database-name","test");
//            String line;
//        try(BufferedReader reader = new BufferedReader(new FileReader(propertiesFile))) {
//            while ((line = reader.readLine()) != null) {
//                String[] property = line.split("->");
//                propertiesMap.put(property[0].trim(), property[1].trim());
//            }
//        } catch (IOException e) {
//            Log.error(e.getMessage(),"Properties");
//        }
    }


    /**
     *
     * @param property The property name
     * @return the value of the property asked for
     */
    public static String getProperty(String property) {
        return propertiesMap.get(property);
    }
}
