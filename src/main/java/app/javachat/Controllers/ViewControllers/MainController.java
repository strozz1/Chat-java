package app.javachat.Controllers.ViewControllers;

import app.javachat.Controllers.CustomControllers.ChatItem;
import app.javachat.Controllers.CustomControllers.LeftChatItem;
import app.javachat.*;
import app.javachat.Logger.Log;
import app.javachat.Models.GroupRoom;
import app.javachat.Models.Room;
import app.javachat.Utilities.Utils;
import app.javachat.Utilities.Info;
import app.javachat.Utilities.MessageSenderService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainController {

    @FXML
    private VBox lateralMenu;
    @FXML
    private Label usernameLeftLabel;
    @FXML
    private BorderPane parent;
    @FXML
    public Circle circle;
    private ServerConnection serverConnection;

    public MainController() {
        startConnection();
    }


    @FXML
    void initialize() throws IOException, SocketNotInitializedException {
        if (!checkUserLogged()) {
            openLoginWindow();
        }
        serverConnection.listen();
        usernameLeftLabel.textProperty().bind(Info.username);
        putUserImage();
    }

    private void putUserImage() {
        try{
        if(Info.image!=null) {
            Image image = Utils.base64ToImage(Info.image);
            circle.setFill(new ImagePattern(image));
            circle.setEffect(new DropShadow(25d, 0d, 0d, Color.GRAY));
        }}catch (Exception e){
            Log.error("cant parse image");
        }
    }


    private void startConnection() {
        this.serverConnection = new ServerConnection(new MessageManager(this));
        serverConnection.connect();
        MessageSenderService.setSocket(serverConnection.getSocket());
    }

    private boolean checkUserLogged() throws SocketNotInitializedException {
        boolean login = false;
        String user = Info.username.getValue();
        String password = Info.getPassword();
        if (user != null && password != null) {
            login = serverConnection.login(user, password);

        }
        return login;

    }


    private void loadStoredChats() {
        // TODO: 18/05/2022
    }

    private void openLoginWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("login-view.fxml"));


        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        LoginController controller = new LoginController();
        controller.setServerConnection(serverConnection);
        loader.setController(controller);
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().clear();
        scene.getStylesheets().add(Info.theme);
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(scene);

        stage.setOnCloseRequest(close -> {
            serverConnection.getSocket().disconnect();

            Platform.exit();
        });
        stage.showAndWait();


    }

    public void openAddChatView() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("add-server-view.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        AddRoomController controller = new AddRoomController();
        controller.setMainController(this);
        controller.setServerConnection(serverConnection);
        loader.setController(controller);
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().clear();
        scene.getStylesheets().add(Info.theme);
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void onAddChat(MouseEvent mouseEvent) throws IOException {
        openAddChatView();
    }

    public VBox getLateralMenu() {
        return lateralMenu;
    }

    public BorderPane getParent() {
        return parent;
    }

    public Room createNewLateralChatContainer(String username) {
        Room room = new SimpleRoom(username);
        ChatItem item = new ChatItem(room);
        LeftChatItem leftChatItem = new LeftChatItem(item);
        leftChatItem.setMainController(this);

        Info.saveChatItemToCOntainer(username, leftChatItem);
        Platform.runLater(() -> lateralMenu.getChildren().add(leftChatItem));
        return room;
    }

    public void createNewLateralRoomContainer(String id, String name) {
        GroupRoom room = new GroupRoom(id, name);
        ChatItem item = new ChatItem(room);
        LeftChatItem leftChatItem = new LeftChatItem(item);
        leftChatItem.setMainController(this);

        Info.saveChatItemToCOntainer(id, leftChatItem);
        Platform.runLater(() -> lateralMenu.getChildren().add(leftChatItem));
    }

}
