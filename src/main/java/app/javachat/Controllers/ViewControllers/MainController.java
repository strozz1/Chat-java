package app.javachat.Controllers.ViewControllers;

import app.javachat.*;
import app.javachat.Calls.CallListener;
import app.javachat.Chats.Chat;
import app.javachat.Chats.ChatListener;
import app.javachat.Chats.ChatRequest;
import app.javachat.Chats.SimpleChat;
import app.javachat.Controllers.CustomControllers.ChatItem;
import app.javachat.Controllers.CustomControllers.LeftChatItem;
import app.javachat.Logger.Log;
import app.javachat.Models.ChatInfo;
import app.javachat.Utilities.Info;
import app.javachat.Utilities.MessageSenderService;
import io.socket.client.Socket;
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
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

public class MainController {
    private HashMap<String, LeftChatItem> chats;

    @FXML
    private VBox lateralMenu;
    @FXML
    private Label usernameLeftLabel;
    @FXML
    private BorderPane parent;
    @FXML
    public Circle circle;

    public MainController() {
        chats = new HashMap<>();
    }

    @FXML
    void initialize() {
        startConnection();
        loadStoredChats(); //todo
        usernameLeftLabel.textProperty().bind(Info.username);
    }

    private void startConnection() {
        ServerConnection serverConnection = new ServerConnection(new MessageManager(this));
        MessageSenderService.setSocket(serverConnection.getSocket());

        serverConnection.connect();
        boolean login = false;
        try {
            login = serverConnection.login("Pepe", "123");
            if (login) serverConnection.listen();
        } catch (SocketNotInitializedException e) {
            Log.error(e.getMessage(), "MainController");
        }
    }



    private void loadStoredChats() {

    }


    public void openAddChatView() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("add-server-view.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(loader.load());
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

    public void createNewLateralChatContainer(String username,SimpleRoom room) {
        ChatItem item = new ChatItem(room);
        LeftChatItem leftChatItem = new LeftChatItem(item);
        chats.put(username,leftChatItem);
        leftChatItem.setMainController(this);
            Platform.runLater(()->lateralMenu.getChildren().add(leftChatItem));
    }

    public LeftChatItem getChat(String username) {
        return chats.get(username);
    }
}
