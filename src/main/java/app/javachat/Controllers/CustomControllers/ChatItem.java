package app.javachat.Controllers.CustomControllers;

import app.javachat.MainApplication;
import app.javachat.Models.SimpleChat;
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
    public ChatItem(SimpleChat chat) {
        super();
        load();
        controller.setChat(chat);
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

    public void setUsername(String name){
        controller.getHeaderUsername().setText(name);
    }

    public VBox getChatBox() {
        return controller.getChatBox();
    }

    public ChatItemController getController() {
        return controller;
    }

}
