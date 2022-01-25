package app.javachat.Controllers.CustomControllers;

import app.javachat.Models.Message;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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

    @FXML
    void initialize() {
        if (isMine) {

            messageVBox.nodeOrientationProperty().set(NodeOrientation.LEFT_TO_RIGHT);
        } else {
            messageVBox.nodeOrientationProperty().set(NodeOrientation.RIGHT_TO_LEFT);
        }
        labelDate.setText(message.getHoraEnvio().toString());
        labelUser.setText(message.getSender().getUsername());
        labelMessage.setText(message.getContent());
    }

    public MessageItemController() {
    }

    public MessageItemController(Message message, boolean isMine) {
        this.message = message;
        this.isMine = isMine;
    }


}
