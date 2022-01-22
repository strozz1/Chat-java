package app.javachat.Controllers.CustomControllers;

import app.javachat.MainApplication;
import app.javachat.Models.SimpleChat;
import app.javachat.Models.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ChatItem extends BorderPane {
    private ChatItemController controller;

    public ChatItem() {
        super();


        load();
    }
    public ChatItem(SimpleChat chat, User otherUser) {
        super();
        load();
        controller.setChat(chat);
        controller.setOtherUser(otherUser);
        controller.startListeningForMessages();
    }
    private void load() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("chat-item.fxml"));
            //Create new controller
            controller = new ChatItemController();
            loader.setController(controller);
            Node node = loader.load();

            this.setCenter(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ChatItemController getController() {
        return controller;
    }

}
