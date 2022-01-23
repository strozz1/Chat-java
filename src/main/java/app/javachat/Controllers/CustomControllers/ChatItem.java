package app.javachat.Controllers.CustomControllers;

import app.javachat.Chats.Chat;
import app.javachat.MainApplication;
import app.javachat.Chats.SimpleChat;
import app.javachat.Models.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ChatItem extends BorderPane {
    private ChatItemController controller;

    public ChatItem() {
        super();

        controller = new ChatItemController();
        load(controller);
    }

    public ChatItem(SimpleChat chat, User otherUser) {
        super();
        controller = new ChatItemController(chat, otherUser);
        load(controller);
        controller.startListeningForMessages();
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

    public Chat getChat() {
        return controller.getChat();
    }
}
