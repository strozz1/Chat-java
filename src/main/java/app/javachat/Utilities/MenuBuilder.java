package app.javachat.Utilities;

import app.javachat.Controllers.ViewControllers.ConfirmOperationController;
import app.javachat.Controllers.ViewControllers.LoggerWindowController;
import app.javachat.Controllers.ViewControllers.MainController;
import app.javachat.Logger.ConsoleType;
import app.javachat.Logger.Log;
import app.javachat.Logger.WindowLogType;
import app.javachat.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MenuBuilder {
    private static MenuBar menuBar;
    private static BorderPane root;
    public static MainController controller;

    public static void createMenu(BorderPane root, MainController controller1) {
        controller = controller1;
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
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select new photo");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Images", "*.*"),
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png")
            );
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                if (Files.exists(Info.profilePictureFile)) {
                    System.out.println(Info.profilePictureFile.toString());
                    Image img = new Image("file:" + Info.profilePictureFile.toString());
                    controller.circle.setFill(new ImagePattern(img));
                } else {
                    String fileName = "file:src/main/resources/app/javachat/images/default.png";
                    controller.circle.setFill(new ImagePattern(new Image(fileName)));
                }

                controller.circle.setEffect(new DropShadow(25d, 0d, 0d, Color.GRAY));


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

        MenuItem itemAbrirLogger = new MenuItem("Delete chats");
        itemAbrirLogger.onActionProperty().set(actionEvent -> {
            try {
                openAreYouSureOperation();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        toolsMenu.getItems().add(itemAbrirLogger);
        menuBar.getMenus().add(toolsMenu);
    }

    private static void openAreYouSureOperation() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("confirm-view.fxml"));
        ConfirmOperationController controller = new ConfirmOperationController();
        controller.setMainController(MenuBuilder.controller);
        loader.setController(controller);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().clear();
        scene.getStylesheets().add(Info.theme);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);

        stage.showAndWait();

    }



    private static void onChangeThemeAction(MenuItem itemToggleMode) {
        if (Info.themeType == ThemeTypes.LIGHT) {
            changeToDarkMode();
            itemToggleMode.setText("Cambiar a modo oscuro");
        } else {
            changeToLightMode();
            itemToggleMode.setText("Cambiar a modo claro");

        }
    }


    private static void changeToLightMode() {
        root.getStylesheets().clear();
        Info.theme = MainApplication.class.getResource("lightMode-style.css").toExternalForm();
        root.getStylesheets().add(Info.theme);
        Info.themeType = ThemeTypes.LIGHT;

    }

    private static void changeToDarkMode() {
        Info.theme = MainApplication.class.getResource("darkMode-style.css").toExternalForm();
        root.getStylesheets().clear();
        root.getStylesheets().add(Info.theme);
        Info.themeType = ThemeTypes.DARK;
    }


}
