package app.javachat.Controllers;

import app.javachat.MainApplication;
import app.javachat.Models.Mensaje;
import app.javachat.Models.SalaModel;
import app.javachat.Models.User;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDateTime;

public class ChatController {
    private SalaCliente sala;
    private User user;

    public SalaModel changeView() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("add-server-view.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(loader.load());
        AddServerController addServerController = loader.getController();
        stage.setScene(scene);
        stage.showAndWait();
        user = addServerController.getUser();
        SalaModel salaModel = addServerController.getSalaModel();
        return salaModel;
    }


    @FXML
    void initialize() throws IOException {
        SalaModel salaModel = changeView();
        sala = new SalaCliente(salaModel, user);
        recibirMensajes(contenedorMensajes);
    }

    public void recibirMensajes(ListView lv) {
        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                Boolean isMyMessage;
                Mensaje mensaje = (Mensaje) sala.recibirMensaje();
                isMyMessage = mensaje.getSender().equals(user);
                Label label = new Label(mensaje.toString());
                if(isMyMessage)
                label.setTextFill(Color.RED);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        lv.getItems().add(label);
                    }

                });
                recibirMensajes(lv);
                return null;
            }

        };
        new Thread(task).start();


    }


    @FXML
    private ListView contenedorMensajes;
    @FXML
    private TextField chatInput;

    public void onSendMensaje(MouseEvent mouseEvent) {
        String mensaje = chatInput.getText();
        Mensaje msg = new Mensaje(mensaje, user, LocalDateTime.now());
        Task task2 = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                sala.enviarMensaje(msg);
                return null;
            }
        };
        (new Thread(task2)).start();
    }
}
