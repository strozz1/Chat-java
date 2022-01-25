package app.javachat.Controllers.ViewControllers;

import app.javachat.Calls.Call;
import app.javachat.Calls.CallRequest;
import app.javachat.Models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class IncomingCallViewController {
    private Call call;
    private User user;
    @FXML
    private Label incomingCallUser;
    @FXML
    private Button acceptCallButton,cancelCallButton;

    public IncomingCallViewController() {
    }

    public IncomingCallViewController(Call call) {
        this.user=call.getOtherUser();
        this.call=call;
    }

    @FXML
    void initialize(){
        incomingCallUser.setText(user.getUsername());
        acceptCallButton.setOnMouseClicked(event ->{
            call.acceptCall();
            call.connect();
        });

        cancelCallButton.setOnMouseClicked(event ->{
            call.callFailed();
        });
    }

}
