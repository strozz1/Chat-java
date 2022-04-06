package app.javachat.Controllers.ViewControllers;

import app.javachat.Controllers.CustomControllers.ChatItem;
import app.javachat.Controllers.CustomControllers.LeftChatItem;
import app.javachat.Logger.Log;
import app.javachat.*;
import app.javachat.Utilities.Info;
import app.javachat.Utilities.MessageSenderService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.HashMap;

public class MainController {

    @FXML
    private VBox lateralMenu;
    @FXML
    private Label usernameLeftLabel;
    @FXML
    private BorderPane parent;
    @FXML
    public Circle circle;

    public MainController() {

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
        boolean login;
        try {
            login = serverConnection.login("pepe", "123");
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

    public void createNewLateralChatContainer(String username) {
        SimpleRoom room = new SimpleRoom(username);
        ChatItem item = new ChatItem(room);
        LeftChatItem leftChatItem = new LeftChatItem(item);
        leftChatItem.setMainController(this);

        Info.saveChatItemToCOntainer(username,leftChatItem);
        Platform.runLater(() -> lateralMenu.getChildren().add(leftChatItem));
    }

}
