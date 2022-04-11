package app.javachat.Controllers.CustomControllers;

import app.javachat.MainApplication;
import app.javachat.Models.Room;
import app.javachat.Models.User;
import app.javachat.SimpleRoom;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

public class ChatItem extends BorderPane implements Serializable {
    private User otherUser;
    private ChatItemController controller;

    public ChatItem() {
        super();

        controller = new ChatItemController();
        load(controller);
    }

    public ChatItem(Room chat) {
        super();
        controller = new ChatItemController(chat);
        load(controller);
    }

    private void load(ChatItemController controller) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("chat-item.fxml"));
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


    public void addMessage(HashMap<String, Object> msg) {
        controller.addMessage(msg);
    }
}
