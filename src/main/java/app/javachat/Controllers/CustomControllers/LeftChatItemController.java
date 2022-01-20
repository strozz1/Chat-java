package app.javachat.Controllers.CustomControllers;

import app.javachat.Controllers.ViewControllers.MainController;
import app.javachat.Models.Chat;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class LeftChatItemController {

    private static MainController controller;
    private static BorderPane parent;
    private Chat chat;

    @FXML
    void initialize(){

        //Start Listening
        chat.start();
        onChatClicked.setOnMouseClicked(mouseEvent -> {
            //Cargar Parent
            getChatVBox();
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

    public static void getChatVBox() {
        FXMLLoader loader = new FXMLLoader(MainController.class.getResource("main-view.fxml"));
        controller = loader.getController();
        parent= controller.getChatContainer();

    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
