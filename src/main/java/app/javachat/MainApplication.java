package app.javachat;

import app.javachat.Utilities.LocalDataManager;
import app.javachat.Utilities.Info;
import app.javachat.Utilities.MenuBuilder;
import app.javachat.Utilities.ThemeTypes;
import javafx.application.Application;
import javafx.application.Platform;
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
        LocalDataManager.loadState();
        setTheme();
        this.stage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        root = fxmlLoader.load();
        setStage();
        MenuBuilder.createMenu(root,fxmlLoader.getController());

        stage.show();

        stage.setOnCloseRequest(e->{
            LocalDataManager.saveState();
            Platform.exit();

        });
    }

    private void setTheme() {
        String theme = Properties.getProperty("theme");
        if(theme.equals("dark")){
            Info.themeType=ThemeTypes.DARK;
            Info.theme=MainApplication.class.getResource("darkMode-style.css").toExternalForm();
        }else if(theme.equals("light")){
            Info.themeType=ThemeTypes.LIGHT;
            Info.theme= MainApplication.class.getResource("lightMode-style.css").toExternalForm();
        }else{
            Info.themeType=ThemeTypes.DARK;
            Info.theme=MainApplication.class.getResource("darkMode-style.css").toExternalForm();
        }
    }

    private void setStage() {
        Scene scene = new Scene(root, 1400, 730);
        stage.setTitle("Chat");
        stage.setScene(scene);
        stage.setMinWidth(1000);
        stage.setMinHeight(650);
    }
}