package app.javachat;

import app.javachat.Utilities.LocalDataManager;
import app.javachat.Utilities.Info;
import app.javachat.Utilities.MenuBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    private Stage stage;
    private BorderPane root;


    public static void main(String[] args) {launch();}

    @Override
    public void start(Stage stage) throws IOException {
        Info.loadState();
        this.stage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        root = fxmlLoader.load();
        setStage();
        MenuBuilder.createMenu(root);

        stage.show();

        stage.setOnCloseRequest(e->{
            LocalDataManager.saveState();
        });
    }

    private void setStage() {
        Scene scene = new Scene(root, 1400, 730);
        stage.setTitle("Chat");
        stage.setScene(scene);
        stage.setMinWidth(1000);
        stage.setMinHeight(650);
    }
}