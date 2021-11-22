package app.javachat.Log;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogInfo {
    public static final Logger logger = Logger.getLogger("Basic");

    public static Logger log() {

        try {
            String file = "logs/"+LocalDate.now().toString()+".log";
            new File("logs").mkdir();
            new File(file).createNewFile();
            FileHandler fileHandler = new FileHandler(file, true);
            logger.addHandler(fileHandler);
            SimpleFormatter formater = new SimpleFormatter();
            fileHandler.setFormatter(formater);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  logger;
    }


}
