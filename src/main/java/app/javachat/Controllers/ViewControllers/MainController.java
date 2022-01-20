package app.javachat.Controllers.ViewControllers;

import app.javachat.Controllers.CustomControllers.UserChatItemControl;
import app.javachat.Controllers.SalaCliente;
import app.javachat.Llamadas.Call;
import app.javachat.Logger.ConsoleType;
import app.javachat.Logger.Log;
import app.javachat.Logger.WindowLogType;
import app.javachat.MainApplication;
import app.javachat.Models.Mensaje;
import app.javachat.Models.SalaModel;
import app.javachat.Models.User;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    private List<UserChatItemControl> chatObjectList;
    private Boolean isLightMode = true;
    private SalaCliente sala;
    private User localUser, otherUser = new User("Alberto Jimenez"); // Temporal user

    @FXML
    private Button btnLog;
    @FXML
    private VBox lateralMenu;
    @FXML
    private VBox paneChat;
    @FXML
    private TextField chatInput;

    @FXML
    private BorderPane parent;


    public MainController() throws IOException {
        chatObjectList = new ArrayList<>();
    }


    @FXML
    void initialize(){
    }


    public SalaModel changeViewToAddOrJoinServer() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("add-server-view.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(loader.load());
        AddServerController addServerController = loader.getController();
        stage.setScene(scene);
        stage.showAndWait();
        localUser = addServerController.getUser();
        SalaModel salaModel = addServerController.getSalaModel();
        return salaModel;
    }

    public void recibirMensajes(VBox lv) {
        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                Boolean isMyMessage;
                Mensaje mensaje = (Mensaje) sala.recibirMensaje();
                isMyMessage = mensaje.getSender().equals(localUser);

                Label label = new Label(mensaje.toString());

                if (isMyMessage)
                    label.setTextFill(Color.RED);
                Platform.runLater(() -> lv.getChildren().add(label));
                recibirMensajes(lv);
                return null;

            }

        };
        new Thread(task).start();


    }


    public void onSendMensaje(MouseEvent mouseEvent) {
        String mensaje = chatInput.getText();
        Mensaje msg = new Mensaje(mensaje, localUser, LocalDateTime.now());
        Task task2 = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                sala.enviarMensaje(msg);
                return null;
            }
        };
        (new Thread(task2)).start();
    }

    public void getButtonLLamar(ActionEvent actionEvent) {

        startCallWindow();

    }

    private void startCallWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("call-window.fxml"));

            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false);
            stage.setTitle("Llamada con $usuario");

            CallWindowController callWindowController = new CallWindowController();
            loadDataToCallController(callWindowController);
            loader.setController(callWindowController);
            Parent root =loader.load();


            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDataToCallController(CallWindowController callWindowController) {
        //Set client user and the other line user
        callWindowController.setLocalUser(localUser);
        callWindowController.setOtherUser(otherUser);
    }

    public void onAddChat(MouseEvent mouseEvent) throws IOException {
        SalaModel salaModel = changeViewToAddOrJoinServer();
        sala = new SalaCliente(salaModel, localUser);
        recibirMensajes(paneChat);
        UserChatItemControl userChatItemControl = new UserChatItemControl();
        chatObjectList.add(userChatItemControl);
        lateralMenu.getChildren().addAll(chatObjectList);

    }
}
