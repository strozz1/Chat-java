package app.javachat.Controllers.ViewControllers;

import app.javachat.Utilities.Info;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ChangeUserSettingsViewController {

    @FXML
    private TextField inputTextChangeUsername;

    public void onSaveBtn(MouseEvent mouseEvent) {
        changeUsername(mouseEvent);
    }

    private void closeWindow(Event event) {
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();
    }

    public void onKeyPressed(KeyEvent event) {
            if (event.getCode() == KeyCode.ENTER)
                changeUsername(event);


    }
    private void changeUsername(Event event) {
        String newUsername = inputTextChangeUsername.getText();
        Info.localUser.setUsername(newUsername);
        Info.username.setValue(newUsername);
        closeWindow(event);
    }
}
