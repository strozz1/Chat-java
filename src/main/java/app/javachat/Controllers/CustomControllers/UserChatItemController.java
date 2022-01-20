package app.javachat.Controllers.CustomControllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class UserChatItemController {
    @FXML
    void initialize(){
        onChatClicked.setOnMouseClicked(mouseEvent -> {
            System.out.println("a");
        });
    }

    @FXML
    private Label leftmenuUserLabel;
    @FXML
    private HBox onChatClicked;


    public Label getLeftmenuUserLabel() {
        return leftmenuUserLabel;
    }
}
