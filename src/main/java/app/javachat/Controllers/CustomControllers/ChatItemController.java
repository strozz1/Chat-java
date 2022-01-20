package app.javachat.Controllers.CustomControllers;

import app.javachat.Controllers.ViewControllers.CallWindowController;
import app.javachat.MainApplication;
import app.javachat.Models.Chat;
import app.javachat.Models.Message;
import app.javachat.Models.User;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;


public class ChatItemController {
    private User localUser, otherUser = new User("Alberto Jimenez"); // Temporal user
    @FXML
    private TextField chatInput;
    @FXML
    private VBox chatBox;
    @FXML
    private Label headerUsername;
    @FXML
    private Button btnLlamar,btnSendMessage;

    @FXML
    void initialize() {
        btnLlamar.setOnMouseClicked(mouseEvent -> {
            startCallWindow();
        });
        btnSendMessage.setOnMouseClicked(mouseEvent -> {
            sendNewMessage();
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

    private void sendNewMessage() {
        String mensaje = chatInput.getText();
        Message msg = new Message(mensaje, localUser, LocalDateTime.now());
        //Todo
    }


    public void onSendMensaje(MouseEvent mouseEvent) {

        Task task2 = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
//                sala.enviarMensaje(msg);
                return null;
            }
        };
        (new Thread(task2)).start();
    }


    private void loadDataToCallController(CallWindowController callWindowController) {
        //Set client user and the other line user
        callWindowController.setLocalUser(localUser);
        callWindowController.setOtherUser(otherUser);
    }

    public Label getHeaderUsername() {
        return headerUsername;
    }


    public VBox getChatBox() {
        return chatBox;
    }
}
