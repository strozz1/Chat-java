package app.javachat.Controllers.ViewControllers;

import app.javachat.Calls.Call;
import app.javachat.Calls.CallListener;
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
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

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
        loadStoredChats();
        usernameLeftLabel.textProperty().bind(Info.username);
        startCallsAndChatsListeners();
    }

    private void startCallsAndChatsListeners() {
        ChatListener chatListener = new ChatListener(this);
        CallListener callListener = new CallListener();
        Thread callListenerThread = new Thread(() -> {
            while (true) {
                callListener.listenCallsRequests();
            }
        });
        Thread callThread = new Thread(() -> {
//            while (true) {
            callListener.listenForIncomingCalls();
            if (Info.Call.isInCall()) {

            }
        });

        callThread.setDaemon(true);
        callListenerThread.setDaemon(true);
        chatListener.setDaemon(true);

        chatListener.start();
        callListenerThread.start();
        callThread.start();
    }

    private void loadStoredChats() {
        if (Files.exists(Info.profilePictureFile)) {
            System.out.println(Info.profilePictureFile.toString());
            Image img = new Image("file:"+Info.profilePictureFile.toString());
            circle.setFill(new ImagePattern(img));
        } else {
           String file="file:src/main/resources/app/javachat/images/default.png";
            circle.setFill(new ImagePattern(new Image(file)));
        }
        circle.setEffect(new DropShadow(25d, 0d, 0d, Color.GRAY));

        List<ChatInfo> chats = Info.chatInfoList;
        chats.forEach(info -> {
            ChatRequest chatRequest = new ChatRequest(info.getUser(), true);
            chatRequest.setChatPort(info.getOtherChatPort());
            Chat chat = new SimpleChat(chatRequest, info.getLocalChatPort());
            ChatItem chatItem = new ChatItem(chat, info);
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
