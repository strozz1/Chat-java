package app.javachat.Controllers.ViewControllers;

import app.javachat.Calls.Call;
import app.javachat.Logger.Log;
import app.javachat.Models.User;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.LocalTime;

public class CallWindowController {
    private User localUser, otherUser;
    private Call call;
    @FXML
    private Button btnEndCall;
    @FXML
    private Label otherUserLabel;
    @FXML
    private Label labelTiempoLlamada;
    public static StringProperty timer=new SimpleStringProperty("00:00:00");
    private boolean isCallOnline=false;


    public CallWindowController() {

    }


    public CallWindowController(Call call) {
        this.call = call;
        call.startCall();
        otherUser = call.getOtherUser();
    }

    @FXML
    void initialize() {
        startCallTimer();
        otherUserLabel.setText(otherUser.getUsername());

        labelTiempoLlamada.textProperty().bind(timer);

        btnEndCall.setOnMouseClicked(event -> {
            call.endCall();
            cerrarVentana();
            stopTimer();
        });
    }


    private void cerrarVentana() {
        Platform.runLater(() -> {
            Stage stage = (Stage) labelTiempoLlamada.getScene().getWindow();
            stage.close();
        });
    }

    public void closeWindow() {

        Stage thisStage = (Stage) btnEndCall.getScene().getWindow();
        thisStage.close();
    }

    void startCallTimer() {
        isCallOnline=true;
       Thread thread= new Thread(this::timer);
       thread.start();
    }

    void stopTimer(){
        isCallOnline=false;
    }

    void timer() {
                LocalTime of = LocalTime.of(00, 00, 00);
        while(isCallOnline) {
            try {
                String info = "Tiempo de llamada  " + of.toString();
                Platform.runLater(()->timer.setValue(info));
                Thread.sleep(1000);
                of = of.plusSeconds(1);
            } catch (InterruptedException e) {
                Log.error(e.getMessage());
            }
        }
    }
}
