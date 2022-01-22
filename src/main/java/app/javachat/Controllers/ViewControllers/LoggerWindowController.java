package app.javachat.Controllers.ViewControllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;

public class LoggerWindowController {
    public enum MSG_STATES {SERVER, CLIENT, INFO}

    ;
    @FXML
    private ListView loggerList;

    public void addLog(String msg) {
        // Para poder cambiar la gui desde otro Thread, debemos usar este metodo.
        Platform.runLater(() -> {
            loggerList.getItems().add(msg);

        });
    }

    public void addLog(String msg, String sender) {
        // Para poder cambiar la gui desde otro Thread, debemos usar este metodo.
        Platform.runLater(() -> {
            Label label = new Label();
            if (sender.equals(MSG_STATES.SERVER.toString())) {
                label.setTextFill(Color.RED);
                label.setText("SERVER: " + msg);
            } else if (sender.equals(MSG_STATES.CLIENT.toString())) {
                label.setTextFill(Color.BLUE);
                label.setText("CLIENT: " + msg);

            } else {
                label.setTextFill(Color.GRAY);
                label.setText("INFO: " + msg);
            }
            loggerList.getItems().add(label);

        });
    }





}
