package app.javachat.Controllers.ViewControllers;

import app.javachat.Utilities.Info;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ChangeUserSettingsViewController {

    @FXML
    private TextField inputTextChangeUsername;


    public void onSaveBtn(MouseEvent mouseEvent) {
        String newUsername = inputTextChangeUsername.getText();
        Info.localUser.setUsername(newUsername);
        closeWindow(mouseEvent);
    }
    private void closeWindow(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();
    }
}
