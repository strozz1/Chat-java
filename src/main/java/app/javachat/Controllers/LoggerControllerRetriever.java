package app.javachat.Controllers;

import app.javachat.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LoggerControllerRetriever {

    private static LoggerController controller;

    public static void setLoggerController(LoggerController controller1){
        controller = controller1;
    }
    public static LoggerController getLoggerController(){
        return controller;
    }
}
