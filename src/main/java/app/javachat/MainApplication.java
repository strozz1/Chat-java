package app.javachat;

import app.javachat.Logger.ConsoleType;
import app.javachat.Logger.Log;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    private Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage=stage;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 650);
        stage.setTitle("Chat");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(windowEvent -> {
            Platform.exit();
            System.exit(1);
        });

    }

    public static void main(String[] args) {launch();}

}