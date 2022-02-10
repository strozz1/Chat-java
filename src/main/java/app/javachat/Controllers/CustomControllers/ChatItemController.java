package app.javachat.Controllers.CustomControllers;

import app.javachat.Calls.Call;
import app.javachat.Calls.CallRequest;
import app.javachat.Chats.Chat;
import app.javachat.Controllers.ViewControllers.CallWindowController;
import app.javachat.Controllers.ViewControllers.IncomingCallViewController;
import app.javachat.Logger.Log;
import app.javachat.MainApplication;
import app.javachat.Models.ChatInfo;
import app.javachat.Models.Message;
import app.javachat.Models.User;
import app.javachat.Utilities.Info;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalDateTime;

import static app.javachat.Utilities.Info.Call.startCallWindow;


public class ChatItemController {
    private int callListenerPort;
    private User otherUser;
    @FXML
    private TextField chatInput;
    @FXML
    private VBox chatBox;
    @FXML
    private ScrollPane scroll;
    @FXML
    private Label headerUsername;
    @FXML
    private Button btnLlamar, btnSendMessage;
    private Chat chat;
    private Stage callWindow;
    private boolean notCalled;

    public ChatItemController() {
    }

    public ChatItemController(Chat chat, ChatInfo chatInfo) {
        this.otherUser = chatInfo.getUser();
        this.chat = chat;
        this.callListenerPort = chatInfo.getCallListenerPort();
    }

    @FXML
    void initialize() {
        headerUsername.setText(otherUser.getUsername());
        btnLlamar.setOnMouseClicked(mouseEvent -> {
            if (!Info.Call.isInCall())
                sendCallRequest();
        });
        btnSendMessage.setOnMouseClicked(mouseEvent -> {
            onSendMessage();
        });
        chatInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                onSendMessage();
        });
        chatBox.heightProperty().addListener((observableValue, number, t1) -> {
            scroll.setVvalue((Double) t1);
        });
    }

    private void sendCallRequest() {
        try {
            Socket socket = new Socket(otherUser.getIP(), callListenerPort);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(new CallRequest(true, false, false));
            outputStream.close();
        } catch (IOException e) {
            Log.error(e.getMessage());
        }

    }

    private void onSendMessage() {
        String message = chatInput.getText();
        new Thread(() -> {
            sendNewMessage(message);
        }).start();
        chatInput.setText("");
    }

    private void sendNewMessage(String message) {
        Message msg = new Message(message, Info.localUser, LocalDateTime.now());
        Platform.runLater(() -> chatBox.getChildren().add(new MessageItem(msg, true)));
        chat.sendMessage(msg);

    }

    public void startListeningForMessages() {
        Thread thread = new Thread(() -> {
            while (true) {
                Message message = chat.receiveMessage();
                if (message != null)
                    Platform.runLater(() -> chatBox.getChildren().add(new MessageItem(message, false)));
            }

        });
        thread.setDaemon(true);
        thread.start();
    }

    public Chat getChat() {
        return chat;
    }
}
