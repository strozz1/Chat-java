package app.javachat.Controllers.CustomControllers;

import app.javachat.Calls.Call;
import app.javachat.Calls.CallRequest;
import app.javachat.Calls.CallResponse;
import app.javachat.Chats.Chat;
import app.javachat.Chats.ChatListener;
import app.javachat.Controllers.ViewControllers.CallWindowController;
import app.javachat.Controllers.ViewControllers.IncomingCallViewController;
import app.javachat.Logger.Log;
import app.javachat.MainApplication;
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
import java.net.Socket;
import java.time.LocalDateTime;


public class ChatItemController {
    private int callListenerPort;
    private Call call;
    private User otherUser;
    private Chat chat;
    private Stage callWindow;
    private boolean notCalled;

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

    public ChatItemController() {
    }

    public ChatItemController(Chat chat, User otherUser, int callListenerPort) {
        this.callListenerPort= callListenerPort;
        this.otherUser = otherUser;
        this.chat = chat;
    }

    @FXML
    void initialize() {
        headerUsername.setText(otherUser.getUsername());
        btnLlamar.setOnMouseClicked(mouseEvent -> {
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
        CallRequest callRequest = ChatListener.sendCallRequest(otherUser.getIP(), callListenerPort, new CallRequest());
        if(callRequest.isUserFree()){
            Call call = new Call(Info.Call.getAvailableCallPort(),callRequest.getSender(),callRequest.getCallPort());
            call.sendCallRequest(true,false);
            this.call=call;
            startListeningForCalls();
        }
    }

    private void onSendMessage() {
        String message = chatInput.getText();
        new Thread(() -> {
            sendNewMessage(message);
        }).start();
        chatInput.setText("");
    }


    private void startCallWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("call-window.fxml"));

            callWindow = new Stage();
            callWindow.initModality(Modality.WINDOW_MODAL);
            callWindow.setResizable(false);
            callWindow.setTitle("Llamada con " + otherUser.getUsername());

            CallWindowController callWindowController = new CallWindowController(call);
            loader.setController(callWindowController);
            Parent root = loader.load();


            Scene scene = new Scene(root);
            callWindow.setScene(scene);
            callWindow.setAlwaysOnTop(true);
            callWindow.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
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


    public void startListeningForCalls() {
        Thread thread = new Thread(() -> {
            notCalled = true;
            while (notCalled) {
                Log.show("Escuchando posibles llamadas");
                CallResponse callResponse = call.listenForIncomingCalls();
                Log.show("llamada encontrada");
                if (callResponse != null) {
                    // Si el otro es el que inicia, creamos la ventana de nueva llamada
                    if (!callResponse.isResponse()) {
                        Platform.runLater(() -> createIncomingCallWindow(call));
                    } else {
                        if (callResponse.isAccept()) {
                            Platform.runLater(() -> startCallWindow());
                        } else callWindow.close();

                    }
                    notCalled = false;
                }
            }

        });
        thread.setDaemon(true);
        thread.start();
    }


    private void createIncomingCallWindow(Call call) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("incoming-call-view.fxml"));
            IncomingCallViewController controller = new IncomingCallViewController(call);
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Chat getChat() {
        return chat;
    }
}
