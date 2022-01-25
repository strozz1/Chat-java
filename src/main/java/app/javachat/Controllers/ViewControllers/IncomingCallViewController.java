package app.javachat.Controllers.ViewControllers;

import app.javachat.Calls.Call;
import app.javachat.Calls.CallRequest;
import app.javachat.MainApplication;
import app.javachat.Models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class IncomingCallViewController {
    private Call call;
    private User user;
    @FXML
    private Label incomingCallUser;
    @FXML
    private Button acceptCallButton,cancelCallButton;
    private Stage callWindow;

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
            Thread thread= new Thread(() -> {
                startCallWindow();
                call.acceptCall();
                call.startCall();
            });
            thread.start();
        });

        cancelCallButton.setOnMouseClicked(event ->{
            call.callCanceled();
            closeWindow(event);

        });
    }
    private void startCallWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("call-window.fxml"));

            callWindow = new Stage();
            callWindow.initModality(Modality.WINDOW_MODAL);
            callWindow.setResizable(false);
            callWindow.setTitle("Llamada con " + call.getOtherUser().getUsername());

            CallWindowController callWindowController = new CallWindowController(call);
            loader.setController(callWindowController);
            Parent root = loader.load();


            Scene scene = new Scene(root);
            callWindow.setScene(scene);
            callWindow.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }    private void closeWindow(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();
    }

}
