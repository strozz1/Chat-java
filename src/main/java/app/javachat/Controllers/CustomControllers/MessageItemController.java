package app.javachat.Controllers.CustomControllers;

import app.javachat.Models.Message;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MessageItemController {

    private boolean isMine;
    private Message message;

    @FXML
    private Label labelDate,labelUser,labelMessage;

    @FXML
    void initialize(){
        labelDate.setText(message.getHoraEnvio().toString());
        labelUser.setText(message.getSender().getUsername());
        labelMessage.setText(message.getContent());
    }

    public MessageItemController() {
    }

    public MessageItemController(Message message, boolean isMine) {
        this.message= message;
        this.isMine=isMine;
    }


}
