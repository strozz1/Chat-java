package app.javachat.Controllers;

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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;

public class ChatController {
    private SalaCliente sala;
    private User user;

    @FXML
    private Button btnLog;
    @FXML
    private ListView contenedorMensajes;
    @FXML
    private TextField chatInput;

    @FXML
    private BorderPane parent;



    @FXML
    void initialize() throws IOException {
        SalaModel salaModel = changeViewToAddOrJoinServer();
        sala = new SalaCliente(salaModel, user);
        recibirMensajes(contenedorMensajes);
    }



    public SalaModel changeViewToAddOrJoinServer() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("add-server-view.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(loader.load());
        AddServerController addServerController = loader.getController();
        stage.setScene(scene);
        stage.setOnCloseRequest(e->{
            Platform.exit();
            System.exit(1);
        });
        stage.showAndWait();
        user = addServerController.getUser();
        SalaModel salaModel = addServerController.getSalaModel();
        return salaModel;
    }

    public void recibirMensajes(ListView lv) {
        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                Boolean isMyMessage;
                Mensaje mensaje = (Mensaje) sala.recibirMensaje();
                isMyMessage = mensaje.getSender().equals(user);

                Label label = new Label(mensaje.toString());

                if (isMyMessage)
                    label.setTextFill(Color.RED);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        lv.getItems().add(label);
                    }

                });
                if (!chatInput.isFocused())
                    notificationMessage(mensaje);
                recibirMensajes(lv);
                return null;

            }

        };
        new Thread(task).start();


    }


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

    public void notificationMessage(Mensaje mensaje) {
        try {
            //Obtain only one instance of the SystemTray object
            SystemTray tray = SystemTray.getSystemTray();

            // If you want to create an icon in the system tray to preview
            Image image = Toolkit.getDefaultToolkit().createImage("some-icon.png");
            //Alternative (if the icon is on the classpath):
            //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

            TrayIcon trayIcon = new TrayIcon(image, "Java AWT Tray Demo");
            //Let the system resize the image if needed
            trayIcon.setImageAutoSize(true);
            //Set tooltip text for the tray icon
            trayIcon.setToolTip("System tray icon demo");
            tray.add(trayIcon);

            // Display info notification:
            trayIcon.displayMessage("Nuevo mensaje de " + mensaje.getSender().getUsername(), mensaje.getContent(), TrayIcon.MessageType.INFO);
            // Error:
            // trayIcon.displayMessage("Hello, World", "Java Notification Demo", MessageType.ERROR);
            // Warning:
            // trayIcon.displayMessage("Hello, World", "Java Notification Demo", MessageType.WARNING);
        } catch (Exception ex) {
            System.err.print(ex);
        }
    }

    private Boolean isLightMode = true;

    public void onThemeButtonClicked(MouseEvent mouseEvent) {
        if (isLightMode)
            changeToDarkMode();
        else
            changeToLightMode();

    }


    private void changeToLightMode() {
        parent.getStylesheets().remove(MainApplication.class.getResource("darkMode-style.css").toExternalForm());
        parent.getStylesheets().add(MainApplication.class.getResource("lightMode-style.css").toExternalForm());
//        javafx.scene.image.Image image = new javafx.scene.image.Image("src/main/java/app/javachat/img/lightIconTheme.png");
//        themeImage.setImage(image);
        isLightMode = !isLightMode;

    }

    private void changeToDarkMode() {
        parent.getStylesheets().remove(MainApplication.class.getResource("lightMode-style.css").toExternalForm());
        parent.getStylesheets().add(MainApplication.class.getResource("darkMode-style.css").toExternalForm());
//        javafx.scene.image.Image image = new javafx.scene.image.Image("src/main/java/app/javachat/img/darkIconTheme.png");
//        themeImage.setImage(image);
        isLightMode = !isLightMode;
    }


    public void onLogPressed(ActionEvent actionEvent) throws IOException {
        loadLog();
        btnLog.setDisable(true);
    }

    private void loadLog() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("logger-window.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(loader.load());
        LoggerController controller = loader.getController();
        LoggerControllerRetriever.setLoggerController(controller);
        Log.setLoggerType(new WindowLogType());
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(windowEvent -> {
            Log.setLoggerType(new ConsoleType());
            btnLog.setDisable(false);
            stage.close();
        });
    }


}
