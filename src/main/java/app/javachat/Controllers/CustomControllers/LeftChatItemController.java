package app.javachat.Controllers.CustomControllers;

import app.javachat.Controllers.ViewControllers.MainController;
import app.javachat.Models.Chat;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class LeftChatItemController {

    private MainController mainController;
    private static BorderPane parent;
    private Chat chat;


    @FXML
    void initialize(){


        onChatClicked.setOnMouseClicked(mouseEvent -> {
            //Cargar Parent
          parent= mainController.getChatContainer();
          parent.setCenter(chat.getChatItem());

        });
    }

    @FXML
    private Label leftmenuUserLabel;
    @FXML
    private HBox onChatClicked;


    public Label getLeftmenuUserLabel() {
        return leftmenuUserLabel;
    }
    public void setMainController(MainController controller) {
        this.mainController = controller;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
