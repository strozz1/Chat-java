package app.javachat.Controllers.CustomControllers;

import app.javachat.Calls.Call;
import app.javachat.Calls.CallRequest;
import app.javachat.Chats.Chat;
import app.javachat.Controllers.ViewControllers.CallWindowController;
import app.javachat.Controllers.ViewControllers.IncomingCallViewController;
import static app.javachat.Utilities.Info.getMapFromJson;

import app.javachat.Logger.Log;
import app.javachat.MainApplication;
import app.javachat.Models.ChatInfo;
import app.javachat.Models.Message;
import app.javachat.Models.User;
import app.javachat.SimpleRoom;
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
import java.util.HashMap;

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
    private SimpleRoom room;
    private Stage callWindow;
    private boolean notCalled;

    public ChatItemController() {
    }

    public ChatItemController(SimpleRoom room) {
        this.room = room;
    }

    @FXML
    void initialize() {
        headerUsername.setText(room.getUsername());
        btnLlamar.setOnMouseClicked(mouseEvent -> {
            if (!Info.Call.isInCall())
                new Thread(this::sendCallRequest).start();
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
            Log.show("Sending call request at"+otherUser.getIP()+":"+ callListenerPort,"ChatItemController");

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
//        Message msg = new Message(message, Info.localUser, );
//        Platform.runLater(() -> chatBox.getChildren().add(new MessageItem(msg, true)));
//        chat.sendMessage(msg);

    }


    public void addMessage(HashMap<String, Object> msg) {
        room.addMessage(msg);
        System.out.println(msg);
        Message message= new Message((String) msg.get("content"), (String) msg.get("sender"),"ahora");
        Platform.runLater(() -> chatBox.getChildren().add(new MessageItem(message, false)));
    }

    public String getUsername() {
        return room.getUsername();
    }
}
