package app.javachat.Controllers.CustomControllers;

import app.javachat.Models.Message;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class MessageItemController {

    private boolean isMine;
    private Message message;
    @FXML
    private Text labelMessage;
    @FXML
    private Label labelDate, labelUser;
    @FXML
    private VBox messageVBox;

    public VBox getMessageVBox() {
        return messageVBox;
    }

    public MessageItemController() {
    }

    public MessageItemController(Message message, boolean isMine) {
        this.message = message;
        this.isMine = isMine;
    }

    @FXML
    void initialize() {
        if (isMine) {

            messageVBox.nodeOrientationProperty().set(NodeOrientation.LEFT_TO_RIGHT);
        } else {
            messageVBox.nodeOrientationProperty().set(NodeOrientation.RIGHT_TO_LEFT);
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm - d MMM");
        labelDate.setText(message.getHoraEnvio().format(format));
        labelUser.setText(message.getSender().getUsername());
        labelMessage.setText(message.getContent());
    }
}
