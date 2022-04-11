package app.javachat.Controllers.ViewControllers;

import app.javachat.Logger.Log;
import app.javachat.Utilities.MessageSenderService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class AddRoomController {

    @FXML
    private TextField inputRoomName, inputUsersList;

    public void onAddNewChat(MouseEvent mouseEvent) {
        createNewRoom(mouseEvent);
    }


    public void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)
            createNewRoom(event);
    }

    private void closeWindow(Event event) {
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();
    }

    private void createNewRoom(Event mouseEvent) {
        String groupName = inputRoomName.getText();
        String[] userList = inputUsersList.getText().split(",");
        MessageSenderService.sendRoomCreation(groupName, userList);
        closeWindow(mouseEvent);
        Log.show("New room created.", "AddRoomController");
    }


}
