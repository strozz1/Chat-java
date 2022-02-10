package app.javachat.Controllers.ViewControllers;

import app.javachat.MainApplication;
import app.javachat.Models.User;
import app.javachat.Utilities.Info;
import javafx.application.Platform;
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
    private User user;
    @FXML
    private Label incomingCallUser;
    @FXML
    private Button acceptCallButton, cancelCallButton;
    private Stage callWindow;

    public IncomingCallViewController() {
        user= Info.Call.getLocalCall().getOtherUser();
    }


    @FXML
    void initialize() {
        incomingCallUser.setText(user.getUsername());
        acceptCallButton.setOnMouseClicked(event -> {
            Thread thread = new Thread(() -> {
                Info.Call.getLocalCall().acceptCall();
                Platform.runLater(this::startCallWindow);
                Info.Call.setInCall(true);
            });
            thread.start();
            closeWindow(event);
        });

        cancelCallButton.setOnMouseClicked(event -> {
            Info.Call.getLocalCall().callCanceled();
            closeWindow(event);


        });
    }

    private void startCallWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("call-window.fxml"));

            callWindow = new Stage();
            callWindow.initModality(Modality.WINDOW_MODAL);
            callWindow.setResizable(false);
            callWindow.setTitle("Llamada con " + Info.Call.getLocalCall().getOtherUser().getUsername());

            CallWindowController callWindowController = new CallWindowController();
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

    private void closeWindow(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();
    }

}
