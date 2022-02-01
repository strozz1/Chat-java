package app.javachat.Utilities;

import app.javachat.Controllers.ViewControllers.LoggerWindowController;
import app.javachat.Logger.ConsoleType;
import app.javachat.Logger.Log;
import app.javachat.Logger.WindowLogType;
import app.javachat.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class MenuBuilder {
    private static MenuBar menuBar;
    private static boolean isLightMode = false;
    private static BorderPane root;

    public static void createMenu(BorderPane root) {
        MenuBuilder.root = root;
        menuBar = new MenuBar();

        createHomeMenu();
        createProfileMenu();
        createViewMenu();
        createToolsMenu();
        root.setTop(menuBar);
    }


    private static void createHomeMenu() {
        Menu homeMenu = new Menu("Inicio");
        menuBar.getMenus().add(homeMenu);
    }

    private static void createProfileMenu() {
        Menu profileMenu = new Menu("Perfil");
        MenuItem itemChangeName = new MenuItem("Cambiar nombre");
        MenuItem itemChangePhoto = new MenuItem("Cambiar Foto");

        itemChangePhoto.setOnAction(actionEvent -> {
            Stage stage = new Stage();
            FileChooser fileChooser= new FileChooser();
            fileChooser.setTitle("Select new photo");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Images", "*.*"),
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png")
            );
            File file = fileChooser.showOpenDialog(stage);
            if(file !=null){
                LocalDataManager.savePhoto(file);
            }
        });

        itemChangeName.setOnAction(actionEvent -> {
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
        profileMenu.getItems().add(itemChangeName);
        profileMenu.getItems().add(itemChangePhoto);
        menuBar.getMenus().add(profileMenu);
    }


    private static void createViewMenu() {
        Menu viewMenu = new Menu("Ver");

        MenuItem itemToggleMode = new MenuItem("Cambiar a modo claro");
        itemToggleMode.onActionProperty().set(actionEvent -> {
            onChangeThemeAction(itemToggleMode);
        });

        viewMenu.getItems().add(itemToggleMode);
        menuBar.getMenus().add(viewMenu);
    }

    private static void createToolsMenu() {
        Menu toolsMenu = new Menu("Herramientas");

        MenuItem itemAbrirLogger = new MenuItem("Abrir logger");
        itemAbrirLogger.onActionProperty().set(actionEvent -> {
            loadLog(itemAbrirLogger);
            itemAbrirLogger.disableProperty().set(true);
        });

        toolsMenu.getItems().add(itemAbrirLogger);
        menuBar.getMenus().add(toolsMenu);
    }

    private static void loadLog(MenuItem itemAbrirLogger) {
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

    private static void onChangeThemeAction(MenuItem itemToggleMode) {
        if (isLightMode) {
            changeToDarkMode();
            itemToggleMode.setText("Cambiar a modo claro");
        } else {
            changeToLightMode();
            itemToggleMode.setText("Cambiar a modo oscuro");

        }
    }


    private static void changeToLightMode() {
        root.getStylesheets().remove(MainApplication.class.getResource("darkMode-style.css").toExternalForm());
        root.getStylesheets().add(MainApplication.class.getResource("lightMode-style.css").toExternalForm());
        isLightMode = !isLightMode;

    }

    private static void changeToDarkMode() {
        root.getStylesheets().remove(MainApplication.class.getResource("lightMode-style.css").toExternalForm());
        root.getStylesheets().add(MainApplication.class.getResource("darkMode-style.css").toExternalForm());
        isLightMode = !isLightMode;
    }


}
