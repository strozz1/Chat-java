package app.javachat.Controllers;

import app.javachat.Models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CallWindowController {
    private User localUser,otherUser;

    @FXML
    private Label otherUserLabel;

    @FXML
    void initialize() {

        setOtherUserLabel();


    }

    private void setOtherUserLabel() {
        otherUserLabel.setText(otherUser.getUsername());

    }

    public void setLocalUser(User user){
        this.localUser=user;
    }
    public void setOtherUser(User user){
        this.otherUser=user;
    }
}
