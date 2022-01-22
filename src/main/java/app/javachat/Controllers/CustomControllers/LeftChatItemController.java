package app.javachat.Controllers.CustomControllers;

import app.javachat.Controllers.ViewControllers.MainController;
import app.javachat.Models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;


public class LeftChatItemController {

    private MainController mainController;
    private static BorderPane parent;
    private User otherUser;
    private ChatItem chatItem;
    @FXML
    private Label leftmenuUserLabel;
    @FXML
    private HBox leftChatContainer;

    public LeftChatItemController() {
    }

    public LeftChatItemController(ChatItem chatItem, User otherUser) {
        this.chatItem = chatItem;
        this.otherUser = otherUser;
    }


    @FXML
    void initialize() {

        leftmenuUserLabel.setText(otherUser.getUsername());
        leftChatContainer.setOnMouseClicked(mouseEvent -> {

            //Cargar Parent
            parent = mainController.getParent();
            parent.setCenter(chatItem);

        });
    }

    public void setMainController(MainController controller) {
        this.mainController = controller;
    }

}
