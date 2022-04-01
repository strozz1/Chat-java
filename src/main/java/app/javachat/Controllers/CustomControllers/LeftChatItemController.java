package app.javachat.Controllers.CustomControllers;

import app.javachat.Controllers.ViewControllers.MainController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;


public class LeftChatItemController{

    private MainController mainController;
    private static BorderPane parent;
    private String user;
    private ChatItem chatItem;
    @FXML
    private Label leftmenuUserLabel;
    @FXML
    private HBox leftChatContainer;

    public LeftChatItemController() {
    }

    public LeftChatItemController(ChatItem chatItem) {
        this.chatItem = chatItem;
        this.user = chatItem.getController().getUsername();
    }


    @FXML
    void initialize() {

        leftmenuUserLabel.setText(user);
        leftChatContainer.setOnMouseClicked(mouseEvent -> {

            //Cargar Parent
            parent = mainController.getParent();
            parent.setCenter(chatItem);

        });
    }

    public void setMainController(MainController controller) {
        this.mainController = controller;
    }

    public ChatItem getChatItem() {
        return chatItem;
    }
}
