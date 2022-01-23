package app.javachat;

import app.javachat.Controllers.ViewControllers.LoggerWindowController;
import app.javachat.Logger.ConsoleType;
import app.javachat.Logger.Log;
import app.javachat.Logger.WindowLogType;
import app.javachat.Models.User;
import app.javachat.Utilities.Info;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainApplication extends Application {
    private Stage stage;
    private MenuBar menuBar;
    private boolean isLightMode = false;
    private BorderPane root;

    public static void main(String[] args) {launch();}

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        Info.localUser = new User("Strozz1");
        Log.show("Mi local Ip: "+Info.localUser.getIP());

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        root = fxmlLoader.load();
        setStage();
        setMenu();
        stage.show();
    }

    private void setStage() {
        Scene scene = new Scene(root, 1400, 730);
        stage.setTitle("Chat");
        stage.setScene(scene);
        stage.setMinWidth(1000);
        stage.setMinHeight(650);
    }

    private void setMenu() {
        menuBar = new MenuBar();

        createMenuInicio();
        createMenuPerfil();
        createMenuVer();
        createMenuHerramientas();

        root.setTop(menuBar);
    }


    private void createMenuInicio() {
        Menu menuInicio = new Menu("Inicio");
        menuBar.getMenus().add(menuInicio);
    }

    private void createMenuPerfil() {
        Menu menuPerfil = new Menu("Perfil");
        MenuItem itemCambiaNombre = new MenuItem("Cambiar nombre");

        itemCambiaNombre.setOnAction(actionEvent -> {
            try {
                FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("change-user-settings-view.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        menuPerfil.getItems().add(itemCambiaNombre);
        menuBar.getMenus().add(menuPerfil);
    }

    private void createMenuVer() {
        Menu menuVer = new Menu("Ver");

        MenuItem itemToggleMode = new MenuItem("Cambiar a modo claro");
        itemToggleMode.onActionProperty().set(actionEvent -> {
            onToggleMode(itemToggleMode);
        });

        menuVer.getItems().add(itemToggleMode);
        menuBar.getMenus().add(menuVer);
    }

    private void createMenuHerramientas() {
        Menu menuHerramientas = new Menu("Herramientas");

        MenuItem itemAbrirLogger = new MenuItem("Abrir logger");
        itemAbrirLogger.onActionProperty().set(actionEvent -> {
            loadLog(itemAbrirLogger);
            itemAbrirLogger.disableProperty().set(true);
        });

        menuHerramientas.getItems().add(itemAbrirLogger);
        menuBar.getMenus().add(menuHerramientas);
    }


    private void onToggleMode(MenuItem itemToggleMode) {
        if (isLightMode) {
            changeToDarkMode();
            itemToggleMode.setText("Cambiar a modo claro");
        } else {
            changeToLightMode();
            itemToggleMode.setText("Cambiar a modo oscuro");

        }
    }


    private void changeToLightMode() {
        root.getStylesheets().remove(MainApplication.class.getResource("darkMode-style.css").toExternalForm());
        root.getStylesheets().add(MainApplication.class.getResource("lightMode-style.css").toExternalForm());
        isLightMode = !isLightMode;

    }

    private void changeToDarkMode() {
        root.getStylesheets().remove(MainApplication.class.getResource("lightMode-style.css").toExternalForm());
        root.getStylesheets().add(MainApplication.class.getResource("darkMode-style.css").toExternalForm());
        isLightMode = !isLightMode;
    }

    private void loadLog(MenuItem itemAbrirLogger) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("logger-window.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            Scene scene;
            scene = new Scene(loader.load());

            LoggerWindowController controller = loader.getController();
            Log.setLoggerType(new WindowLogType(controller));
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(windowEvent -> {
                Log.setLoggerType(new ConsoleType());
                stage.close();
                itemAbrirLogger.disableProperty().set(false);


            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}