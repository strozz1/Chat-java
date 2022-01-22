package app.javachat.Controllers.CustomControllers;

import app.javachat.Controllers.ViewControllers.CallWindowController;
import app.javachat.Utilities.Info;
import app.javachat.MainApplication;
import app.javachat.Chats.Chat;
import app.javachat.Models.Message;
import app.javachat.Chats.SimpleChat;
import app.javachat.Models.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;


public class ChatItemController {
    private User otherUser;
    @FXML
    private TextField chatInput;
    @FXML
    private VBox chatBox;
    @FXML
    private Label headerUsername;
    @FXML
    private Button btnLlamar, btnSendMessage;
    private Chat chat;

    public ChatItemController() {
    }

    public ChatItemController(SimpleChat chat, User otherUser) {
        this.otherUser = otherUser;
        this.chat=chat;
    }

    @FXML
    void initialize() {
        headerUsername.setText(otherUser.getUsername());
        btnLlamar.setOnMouseClicked(mouseEvent -> {
            startCallWindow();
        });
        btnSendMessage.setOnMouseClicked(mouseEvent -> {
            String message = chatInput.getText();
            new Thread(()->{
                sendNewMessage(message);
            }).start();
            chatInput.setText("");
        });
    }


    private void startCallWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("call-window.fxml"));

            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false);
            stage.setTitle("Llamada con $usuario");

            CallWindowController callWindowController = new CallWindowController();
            loadDataToCallController(callWindowController);
            loader.setController(callWindowController);
            Parent root = loader.load();


            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendNewMessage(String message) {
        Message msg = new Message(message, Info.localUser, LocalDateTime.now());
        Platform.runLater(() ->chatBox.getChildren().add(new MessageItem(msg,true)));
        chat.sendMessage(msg);

    }

    private void loadDataToCallController(CallWindowController callWindowController) {
        //Set client user and the other line user
        callWindowController.setLocalUser(Info.localUser);
        callWindowController.setOtherUser(otherUser);
    }



    public void startListeningForMessages() {
        Thread thread = new Thread(() -> {
            while (true) {
                Message message = chat.receiveMessage();
                if (message != null)
                    Platform.runLater(() -> chatBox.getChildren().add(new MessageItem(message,false)));
            }

        });
        thread.setDaemon(true);
        thread.start();
    }
}
