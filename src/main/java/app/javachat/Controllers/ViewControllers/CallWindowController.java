package app.javachat.Controllers.ViewControllers;

import app.javachat.Calls.Call;
import app.javachat.Calls.States.DisconnectedCallState;
import app.javachat.Models.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CallWindowController {
    private User localUser,otherUser;
    private Call call;

    @FXML
    private Label otherUserLabel;
    @FXML
    private Label labelTiempoLlamada;

    public CallWindowController() {

    }
    public CallWindowController(int selfPort,int otherPort) {
        this.call=new Call(selfPort,otherPort);
    }

    @FXML
    void initialize() {
        //Configurar UI
        setOtherUserLabel();
        //Iniciar la llamada
        Thread hiloLLamada= new Thread(this::startCall);
        hiloLLamada.start();

    }

    private void startCall() {
        call.startCall();
        call.waitResponse();
        if(call.getState() instanceof DisconnectedCallState){
            cerrarVentana();
        }
    }

    private void cerrarVentana() {
        Platform.runLater(() -> {
        Stage stage = (Stage) labelTiempoLlamada.getScene().getWindow();
        stage.close();
        });
    }

    private void setOtherUserLabel() {
        labelTiempoLlamada.setText("Esperando a la llamada...");
        otherUserLabel.setText(otherUser.getUsername());

    }

    public void setLocalUser(User user){
        this.localUser=user;
        call.setLocalUser(user);
    }
    public void setOtherUser(User user){
        this.otherUser=user;
        call.setOtherUser(user);

    }

}
