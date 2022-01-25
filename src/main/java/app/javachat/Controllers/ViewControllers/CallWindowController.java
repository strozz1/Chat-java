package app.javachat.Controllers.ViewControllers;

import app.javachat.Calls.Call;
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


    public CallWindowController(Call call) {
        this.call=call;
        otherUser=call.getOtherUser();
    }

    @FXML
    void initialize() {
        //Configurar UI
        setOtherUserLabel();
        //Iniciar la llamada
        call.startCall();
    }


    private void cerrarVentana() {
        Platform.runLater(() -> {
        Stage stage = (Stage) labelTiempoLlamada.getScene().getWindow();
        stage.close();
        });
    }

    private void setOtherUserLabel() {
        labelTiempoLlamada.setText("Tiempo llamada 00:00:12");
        otherUserLabel.setText(otherUser.getUsername());
    }

}
