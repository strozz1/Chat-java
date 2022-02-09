package app.javachat.Controllers.ViewControllers;

import app.javachat.Calls.Call;
import app.javachat.Chats.Chat;
import app.javachat.Chats.ChatListener;
import app.javachat.Chats.ChatRequest;
import app.javachat.Chats.SimpleChat;
import app.javachat.Controllers.CustomControllers.ChatItem;
import app.javachat.Controllers.CustomControllers.LeftChatItem;
import app.javachat.MainApplication;
import app.javachat.Models.ChatInfo;
import app.javachat.Utilities.Info;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;

public class MainController {

    @FXML
    private VBox lateralMenu;
    @FXML
    private Label usernameLeftLabel;
    @FXML
    private BorderPane parent;

    public MainController() {

    }

    @FXML
    void initialize() {
        ChatListener chatListener = new ChatListener(this);
        chatListener.startThreads();
        usernameLeftLabel.textProperty().bind(Info.username);
        loadStoredChats();
    }

    private void loadStoredChats() {
        List<ChatInfo> chats = Info.chatInfoList;
        chats.forEach(info -> {
            ChatRequest chatRequest = new ChatRequest(info.getUser(), true);
            chatRequest.setChatPort(info.getOtherChatPort());
            Chat chat = new SimpleChat(chatRequest, info.getLocalChatPort());
            ChatItem chatItem = new ChatItem(chat, info.getUser(),info.getOtherCallListenerPort());
            LeftChatItem leftChatItem = new LeftChatItem(chatItem);
            leftChatItem.setMainController(this);
            lateralMenu.getChildren().add(leftChatItem);
        });
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
}
